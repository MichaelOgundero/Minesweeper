package com.sexybeast.michael.minesweeper.model;

import java.util.Random;

public class MinesweeperModel {

    private static MinesweeperModel instance = null;

    private MinesweeperModel() {
    }

    public static MinesweeperModel getInstance() {   //getinstance is used to make only instance of the class and the rest refer to this method
        if (instance == null) {
            instance = new MinesweeperModel();
            setBombTiles();
        }

        return instance;
    }

    public static final short openedBomb = 0;
    public static final short openedNoBomb = 1;
    public static final short closedBomb = 2;
    public static final short closedNoBomb = 3;

    public static boolean flag = false;
    public static int noBomb;


    private static short[][] model = {
            {closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb,closedNoBomb,closedNoBomb,closedNoBomb},
            {closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb,closedNoBomb,closedNoBomb,closedNoBomb},
            {closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb,closedNoBomb,closedNoBomb,closedNoBomb},
            {closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb,closedNoBomb,closedNoBomb,closedNoBomb},
            {closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb,closedNoBomb,closedNoBomb,closedNoBomb},
            {closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb,closedNoBomb,closedNoBomb,closedNoBomb},
            {closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb,closedNoBomb,closedNoBomb,closedNoBomb},
            {closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb, closedNoBomb,closedNoBomb,closedNoBomb,closedNoBomb}
    };

    private static boolean[][] flags = {
            {flag,flag,flag,flag, flag,flag,flag,flag},
            {flag,flag,flag,flag, flag,flag,flag,flag},
            {flag,flag,flag,flag, flag,flag,flag,flag},
            {flag,flag,flag,flag, flag,flag,flag,flag},
            {flag,flag,flag,flag, flag,flag,flag,flag},
            {flag,flag,flag,flag, flag,flag,flag,flag},
            {flag,flag,flag,flag, flag,flag,flag,flag},
            {flag,flag,flag,flag, flag,flag,flag,flag}
    };

    public void setField(int x, int y, short tile){
        model[x][y] = tile;
    }

    public short getField(int x, int y){
        return model[x][y];
    }

    public boolean getFlags(int x, int y){
        return flags[x][y];
    }

    public void setFlags(int x, int y, boolean Flag){
        flags[x][y] = Flag;
    }

    public static int bombs = 0;

    public static void setBombTiles(){

        for(int i=0;i<8;i++){
           for(int j=0;j<8;j++){
               Random rand = new Random();
               noBomb = rand.nextInt(64)+1;
               if(noBomb < 19){
                   model[i][j] = closedBomb;
                   bombs++;
               }
           }
       }
    }

    public void resetBoard(){
        for(int i = 0;i<8;i++){
            for(int j=0;j<8;j++){
                model[i][j] = closedNoBomb;
            }
        }
        setBombTiles();

    }

    public void resetFlags(){
        for(int i = 0;i<8;i++){
            for(int j=0;j<8;j++){
                flags[i][j] = flag;
            }
        }

    }


