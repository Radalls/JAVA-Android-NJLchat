package com.example.njlchat.controller;

import android.app.Activity;
import android.os.Bundle;

import com.example.njlchat.model.Post;
import com.example.njlchat.service.HttpClient;
import com.example.njlchat.service.NLJchatAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.njlchat.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * activité permettant à un utilisateur connecté de créer et uploader un nouveau post
 */
public class AddPostActivity extends AppCompatActivity {
    private AddPostActivity activity = this;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Fetch controls
        this.content = findViewById(R.id.newContent);
    }

    /***
     * permet de tenter l'enregistrement du post dans la BDD
     * @param v
     */
    public void handleClickAddButton(View v){
        final Post Post = new Post(this.content.getText().toString());

        NLJchatAPI api = HttpClient.getInstance().getClient().create(NLJchatAPI.class);
        Call<Post> apiCall = api.createPost(HttpClient.getInstance().getBearerString(), Post);
        apiCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                finish();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(activity.getApplicationContext(),"Add Post failed",Toast.LENGTH_SHORT).show();
            }
        });
        finish();
    }
}
