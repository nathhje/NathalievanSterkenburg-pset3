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

    String ID;
    String title;
    boolean favorites = false;
    Set<String> theIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Log.i("mar dit niet", "lijkt het op");

        makeMovie();
    }

    public void makeMovie() {

        Log.i("of toch", "wel");
        
        title = getIntent().getStringExtra("title");
        String plot = getIntent().getStringExtra("plot");
        ID = getIntent().getStringExtra("ID");
        
        String poster = getIntent().getStringExtra("poster");

        Log.i(ID, title);

        TextView setTitle = (TextView) findViewById(R.id.title);
        TextView setPlot = (TextView) findViewById(R.id.plot);
        ImageView setPoster = (ImageView) findViewById(R.id.poster); 

        setTitle.setText(title);
        setPlot.setText(plot);
        DownloadImageTask anotherTask = new DownloadImageTask(setPoster);
        anotherTask.execute(poster);

        Log.i("we gaan", "het zien");

        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        Map<String,?> entries = pref.getAll();
        theIDs = entries.keySet();

        for (String str : theIDs) {
            if (Objects.equals(str, ID)) {
                favorites = true;
            }
        }

        Log.i("krijg ik dit nog", "binnen?");
        Button add = (Button) findViewById(R.id.addfave);
        Button remove = (Button) findViewById(R.id.removefave);

        if (favorites) {
            add.setVisibility(View.GONE);
            remove.setVisibility(View.VISIBLE);
        }
        else {
            add.setVisibility(View.VISIBLE);
            remove.setVisibility(View.GONE);
        }
    }

    public void addFavorites(View view) {

        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        Log.i(ID, title);
        editor.putString(ID, title);
        editor.apply();
    }

    public void toFavorites(View view) {

        Log.i("Hier moet ik komen", "zeker");
//        ArrayList<String> IDs = new ArrayList<String>();
//        ArrayList<String> titles = new ArrayList<String>();

        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        Log.i("Hier eigenlijk", String.valueOf(pref));
        Map<String,?> entries = pref.getAll();
        Set<String> theIDs = entries.keySet();
        Log.i(String.valueOf(theIDs), "iets");

        String[] IDs = new String[entries.size()];
        String[] titles = new String[entries.size()];
        Integer i = 0;
        for (String str : theIDs) {
            Log.i(str, pref.getString(str, null));
            IDs[i] = str;

            titles[i] = pref.getString(str, null);
            i++;
        }

        Intent intent = new Intent(this, FavoriteActivity.class);
        Log.i(String.valueOf(IDs), String.valueOf(titles));
        intent.putExtra("IDs", IDs);
        intent.putExtra("titles", titles);
        Log.i("hallo", "hallo");
        startActivity(intent);
    }

    public void removeFavorites(View view) {

        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        editor.remove(ID);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
