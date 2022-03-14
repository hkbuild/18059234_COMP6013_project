package com.hkbuild.FitApp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private Button login;
    private Button register;
    boolean isValid = false;

    String adminUsername = "Tes";
    String adminPassword = "tes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login =  findViewById(R.id.login);
        register =  findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String inputUser = user.getText().toString();
                String inputPassword = password.getText().toString();

                if (inputUser.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter details.", Toast.LENGTH_SHORT).show();
                }
                else{
                    isValid = validate(inputUser, inputPassword);
                    if (!isValid) {
                        Toast.makeText(MainActivity.this, "Incorrect user/Pass.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Logged in.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        MainActivity.this.startActivity(i);
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(i);
            }
        });
    }

    private boolean validate(String user, String password){
        if ((user.equalsIgnoreCase(adminUsername) && password.equals(adminPassword))
        ){
            return true;
        }
        else {
            return false;
        }
    }
}
