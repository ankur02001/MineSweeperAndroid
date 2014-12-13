package com.ankur.pandey.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.util.Log;

public class MyActivity extends Activity implements OnClickListener{
    private static final String TAG = "my activity :";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        View newGameButton = findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(this);
        View exitGameButton = findViewById(R.id.exitGameButton);
        exitGameButton.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view){
        switch(view.getId())
        {
            case R.id.newGameButton:
                Intent newGameActivity = new Intent(this,newGameActivity.class);
                startActivity(newGameActivity);
                break;

            case R.id.exitGameButton:
                finish();
                break;
        }
    }
}
