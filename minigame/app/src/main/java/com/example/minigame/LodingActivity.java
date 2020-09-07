package com.example.minigame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LodingActivity extends AppCompatActivity {
    private Handler handler;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(LodingActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);

        getSupportActionBar().hide();

        //1000=1초뒤에 다음을 실행
        handler = new Handler();
        handler.postDelayed(runnable, 10000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}