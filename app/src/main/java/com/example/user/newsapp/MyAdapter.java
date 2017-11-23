package com.example.user.newsapp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 23/11/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<JSONObject> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout constraintLayout;
        public ViewHolder(ConstraintLayout c) {
            super(c);
            constraintLayout = c;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<JSONObject> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_stories, parent,false);
        TextView articleDescription = constraintLayout.findViewById(R.id.articleDescription);

        ViewHolder vh = new ViewHolder(constraintLayout);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView articleTitle = holder.constraintLayout.findViewById(R.id.articleTitle);
        TextView articleAuthor = holder.constraintLayout.findViewById(R.id.articleDescription);
        TextView articleDescription = holder.constraintLayout.findViewById(R.id.articleDescription);

        for (int i = 0; i < mDataset.size() ; i++) {
            JSONObject article = mDataset.get(position);
            try {
                articleAuthor.setText(article.getString("author"));
                articleTitle.setText(article.getString("title"));
                articleDescription.setText(article.getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}