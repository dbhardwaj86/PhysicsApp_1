package com.example.deepak.physicsapp_1;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class MessageAdapter extends FirebaseRecyclerAdapter<MessageModel, MessageHolder> {
    private static final String TAG = MessageAdapter.class.getSimpleName();
    private Context context;
    public MessageAdapter(Class<MessageModel> modelClass, int modelLayout, Class<MessageHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }
    @Override
    protected void populateViewHolder(MessageHolder viewHolder, MessageModel model, int position) {
        viewHolder.messageText.setText(model.getContent());
        Glide.with(context).load(model.getImageUrl()).into(viewHolder.messageImage);


    }
}
