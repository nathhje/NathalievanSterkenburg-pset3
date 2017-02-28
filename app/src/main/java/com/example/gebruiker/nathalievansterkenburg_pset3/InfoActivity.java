package com.example.gebruiker.nathalievansterkenburg_pset3;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static android.os.Build.ID;

public class InfoActivity extends AppCompatActivity {

    // checks if movie is a favorite, used to decide between add and remove as favorite button
    boolean favorites = false;

    String ID;
    String title;
    Set<String> theIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        makeMovie();
    }

    // creates screen with additional information
    public void makeMovie() {

        // info of movie is initialized
        title = getIntent().getStringExtra("title");
        String plot = getIntent().getStringExtra("plot");
        ID = getIntent().getStringExtra("ID");
        String poster = getIntent().getStringExtra("poster");

        TextView setTitle = (TextView) findViewById(R.id.title);
        TextView setPlot = (TextView) findViewById(R.id.plot);
        ImageView setPoster = (ImageView) findViewById(R.id.poster); 

        setTitle.setText(title);
        setPlot.setText(plot);

        // poster is a url and is converted to image with DownloaadImageTask
        DownloadImageTask anotherTask = new DownloadImageTask(setPoster);
        anotherTask.execute(poster);

        // favorites are retrieved
        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        Map<String,?> entries = pref.getAll();
        theIDs = entries.keySet();

        // check if ID of the movie is already in favorites
        for (String str : theIDs) {
            if (Objects.equals(str, ID)) {
                favorites = true;
            }
        }

        // whether add or remove button is shown is decided by favorites
        Button add = (Button) findViewById(R.id.addfave);
        Button remove = (Button) findViewById(R.id.removefave);

        // if movie is already in favorites, it can be deleted
        if (favorites) {
            add.setVisibility(View.GONE);
            remove.setVisibility(View.VISIBLE);
        }
        // if movie isn't in favorites, it can be added
        else {
            add.setVisibility(View.VISIBLE);
            remove.setVisibility(View.GONE);
        }
    }

    // opens FavoriteActivity when button is pressed
    public void toFavorites(View view) {

        // favorieten worden opgehaald
        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        Map<String,?> entries = pref.getAll();
        Set<String> theIDs = entries.keySet();

        String[] IDs = new String[entries.size()];
        String[] titles = new String[entries.size()];
        Integer i = 0;

        // favorieten worden in ArrayList gestopt
        for (String str : theIDs) {
            IDs[i] = str;
            titles[i] = pref.getString(str, null);
            i++;
        }

        // Intent voor InfoActivity wordt gemaakt
        Intent intent = new Intent(this, FavoriteActivity.class);

        // favorieten zijn extra's
        intent.putExtra("IDs", IDs);
        intent.putExtra("titles", titles);

        startActivity(intent);
    }

    // when clicked, adds movie to favorites
    public void addFavorites(View view) {

        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        // ID and title are stored in preferences
        editor.putString(ID, title);
        editor.apply();
    }

    // when clicked, removes movie from favorites
    public void removeFavorites(View view) {

        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        // movie is removed
        editor.remove(ID);
        editor.apply();
    }

    // go back to start screen when Back is pressed
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
