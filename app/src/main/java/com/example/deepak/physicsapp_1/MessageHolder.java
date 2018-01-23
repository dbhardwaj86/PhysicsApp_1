package com.example.deepak.physicsapp_1;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageHolder extends RecyclerView.ViewHolder{
    private static final String TAG = MessageHolder.class.getSimpleName();
    public TextView messageText;
    public ImageView messageImage;
    public MessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView)itemView.findViewById(R.id.messagetext);
        messageImage = (ImageView)itemView.findViewById(R.id.msgimage);
    }
}
