package com.app.ray.testingtimer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView timer;
    private Button start;
    private Button stop;
    private Button zero;
    private Button end;
    private boolean startflag = false;
    private int tms = 0, csec = 0, cmin = 0, cms = 0, chr = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        zero = (Button) findViewById(R.id.zero);
        end = (Button) findViewById(R.id.end);

        //宣告Timer
        Timer timer01 = new Timer();

        //設定Timer(task為執行內容，0代表立刻開始,間格10豪秒執行一次)
        timer01.schedule(task, 0, 10);

        //Button監聽
        start.setOnClickListener(listener);
        stop.setOnClickListener(listener);
        zero.setOnClickListener(listener);
        end.setOnClickListener(listener);
    }

    //TimerTask無法直接改變元件因此要透過Handler來當橋樑
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    cms = tms % 100;
                    csec = (tms / 100) % 60;
                    cmin = (tms / 100) / 60 % 60;
                    chr = ((tms / 100) / 60) / 60;
                    StringBuffer sb = new StringBuffer();
                    if(chr>0){
                        if(chr < 10){
                            sb.append("0").append(chr).append(":");
                        }else {
                            sb.append(chr).append(":");
                        }
                    }
                    if (cmin < 10) {
                        sb.append("0").append(cmin).append(":");
                    } else {
                        sb.append(cmin).append(":");
                    }
                    if (csec < 10) {
                        sb.append("0").append(csec);
                    } else {
                        sb.append(csec);
                    }

                    if (cms < 10) {
                        sb.append(".0").append(cms);
                    } else {
                        sb.append(".").append(cms);
                    }

                    //s字串為00:00.00格式
                    timer.setText(sb.toString());
                    break;
            }
        }
    };

    private TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (startflag) {
                //如果startflag是true則每秒tsec+1
                tms++;
                Message message = new Message();

                //傳送訊息1
                message.what = 1;
                handler.sendMessage(message);
            }
        }

    };

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.start:
                    startflag = true;
                    break;
                case R.id.stop:
                    startflag = false;
                    break;
                case R.id.zero:
                    tms = 0;
                    //TextView 初始化
                    timer.setText("00:00.00");
                    break;
                case R.id.end:
                    finish();
                    break;
            }
        }

    };

}
