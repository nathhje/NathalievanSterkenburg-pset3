package com.example.gebruiker.nathalievansterkenburg_pset3;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by Nathalie van Sterkenburg on 26-2-2017.
 *
 * Searches for movies by key word in background.
 */

public class MovieAsyncTask extends AsyncTask<String, Integer, String>{

    Context context;
    MainActivity mainAct;

    // context and mainAct are initialized
    public MovieAsyncTask(MainActivity main) {
        this.mainAct = main;
        this.context = this.mainAct.getApplicationContext();
    }

    // let's user know program is waiting for result
    protected void onPreExecute() {
        Toast.makeText(context, "searching for movies", Toast.LENGTH_SHORT).show();
    }

    // movies are retrieved
    protected String doInBackground(String... parameters) {

        try {
            // with use of HttpRequestHelper
            return HttpRequestHelper.downloadFromServer(parameters);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return "";
    }

    // processes result from HttpRequestHelper
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // the information on the movie
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<String> imdbIDs = new ArrayList<String>();

        try {

            // the result is put in a JSONObject
            JSONObject moviesearch = new JSONObject(result);

            // the useful information is extracted from "search"
            JSONArray inmoviesearch = moviesearch.getJSONArray("Search");

            // the information is put in lists
            for (int i = 0; i < inmoviesearch.length(); i++) {

                // each result is in turn extracted from the array
                JSONObject listitem = inmoviesearch.getJSONObject(i);

                // all pieces of information are stored in their own array
                String title = listitem.getString("Title");
                titles.add(title);

                String imdbID = listitem.getString("imdbID");
                imdbIDs.add(imdbID);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // the new activity is started with the information
        this.mainAct.movieStartIntent(titles, imdbIDs);
    }
}
