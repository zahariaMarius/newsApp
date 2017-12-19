package com.example.user.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by user on 23/11/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    MyFunctions myFunctions = new MyFunctions();
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
            //Log.d("oggetto cliccato", String.valueOf(newsArticleDataArrayList.get(idCliccato).getTitle()));
            goToArticleDetailsActivity(idCliccato);
        }

        public void goToArticleDetailsActivity(int articleId) {
            Intent intent = new Intent(context, StoriesDetailsActivity.class);
            NewsArticleData newsArticleData = newsArticleDataArrayList.get(articleId);
            intent.putExtra("Article_selected", newsArticleData);
            context.startActivity(intent);
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
        holder.articlePublished.setText(myFunctions.parseArticleDate(newsArticleDataArrayList.get(position).getPublishedAt()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return  newsArticleDataArrayList.size();
    }
}