package com.example.user.newsapp;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 23/11/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<NewsArticleData> newsArticleDataArrayList;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context ctx, ArrayList<NewsArticleData> myDataset) {
        context = ctx;
        newsArticleDataArrayList = myDataset;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        //create all items into card
        public ImageView articleImage;
        public TextView articleAuthor;
        public TextView articleTitle;
        public TextView articleDescription;
        public ViewHolder(View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.articleImage);
            articleAuthor = itemView.findViewById(R.id.articleAuthor);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            articleDescription = itemView.findViewById(R.id.articleDescription);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View cardItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_stories, parent,false);
        return new ViewHolder(cardItem);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Picasso.with(context).load(newsArticleDataArrayList.get(position).getUrlToImage()).into(holder.articleImage);
        holder.articleTitle.setText(newsArticleDataArrayList.get(position).getTitle());
        holder.articleAuthor.setText(newsArticleDataArrayList.get(position).getAuthor());
        holder.articleDescription.setText(newsArticleDataArrayList.get(position).getDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newsArticleDataArrayList.size();
    }
}