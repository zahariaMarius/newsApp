package com.example.user.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 23/11/2017.
 */

public class ArticlesTabActivityAdapter extends RecyclerView.Adapter<ArticlesTabActivityAdapter.ViewHolder> {

    MyFunctions myFunctions = new MyFunctions();
    private ArrayList<NewsArticle> newsArticleArrayList;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArticlesTabActivityAdapter(Context ctx, ArrayList<NewsArticle> myDataset) {
        context = ctx;
        newsArticleArrayList = myDataset;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //create all items into card
        ImageView articleImage;
        TextView articleAuthor;
        TextView articleTitle;
        TextView articlePublished;
        ViewHolder(View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.articleImage);
            articleAuthor = itemView.findViewById(R.id.articleAuthor);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            articlePublished = itemView.findViewById(R.id.articlePublishedAt);
            //set click listener to go on news view activity
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Date currentTime = Calendar.getInstance().getTime();
            int idCliccato = getAdapterPosition();
            Log.d("data: ", "onCreateView: " + currentTime);
            Log.d("cliccato", "onClick: beella" + getAdapterPosition());
            //Log.d("oggetto cliccato", String.valueOf(newsArticleArrayList.get(idCliccato).getTitle()));
            goToArticleDetailsActivity(idCliccato);
        }

        public void goToArticleDetailsActivity(int articleId) {
            Intent intent = new Intent(context, ArticlesDetailsActivity.class);
            NewsArticle newsArticle = newsArticleArrayList.get(articleId);
            intent.putExtra("Article_selected", newsArticle);
            context.startActivity(intent);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticlesTabActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View cardItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_articles, parent,false);
        return new ViewHolder(cardItem);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Picasso.with(context).load(newsArticleArrayList.get(position).getUrlToImage()).into(holder.articleImage);
        holder.articleTitle.setText(newsArticleArrayList.get(position).getTitle());
        holder.articleAuthor.setText(newsArticleArrayList.get(position).getAuthor());
        holder.articlePublished.setText(myFunctions.parseArticleDate(newsArticleArrayList.get(position).getPublishedAt()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return  newsArticleArrayList.size();
    }
}