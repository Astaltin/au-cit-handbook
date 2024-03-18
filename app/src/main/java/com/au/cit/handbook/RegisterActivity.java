package com.au.cit.handbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.au.cit.handbook.databinding.ActivityRegisterBinding;
import com.au.cit.handbook.singleton.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("ResourceType")
public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private boolean setBackButtonEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBackButtonEnabled = true;

        initActionBar();
        initEventListeners();
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
        binding.buttonRegister.setOnClickListener(this::buttonRegisterHandler);
    }

    private void buttonRegisterHandler(View v) {
        setEventsEnabled(false);

        String givenName = binding.editTextGivenName.getText().toString().trim();
        String familyName = binding.editTextFamilyName.getText().toString().trim();
        String email = binding.editTextEmail.getText().toString().trim();
        String password = binding.editTextPassword.getText().toString().trim();
        String passwordConfirm = binding.editTextPasswordConfirm.getText().toString().trim();

        signup(givenName, familyName, email, password, passwordConfirm);
    }

    private void signup(
            String givenName, String familyName,
            String email, String password, String passwordConfirm) {

        final int[] code = new int[1];

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("givenName", givenName);
            jsonRequest.put("familyName", familyName);
            jsonRequest.put("email", email);
            jsonRequest.put("password", password);
            jsonRequest.put("passwordConfirm", passwordConfirm);

        } catch (JSONException e) {

            e.printStackTrace();

            showSnackbar(getString(R.string.server_error));

            setEventsEnabled(true);

            return;
        }

        String url = getString(R.string.AUTH_REGISTER);
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
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        code[0] = response.statusCode;

                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected VolleyError parseNetworkError(VolleyError error) {
                        code[0] = error.networkResponse.statusCode;

                        return super.parseNetworkError(error);
                    }
                };

        RequestQueue requestQueue = Volley.getInstance(getApplicationContext())
                .getRequestQueue();
        requestQueue.add(jsonObjectRequest);
    }

    private void handleResponseSuccess(JSONObject response, int code) {
        try {

            if (code == Integer.parseInt(getString(R.integer.created))) {
                finish();
                Intent i = new Intent(this, LoginActivity.class);
                i.putExtra("email", binding.editTextEmail.getText().toString().trim());
                i.putExtra("body", response.getString("body"));
                startActivity(i);
            }

        } catch (JSONException e) {
            e.printStackTrace();

            showSnackbar(getString(R.string.server_error));

            setEventsEnabled(true);
        }
    }

    private void handleResponseError(VolleyError error) {

        if (error.networkResponse == null) {
            return;
        }

        String networkResponseError =
                new String(error.networkResponse.data, StandardCharsets.UTF_8);
        try {
            if (error.networkResponse.statusCode == Integer.parseInt(getString(R.integer.invalid_data))) {

                failValidationError(networkResponseError);
            }
            if (error.networkResponse.statusCode == Integer.parseInt(getString(R.integer.server_error))) {

                failServerError(networkResponseError);
            }

            setEventsEnabled(true);

        } catch (JSONException e) {
            e.printStackTrace();

            showSnackbar(getString(R.string.server_error));

            setEventsEnabled(true);
        }
    }

    private void failValidationError(String networkResponseError) throws JSONException {

        JSONObject registerValidationError =
                new JSONObject(networkResponseError)
                        .getJSONObject("registerValidationError");

        setErrorIfPresent(
                registerValidationError, "givenName",
                binding.editTextGivenName);
        setErrorIfPresent(
                registerValidationError, "familyName",
                binding.editTextFamilyName);
        setErrorIfPresent(
                registerValidationError, "email",
                binding.editTextEmail);
        setErrorIfPresent(
                registerValidationError, "password",
                binding.editTextPassword);
        setErrorIfPresent(
                registerValidationError, "passwordConfirm",
                binding.editTextPasswordConfirm);
    }

    private void failServerError(String networkResponseError) throws JSONException {

        JSONObject body = new JSONObject(networkResponseError);
        showSnackbar(body.getString("body"));
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

        binding.editTextGivenName.setEnabled(bool);
        binding.editTextFamilyName.setEnabled(bool);
        binding.editTextEmail.setEnabled(bool);
        binding.editTextPassword.setEnabled(bool);
        binding.editTextPasswordConfirm.setEnabled(bool);

        binding.buttonRegister.setEnabled(bool);
    }

    private void showSnackbar(CharSequence text) {
        Snackbar.make(binding.container, text, Snackbar.LENGTH_LONG).show();
    }
}