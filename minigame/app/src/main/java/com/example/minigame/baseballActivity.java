package com.example.minigame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class baseballActivity extends AppCompatActivity {

    int[] comNumbers = new int[4];
    int inputTextCount = 0;
    int okCount = 1;

    TextView[] inputTextView = new TextView[4];
    Button[] numButton = new Button[10];
    ImageButton backSpaceButton;
    Button okButton;
    TextView resultTextView;
    ScrollView scrollView;

    SoundPool soundPool;
    int[] buttonSound = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseball);
        comNumbers = getComNumbers();

        for (int i = 0; i<inputTextView.length; i++){
            inputTextView[i] = findViewById(R.id.input_text_view_0 + i);
        }

        for (int i = 0; i<numButton.length; i++){
            numButton[i] = findViewById(R.id.num_button_0 + i);
        }

        backSpaceButton = findViewById(R.id.back_space_button);
        okButton = findViewById(R.id.ok_button);
        resultTextView = findViewById(R.id.result_text_view);
        scrollView = findViewById(R.id.scroll_view);


        for(final Button getNumButton : numButton){
            getNumButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    numButtonClick(view);

                }
            });
        }

        backSpaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backSpaceClick();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okButtonClick();
            }
        });

    }

    private void okButtonClick() {
        if(inputTextCount < 4){
            Toast.makeText(getApplicationContext(), "숫자를 입력해 주세요", Toast.LENGTH_SHORT).show();
        }
        else{
            int[] userNumbers = new int[4];
            for (int i = 0; i < userNumbers.length; i++) {
                userNumbers[i] = Integer.parseInt(inputTextView[i].getText().toString());
            }
            int[] countCheck = getCountCheck(comNumbers, userNumbers);

            Log.e("okButton" , "countCheck = S : " + countCheck[0] + "  B : " + countCheck[1]);

            String resultCount = getCountString(userNumbers, countCheck);

            if(okCount == 1){
                resultTextView.setText(resultCount);
                resultTextView.append("\n");
            }
            else{
                resultTextView.append(resultCount);
                resultTextView.append("\n");
            }

            if(countCheck[0]==4){
                okCount = 1;
                comNumbers = getComNumbers();
            }
            else{
                okCount++;
            }

            scrollView.fullScroll(View.FOCUS_DOWN);


            for (int i = 0; i < inputTextView.length ; i++) {
                backSpaceClick();
            }
            inputTextCount = 0;

        }
    }

    private String getCountString(int[] userNumbers, int[] countCheck) {
        String resultCount;
        if(countCheck[0] == 4){
            resultCount = okCount +  ".  [" + userNumbers[0] + " " + userNumbers[1] + " "
                    + userNumbers[2] + " "+ userNumbers[3] + "]  정답 - 축하드립니다. ";
            soundPool.play(buttonSound[2], 1, 1, 1, 0, 1);
        }
        else{
            resultCount = okCount + ".  [" + userNumbers[0] + " " + userNumbers[1] + " "
                    + userNumbers[2] + " "+ userNumbers[3] + "] S : " + countCheck[0] + "  B: " + countCheck[1];
            soundPool.play(buttonSound[0], 1, 1, 1, 0, 1);
        }
        return resultCount;
    }

    private void backSpaceClick() {
        if(inputTextCount > 0) {
            int buttonEnableCount =  Integer.parseInt(inputTextView[inputTextCount - 1].getText().toString());
            numButton[buttonEnableCount].setEnabled(true);
            inputTextView[inputTextCount - 1].setText("");
            inputTextCount--;
            soundPool.play(buttonSound[1], 1, 1, 1, 0, 1);
        }
        else{
            Toast.makeText(getApplicationContext(), "숫자를 입력해 주세요", Toast.LENGTH_SHORT).show();
        }
    }

    private void numButtonClick(View view) {
        if(inputTextCount < 4) {
            Button button = findViewById(view.getId());
            inputTextView[inputTextCount].setText(button.getText().toString());
            button.setEnabled(false);
            inputTextCount++;
            soundPool.play(buttonSound[1], 1, 1, 1, 0, 1);
        }

        else{
            Toast.makeText(getApplicationContext(), "OK 버튼을 눌러주세요",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private int[] getCountCheck(int[] comNumbers, int[] userNumbers) {
        int[] setCount = new int[2];
        for (int i = 0; i < comNumbers.length; i++) {
            for (int j = 0; j < userNumbers.length; j++) {
                if (comNumbers[i] == userNumbers[j] ) {
                    if (i == j){
                        setCount[0]++;
                    }
                    else{
                        setCount[1]++;
                    }
                }
            }
        }

        return setCount;
    }

    public int[] getComNumbers() {
        int[] setComNumbers = new int[4];

        for (int i=0; i<setComNumbers.length; i++){
            setComNumbers[i] = new Random().nextInt(10);
            for (int j = 0; j<i; j++){
                if (setComNumbers[i] == setComNumbers[j]) {
                    i--;
                    break;
                }
            }
        }

        Log.e("setComNumbers", "setComNumbers = " + setComNumbers[0] + ","
                + setComNumbers[1] + "," + setComNumbers[2] + "," + setComNumbers[3]);
        return setComNumbers;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onStart() {
        super.onStart();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(6)
                    .build();
        }
        else{
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }

        for (int i = 0; i < buttonSound.length; i++) {
            buttonSound[i] = soundPool.load(getApplicationContext(), R.raw.button1+i, 1);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        soundPool.release();
    }
}