package com.example.englearning.model;

public class Tutorial {


    int id;
    int title;
    int desc;


    public Tutorial(int id, int title, int desc){

        this.id= id;
        this.title = title;
        this.desc = desc;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDesc() {
        return desc;
    }

    public void setDesc(int desc) {
        this.desc = desc;
    }
}
