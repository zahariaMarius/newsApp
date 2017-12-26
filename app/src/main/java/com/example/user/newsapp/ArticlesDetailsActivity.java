package com.example.user.newsapp;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ArticlesDetailsActivity extends AppCompatActivity {

    Menu myMenu;
    MyFunctions myFunctions = new MyFunctions();
    TextView title, publishedAt, description, author;
    ImageView imageView;
    NewsArticle newsArticle;
    Uri articleUri;
    NewsArticle articleSelected;
    String articleSelectedId;
    RealmChangeListener realmChangeListener;
    String previousActivity;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        articleSelected = (NewsArticle) intent.getSerializableExtra("Article_selected");
        articleSelectedId = intent.getStringExtra("Id_Article_selected");

        if (articleSelected != null) {
            newsArticle = articleSelected;
            previousActivity = "ArticlesTabActivity";
        }

        if (articleSelectedId != null) {
            newsArticle = getNewsArticleSelected(articleSelectedId);
            previousActivity = "ArticlesFavoriteTabActivity";
        }

        title = findViewById(R.id.articleDetailsTitle);
        publishedAt = findViewById(R.id.articleDetailsPublished);
        description = findViewById(R.id.articleDetailsDescription);
        author = findViewById(R.id.articleDetailsAuthor);
        imageView = findViewById(R.id.articleDetailsImage);

        title.setText(newsArticle.getTitle());
        publishedAt.setText(myFunctions.parseArticleDate(newsArticle.getPublishedAt()));
        description.setText(newsArticle.getDescription());
        author.setText(getString(R.string.read_more_article_author) + " " + newsArticle.getAuthor());
        Picasso.with(this).load(newsArticle.getUrlToImage()).into(imageView);
        articleUri = Uri.parse(newsArticle.getUrl());
    }

    public void readMoreOnBrowser(View view) {
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, articleUri);
        startActivity(launchBrowser);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem starItem = menu.findItem(R.id.action_favorite);
        if (checkIfArticlesAlreadySaved(newsArticle)) {
            starItem.setIcon(R.drawable.ic_star_yellow_a400_24dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        myMenu = menu;
        getMenuInflater().inflate(R.menu.menu_articles_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_favorite:
                saveFavoriteArticleOnDB(newsArticle);
                return true;
            case R.id.action_share:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * function that get the article selected for id and display it
     * @param articleId
     * @return the article selected
     */
    public NewsArticle getNewsArticleSelected(String articleId) {
        final NewsArticle[] results = new NewsArticle[1];
        try(Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(inRealm -> {
                results[0] = inRealm.where(NewsArticle.class).equalTo("uuid", articleId).findFirst();
            });
        }
        return results[0];
    }

    /**
     * function the check if the article seleced already exist into db
     * @param newsArticle
     * @return true if already exist
     */
    public Boolean checkIfArticlesAlreadySaved(NewsArticle newsArticle) {
        final Boolean[] flag = {false};
        try(Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(inRealm -> {
                RealmResults<NewsArticle> newsArticleRealmResults = inRealm.where(NewsArticle.class).equalTo("url", newsArticle.getUrl()).findAll();
                if (newsArticleRealmResults.size() != 0) {
                    flag[0] = true;
                }
            });
        }
        return flag[0];
    }

    /**
     * function that save into db the selected article
     * @param articleSelected
     */
    public void saveFavoriteArticleOnDB(final NewsArticle articleSelected) {
        if (!checkIfArticlesAlreadySaved(newsArticle)) {
            Realm realm = null;
            try {
                realm = Realm.getDefaultInstance();
                realm.addChangeListener(getSavedRealmChangedNotification());
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        articleSelected.setSavedDate(new Date());
                        realm.insertOrUpdate(articleSelected);
                    }
                });
            } finally {
                if(realm != null) {
                    realm.close();
                }
            }
        }else {
            showDeleteSavedNewsArticleDialog();
        }
    }

    /**
     * function that remove from the db the selected article
     */
    public void removeFavoriteArticleOnDB() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.addChangeListener(getRemovedRealmChangedNotification());
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    NewsArticle result = realm.where(NewsArticle.class).equalTo("uuid", newsArticle.getUuid()).findFirst();
                    result.deleteFromRealm();
                }
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    public RealmChangeListener getSavedRealmChangedNotification() {
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                MenuItem starItem = myMenu.findItem(R.id.action_favorite);
                starItem.setIcon(R.drawable.ic_star_yellow_a400_24dp);
                showToastMessage("Article successfully saved");
            }
        };
        return realmChangeListener;
    }

    public RealmChangeListener getRemovedRealmChangedNotification() {
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                MenuItem starItem = myMenu.findItem(R.id.action_favorite);
                starItem.setIcon(R.drawable.ic_star_white_24dp);
                showToastMessage("Article successfully removed");
                if (Objects.equals(previousActivity, "ArticlesFavoriteTabActivity")) { finish(); }
            }
        };
        return realmChangeListener;
    }

    /**
     * function that show the alert dialog for remove the selected article
     */
    public void showDeleteSavedNewsArticleDialog() {
        DeleteSavedNewsArticleDialog deleteSavedNewsArticleDialog = new DeleteSavedNewsArticleDialog();
        FragmentManager fragmentManager = getFragmentManager();
        deleteSavedNewsArticleDialog.show(fragmentManager, "dddd");
    }

    /**
     * function that show a toast message for operations confirm
     * @param message
     */
    public void showToastMessage(String message) {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
