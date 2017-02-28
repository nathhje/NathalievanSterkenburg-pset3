package com.example.gebruiker.nathalievansterkenburg_pset3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class FavoriteActivity extends AppCompatActivity {

    ListView favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        makeFavoriteList();
    }

    // creates list of favorites
    private void makeFavoriteList() {

        // retrieves lists favorites
        String[] titles = getIntent().getStringArrayExtra("titles");
        final String[] IDs = getIntent().getStringArrayExtra("IDs");

        // adapter is created
        ArrayAdapter adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);

        // adapter is set to ListView
        favorites = (ListView) findViewById(R.id.fave);
        assert favorites != null;
        favorites.setAdapter(adapter);

        // retrieves additional information on a favorite when clicked
        final FavoriteAsyncTask asyncTask = new FavoriteAsyncTask(this);
        favorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                asyncTask.execute("i=" + IDs[position] + "&", IDs[position]);
            }
        });
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    }

    // starts InfoActivity when asyncTask is done retrieving info
    public void favoriteStartIntent(String title, String plot, String ID, String poster) {

        Intent dataIntent = new Intent(this, InfoActivity.class);

        // info is added to intent as extra
        dataIntent.putExtra("title", title);
        dataIntent.putExtra("plot", plot);
        dataIntent.putExtra("ID", ID);
        dataIntent.putExtra("poster", poster);

        startActivity(dataIntent);
    }

    // go back to start screen when Back is pressed
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
