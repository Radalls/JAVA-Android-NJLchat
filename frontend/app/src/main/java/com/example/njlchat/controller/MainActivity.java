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

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.njlchat.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * activité permettant de voir les posts de la communauté
 */
public class MainActivity extends AppCompatActivity {

    private MainActivity activity = this;
    private ListView listView;
    private List<Post> posts;
    private FloatingActionButton fab;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Pouvoir trier les dates par ordre croissant
        // Le post le plus récent en haut
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Fetch controls
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.listView = findViewById(R.id.timeline);
        this.fab = findViewById(R.id.fab);
        this.fab2 = findViewById(R.id.fab2);
        this.fab3 = findViewById(R.id.fab3);
        setSupportActionBar(toolbar);
    }

    /***
     * récupération des messages par ordre de parution et par utilisateur
     */
    private void fetchPosts() {
        NLJchatAPI api = HttpClient.getInstance().getClient().create(NLJchatAPI.class);
        Call<List<Post>> apiCall = api.getPosts(HttpClient.getInstance().getBearerString());
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
    
    private void loadData(List<Post> Posts) {
        PostsAdapter adapter = new PostsAdapter(this,Posts);
        this.listView.setAdapter(adapter);
    }

    /***
     * actualisation des posts
     */
    @Override
    protected void onResume() {
        super.onResume();
        fetchPosts();
    }

    /***
     * chargement de l'activité nouveau message suite au clic du bouton Add
     * @param v
     */
    public void handleClickAddButton(View v){
        Intent i = new Intent(getApplicationContext(), AddPostActivity.class);
        startActivity(i);
    }

    /***
     * démarrage de l'activité compte utilisateur suite au clic du bouton Profile
     * @param v
     */
    public void handleClickProfileButton(View v){
        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(i);
    }

    /***
     * le rafraichissement provoque une récupération de donnée
     * @param v : la view a update
     */
    public void handleClickRefreshButton(View v){
        fetchPosts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
