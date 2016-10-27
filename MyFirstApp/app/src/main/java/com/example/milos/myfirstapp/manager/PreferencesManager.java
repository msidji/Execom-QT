package com.example.milos.myfirstapp.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.milos.myfirstapp.activity.MainActivity;
import com.example.milos.myfirstapp.model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Milos on 27/10/2016.
 */

public class PreferencesManager {

    public static final String PREFS_NAME = "SHARED_PREFS";
    public static final String KEY_VALUE = "TODO_ITEMS";

    private static PreferencesManager spInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized  void initializeInstance(Context context){
        if (spInstance == null) {
            spInstance = new PreferencesManager(context);
            Log.i("initializeInstance", "initializeInstance_initializeInstance");
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (spInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return spInstance;
    }

    public void setValue(Object obj){
        Gson gson = new Gson();
        String jsonObj = gson.toJson(obj);

        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(KEY_VALUE, jsonObj);
        editor.commit();
    }

    public Object getValue(){
        Gson gson = new Gson();
        String jsonObj = mPref.getString(KEY_VALUE, null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        ArrayList<Item> arrayList = gson.fromJson(jsonObj, type);

        return  arrayList;
    }

    public void addValue(Item item){
        ArrayList beforeList = (ArrayList) getValue();
        beforeList.add(item);
        setValue(beforeList);
    }

    public  void removeValue(Item item){
        ArrayList beforeList = (ArrayList) getValue();
        Item current;
        for (Object obj: beforeList) {
            current = (Item) obj;
            if (current.getTitle().equals(item.getTitle())){
                beforeList.remove(current);
                setValue(beforeList);
                break;
            }
        }
    }

    public void editValue(Item itemOld, Item itemNew){
        ArrayList beforeList = (ArrayList) getValue();
        Item current;
        for (Object obj: beforeList) {
            current = (Item) obj;
            if (current.getTitle().equals(itemOld.getTitle())){
                current.setTitle(itemOld.getTitle());
                //TODO
                Log.i("pm weditValue", "FOUND");
                beforeList.remove(current);
                beforeList.add(itemNew);

                setValue(beforeList);
                break;
            }
        }
    }

    public void changeStatusValue(Item item){
        ArrayList beforeList = (ArrayList) getValue();
        Item current;
        for (int i=0; i<beforeList.size(); i++){
            current = (Item) beforeList.get(i);
            if (current.getTitle().equals(item.getTitle())){
                if (current.getCheckbox() == MainActivity.CHECKBOXTRUE){
                    current.setCheckbox(MainActivity.CHECKBOXTRUE);
                }
                else {
                    current.setCheckbox(MainActivity.CHECKBOXFALSE);
                }

                setValue(beforeList);
                break;
            }
        }

    }
}
