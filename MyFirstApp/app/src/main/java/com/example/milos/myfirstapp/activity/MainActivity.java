package com.example.milos.myfirstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.milos.myfirstapp.R;
import com.example.milos.myfirstapp.adapter.MyAdapter;
import com.example.milos.myfirstapp.manager.PreferencesManager;
import com.example.milos.myfirstapp.model.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int CHECKBOXTRUE = 1;
    public static final int CHECKBOXFALSE = 0;
    public static final String METHOD_REMOVE = "METHOD_REMOVE";
    public static final String METHOD_EDIT = "METHOD_EDIT";
    public static final String METHOD_CHANGESTATUS = "METHOD_CHANGESTATUS";
    public static final String METHOD_ADD = "METHOD_ADD";
    private final ArrayList<Item> listItems = new ArrayList<>();
    private ListView lvItems;
    private MyAdapter itemsAdapter;
    private PreferencesManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        initToDosList();
        initListView();
        initAdapter();
        // Assign adapter to ListView
        lvItems.setAdapter(itemsAdapter);

        processExtraData();

    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
        processExtraData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToDosList() {
        Item item1 = new Item("Eat", "Breakfast at 9h.", CHECKBOXFALSE);
        Item item2 = new Item("Sport", "Basketball at 19h.", CHECKBOXFALSE);
        Item item3 = new Item("Code", "All night.", CHECKBOXTRUE);
        listItems.add(item1);
        listItems.add(item2);
        listItems.add(item3);
    }

    private void initListView() {
        lvItems= (ListView) findViewById(R.id.mylistview);
    }

    private void initAdapter(){
        pm.initializeInstance(getApplicationContext());
        ArrayList<Item> list = (ArrayList<Item>) pm.getInstance().getValue();

        if (list == null) {
            list = listItems;
            pm.getInstance().setValue(list);
        }

        itemsAdapter= new MyAdapter(this, R.layout.list_view_items, list);
    }

    private void processExtraData(){
        Intent intent = getIntent();
        Item item;

        Bundle extras = intent.getExtras();
        if (extras != null) {
            item = GetItemFromIntent(intent);

            if (intent.getStringExtra("method").equals(METHOD_REMOVE)) {
                RemoveItem(item);
            }

            if (intent.getStringExtra("method").equals(METHOD_ADD)) {
                AddItem(item);
            }

            if (intent.getStringExtra("method").equals(METHOD_EDIT)) {
                Item itemOld = new Item(intent.getStringExtra("titleOld").toString(),
                        intent.getStringExtra("descrOld").toString(),
                        intent.getIntExtra("statusOld", 0));
                EditItem(itemOld, item);
            }

            if (intent.getStringExtra("method").equals(METHOD_CHANGESTATUS)) {
                ChangeStatus(item);
            }
        }
    }

    private Item GetItemFromIntent(Intent intent){
        Item item = new Item(intent.getStringExtra("title").toString(),
                intent.getStringExtra("descr").toString(),
                intent.getIntExtra("status", 0));
        return item;
    }

    private void AddItem(Item item){
        pm.getInstance().addValue(item);
        itemsAdapter.getTodosList().add(item);
    }

    private void RemoveItem(Item item){
        pm.getInstance().removeValue(item);
        itemsAdapter.remove(item);
    }

    private void EditItem(Item itemOld, Item itemNew){
        pm.getInstance().editValue(itemOld, itemNew);
        itemsAdapter.edit(itemOld, itemNew);
    }

    private void ChangeStatus(Item item){
        pm.getInstance().changeStatusValue(item);
    }

}
