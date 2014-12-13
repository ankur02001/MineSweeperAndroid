package com.ankur.pandey.myapplication;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.content.Context;
import android.graphics.Rect;
import java.util.Random;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.TextView;
import android.view.Gravity;
import android.content.DialogInterface;
import android.graphics.Color;


public class myMainView extends View  {

    private float width;    // width of one tile
    private float height;   // height of one tile

    //private final Rect selRect = new Rect();
    private int totalScoretobecounted = 0;
    private int placedMineCount = 0;
    private boolean gameOver;
    public gridClass grid[][];
    GestureDetector gestureDet;

    private static final String TAG = "myMainVIew";
    private static final int ID = 42;
    private final newGameActivity newGameActivity;

    public myMainView(Context context) {
        super(context);
       // Log.d(TAG, "I am here now 3");
        this.newGameActivity = (newGameActivity) context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        makeGridObjects();
        resetGame();
        // creating new gesture detector
        gestureDet = new GestureDetector(context, new GestureListener());
        //   gestureDetector = new GestureDetector(context, new GestureListener());
        // setId(ID);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        gestureDet.onTouchEvent(e);
        return true;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();
            int j = (int)(x/width);
            int i = (int)(y/height);

            if(grid[j][i].flag){ // if flag has been set returning
                return true;
            }
            if(!grid[j][i].visited) { // opening grid
                grid[j][i].opened = true;

                if (grid[j][i].mine == true) { // chechking mine is clicked
                    gameOver = true;
                } else {
                    // calling recursively to open grid if mineNumber count as zero
                    if (grid[j][i].mineNumbers == 0) {
                        recursivelyOpen(grid[j][i]);
                    }
                }
            }


            invalidate();
               // Toast.makeText(getContext(), "This is Toast example.", Toast.LENGTH_SHORT).show();
           // Log.d("touch event Tap ij value", "Tapped at: (" + i + "," + j + ")");
          //  Log.d("Double Tap", "Tapped at: (" + x + "," + y + ")");
            return super.onDoubleTap(e);
        }
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();
            int j = (int)(x /width);
            int i = (int)(y/height);
          //  Log.d("touch event Tap ij value", "Tapped at: (" + i + "," + j + ")");
           // Log.d("Single Tap", "Tapped at: (" + x + "," + y + ")");

            if((!grid[j][i].opened) || (grid[j][i].visited == true && grid[j][i].flag== true)){ // stopping to display flag on open cell

                if((grid[j][i].visited == true && grid[j][i].flag== true)){
                   grid[j][i].visited = false;
               }

                if(!grid[j][i].flag){ // checking is flag set or not
                    grid[j][i].flag = true;

                }else{ //clearing flagset
                    grid[j][i].flag = false;
                }
            }
            invalidate();
            return super.onSingleTapConfirmed(e);
        }
    }

 // Adding new instance objects
    public void makeGridObjects() {
        gridClass g;
        grid = new gridClass[16][16];
        for (int i = 0; i < 16; i++) //not sure if I need this
        {
            for (int j = 0; j < 16; j++) {
                grid[i][j] = new gridClass(getContext());
                grid[i][j].rowValue = i;
                grid[i][j].colValue = j;
            }
        }
    }

 // game rest function
    public void resetGame() {
        for (int i = 0; i < 16; i++) //not sure if I need this
        {
            for (int j = 0; j < 16; j++) {
                grid[i][j].mineNumbers = 0;
                grid[i][j].mine = false;
                grid[i][j].opened = false;
                grid[i][j].flag = false;
                grid[i][j].visited = false;
            }
        }

        totalScoretobecounted = 0;
        placedMineCount = 0;
        gameOver = false;
        placeMines(); // calling mine placing
        mineNumbersCount(); // calling counting mine number
    }

