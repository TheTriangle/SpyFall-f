package com.example.inv.test.utils;

import android.text.Editable;

public class Element {
    private int id;
    private String content;

    public Element(String content, int id){
        this.id =id;
        this.content = content;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }
}
