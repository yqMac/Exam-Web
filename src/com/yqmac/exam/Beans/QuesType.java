package com.yqmac.exam.Beans;

/**
 * Created by yqmac on 2016/5/25 0025.
 */
public class QuesType {

    private  int id;
    private String name;


    public QuesType() {
    }

    public QuesType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
