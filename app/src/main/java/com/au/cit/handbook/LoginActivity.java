package com.au.cit.handbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.au.cit.handbook.databinding.ActivityLoginBinding;
import com.au.cit.handbook.singleton.SharedPrefsManager;
import com.au.cit.handbook.singleton.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private final int RC_SIGN_IN = 9001;

    private ActivityLoginBinding binding;

    private GoogleSignInClient mGoogleSignInClient;

    private boolean setBackButtonEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBackButtonEnabled = true;

        initActionBar();
        initEventListeners();
        initGoogleLoginServices();

        checkForIntentExtras();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    /*
     * For some reason, the IDE is detecting this method of AppCompatActivity
     * as deprecated. As of 2022, stated by ChatGPT, that is not the case.
     * In fact, it is still the standard method for programming the on back
     * pressed.
     *
     * SO BASICALLY, JUST IGNORE THE DEPRECATION WARNING. ;-;
     *
     * - Edward Gulmayo
     */
    public void onBackPressed() {
        if (setBackButtonEnabled) {
            finish();
            Intent i = new Intent(
                    this, AuthenticationActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }

    private void initActionBar() {
        setSupportActionBar(binding.actionBar.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        binding.actionBar.toolbar.setNavigationOnClickListener(v -> {
            if (setBackButtonEnabled) {
                finish();
                Intent i = new Intent(
                        this, AuthenticationActivity.class);
                startActivity(i);
            }
        });
    }

    private void initEventListeners() {
        binding.buttonLogin.setOnClickListener(this::handleButtonLogin);
    }

    private void handleButtonLogin(View v) {
        setEventsEnabled(false);

        String email = binding.editTextEmail.getText().toString().trim();
        String password = binding.editTextPassword.getText().toString().trim();

        login(email, password);
    }

    private void login(String email, String password) {

        final int[] code = new int[1];

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("email", email);
            jsonRequest.put("password", password);

        } catch (JSONException e) {

            e.printStackTrace();

            showSnackbar(getString(R.string.server_error));

            setEventsEnabled(true);

            return;
        }

        String url = getString(R.string.AUTH_LOGIN);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                        (response) -> handleResponseSuccess(response, code[0]),
                        this::handleResponseError) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", getString(R.string.json));

                        return headers;
                    }

                    @Override
                    protected Response<JSONObject> parseNetworkResponse(
                            NetworkResponse response) {

                        code[0] = response.statusCode;

                        return super.parseNetworkResponse(response);
                    }
                };

        RequestQueue requestQueue = Volley.getInstance(getApplicationContext())
                .getRequestQueue();
        requestQueue.add(jsonObjectRequest);
    }

    @SuppressLint("ResourceType")
    private void handleResponseSuccess(JSONObject response, int code) {
        try {
            if (code == Integer.parseInt(getString(R.integer.ok))) {

                JSONObject session = response.getJSONObject("session");

                SharedPreferences.Editor editor =
                        SharedPrefsManager.getInstance(this).editor();
                editor.putString("ip_address", session.getString("ip_address"))
                        .putString("session_token", session.getString("session_token"))
                        .putString("user_token", session.getString("user_token"))
                        .apply();

                finish();
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("authenticator", "local");
                i.putExtra("body", response.getString("body"));
                startActivity(i);
            }
        } catch (JSONException e) {

            e.printStackTrace();

            showSnackbar(getString(R.string.server_error));

            setEventsEnabled(true);
        }
    }

    @SuppressLint("ResourceType")
    private void handleResponseError(VolleyError error) {

        if (error.networkResponse == null) {
            return;
        }

        String networkResponseError =
                new String(error.networkResponse.data, StandardCharsets.UTF_8);
        try {
            if (error.networkResponse.statusCode
                    == Integer.parseInt(getString(R.integer.invalid_data))) {

                failValidationError(networkResponseError);
            }
            if (error.networkResponse.statusCode
                    == Integer.parseInt(getString(R.integer.unauthorized))) {

                failUnauthorized(networkResponseError);
            }

            setEventsEnabled(true);

        } catch (JSONException e) {

            e.printStackTrace();

            showSnackbar(getString(R.string.server_error));

            setEventsEnabled(true);
        }
    }

    private void failValidationError(String networkResponseError) throws JSONException {

        JSONObject loginValidationError =
                new JSONObject(networkResponseError)
                        .getJSONObject("loginValidationError");

        setErrorIfPresent(loginValidationError, "email",
                binding.editTextEmail);
        setErrorIfPresent(loginValidationError, "password",
                binding.editTextPassword);
    }

    private void failUnauthorized(String networkResponseError) throws JSONException {

        JSONObject body = new JSONObject(networkResponseError);
        showSnackbar(body.getString("body"));
    }

    private void initGoogleLoginServices() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.WEB_CLIENT_ID))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.buttonGoogleLogin.setOnClickListener(this::buttonGoogleLoginHandler);
    }

    private void buttonGoogleLoginHandler(View v) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleLoginResult(task);
        }
    }

    private void handleLoginResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            if (account != null) {
                finish();
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("authenticator", "google");
                i.putExtra("body", "Logged in");
                startActivity(i);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkForIntentExtras() {

        Intent i = getIntent();

        binding.editTextEmail.setText(i.getStringExtra("email"));

        if (i.hasExtra("body")) {
            Snackbar.make(
                    binding.container,
                    i.getStringExtra("body"),
                    Snackbar.LENGTH_SHORT
            ).show();
        }
    }

    private void setErrorIfPresent(
            JSONObject registerValidationError,
            String fieldName,
            EditText editText) throws JSONException {

        if (registerValidationError.has(fieldName)) {
            editText.setError(registerValidationError.getString(fieldName));
            return;
        }
        editText.setError(null);
    }

    private void setEventsEnabled(boolean bool) {
        if (bool) {
            getSupportActionBar().setIcon(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setIcon(R.drawable.ic_navigation_back_outlined_24);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        setBackButtonEnabled = bool;

        binding.editTextEmail.setEnabled(bool);
        binding.editTextPassword.setEnabled(bool);

        binding.buttonLogin.setEnabled(bool);
        binding.buttonGoogleLogin.setEnabled(bool);
    }

    private void showSnackbar(CharSequence text) {
        Snackbar.make(binding.container, text, Snackbar.LENGTH_LONG).show();
    }
}
