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
        Log.i("kom ik", "toevallig hier");
        makeFavoriteList();
    }

    private void makeFavoriteList() {

        String[] titles = getIntent().getStringArrayExtra("titles");
        final String[] IDs = getIntent().getStringArrayExtra("IDs");

        Log.i("daar gaan", "we weer");

        ArrayAdapter adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);

        favorites = (ListView) findViewById(R.id.fave);
        assert favorites != null;
        favorites.setAdapter(adapter);


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

    public void favoriteStartIntent(String title, String plot, String ID, String poster) {
        Intent dataIntent = new Intent(this, InfoActivity.class);
        dataIntent.putExtra("title", title);
        dataIntent.putExtra("plot", plot);
        dataIntent.putExtra("ID", ID);
        dataIntent.putExtra("poster", poster);
        startActivity(dataIntent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
