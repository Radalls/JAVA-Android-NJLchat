package com.example.njlchat.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
 * activité permettant a un utilisateur inscrit de se connecter à l'application
 */
public class LoginActivity extends AppCompatActivity {

    private Activity activity = this;

    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fetch controls
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
    }

    /***
     * recherche un couple login / mot de passe avec le contenu du formulaire et tente une connexion si l'utilisateur est trouvé dans la BDD
     * @param v
     */
    public void handleClickLoginButton(View v){
        User u = new User(login.getText().toString(), password.getText().toString());
        NLJchatAPI api = HttpClient.getInstance().getClient().create(NLJchatAPI.class);
        Call<Void> apiCall = api.login(u);
        apiCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                String bearerString = response.headers().get("Authorization");
                if(bearerString == null || bearerString.isEmpty()){
                    Toast.makeText(activity.getApplicationContext(),"Wrong credentials",Toast.LENGTH_SHORT).show();
                }
                else {
                    HttpClient.getInstance().setBearerString(bearerString);
                    Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
                    activity.startActivity(i);
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(activity.getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /***
     * charge l'activité d'insciption pour créer un nouvel utilisateur dans la BDD
     * @param v
     */
    public void handleClickSignUpButton(View v){
        Intent i = new Intent(getApplicationContext(), AddUserActivity.class);
        activity.startActivity(i);
    }
}
