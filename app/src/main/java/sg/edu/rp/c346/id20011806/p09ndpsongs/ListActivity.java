package sg.edu.rp.c346.id20011806.p09ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ListActivity extends AppCompatActivity {

    Button btnFilter;
    ListView lv;
    Spinner spinnerFilter;
    ArrayList<Song> al;
    ArrayAdapter<Song> aa;
    ArrayList<Song> alFilter;
    ArrayAdapter<Song> aaFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btnFilter = findViewById(R.id.btnFilter);
        lv = findViewById(R.id.lv);
        spinnerFilter = findViewById(R.id.spinner);
        //Song data = al.get(position);
        DBHelper dbh = new DBHelper(ListActivity.this);

        alFilter = new ArrayList<Song>();




        al = new ArrayList<Song>();
        al = dbh.getAllSongs();
        aa = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, al);
      //  lv.setAdapter(aa);

        //filter enhancement
        ArrayList<String> spinneral = new ArrayList<String>();
        for (int i = 0; i < al.size(); i++) {
            String addyear = String.valueOf(al.get(i).getYear());
            spinneral.add(addyear);
        }
        Set<String> spinnerset = new HashSet<>(spinneral);
        spinneral.clear();
        spinneral.addAll(spinnerset);
        spinneral.add("No Filter");
        Collections.sort(spinneral);
        Collections.reverse(spinneral);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinneral);
        spinnerFilter.setAdapter(spinnerAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 1; i<al.size(); i++) {
                    if (position == 0) {
                        lv.setAdapter(aa);
                    } else if (position == i) {
                        alFilter.clear();
                        alFilter = dbh.getAllSongs(Integer.parseInt(spinnerFilter.getSelectedItem().toString()));
                        aaFilter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, alFilter);
                        lv.setAdapter(aaFilter);
                    }
            }}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ListActivity.this);
                al.clear();
                al.addAll(dbh.getAll5StarSongs());
                aa.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song data = al.get(position);
                Intent i = new Intent(ListActivity.this, EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        Log.d("MainActivity", "onResume() called.");
        DBHelper dbh = new DBHelper(ListActivity.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        aa.notifyDataSetChanged();
        super.onResume();

    }
}