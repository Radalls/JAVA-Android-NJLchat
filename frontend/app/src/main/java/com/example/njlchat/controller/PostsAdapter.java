package com.example.njlchat.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.njlchat.R;
import com.example.njlchat.model.Post;

import java.util.List;

/***
 * application permettant de généré l'affichage des posts de l'application en colonne et par ordre de création
 */
public class PostsAdapter extends ArrayAdapter<Post> {

    private Context localContext;
    private List<Post> posts;

    public PostsAdapter(@NonNull Context context, List<Post> Posts) {
        super(context, 0, Posts);
        this.localContext = context;
        this.posts = Posts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Create the item's View
        if(convertView == null){
            convertView = LayoutInflater.from(this.localContext)
                    .inflate(R.layout.listview_item_layout, parent, false);
        }

        // Get the current item data
        final Post Post = this.posts.get(position);

        // Fetching view controls
        final TextView author = convertView.findViewById(R.id.author);
        final TextView date = convertView.findViewById(R.id.date);
        final TextView content = convertView.findViewById(R.id.content);

        // Filling the view with data
        author.setText(Post.getAuthor().getUsername());
        date.setText(Post.getCreatedDate());
        content.setText(Post.getContent());

        return convertView;
    }
}
