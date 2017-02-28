package com.example.gebruiker.nathalievansterkenburg_pset3;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Nathalie on 26-2-2017.
 */

class FavoriteAsyncTask extends AsyncTask<String, Integer, String>{

    Context context;
    FavoriteActivity mainAct;
    String ID;

    public FavoriteAsyncTask(FavoriteActivity main) {
        this.mainAct = main;
        this.context = this.mainAct.getApplicationContext();
    }

    protected void onPreExecute() {
        Toast.makeText(context, "retrieving info", Toast.LENGTH_SHORT).show();
    }

    protected String doInBackground(String... parameters) {
        ID = parameters[1];

        try {

            return HttpRequestHelper.downloadFromServer(parameters);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return "";
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        String title = "error";
        String plot = "error";
        String poster = "https://images-na.ssl-images-amazon.com/images/M/MV5BMTY2MTk3MDQ1N15BMl5BanBnXkFtZTcwMzI4NzA2NQ@@._V1_SX300.jpg";

        try {
            JSONObject moviesearch = new JSONObject(result);

            title = moviesearch.getString("Title");
            plot = moviesearch.getString("Plot");
            poster = moviesearch.getString("Poster");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.mainAct.favoriteStartIntent(title, plot, ID, poster);
    }
}