//------------------------------------------------------------------------------
// Placing Mine and updating total score to be counted for wining the game
//------------------------------------------------------------------------------
    public void placeMines() {
        int bombsCounter = 47;
        while (bombsCounter > 0) {
            Random rand = new Random();
            int randomNumber = rand.nextInt(255);
            int rowCount = (randomNumber / 16);
            int columnCount = randomNumber % 16;
            int k = 0;
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    if (grid[i][j].rowValue == rowCount && grid[i][j].colValue == columnCount) {
                        grid[i][j].mine = true;
                        grid[i][j].mineNumbers = 15;
                    }
                    k++;
                }
            }
            bombsCounter--;
        }

        for (int i = 1; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (grid[i][j].mine) {
                    placedMineCount++;
                }
            }
        }

        totalScoretobecounted = 256 - placedMineCount;
    }

//------------------------------------------------------------------------------
// Counting near by mineNumbers and updating grid mineNumber
//------------------------------------------------------------------------------
    public void mineNumbersCount() {
        int mineCount;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                mineCount = 0;
                if (i != 0) {
                    if (j != 0) {
                        if (grid[i - 1][j - 1].mine) {
                            mineCount++;
                        }
                    }
                    if (j != 15) {
                        if (grid[i - 1][j + 1].mine) {
                            mineCount++;
                        }
                    }
                    if (grid[i - 1][j].mine) {
                        mineCount++;
                    }
                }
                if (j != 0) {
                    if (grid[i][j - 1].mine) {
                        mineCount++;
                    }
                }
                if (j != 15) {
                    if (grid[i][j + 1].mine) {
                        mineCount++;
                    }
                }
                if (i != 15) {
                    if (j != 0) {
                        if (grid[i + 1][j - 1].mine) {
                            mineCount++;
                        }
                    }
                    if (grid[i + 1][j].mine) {
                        mineCount++;
                    }
                    if (j != 15) {
                        if (grid[i + 1][j + 1].mine) {
                            mineCount++;
                        }
                    }
                }
                grid[i][j].mineNumbers = mineCount;
            }
        }
    }


