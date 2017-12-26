package com.example.user.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by zahrsino on 21/12/17.
 */

public class ArticlesFavoriteTabActivityAdapter extends RecyclerView.Adapter<ArticlesFavoriteTabActivityAdapter.ViewHolder> {

    private MyFunctions myFunctions = new MyFunctions();
    private Context context;
    private ArrayList<NewsArticle> newsArticleArrayList;

    public ArticlesFavoriteTabActivityAdapter(Context ctx, ArrayList<NewsArticle> myDataSet) {
        context = ctx;
        newsArticleArrayList = myDataSet;
    }

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
            int idCliccato = getAdapterPosition();
            goToArticleDetailsActivity(idCliccato);
        }

        public void goToArticleDetailsActivity(int articleId) {
            Intent intent = new Intent(context, ArticlesDetailsActivity.class);
            String newsArticleId = newsArticleArrayList.get(articleId).getUuid();
            intent.putExtra("Id_Article_selected", newsArticleId);
            context.startActivity(intent);
        }
    }

    @Override
    public ArticlesFavoriteTabActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_articles, parent,false);
        return new ArticlesFavoriteTabActivityAdapter.ViewHolder(cardItem);
    }

    @Override
    public void onBindViewHolder(ArticlesFavoriteTabActivityAdapter.ViewHolder holder, int position) {
        Picasso.with(context).load(newsArticleArrayList.get(position).getUrlToImage()).into(holder.articleImage);
        holder.articleTitle.setText(newsArticleArrayList.get(position).getTitle());
        holder.articleAuthor.setText(newsArticleArrayList.get(position).getAuthor());
        holder.articlePublished.setText(myFunctions.parseArticleDate(newsArticleArrayList.get(position).getPublishedAt()));
    }

    @Override
    public int getItemCount() {
        return newsArticleArrayList.size();
    }
}
