package com.rosssveback.elevatingtheeveryday.model;

/**
 * Created by a1121661 on 9/4/15.
 */
public class NavDrawerItemModel {

        private String title;
        private int icon;
        public NavDrawerItemModel(){}

        public NavDrawerItemModel(String title, int icon){
            this.title = title;
            this.icon = icon;
        }

        public String getTitle(){
            return this.title;
        }

        public int getIcon(){
            return this.icon;
        }

        public void setTitle(String title){
            this.title = title;
        }

        public void setIcon(int icon){
            this.icon = icon;
        }
}
