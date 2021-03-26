package com.example.njlchat.model;

import java.time.LocalDateTime;

/***
 * a post is a message containing a text. 
 * It has an author which is a user of the application.
 * It can be uploaded to the application and will keep the date of creation in memory.
 */
public class Post {
    User author;
    String createdDate;
    String content;

    public Post(User author, String createdDate, String content) {
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
    }

    public Post(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
