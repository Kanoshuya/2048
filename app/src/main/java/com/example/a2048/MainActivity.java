package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout box;
    private TextView ic[][] = new TextView[4][4];
    private int num[][] = new int[4][4];
    private int last[][] = new int[4][4];
    private int gameover = 0;
    private int step = 0;
    private Button restart;
    private ImageView success;
    private ImageView over;
    private ImageButton back;
    private TextView steps;

    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    private GestureDetector gestureDector;
    private ScreenInfo screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = ScreenInfo.getSreenInfo(this);
        gestureDector = new GestureDetector(MainActivity.this, OnGestureListener);

        box = (RelativeLayout)findViewById(R.id.box);
        restart = (Button)findViewById(R.id.restart);
        success = (ImageView)findViewById(R.id.success);
        back = (ImageButton)findViewById(R.id.back);
        over = (ImageView)findViewById(R.id.gameover);
        steps = (TextView)findViewById(R.id.step);
        ic[0][0] = (TextView)findViewById(R.id.x0_0);
        ic[0][1] = (TextView)findViewById(R.id.x0_1);
        ic[0][2] = (TextView)findViewById(R.id.x0_2);
        ic[0][3] = (TextView)findViewById(R.id.x0_3);
        ic[1][0] = (TextView)findViewById(R.id.x1_0);
        ic[1][1] = (TextView)findViewById(R.id.x1_1);
        ic[1][2] = (TextView)findViewById(R.id.x1_2);
        ic[1][3] = (TextView)findViewById(R.id.x1_3);
        ic[2][0] = (TextView)findViewById(R.id.x2_0);
        ic[2][1] = (TextView)findViewById(R.id.x2_1);
        ic[2][2] = (TextView)findViewById(R.id.x2_2);
        ic[2][3] = (TextView)findViewById(R.id.x2_3);
        ic[3][0] = (TextView)findViewById(R.id.x3_0);
        ic[3][1] = (TextView)findViewById(R.id.x3_1);
        ic[3][2] = (TextView)findViewById(R.id.x3_2);
        ic[3][3] = (TextView)findViewById(R.id.x3_3);
        init();
        restart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                restart.setVisibility(View.INVISIBLE);
                success.setVisibility(View.INVISIBLE);
                over.setVisibility(View.INVISIBLE);
                init();
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                step--;
                for(int i=0;i<4;i++){
                    for(int j=0;j<4;j++){
                        num[i][j] = last[i][j];
                    }
                }
                print();
                restart.setVisibility(View.INVISIBLE);
                success.setVisibility(View.INVISIBLE);
                over.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void init() {
        step = 0;
        gameover = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                num[i][j] = 0;
            }
        }
        int a = 0,b = 0,c = 0,flag = 0;
        while(flag<2) {
            a = (int)(Math.random()*4);
            b = (int)(Math.random()*4);
            c = (int)(Math.random()*2);
            if(num[a][b] == 0){
                if(c != 0)
                    num[a][b] = 2;
                else
                    num[a][b] = 4;
                flag++;
            }
        }
        print();
    }

    private void print() {
        steps.setText(""+step+"");
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                setImage(ic[i][j],num[i][j]);
            }
        }
    }

    private void setImage(TextView tv, int i) {
        switch (i){
            case 0:tv.setBackground(getResources().getDrawable(R.drawable.ic_null));break;
            case 2:tv.setBackground(getResources().getDrawable(R.drawable.ic_2));break;
            case 4:tv.setBackground(getResources().getDrawable(R.drawable.ic_4));break;
            case 8:tv.setBackground(getResources().getDrawable(R.drawable.ic_8));break;
            case 16:tv.setBackground(getResources().getDrawable(R.drawable.ic_16));break;
            case 32:tv.setBackground(getResources().getDrawable(R.drawable.ic_32));break;
            case 64:tv.setBackground(getResources().getDrawable(R.drawable.ic_64));break;
            case 128:tv.setBackground(getResources().getDrawable(R.drawable.ic_128));break;
            case 256:tv.setBackground(getResources().getDrawable(R.drawable.ic_256));break;
            case 512:tv.setBackground(getResources().getDrawable(R.drawable.ic_512));break;
            case 1024:tv.setBackground(getResources().getDrawable(R.drawable.ic_1024));break;
            case 2048:tv.setBackground(getResources().getDrawable(R.drawable.ic_2048));break;
        }
    }

    private GestureDetector.OnGestureListener OnGestureListener  = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent arg0) {
            return false;
        }
        @Override
        public void onShowPress(MotionEvent arg0) {
        }
        @Override
        public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
            return false;
        }
        @Override
        public void onLongPress(MotionEvent arg0) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float arg2, float arg3) {
            float x = e2.getX() - e1.getX();
            float y = e2.getY() - e1.getY();

            //判断为屏幕宽度的1/4
            float x_min = screen.widthPixels / 7;
            //判断为屏幕高度的1/5
            float y_min = screen.heighPixel  / 7;

            if (x > x_min || x < -x_min) {
                if (x > 0) {
                    doResult(RIGHT);
                } else if (x <= 0) {
                    doResult(LEFT);
                }
            }
            if (y > y_min || y < -y_min) {
                if (y > 0) {
                    doResult(DOWN);
                } else if (y <= 0) {
                    doResult(UP);
                }
            }
            return true;
        }
        @Override
        public boolean onDown(MotionEvent arg0) {
            return false;
        }
    };

    public void doResult(int action) {
        save();
        switch (action) {
            case UP:    move(1);up(1);break;
            case DOWN:  move(2);down(2);break;
            case LEFT:  move(3);left(3);break;
            case RIGHT: move(4);right(4);break;
        }
        add();
        gameover = 0;
        for(int i = 0 ; i < 4 ; i++) {
            if(gameover == -1)
                break;
            for(int j = 0 ; j < 4 ; j++) {
                if(num[i][j] == 2048) {
                    gameover = -1;
                    break;
                }
                gameover += judge( i , j , i - 1 , j );
                gameover += judge( i , j , i + 1 , j );
                gameover += judge( i , j , i , j - 1 );
                gameover += judge( i , j , i , j + 1 );
            }
        }
        if(gameover == 16) {
            restart.setVisibility(View.VISIBLE);
            over.setVisibility(View.VISIBLE);
        }
        else if(gameover == -1) {
            restart.setVisibility(View.VISIBLE);
            success.setVisibility(View.VISIBLE);
        }
        int stepadd = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(last[i][j] != num[i][j])
                    stepadd = -1;
            }
        }
        if(stepadd == -1)
            step++;
        print();
    }

    private void save() {
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                last[i][j] = num[i][j];
            }
        }
        back.setVisibility(View.VISIBLE);
    }

    private int judge(int x,int y,int x1,int y1) {
        if(x1 < 0 || y1 < 0 || x1 > 3 || y1 > 3)
            return 1;
        else {
            if(num[x1][y1] == num[x][y] || num[x1][y1] == 0)
                return 1;
            else
                return 0;
        }
    }

    void up(int value) {
        for(int i = 0 ; i < 4 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                if(num[j][i] == num[j+1][i]) {
                    num[j][i] += num[j+1][i];
                    num[j+1][i] = 0;
                    move(value);
                }
            }
        }
    }

    void down(int value) {
        for(int j = 0 ; j < 4 ; j++) {
            for(int i = 3 ; i >= 1 ; i--) {
                if(num[i][j] == num[i-1][j]) {
                    num[i][j] += num[i-1][j];
                    num[i-1][j] = 0;
                    move(value);
                }
            }
        }
    }

    void left(int value) {
        for(int i = 0 ; i < 4 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                if(num[i][j] == num[i][j+1]) {
                    num[i][j] += num[i][j+1];
                    num[i][j+1]=0;
                    move(value);
                }
            }
        }
    }

    void right(int value) {
        for(int i = 0 ; i < 4 ; i++) {
            for(int j = 3 ; j >= 1 ; j--) {
                if(num[i][j] == num[i][j-1]) {
                    num[i][j] += num[i][j-1];
                    num[i][j-1] = 0;
                    move(value);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDector.onTouchEvent(event);
    }

    public void swap(int x1,int y1,int x2,int y2) {
        int temp = num[x1][y1];
        num[x1][y1] = num[x2][y2];
        num[x2][y2] = temp;
    }

    private void move(int value) {
        int i,j,k;
        if(value == 1) {
            for(i = 0 ; i < 4 ; i++) {
                for(j = 1 ; j < 4 ; j++) {
                    k = j;
                    while(k - 1 >= 0 && num[k-1][i] == 0) {
                        swap( k , i , k-1 , i );
                        k--;
                    }
                }
            }
        }
        else if(value == 2) {
            for(i = 0 ; i < 4 ; i++) {
                for(j = 2 ; j >= 0; j--) {
                    k = j;
                    while(k + 1 <= 3 && num[k+1][i] == 0) {
                        swap( k , i , k+1 , i );
                        k++;
                    }
                }
            }
        }
        else if(value == 3) {
            for(i = 0 ; i < 4 ; i++) {
                for(j = 1 ; j < 4 ; j++) {
                    k = j;
                    while(k - 1 >= 0 && num[i][k-1] == 0) {
                        swap( i , k , i , k-1 );
                        k--;
                    }
                }
            }
        }
        else if(value == 4) {
            for(i = 0 ; i < 4 ; i++) {
                for(j = 2 ; j >=0 ; j--) {
                    k = j;
                    while(k + 1 <= 3 && num[i][k+1] == 0) {
                        swap( i , k , i , k+1 );
                        k++;
                    }
                }
            }
        }
    }

    void add() {
        int cube = 0;
        for(int i = 0 ; i < 4 ; i++) {
            if(cube == -1)
                break;
            for(int j = 0 ; j < 4 ; j++) {
                if( num[i][j] != 0)
                    cube ++;
                else {
                    cube = -1;
                    break;
                }
            }
        }
        if(cube == 16)
            return;
        int a = 0,b = 0,c = 0,flag = 0;
        while(flag == 0) {
            a = (int)(Math.random()*4);
            b = (int)(Math.random()*4);
            c = (int)(Math.random()*2);
            if(num[a][b] == 0) {
                if(c != 0)
                    num[a][b] = 2;
                else
                    num[a][b] = 4;
                flag = 1;
            }
        }
    }
}
