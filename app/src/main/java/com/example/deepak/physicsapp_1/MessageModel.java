package com.example.deepak.physicsapp_1;

import java.io.File;

/**
 * Created by deepak on 19-01-2018.
 */

public class MessageModel {
    private String content;

    private String imageUrl;

    public MessageModel() {
    }

    public MessageModel(String content, String imageUrl) {
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
