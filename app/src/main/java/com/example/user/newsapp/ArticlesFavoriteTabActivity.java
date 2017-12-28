package com.example.user.newsapp;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ArticlesFavoriteTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_favorite_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        Log.d("activity", "onCreate: creata activity contenente i tab");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_articles_favorite_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        protected RecyclerView mRecyclerView;
        protected RecyclerView.Adapter mAdapter;
        protected RecyclerView.LayoutManager mLayoutManager;
        public Realm realm = Realm.getDefaultInstance();
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {}

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public ArrayList<NewsArticle> getNewsArticles() {
            ArrayList<NewsArticle> newsArticlesArrayList = new ArrayList<NewsArticle>();
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            RealmResults<NewsArticle> newsArticleRealmResults;
            switch (sectionNumber) {
                case 1:
                    newsArticleRealmResults = realm.where(NewsArticle.class).equalTo("type", "bbc-news").sort("savedDate", Sort.DESCENDING).findAll();
                    newsArticlesArrayList.addAll(newsArticleRealmResults);
                    return newsArticlesArrayList;
                case 2:
                    newsArticleRealmResults = realm.where(NewsArticle.class).equalTo("type", "ansa").sort("savedDate", Sort.DESCENDING).findAll();
                    newsArticlesArrayList.addAll(newsArticleRealmResults);
                    return  newsArticlesArrayList;
                case 3:
                    newsArticleRealmResults = realm.where(NewsArticle.class).equalTo("type", "the-sport-bible").sort("savedDate", Sort.DESCENDING).findAll();
                    newsArticlesArrayList.addAll(newsArticleRealmResults);
                    return newsArticlesArrayList;
            }
            return null;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_articles_favorite_tab, container, false);

            mRecyclerView = rootView.findViewById(R.id.cardFavoriteStoriesRecycleView);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // create new adapter and pass it data
            mAdapter = new ArticlesFavoriteTabActivityAdapter(this.getContext(), getNewsArticles());
            mRecyclerView.setAdapter(mAdapter);

            return rootView;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            realm.close();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
}
