package com.example.njlchat.controller;

import android.content.Intent;
import android.os.Bundle;

import com.example.njlchat.model.Post;
import com.example.njlchat.service.HttpClient;
import com.example.njlchat.service.NLJchatAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.njlchat.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * activité permettant à un utilisateur connecté de visualiser ses posts sur l'application
 */
public class ProfileActivity extends AppCompatActivity {
    private ProfileActivity activity = this;
    private ListView listView;
    private List<Post> posts;
    private FloatingActionButton fab;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.listView = findViewById(R.id.timeline);
        this.fab = findViewById(R.id.fab);
        this.fab2 = findViewById(R.id.fab2);
        this.fab3 = findViewById(R.id.fab3);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPosts();
    }

    private void loadData(List<Post> Posts) {
        PostsAdapter adapter = new PostsAdapter(this,Posts);
        this.listView.setAdapter(adapter);
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /***
     * appel a à la BDD pour récupération des posts de l'utilisateur
     */
    private void fetchPosts() {
        NLJchatAPI api = HttpClient.getInstance().getClient().create(NLJchatAPI.class);
        Call<List<Post>> apiCall = api.getMyPosts(HttpClient.getInstance().getBearerString());
        apiCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                loadData(response.body());
                activity.setPosts(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(activity.getApplicationContext(),"Fetch Posts failed : "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    /***
     * redirige vers la création de post
     * @param v
     */
    public void handleClickAddButton(View v){
        Intent i = new Intent(getApplicationContext(), AddPostActivity.class);
        startActivity(i);
    }

    /***
     * redirige vers la page d'accueil
     * @param v
     */
    public void handleClickHomeButton(View v){
        finish();
    }

    /***
     * update les posts
     * @param v
     */
    public void handleClickRefreshButton(View v){
        fetchPosts();
    }
}
