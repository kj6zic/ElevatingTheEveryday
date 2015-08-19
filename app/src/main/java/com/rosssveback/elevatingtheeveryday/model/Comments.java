package com.rosssveback.elevatingtheeveryday.model;

import java.util.ArrayList;

/**
 * Created by a1121661 on 8/18/15.
 */
public class Comments {
    private String name;
    private String date;
    private String content;
    private int id;
    private ArrayList<String> comments;

    public Comments() {
    }

    /**
     * Override equals method to help Set decide if two posts are the same
     *
     * @param o Another object
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof Post && this.getId() == (((Post) o).getId());
    }

    /**
     * Override hashCode method to help Set decide if two posts are the same
     */
    @Override
    public int hashCode() {
        //return this.getId();
        return Integer.valueOf(this.getId()).hashCode();
    }

    /**
     * Getter and setter for name of the commenter
     * @return
     */
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    /**
     * Getter and setter for the ID of the commenter
     * @return
     */
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    /**
     * Getter and setter for the date of comment
     * @return
     */
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }

    /**
     * Getter and setter for the content of the comment
     * @return
     */
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
}
