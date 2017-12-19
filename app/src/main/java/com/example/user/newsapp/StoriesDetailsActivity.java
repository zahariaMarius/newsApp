package com.example.user.newsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import io.realm.Realm;

public class StoriesDetailsActivity extends AppCompatActivity {

    MyFunctions myFunctions = new MyFunctions();
    Realm realm;
    TextView title, publishedAt, description, author, dbNews;
    ImageView imageView;
    NewsArticleData articleSelected;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        articleSelected = (NewsArticleData) intent.getSerializableExtra("Article_selected");

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        title = findViewById(R.id.articleDetailsTitle);
        publishedAt = findViewById(R.id.articleDetailsPublished);
        description = findViewById(R.id.articleDetailsDescription);
        author = findViewById(R.id.articleDetailsAuthor);
        imageView = findViewById(R.id.articleDetailsImage);

        title.setText(articleSelected.getTitle());
        publishedAt.setText(myFunctions.parseArticleDate(articleSelected.getPublishedAt()));
        description.setText(articleSelected.getDescription());
        author.setText(getString(R.string.read_more_article_author) + " " + articleSelected.getAuthor());
        Picasso.with(this).load(articleSelected.getUrlToImage()).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_stories_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_favorite:
                saveFavoriteArticleOnDB(articleSelected);
                return true;
            case R.id.action_share:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveFavoriteArticleOnDB(final NewsArticleData articleSelected) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(articleSelected);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d("succes", "onSuccess: beelllaaaa");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("error", "onError: brutttaaaaa");
            }
        });
    }
}
