package com.au.cit.handbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.au.cit.handbook.databinding.ActivityMainBinding;
import com.au.cit.handbook.singleton.SharedPrefsManager;
import com.au.cit.handbook.singleton.Volley;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final String WEB_CLIENT_ID =
            "898857046786-ad6765i2qhcltsg0sku7ti5t0rqb44pg.apps.googleusercontent.com";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        initDrawerLayout();
        initFacebookSdk();


        final int[] code = new int[1];

        SharedPreferences sharedPreferences = SharedPrefsManager.getInstance(this)
                .sharedPreferences();

        String url = getString(R.string.BASE_URL)
                + '?'
                + "ip_address=" + sharedPreferences.getString("ip_address", "")
                + '&'
                + "user_token=" + sharedPreferences.getString("user_token", "");
        Log.i("internet_URL", url);

        @SuppressLint("ResourceType") JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null,
                        (response) -> {
                            if (code[0] == Integer.parseInt(getString(R.integer.ok))) {
                                try {
                                    JSONObject user = response.getJSONObject("user");

                                    View navigationHeader = navigationView.getHeaderView(0);
                                    ((TextView) navigationHeader.findViewById(R.id.nav_header_email))
                                            .setText(user.getString("email"));
                                    ((TextView) navigationHeader.findViewById(R.id.nav_header_name))
                                            .setText(user.getString("display_name"));
                                } catch (JSONException e) {

                                    e.printStackTrace();

                                    showSnackbar(getString(R.string.server_error));
                                }
                            }
                        },
                        (error) -> {
                            Log.e("internet_ERROR", Arrays.toString(error.getStackTrace()));
                        }) {

                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                        code[0] = response.statusCode;

                        return super.parseNetworkResponse(response);
                    }
                };

        RequestQueue requestQueue = Volley.getInstance(getApplicationContext())
                .getRequestQueue();
        requestQueue.add(jsonObjectRequest);


        Menu navigationMenu = navigationView.getMenu();
        navigationMenu.findItem(R.id.nav_logout).setOnMenuItemClickListener(item -> {

            SharedPreferences.Editor editor =
                    SharedPrefsManager.getInstance(this).editor();
            editor.remove("ip_address")
                    .remove("session_token")
                    .remove("user_token")
                    .apply();

            finish();
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);

            return true;
        });

        checkForIntentExtras();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(
                this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void initGoogleLoginServices() {
        /*        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(WEB_CLIENT_ID)
                .requestProfile()
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);

        View navigationHeader = navigationView.getHeaderView(0);
        ImageView navHeaderProfile = navigationView.findViewById(R.id.nav_header_profile);
        if (account.getPhotoUrl() != null) {
            navHeaderProfile.setImageURI(account.getPhotoUrl().normalizeScheme());
        }
        TextView navHeaderEmail = navigationHeader.findViewById(R.id.nav_header_email);
        if (account.getEmail() != null) {
            navHeaderEmail.setText(account.getEmail());
        }
        TextView navHeaderName = navigationHeader.findViewById(R.id.nav_header_name);
        if (account.getDisplayName() != null) {
            navHeaderName.setText(account.getDisplayName());
        }

        Menu navigationMenu = navigationView.getMenu();
        navigationMenu.findItem(R.id.nav_logout).setOnMenuItemClickListener(item -> {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, (task) -> {
                        finish();
                        Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, LoginActivity.class);
                        startActivity(i);
                    });
            return true;
        });*/
    }

    private void initDrawerLayout() {

        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_services, R.id.nav_uniform,
                R.id.nav_map, R.id.nav_scholarship, R.id.nav_event,
                R.id.nav_studentManual, R.id.nav_faq, R.id.nav_about,
                R.id.nav_phinmaEducation, R.id.nav_developers, R.id.nav_contact,
                R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(
                this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(
                this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void initFacebookSdk() {
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(getApplication());
    }

    private void checkForIntentExtras() {
        Intent i = getIntent();
        if (i.hasExtra("body")) {
            showSnackbar(i.getStringExtra("body"));
        }
    }

    private void showSnackbar(CharSequence text) {
        Snackbar.make(binding.drawerLayout, text, Snackbar.LENGTH_LONG).show();
    }
}