package com.example.app_cronometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn_cronometro;
    private boolean cronometro = true;
    private TextView txt_tiempo;
    private LinearLayout layouts;
    Thread cronom;
    Handler h = new Handler();
    private int minutes = 0, seconds = 0, mills = 0;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_cronometro = (Button) findViewById(R.id.btnAction);
        txt_tiempo = (TextView) findViewById(R.id.lblTime);
        layouts = (LinearLayout) findViewById(R.id.llyFlags);
        cronom = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (!cronometro){
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){}
                        mills++;
                        if (mills==999){
                            seconds++;
                            mills=0;
                        }
                        if (seconds==59){
                            minutes++;
                            seconds=0;
                        }
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                txt_tiempo.setText(formatedTime());
                            }
                        });
                    }
                }
            }
        });
        cronom.start();
    }

    public void Continuar(View view){
        if (cronometro){
            btn_cronometro.setText(R.string.pausar);
            cronometro = false;
        } else {
            btn_cronometro.setText(R.string.iniciar);
            cronometro = true;
        }
    }
    public void Registro(View view){
        if(!cronometro){
            TextView lblStep = new TextView(this);
            flag++;
            lblStep.setText(flag+" - "+formatedTime());
            lblStep.setTextSize(20);
            lblStep.setGravity(Gravity.CENTER);
            layouts.addView(lblStep);
        }
    }
    public void Reseteo(View view){
        cronometro = true;
        layouts.removeAllViews();
        btn_cronometro.setText("Start");
        txt_tiempo.setText(R.string.tiempo);
        mills = 0;
        seconds = 0;
        minutes = 0;
        flag = 0;
    }
    private String formatedTime(){
        String m = "", s = "", mi = "";
        if (mills<10){
            m="00"+mills;
        } else if (mills < 100) {
            m="0"+mills;
        }else {
            m=""+mills;
        }
        if (seconds<10){
            s="0"+seconds;
        }else{
            s=""+seconds;
        }
        if (minutes<10){
            mi="0"+minutes;
        }else{
            mi=""+minutes;
        }
        return mi+":"+s+":"+m;
    }

}

