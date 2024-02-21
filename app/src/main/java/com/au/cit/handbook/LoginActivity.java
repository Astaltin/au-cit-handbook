package com.au.cit.handbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.au.cit.handbook.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    EditText _email, _password;
    Button _login_btn;
    Context ctx;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ctx = this;
        _email = (EditText) findViewById(R.id._email);
        _password = (EditText) findViewById(R.id._password);
        _login_btn = (Button) findViewById(R.id._login_btn);
        _login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _email_ = String.valueOf(_email.getText());
                String _password_ = String.valueOf(_password.getText());
                Toast.makeText(ctx, _email_ + " " + _password_, Toast.LENGTH_LONG).show();
                if (_email_.equals("admin") && _password_.equals("password")) {
                    Intent i = new Intent(ctx, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    public void register(View v) {
        Intent i = new Intent(ctx, RegisterActivity.class);
        startActivity(i);
    }
}
