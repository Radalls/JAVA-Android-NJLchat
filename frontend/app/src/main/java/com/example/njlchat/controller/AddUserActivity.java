package com.example.njlchat.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.njlchat.R;
import com.example.njlchat.model.User;
import com.example.njlchat.service.HttpClient;
import com.example.njlchat.service.NLJchatAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * activite permettant l'inscription d'un nouvel utilisateur sur l'application
 */
public class AddUserActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button signUpButton;
    private CheckBox termsOfUse;
    private AddUserActivity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fetch controls
        this.username = findViewById(R.id.newusername);
        this.password = findViewById(R.id.newpassword);
        this.termsOfUse = findViewById(R.id.checkBox);
        this.signUpButton = findViewById(R.id.signupbutton);
        this.signUpButton.setEnabled(false);
    }

    /***
     * vérifie si termsOfUse est coché pour pouvoir valider l'inscription
     * @param v
     */
    public void handleCheckTermsOfUse(View v) {
        if (termsOfUse.isChecked()) {
            signUpButton.setEnabled(true);
        }
    }

    /***
     * vérifie que l'utilisateur a bien rentré un login, un mot de passe (et confirmation identique) et a coché termsOfUse
     * @param v
     */
    public void handleClickSignupButton(View v) {
        EditText confpassword = findViewById(R.id.confirmpassword);
        if (username.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this.getApplicationContext(), "enter a username and a password", Toast.LENGTH_LONG).show();
        } else if (!password.getText().toString().equals(confpassword.getText().toString())) {
            Toast.makeText(this.getApplicationContext(), "Passwords can't be different", Toast.LENGTH_LONG).show();
        } else if (!termsOfUse.isChecked()) {
            Toast.makeText(this.getApplicationContext(), "you have to accept the terms of use", Toast.LENGTH_LONG).show();
        } else {

            User newUser = new User(username.getText().toString(), password.getText().toString());
            NLJchatAPI api = HttpClient.getInstance().getClient().create(NLJchatAPI.class);
            Call<Void> apiCall = api.createUser(newUser);
            apiCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(activity.getApplicationContext(),"user creation failure", Toast.LENGTH_SHORT).show();
                }
            });
            finish();
        }
    }
}
