package com.ankur.pandey.myapplication;

import android.content.Context;
import android.view.GestureDetector;
import android.widget.Button;

import java.util.Objects;
import android.view.GestureDetector;

/**
 * Created by Ankur on 10/30/2014.
 */

public class gridClass extends Button {

    int rowValue; // store row Value
    int colValue; // store col Value
    boolean opened; // to check cell is open or not
    boolean mine; // to check mine is present or not
    boolean visited; // to check grid has been visited
    int mineNumbers; //counting surrounding minenumber
    boolean flag; // to check flag is set or not

    public gridClass(Context context) {
        super(context);
        mineNumbers = 0;
        mine = false;
        opened = false;
        flag = false;
        visited =false;
    }
}
