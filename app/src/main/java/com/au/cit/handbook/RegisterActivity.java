package com.au.cit.handbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.au.cit.handbook.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    EditText _email, _password, _name;
    Button _register;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_register);

        ctx = this;
        _email = (EditText) findViewById(R.id._email);
        _password = (EditText) findViewById(R.id._password);
        _name = (EditText) findViewById(R.id._name);
        _register = (Button) findViewById(R.id._register_btn);
        _register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _email_ = String.valueOf(_email.getText());
                String _password_ = String.valueOf(_password.getText());
                String _name_ = String.valueOf(_name.getText());
                Toast.makeText(ctx, _name_ + " " + _email_ + " " + _password_, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void login(View v) {
        Intent i = new Intent(ctx, LoginActivity.class);
        startActivity(i);
    }
}