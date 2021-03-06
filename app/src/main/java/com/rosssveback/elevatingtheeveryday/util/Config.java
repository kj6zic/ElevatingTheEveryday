package com.rosssveback.elevatingtheeveryday.util;

/**
 * Configuration class
 */
public class Config {
    // Fill in your own WordPress URL, don't forget the "/" at the end
    public static final String BASE_URL = "http://www.rosssveback.com/";

    public static String CATEGORY_URL = BASE_URL + "?json=get_category_index";
    public static String POST_URL = BASE_URL + "?json=get_post&post_id=";
    public static final String DEFAULT_THUMBNAIL_URL = "https://lh3.ggpht.com/xlrINVZvK8JZKSDk_X6a91qZ1nmMh4AIUNQqAc3yNkYpVNpWY99LX7Ztzi0g8EkOBHk=w300";
}
