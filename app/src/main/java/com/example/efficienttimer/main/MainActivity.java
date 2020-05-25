package com.example.efficienttimer.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.efficienttimer.R;
import com.example.efficienttimer.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView countdown;
    SharedPreferences preferences;
    Button numberOfSession;
    TextView current_doing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        ImageButton settings = (ImageButton) findViewById(R.id.settings);
        settings.setOnClickListener(this);

        countdown = (TextView) findViewById(R.id.countdown);
        countdown.setOnClickListener(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String minutes_work = preferences.getString("work_session_duration", "0");
        if(Integer.parseInt(minutes_work)>=0 && Integer.parseInt(minutes_work)<=9){
            minutes_work = "0" + minutes_work;
        }
        String initialTime = minutes_work + ":" + "00";
        countdown.setText(initialTime);

        numberOfSession = findViewById(R.id.sessions);
        numberOfSession.setText("0");
        numberOfSession.setOnClickListener(this);

        current_doing = findViewById(R.id.current_doing);
        current_doing.setText("Work");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.sessions:
                CustomCountDownTimer.stop();
                preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String minutes = preferences.getString("work_session_duration", "0");
                if(Integer.parseInt(minutes)>=0 && Integer.parseInt(minutes)<=9){
                    minutes = "0" + minutes;
                }
                String initialTime = minutes + ":" + "00";
                countdown.setText(initialTime);
                numberOfSession.setText("0");
                break;
            case R.id.countdown:
                if (CustomCountDownTimer.isStopped()){
                    String minutes_work = preferences.getString("work_session_duration", "0");
                    String minutes_pause = preferences.getString("break_duration", "0");
                    Boolean long_break_enables = preferences.getBoolean("long_break_enabled", false);
                    String minutes_long_pause = preferences.getString("long_break_duration", "0");
                    String number_of_sessions = preferences.getString("session_number", "0");

                    CustomCountDownTimer.start(countdown, Integer.parseInt(minutes_work), Integer.parseInt(minutes_pause), long_break_enables, Integer.parseInt(minutes_long_pause), Integer.parseInt(number_of_sessions), numberOfSession, current_doing );
                }else if(CustomCountDownTimer.isPaused()){
                    CustomCountDownTimer.resume();
                }else if(!CustomCountDownTimer.isPaused()){
                    CustomCountDownTimer.pause();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        countdown.setEnabled(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        countdown.setEnabled(true);
    }

    @Override
    protected void onStart(){
        super.onStart();
        countdown.setEnabled(true);
    }

    @Override
    protected void onPause(){
        super.onPause();
        countdown.setEnabled(true);
        CustomCountDownTimer.stop();
    }

    @Override
    protected void onStop(){
        super.onStop();
        countdown.setEnabled(true);
        CustomCountDownTimer.stop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        countdown.setEnabled(true);
        CustomCountDownTimer.stop();
    }











    }
