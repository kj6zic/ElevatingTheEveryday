package com.rosssveback.elevatingtheeveryday.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rosssveback.elevatingtheeveryday.model.Comments;
import com.rosssveback.elevatingtheeveryday.util.OvalImageview;

import java.util.ArrayList;
import java.util.List;

import me.declangao.wordpressreader.R;

/**
 * Created by ankit on 8/18/15.
 */
public class CommentsViewAdaptor extends RecyclerView.Adapter<CommentsViewAdaptor.ViewHolder>{
    private List<Comments> comments;
    private Context mContext;
    private OvalImageview nameAlphaCaps;

    public CommentsViewAdaptor(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comment_view_item, viewGroup, false);
        mContext = viewGroup.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .load(comments.get(i).getContent())
                .centerCrop()
                .into(viewHolder.nameAlphaCaps);

        viewHolder.nameAlphaCaps.setRadius(10);
        viewHolder.name.setText(comments.get(i).getName());

        String commentText = comments.get(i).getContent();
        viewHolder.comment.setText(commentText);

        String commentDate = comments.get(i).getDate();
        viewHolder.date.setText(commentDate);

        String commenterName = comments.get(i).getName();
        viewHolder.name.setText(commenterName);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        OvalImageview nameAlphaCaps;
        TextView name;
        TextView comment;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            nameAlphaCaps = (OvalImageview) itemView.findViewById(R.id.nameAlphaCaps);
            name = (TextView) itemView.findViewById(R.id.name);
            comment = (TextView) itemView.findViewById(R.id.comment);
            date = (TextView) itemView.findViewById(R.id.date);

        }

    }

}
