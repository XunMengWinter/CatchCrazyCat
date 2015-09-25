package myapplication.nomasp.com.catchcrazycat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * Created by nomasp on 2015/09/25.
 */

public class Playground extends SurfaceView{

    private static final int COL = 10;   // 每列有10个元素
    private static final int ROW = 10;   // 每行有10个元素
    private static final int BLOCKS = 10;   // 默认添加的路障数量

    private Dot matrix[][];  // 声明数组matrix来保存游戏元素
    private Dot cat;    // 猫

    public Playground(Context context) {
        super(context);
        getHolder().addCallback(callback);  // 为getHolder设置回调
        matrix = new Dot[ROW][COL];   // 初始化
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                matrix[i][j]=new Dot(j,i);  // x表示行，y表示列，因此和数组的COL/ROW是错开的
            }
        }
        initGame();
    }

    private void redraw(){
        Canvas c = getHolder().lockCanvas();  // 先上锁
        c.drawColor(Color.CYAN);   // 为canvas设置为青色
        getHolder().unlockCanvasAndPost(c);   // 解锁
    }

    Callback callback = new Callback() {    // 回调方法
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            redraw();  // 调用redraw进行重绘
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    private void initGame(){
        for (int i = 0; i < ROW; i++){
            for (int j = 0; j < COL; j++){
                matrix[i][j].setStatus(Dot.STATUS_OFF);
            }
        }
        cat = new Dot(ROW/2,COL/2);   // 初始化猫的位置
        getDot(ROW/2,COL/2).setStatus(Dot.STATUS_IN);  // 初始化猫所在位置的状态
        for(int i = 0; i < BLOCKS;){
            int x = (int)((Math.random()*1000)%COL);  // x为横坐标，数组中的列
            int y = (int)((Math.random()*1000)%ROW);  // y为纵坐标，数组中的行
            if(getDot(x,y).getStatus() == Dot.STATUS_OFF){   // 如果当前Dot的状态为OFF
                getDot(x,y).setStatus(Dot.STATUS_ON);        // 则将其打开，并让i自增
                i++;
                System.out.println("BLOCKS+"+i);
            }
        }
    }

    private Dot getDot(int x, int y){  // 传入x,y返回matrix[y][x]
        return matrix[y][x];
    }
}
