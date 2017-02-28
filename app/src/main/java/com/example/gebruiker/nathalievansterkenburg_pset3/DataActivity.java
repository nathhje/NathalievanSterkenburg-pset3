package com.example.gebruiker.nathalievansterkenburg_pset3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class DataActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView thelist;
    ArrayList<String> titles;
    ArrayList<String> IDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // retrieves movie information
        Bundle extras = getIntent().getExtras();
        titles = (ArrayList<String>) extras.getStringArrayList("titles");
        IDs = (ArrayList<String>) extras.getStringArrayList("IDs");

        makeTrackAdapter();
    }

    public void makeTrackAdapter() {

        // adapter is initialized
        ArrayAdapter adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, titles);

        // ListView is initialized and set to adapter
        thelist = (ListView) findViewById(R.id.list);
        assert thelist != null;
        thelist.setAdapter(adapter);

        // when list item is clicked, additional info for that movie is retrieved
        final InfoAsyncTask asyncTask = new InfoAsyncTask(this);
        thelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // info must be retrieved for right ID
                asyncTask.execute("i=" + IDs.get(position) + "&", IDs.get(position));
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    // opens InfoActivity when asyncTask is done
    public void detailsStartIntent(String title, String plot, String ID, String poster) {

        Intent dataIntent = new Intent(this, InfoActivity.class);
        Log.i(title, plot);

        // movie information are all extra's
        dataIntent.putExtra("title", title);
        dataIntent.putExtra("plot", plot);
        dataIntent.putExtra("ID", ID);
        dataIntent.putExtra("poster", poster);

        Log.i(ID, String.valueOf(dataIntent));
        // InfoActivity
        startActivity(dataIntent);
    }

    // go back to start screen when Back is pressed
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

