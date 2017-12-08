package com.example.android.notesbc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
    int notesid;
    SharedPreferences sharedPreferences;
    EditText editorTextView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.saveNote){
            Toast.makeText(getApplicationContext(), "Note Saved!",Toast.LENGTH_SHORT).show();
            HashSet<String> hashSet = new HashSet<String>(MainActivity.noteslist);
            sharedPreferences.edit().putStringSet("notes",hashSet).apply();

            Intent intent = new Intent(NoteEditorActivity.this,MainActivity.class);
            startActivity(intent);

        }
        if(item.getItemId()==R.id.editNote){
            editorTextView.setFocusableInTouchMode(true);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        editorTextView = (EditText) findViewById(R.id.editorTextView);

        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.android.notesbc", Context.MODE_PRIVATE);
        Intent intent =getIntent();
        notesid =intent.getIntExtra("noteid",-1);
        if(notesid!=-1){
            String note = MainActivity.noteslist.get(intent.getIntExtra("noteid",-1));
            editorTextView.setText(note);
            editorTextView.setFocusable(false);
        }else
        {
            MainActivity.noteslist.add("");
            notesid=MainActivity.noteslist.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();


        }

        editorTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString()!="") {
                    Log.i("s value",s.toString());
                    MainActivity.noteslist.set(notesid, String.valueOf(s));
                    MainActivity.arrayAdapter.notifyDataSetChanged();

                    HashSet<String> hashSet = new HashSet<String>(MainActivity.noteslist);
                    sharedPreferences.edit().putStringSet("notes",hashSet).apply();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
