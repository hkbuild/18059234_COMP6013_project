package com.hkbuild.FitApp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText regEmail, regUsername, regPassword, regPassword2;
    private Button Continue, back;
    boolean validEmil = false;
    boolean validPass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regEmail = findViewById(R.id.regEmail);
        regUsername = findViewById(R.id.regUsername);
        regPassword = findViewById(R.id.regPassword);
        regPassword2 = findViewById(R.id.regPassword2);
        Continue = findViewById(R.id.Continue);
        back = findViewById(R.id.backButton);

        Continue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String inputEmail = regEmail.getText().toString();
                String inputUser = regUsername.getText().toString();
                String inputPassword = regPassword.getText().toString();
                String inputPassword2 = regPassword2.getText().toString();

                if (inputEmail.isEmpty() || inputUser.isEmpty() || inputPassword.isEmpty()
                        || inputPassword2.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter details.", Toast.LENGTH_SHORT).show();
                } else {
                    validEmil = checkEmail(inputEmail);
                    if (!validEmil) {
                        Toast.makeText(RegisterActivity.this, "Please use a valid email.", Toast.LENGTH_SHORT).show();
                    } else {
                        validPass = validate(inputPassword, inputPassword2);
                        if (!validPass) {
                            Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this, PaywallActivity.class);
                            RegisterActivity.this.startActivity(i);
                        }
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(i);
            }
        });
    }


    private boolean validate(String password, String password2) {
        if
        (password.equals(password2)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            return true;
        } else {
            return false;
        }
    }
}

