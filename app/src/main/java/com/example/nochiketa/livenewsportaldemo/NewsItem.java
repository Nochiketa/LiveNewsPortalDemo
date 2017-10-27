package com.example.nochiketa.livenewsportaldemo;

import java.io.Serializable;

/**
 * Created by Nochiketa on 10/27/2017.
 */

public class NewsItem implements Serializable{

    String title;
    String link;
    String description;
    String date;
    String imagepath;

    @Override
    public String toString() {
        return title;
    }
}