    public int returnBombs(int i, int j){
        int sum = 0;
        if(i==0 && j==0){
            if(model[i+1][j] == closedBomb || model[i+1][j]==openedBomb ){
                sum++;
            }if(model[i][j+1] == closedBomb || model[i][j+1] == openedBomb){
                sum++;
            } if(model[i+1][j+1] == closedBomb ||model[i+1][j+1] == openedBomb){
                sum++;
            }return sum;
        }else if(i==0 && j==7){
            if(model[i+1][j] == closedBomb||model[i+1][j] == openedBomb){
                sum++;
            } if(model[i][j-1] == closedBomb||model[i][j-1] == openedBomb){
                sum++;
            } if(model[i+1][j-1] == closedBomb||model[i+1][j-1] == openedBomb){
                sum++;
            }return sum;
        }else if(i==7 && j==0){
            if(model[i-1][j] == closedBomb||model[i-1][j] == openedBomb){
                sum++;
            } if(model[i][j+1] == closedBomb||model[i][j+1] == openedBomb){
                sum++;
            } if(model[i-1][j+1] == closedBomb||model[i-1][j+1] == openedBomb){
                sum++;
            }return sum;
        }else if(i==7 && j ==7){
            if(model[i][j-1] == closedBomb||model[i][j-1] == openedBomb){
                sum++;
            } if(model[i-1][j] == closedBomb||model[i-1][j] == openedBomb){
                sum++;
            }if(model[i-1][j-1] == closedBomb||model[i-1][j-1] == openedBomb){
                sum++;
            }return sum;
        }else if(i==0){
            if(model[i][j-1] == closedBomb||model[i][j-1] == openedBomb){
                sum++;
            } if(model[i][j+1] == closedBomb||model[i][j+1] == openedBomb){
                sum++;
            } if(model[i+1][j+1] == closedBomb||model[i+1][j+1] == openedBomb){
                sum++;
            } if(model[i+1][j-1] == closedBomb||model[i+1][j-1] == openedBomb){
                sum++;
            }if(model[i+1][j] == closedBomb||model[i+1][j] == openedBomb){
                sum++;
            }return sum;
        }else if(i==7){
            if(model[i][j-1]==closedBomb||model[i][j-1]==openedBomb){
                sum++;
            } if(model[i][j+1]==closedBomb||model[i][j+1]==openedBomb){
                sum++;
            } if(model[i-1][j]==closedBomb||model[i-1][j]==openedBomb){
                sum++;
            } if(model[i-1][j+1]==closedBomb||model[i-1][j+1]==openedBomb){
                sum++;
            } if(model[i-1][j-1]==closedBomb||model[i-1][j-1]==openedBomb){
                sum++;
            }return sum;
        }else if(j==0){
            if(model[i][j+1] == closedBomb||model[i][j+1] == openedBomb){
                sum++;
            } if(model[i-1][j]==closedBomb||model[i-1][j]==openedBomb){
                sum++;
            } if(model[i+1][j]==closedBomb||model[i+1][j]==openedBomb){
                sum++;
            } if(model[i-1][j+1]==closedBomb||model[i-1][j+1]==openedBomb){
                sum++;
            } if(model[i+1][j+1]==closedBomb||model[i+1][j+1]==openedBomb){
                sum++;
            }return sum;
        }else if(j==7){
            if(model[i-1][j]==closedBomb||model[i-1][j]==openedBomb){
                sum++;
            }if(model[i+1][j]==closedBomb||model[i+1][j]==openedBomb){
                sum++;
            } if(model[i][j-1]==closedBomb||model[i][j-1]==openedBomb){
                sum++;
            }if(model[i-1][j-1]==closedBomb||model[i-1][j-1]==openedBomb){
                sum++;
            } if(model[i+1][j-1]==closedBomb||model[i+1][j-1]==openedBomb){
                sum++;
            }return sum;
        }else{
            if(model[i+1][j] == closedBomb||model[i+1][j] == openedBomb){
                sum++;
            } if(model[i-1][j]==closedBomb||model[i-1][j]==openedBomb){
                sum++;
            }if(model[i][j-1]==closedBomb||model[i][j-1]==openedBomb){
                sum++;
            } if(model[i][j+1]==closedBomb||model[i][j+1]==openedBomb){
                sum++;
            } if(model[i-1][j-1]==closedBomb||model[i-1][j-1]==openedBomb){
                sum++;
            } if(model[i+1][j-1]==closedBomb||model[i+1][j-1]==openedBomb){
                sum++;
            } if(model[i-1][j+1]==closedBomb||model[i-1][j+1]==openedBomb){
                sum++;
            } if(model[i+1][j+1]==closedBomb||model[i+1][j+1]==openedBomb){
                sum++;
            } return sum;
        }

    }

}