//------------------------------------------------------------------------------
// Recursively Opening the grid based on calling upperleft, upper, upperright
// left,right ,lowerleft, lower, lower right cell
//------------------------------------------------------------------------------
    public void recursivelyOpen(gridClass gridPoint) {
        // base case
        if (gridPoint.mineNumbers != 0 || gridPoint.visited == true) {
            gridPoint.opened = true; // opening nearby element
            gridPoint.visited = true;
            return;
        }
        // marking grid as visited
        gridPoint.visited = true;
        // marking grid open as true
        gridPoint.opened = true;
        int i = gridPoint.rowValue;
        int j = gridPoint.colValue;
        if (i != 0) {
            if (j != 0) {
                recursivelyOpen(grid[i - 1][j - 1]);
            }
            if (j != 15) {
                recursivelyOpen(grid[i - 1][j + 1]);
            }
            recursivelyOpen(grid[i - 1][j]);
        }
        if (j != 0) {
            recursivelyOpen(grid[i][j - 1]);
        }
        if (j != 15) {
            recursivelyOpen(grid[i][j + 1]);
        }
        if (i != 15) {
            if (j != 0) {
                recursivelyOpen(grid[i + 1][j - 1]);
            }
            recursivelyOpen(grid[i + 1][j]);
            if (j != 15) {
                recursivelyOpen(grid[i + 1][j + 1]);
            }
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 16f;
        height = h / 16f;
        //getRect(selX, selY, selRect);
       // Log.d(TAG, "onSizeChanged: width " + width + ", height "
         //       + height);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // Draw the background...
        Paint background = new Paint();
        background.setColor(getResources().getColor(
                R.color.game_board));
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);
        // Draw the board...
        // Define colors for the grid lines
        Paint dark = new Paint();
        dark.setColor(getResources().getColor(R.color.puzzle_dark));
        Paint hilite = new Paint();
        hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
        Paint light = new Paint();
        light.setColor(getResources().getColor(R.color.puzzle_light));

        // Draw the minor grid lines
        for (int i = 0; i < 16; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height,
                    dark);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, dark);
            canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
            canvas.drawLine(i * width + 1, 0, i * width + 1,
                    getHeight(), dark);
        }

        // Draw the major grid lines
        for (int i = 0; i < 16; i++) {
            // if (i % 3 != 0)
            //   continue;
            canvas.drawLine(0, i * height, getWidth(), i * height,
                    dark);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height
                    + 1, dark);
            canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
            canvas.drawLine(i * width + 1, 0, i * width + 1,
                    getHeight(), dark);
        }


        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
        foreground.setStyle(Style.FILL);
        foreground.setTextSize(height * 0.75f);
        foreground.setTextScaleX(width / height);
        foreground.setTextAlign(Paint.Align.CENTER);
        // Draw the number in the center of the tile
        FontMetrics fm = foreground.getFontMetrics();
        // Centering in X: use alignment (and X at midpoint)
        float x = width / 2;
        // Centering in Y: measure ascent/descent first
        float y = height / 2 - (fm.ascent + fm.descent) / 2;

        Drawable picMine = getResources().getDrawable(R.drawable.mineimage);
        Drawable picFlag = getResources().getDrawable(R.drawable.flagimage);
        Drawable picWhite = getResources().getDrawable(R.drawable.whiteimage);
        Rect rect =  new Rect();

        int currentScoreCount = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (gameOver) // gameover condition
                {
                    if (grid[i][j].mine) {
                        rect.set((int)(i*width),(int)(j*height),(int)(i*width+width),(int)(j*height+height));
                        picMine.setBounds(rect);
                        picMine.draw(canvas);
                    }

                    if (grid[i][j].mine == false && grid[i][j].opened == true) {
                        if (grid[i][j].mineNumbers == 0) {
                            rect.set((int)((i*width)+1.0),(int)((j*height)+1.0),(int)((i*width+width)-2.0),(int)((j*height+height)-2.0));
                            picWhite.setBounds(rect);
                            picWhite.draw(canvas);
                        } else {
                            int mineNum = grid[i][j].mineNumbers;
                            canvas.drawText(this.newGameActivity.getNumString(i, j, mineNum), i
                                    * width + x, j * height + y, foreground);
                        }
                    }
                } else if (grid[i][j].flag) { // flag condition
                    rect.set((int)(i*width),(int)(j*height),(int)(i*width+width),(int)(j*height+height));
                    picFlag.setBounds(rect);
                    picFlag.draw(canvas);

                } else if (grid[i][j].mine == false && grid[i][j].opened == true) {
                    // displaying opened grid if grid has not mine in it.
                    currentScoreCount++;
                    if (grid[i][j].mineNumbers == 0) {
                        rect.set((int)((i*width)+1.0),(int)((j*height)+1.0),(int)((i*width+width)-2.0),(int)((j*height+height)-2.0));
                        picWhite.setBounds(rect);
                        picWhite.draw(canvas);
                    } else {
                        int mineNum = grid[i][j].mineNumbers;
                        canvas.drawText(this.newGameActivity.getNumString(i, j, mineNum), i
                                * width + x, j * height + y, foreground);
                    }
                }
            }
        }

        // Alerting if game is over
        if(gameOver)
        {
           showAlert("Game Over...You Lose !!!");
        }

        // NSLog(@"currentScoreCount %d = ",  currentScoreCount);

        // Alerting win state
        if(currentScoreCount==totalScoretobecounted)
        {
           showAlert("Congratulations !!! You Win");
        }
    }


    public void showAlert(String message) {

        TextView title = new TextView(getContext());
        title.setText("Mine Sweeper");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Mine Sweeper");
        //builder.setCustomTitle(title);
        // builder.setIcon(R.drawable.abc_ic_go);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);
            }
        });
        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                resetGame();
                invalidate();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

