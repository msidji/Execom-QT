package com.example.milos.myfirstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.milos.myfirstapp.R;

public class AddActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescr;
    private CheckBox cbStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescr = (EditText) findViewById(R.id.etDescr);
        cbStatus = (CheckBox) findViewById(R.id.cbStatus);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                intent.putExtra("method", MainActivity.METHOD_ADD);
                intent.putExtra("title", etTitle.getText().toString());
                intent.putExtra("descr", etDescr.getText().toString());
                intent.putExtra("status", MainActivity.CHECKBOXFALSE);
                startActivity(intent);
            }
        });
    }

}
