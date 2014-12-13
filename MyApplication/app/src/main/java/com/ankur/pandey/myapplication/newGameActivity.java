package com.ankur.pandey.myapplication;

/**
 * Created by Ankur on 10/30/2014.
 */
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.util.Log;

import java.util.Random;

public class newGameActivity extends Activity {
    private static final String TAG = "new Game activity";
    private myMainView myMineView;


    //private gridClass grid;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myMineView = new myMainView(this);
        setContentView(myMineView);
        myMineView.requestFocus();
    }

    protected String getNumString(int x, int y,int z) {
        switch(z){
            case 0:
                return "0";
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "7";
            case 8:
                return "8";
            case 9:
                return "9";
            case 10:
                return "10";
            default:
                return "12";
        }
    }
}