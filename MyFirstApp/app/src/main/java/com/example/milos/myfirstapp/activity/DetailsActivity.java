package com.example.milos.myfirstapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.milos.myfirstapp.R;
import com.example.milos.myfirstapp.adapter.MyAdapter;
import com.example.milos.myfirstapp.model.Item;

public class DetailsActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescr;
    private CheckBox cbStatus;
    private Intent intent;
    private Button btnRemove;
    private Button btnEdit;
    private Item itemOld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        intent = this.getIntent();

        Bundle extras = intent.getExtras();
        if (extras != null) {
            itemOld = new Item(intent.getStringExtra("titleOld").toString(),
                    intent.getStringExtra("descrOld").toString(),
                    intent.getIntExtra("statusOld", 0));
        }

        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescr = (EditText) findViewById(R.id.etDescr);
        cbStatus = (CheckBox) findViewById(R.id.cbStatus);
        btnRemove = (Button) findViewById(R.id.btnRemove);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnRemove.setBackgroundColor(Color.RED);
        btnEdit.setBackgroundColor(Color.GREEN);

        setValues();

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                intent.putExtra("method", MainActivity.METHOD_REMOVE);
                intent.putExtra("title", etTitle.getText().toString());
                intent.putExtra("descr", etDescr.getText().toString());
                if (cbStatus.isChecked()){
                    intent.putExtra("status", MainActivity.CHECKBOXTRUE);
                }
                else{
                    intent.putExtra("status", MainActivity.CHECKBOXFALSE);
                }
                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                intent.putExtra("method", MainActivity.METHOD_EDIT);
                // New item data
                intent.putExtra("title", etTitle.getText().toString());
                intent.putExtra("descr", etDescr.getText().toString());
                if (cbStatus.isChecked()){
                    intent.putExtra("status", MainActivity.CHECKBOXTRUE);
                }
                else{
                    intent.putExtra("status", MainActivity.CHECKBOXFALSE);
                }
                // Old item data
                intent.putExtra("titleOld", itemOld.getTitle());
                intent.putExtra("descrOld", itemOld.getDescr());
                intent.putExtra("statusOld", itemOld.getCheckbox());

                startActivity(intent);
            }
        });
    }

    private void setValues(){
        etTitle.setText(intent.getStringExtra("titleOld"));
        etDescr.setText(intent.getStringExtra("descrOld"));
        switch (intent.getIntExtra("statusOld", 0)){
            case MainActivity.CHECKBOXTRUE:
                cbStatus.setChecked(true);
                break;
            default:
                cbStatus.setChecked(false);
        }
    }
}
