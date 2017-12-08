package com.example.android.notesbc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> noteslist =new ArrayList<String>();
    static ArrayAdapter<String> arrayAdapter;
    ListView listView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.addNote){
            Intent intent = new Intent(MainActivity.this,NoteEditorActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.android.notesbc", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

        Log.i("test",set.toString());

        if(set==null){
            noteslist.add("Example note");
        }else
        {
            noteslist = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,noteslist);

        listView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);



        final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.android.notesbc", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);

        Log.i("test",set.toString());

        if(set==null){
            noteslist.add("Example note");
        }else
        {
            noteslist = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,noteslist);

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,NoteEditorActivity.class);
                intent.putExtra("noteid",position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are You Sure?")
                        .setMessage("Do you want to delete this note")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                noteslist.remove(position);
                                arrayAdapter.notifyDataSetChanged();



                                HashSet<String> hashSet = new HashSet<String>(MainActivity.noteslist);
                                sharedPreferences.edit().putStringSet("notes",hashSet).apply();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                return true;
            }
        });
    }
}
