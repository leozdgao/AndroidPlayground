package me.leozdgao.mydemoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.net.URL;

import me.leozdgao.mydemoapp.data.WaitlistDbHelper;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String SEARCH_QUERY_URL_EXTRA = "query";
    private static final String SEARCH_RESULTS_RAW_JSON = "results";

    private static final int GITHUB_SEARCH_LOADER = 22;

    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;
    private TextView mSearchResults;

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("show_bass")) {
            setUrlDisplayVisibility(sharedPreferences);
        }
    }

    private void setUrlDisplayVisibility(SharedPreferences sharedPreferences) {
        Boolean isShowUrlDisplay = sharedPreferences.getBoolean("show_bass", true);

        if (!isShowUrlDisplay) {
            mUrlDisplayTextView.setVisibility(View.INVISIBLE);
        } else {
            mUrlDisplayTextView.setVisibility(View.VISIBLE);
        }
    }

    class GithubQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String result = null;
            try {
                result = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                mSearchResults.setText(s);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResults = (TextView) findViewById(R.id.tv_github_search_results_json);

        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        preference.registerOnSharedPreferenceChangeListener(this);
        setUrlDisplayVisibility(preference);

        SQLiteDatabase mDb = new WaitlistDbHelper(this).getReadableDatabase();
//        mDb.query()

//        Boolean isShowUrlDisplay = mPreference.getBoolean("show_bass", true);

//        if (!isShowUrlDisplay) {
//            mUrlDisplayTextView.setVisibility(View.INVISIBLE);
//        }

        if (savedInstanceState != null) {
            String queryUrl = savedInstanceState.getString(SEARCH_QUERY_URL_EXTRA);
            String rawJsonSearchResults = savedInstanceState.getString(SEARCH_RESULTS_RAW_JSON);

            mUrlDisplayTextView.setText(queryUrl);
            mSearchResults.setText(rawJsonSearchResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String queryUrl = mUrlDisplayTextView.getText().toString();
        outState.putString(SEARCH_QUERY_URL_EXTRA, queryUrl);

        String rawJsonSearchResults = mSearchResults.getText().toString();
        outState.putString(SEARCH_RESULTS_RAW_JSON, rawJsonSearchResults);
    }

//    @Override
//    public Loader<String> onCreateLoader(int i, final Bundle bundle) {
//        return new AsyncTaskLoader<String>(this) {
//            @Override
//            protected void onStartLoading() {
//                super.onStartLoading();
//
//                if (bundle == null) {
//                    return;
//                }
//            }
//
//            @Override
//            public String loadInBackground() {
//                String searchQueryUrlString = bundle.getString(SEARCH_QUERY_URL_EXTRA);
//                if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
//                    return null;
//                }
//
//                try {
//                    URL githubUrl = new URL(searchQueryUrlString)
//                    return NetworkUtils.getResponseFromHttpUrl(githubUrl);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onLoadFinished(Loader<String> loader, String s) {
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int itemId = item.getItemId();

         if (itemId == R.id.action_search) {
//             Toast toast = Toast.makeText(MainActivity.this, "Search Clicked", Toast.LENGTH_LONG);
//             toast.show();
             makeGithubSearchQuery();
         } else if (itemId == R.id.action_settings) {
             Intent intent = new Intent(this, SettingsActivity.class);
             startActivity(intent);
         }

         return true;
    }

    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();
        String urlString = NetworkUtils.generateGithubSearchUrl(githubQuery, "stars");
        URL url = NetworkUtils.buildUrl(urlString);
        mUrlDisplayTextView.setText(urlString);

        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, githubQuery);

//        LoaderManager loaderManager = getSupportLoaderManager();
//        Loader<String> githubSearchLoader = loaderManager.getLoader(GITHUB_SEARCH_LOADER);

//        if (githubSearchLoader == null) {
//            loaderManager.initLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
//        } else {
//            loaderManager.restartLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
//        }
        new GithubQueryTask().execute(url);
    }
}
