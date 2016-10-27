package com.example.milos.myfirstapp.model;

/**
 * Created by Milos on 26/10/2016.
 */

public class Item {

    private String title;
    private String descr;
    private int checkbox;

    public Item(String title, String descr, int checkbox) {
        this.title = title;
        this.descr = descr;
        this.checkbox = checkbox;
    }

    public String getTitle() {
        return title;
    }

    public String getDescr() {
        return descr;
    }

    public int getCheckbox() {
        return checkbox;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setCheckbox(int checkbox) {
        this.checkbox = checkbox;
    }
}
