package com.example.gebruiker.nathalievansterkenburg_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    // where user requests key word
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (EditText) findViewById(R.id.searchbox);
        assert search != null;
    }

    // searches for movies based on key word
    public void movieSearch(View view) {

        // retrieves key word
        String movie = search.getText().toString();

        // searches for movies in background
        MovieAsyncTask asyncTask = new MovieAsyncTask(this);

        // argument is adjusted to fit url
        String urlmovie = movie.replaceAll(" ","_");
        asyncTask.execute("s=" + urlmovie + "&type=movie&");

        search.getText().clear();
    }

    // opens DataActivity when MovieAsyncTask is done
    public void movieStartIntent(ArrayList<String> titles, ArrayList<String> IDs) {

        // intent is made
        Intent dataIntent = new Intent(this, DataActivity.class);

        // results from database are extras
        dataIntent.putExtra("titles", titles);
        dataIntent.putExtra("IDs", IDs);

        // DataActivity
        this.startActivity(dataIntent);
    }

    // opens FavoriteActivity when button is pressed
    public void toFavorites(View view) {

//        ArrayList<String> IDs = new ArrayList<String>();
//        ArrayList<String> titles = new ArrayList<String>();

        // favorieten worden opgehaald
        SharedPreferences pref = getApplication().getSharedPreferences("MyPref", 0);
        Map<String,?> entries = pref.getAll();
        Set<String> theIDs = entries.keySet();

        String[] IDs = new String[entries.size()];
        String[] titles = new String[entries.size()];
        Integer i = 0;

        Log.i(String.valueOf(theIDs), "iets");

        // favorieten worden in ArrayList gestopt
        for (String str : theIDs) {
            Log.i(str, pref.getString(str, null));

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

    // can't press back on start screen
    @Override
    public void onBackPressed() {
    }
}
