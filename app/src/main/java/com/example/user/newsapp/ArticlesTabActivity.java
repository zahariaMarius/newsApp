package com.example.user.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ArticlesTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_tab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_articles_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            goToStoriesFavoriteActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToStoriesFavoriteActivity() {
        Intent launchStoriesFavoriteActivity = new Intent(this, ArticlesFavoriteTabActivity.class);
        startActivity(launchStoriesFavoriteActivity);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        protected RecyclerView mRecyclerView;
        protected RecyclerView.Adapter mAdapter;
        protected RecyclerView.LayoutManager mLayoutManager;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public void createNewsArticleUrlForSection() {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            BackgroundTask backgroundTask = new BackgroundTask(this);
            switch (sectionNumber) {
                case 1:
                    try {
                        NewsArticleSingleton.getInstance().clearTopNewsArticles();
                        URL urlTopNews = new URL("https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=fe03d77e633f4995a9cae0643c6a2857");
                        backgroundTask.execute(urlTopNews);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        NewsArticleSingleton.getInstance().clearWorldNewsArticles();
                        URL urlWorldNews = new URL("https://newsapi.org/v2/top-headlines?sources=ansa&apiKey=fe03d77e633f4995a9cae0643c6a2857");
                        backgroundTask.execute(urlWorldNews);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        NewsArticleSingleton.getInstance().clearSportNewsArticles();
                        URL urlSportNews = new URL("https://newsapi.org/v2/top-headlines?sources=the-sport-bible&apiKey=fe03d77e633f4995a9cae0643c6a2857");
                        backgroundTask.execute(urlSportNews);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        /**
         * function that return the rigth arrayList for the display tab
         * @return ArrayList<NewsArticle>
         */
        public ArrayList<NewsArticle> getNewsArticleData() {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (sectionNumber) {
                case 1: return NewsArticleSingleton.getInstance().getTopNewsArrayList();
                case 2: return NewsArticleSingleton.getInstance().getWorldNewsArrayList();
                case 3: return NewsArticleSingleton.getInstance().getSportNewsArrayList();
            }
            return null;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            createNewsArticleUrlForSection();
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_articles_tab, container, false);

            SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);

            mRecyclerView = rootView.findViewById(R.id.cardStoriesRecycleView);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            // create new adapter and pass it data
            mAdapter = new ArticlesTabActivityAdapter(this.getContext(), getNewsArticleData());
            mRecyclerView.setAdapter(mAdapter);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateNewsArticle(swipeRefreshLayout);
                }
            });
            return rootView;
        }

        public void updateNewsArticle(SwipeRefreshLayout swipeRefreshLayout) {
            createNewsArticleUrlForSection();
            mAdapter = new ArticlesTabActivityAdapter(this.getContext(), getNewsArticleData());
            mRecyclerView.setAdapter(mAdapter);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
