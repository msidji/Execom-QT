package com.example.milos.myfirstapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.milos.myfirstapp.R;
import com.example.milos.myfirstapp.activity.DetailsActivity;
import com.example.milos.myfirstapp.activity.MainActivity;
import com.example.milos.myfirstapp.model.Item;

import java.util.ArrayList;

/**
 * Created by Milos on 26/10/2016.
 */

public class MyAdapter extends ArrayAdapter {

    private ArrayList<Item> todosList = new ArrayList<>();
    private Context context;

    public MyAdapter(Context context, int resource, ArrayList<Item> objects) {
        super(context, resource, objects);
        this.context = context;
        this.todosList = objects;
    }

    public void remove(Item item){
        for (int i=0; i<todosList.size(); i++) {
            if (todosList.get(i).getTitle().equals(item.getTitle())
                    && todosList.get(i).getDescr().equals(item.getDescr())) {
                todosList.remove(i);
                break;
            }
        }
    }

    public void edit(Item itemOld, Item itemNew){
        for (int i=0; i<todosList.size(); i++) {
            if (todosList.get(i).getTitle().equals(itemOld.getTitle())
                    && todosList.get(i).getDescr().equals(itemOld.getDescr())) {
                todosList.remove(i);
                todosList.add(i, itemNew);
                break;
            }
        }
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Item item = todosList.get(position);

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_view_items, null); //set layout for displaying items
        final TextView textView = (TextView) v.findViewById(R.id.textView); //get id for text view
        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        textView.setText(item.getTitle());
        switch (item.getCheckbox()){
            case MainActivity.CHECKBOXTRUE:
                checkBox.setChecked(true);

                // If "done", make background other color
                textView.setBackgroundColor(Color.LTGRAY);
                checkBox.setBackgroundColor(Color.LTGRAY);
                break;
            default:
                checkBox.setChecked(false);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ITEM CLICKED", textView.getText().toString());

                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("titleOld", item.getTitle().toString());
                intent.putExtra("descrOld", item.getDescr().toString());
                intent.putExtra("statusOld", item.getCheckbox());

                v.getContext().startActivity(intent);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO: save changes
            }
        });

        return v;
    }

    public ArrayList<Item> getTodosList() {
        return todosList;
    }
}
