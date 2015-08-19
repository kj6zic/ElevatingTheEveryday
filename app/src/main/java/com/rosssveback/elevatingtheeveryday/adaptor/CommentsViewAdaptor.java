package com.rosssveback.elevatingtheeveryday.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rosssveback.elevatingtheeveryday.model.Comments;

import java.util.ArrayList;
import java.util.List;

import me.declangao.wordpressreader.R;

/**
 * Created by ankit on 8/18/15.
 */
public class CommentsViewAdaptor extends RecyclerView.Adapter<CommentsViewAdaptor.ViewHolder>{
    private List<Comments> comments;
    private Context mContext;


    public CommentsViewAdaptor(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_item, viewGroup, false);
        mContext = viewGroup.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .load(comments.get(i).getContent())
                .centerCrop()
                .into(viewHolder.thumbnailImageView);

        viewHolder.title.setText(comments.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnailImageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
        }

    }

}
