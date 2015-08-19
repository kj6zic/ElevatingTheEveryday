package com.rosssveback.elevatingtheeveryday.model;

/**
 * Created by a1121661 on 8/18/15.
 */
public class Comments {
    private String name;
    private String date;
    private String content;
    private int id;
    private Integer ids;

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

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    //public Stringj
    public void setIds(Integer ids){
        this.ids = ids;
    }

}
