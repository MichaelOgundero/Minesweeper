package com.sexybeast.michael.minesweeper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sexybeast.michael.minesweeper.MainActivity;
import com.sexybeast.michael.minesweeper.model.MinesweeperModel;


public class MinesweeperView extends View {

   // private Paint paintText;
    private Paint paintBg;
    private Paint paintLine;
    private Paint paintBombTile;
    private Paint paintTiles;
    private Paint paintClosed;
    private Paint paintFlag;

    private boolean mode;


    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.rgb(255,255, 100));
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);
        paintLine.setTextSize(60);

        paintTiles = new Paint();
        paintTiles.setColor(Color.rgb(0,0, 255));
        paintTiles.setStyle(Paint.Style.FILL);

        paintBombTile = new Paint();
        paintBombTile.setColor(Color.rgb(0,0, 0));
        paintBombTile.setStyle(Paint.Style.FILL);

        paintClosed = new Paint();
        paintClosed.setColor(Color.rgb(255,0, 255));
        paintClosed.setStyle(Paint.Style.FILL);

        paintFlag = new Paint();
        paintFlag.setColor(Color.rgb(255,255, 255));
        paintFlag.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);        //draws the rectangle with the background color set in the constructor

        drawGameGrid(canvas);   //draws the grid set in drawgamegrid
        drawTiles(canvas);
        drawFlag(canvas);
        drawSum(canvas);


    }

    private void drawGameGrid(Canvas canvas) {
        //borders
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);


        // four horizontal lines
        for(int i=1 ;i<8;i++) {
            canvas.drawLine(0, i * getHeight() / 8, getWidth(), i * getHeight() / 8, paintLine);
        }

        // four vertical lines
        for(int i=1 ; i<8; i++) {
            canvas.drawLine(i * getWidth() / 8, 0, i * getWidth() / 8, getHeight(), paintLine);
        }
//
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }



    private void drawTiles(Canvas canvas){

        for(int i = 0; i < 8; i++){
            for(int j = 0; j<8; j++){
                if(MinesweeperModel.getInstance().getField(i,j) == MinesweeperModel.openedBomb) {
                    int left = i * getWidth() / 8 + 20;     // distance of the left side of the rect from the left of canvas
                    int top = j * getHeight() / 8 + 20;       //distance of the top side from the top of canvas
                    int right = getWidth()/12 + left;
                    int bottom =getHeight()/12 + top;                   //distance of bottom from top of canvas

                    canvas.drawRect(left, top, right, bottom, paintBombTile);
                }else if(MinesweeperModel.getInstance().getField(i,j) == MinesweeperModel.openedNoBomb){
                    int left = i* getWidth()/8 + 20;
                    int top = j * getHeight()/8 + 20;
                    int right = getWidth()/12 + left;
                    int bottom =getHeight()/12 + top;;
                    canvas.drawRect(left, top, right, bottom, paintTiles);
                }else if(MinesweeperModel.getInstance().getField(i,j) == MinesweeperModel.closedNoBomb || MinesweeperModel.getInstance().getField(i,j) == MinesweeperModel.closedBomb ){
                    int left = i* getWidth()/8 + 20;
                    int top = j * getHeight()/8 + 20;
                    int right = getWidth()/12 + left;
                    int bottom =getHeight()/12 + top;

                    canvas.drawRect(left, top, right, bottom, paintClosed);
                }

            }
        }

    }
    public int flag = 0;
    private void drawFlag(Canvas canvas) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
               if(MinesweeperModel.getInstance().getFlags(i, j) == true){
                   int left = i* getWidth()/8 + 20;
                   int top = j * getHeight()/8 + 20;
                   int right = getWidth()/12 + left;
                   int bottom =getHeight()/12 + top;

                   canvas.drawRect(left, top, right, bottom, paintFlag);
               }
            }
        }
    }

    public void setMode(boolean mode){
        this.mode = mode;
    }



    boolean game = true;

    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction() == MotionEvent.ACTION_DOWN && game == true) {
            int tX = ((int) event.getX()) / (getWidth() / 8);
            int tY = ((int) event.getY()) / (getHeight() / 8);

            if (MinesweeperModel.getInstance().getField(tX, tY) == MinesweeperModel.closedBomb && mode == false) {
                MinesweeperModel.getInstance().setField(tX, tY, MinesweeperModel.openedBomb);
                MinesweeperModel.getInstance().setFlags(tX, tY, false);
                game = false;
                MainActivity.playBombSound(true);
                MainActivity.setText("You Lose, hit reset to try again", 0xffff0000);
                MainActivity.setMessage(1);

            } else if (MinesweeperModel.getInstance().getField(tX, tY) == MinesweeperModel.closedNoBomb && mode ==false) {
                MinesweeperModel.getInstance().setField(tX, tY, MinesweeperModel.openedNoBomb);
                MinesweeperModel.getInstance().setFlags(tX, tY, false);
                //if you flag a normal tile you lose

            }  if(mode == true && MinesweeperModel.getInstance().getField(tX, tY) == MinesweeperModel.closedNoBomb ){
                MinesweeperModel.getInstance().setFlags(tX, tY, true);
                game = false;
                MainActivity.playBombSound(true);
                MainActivity.setText("You Lose, hit reset to try again", 0xffff0000);

                //if you flag a bomb tile
            }  if(mode == true && MinesweeperModel.getInstance().getField(tX, tY) == MinesweeperModel.closedBomb ){
                MinesweeperModel.getInstance().setFlags(tX, tY, true);
                 flag++;
                 //if you flag all the bombs you win
                if(flag == MinesweeperModel.bombs){
                    game = false;
                    MainActivity.playVictorySound(true);
                    MainActivity.setText("You win", 0xff02fc17);
                    MainActivity.setMessage(2);
                }
            }
        }
        invalidate();
        return true;
    }



    private void drawSum(Canvas canvas){
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if(MinesweeperModel.getInstance().getField(i,j) == MinesweeperModel.openedNoBomb){
                    int left = i * getWidth() / 8 + 20;     // distance of the left side of the rect from the left of canvas
                    int top = j * getHeight() / 8 + 20;       //distance of the top side from the top of canvas
                    int right = 90 + left;                   // distance of right from the left side of canvas
                    int bottom = 90 + top;

                    canvas.drawText(String.valueOf(MinesweeperModel.getInstance().returnBombs(i,j)), left+getWidth()/42, top+getHeight()/16, paintLine);
                }
            }
        }
    }

    public void resetGame(){
        MinesweeperModel.getInstance().resetBoard();
        MinesweeperModel.getInstance().resetFlags();
        game = true;
        MainActivity.playBombSound(false);
        MainActivity.playVictorySound(false);
        MainActivity.getToggleFlag().setChecked(false);
        setMode(false);
        MainActivity.setText(" ", 0xffffffff);
        invalidate();
    }



}
