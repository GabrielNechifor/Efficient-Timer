package com.example.efficienttimer.main;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class CustomCountDownTimer {
    private static TextView countView;
    private static int minutes_work = 0;
    private static int minutes_pause = 0;
    public static Boolean long_break_enabled = false;
    public static int minutes_long_break = 0;
    private static int total_number_sessions = 0;

    private static int current_millis = 0;
    private static int current_minutes = 0;
    private static int current_session = 0;
    public static boolean inWorkSession = true;
    private static MyCount counterSeconds;
    private static Boolean paused = true;
    public static Boolean stopped = true;

    private static Button session_counter;
    private static TextView current_doing;

    public static Boolean isPaused(){
        return paused;
    }

    public static Boolean isStopped(){
        return stopped;
    }

    public static void start(TextView textView, int minutes_work, int minutes_pause, Boolean long_break_enabled, int minutes_long_break, int total_number_sessions, Button sessions_button, TextView current_doing){
        CustomCountDownTimer.countView = textView;
        CustomCountDownTimer.minutes_work = minutes_work;
        CustomCountDownTimer.minutes_pause = minutes_pause;
        CustomCountDownTimer.minutes_long_break = minutes_long_break;
        CustomCountDownTimer.long_break_enabled = long_break_enabled;
        CustomCountDownTimer.total_number_sessions = total_number_sessions;
        CustomCountDownTimer.session_counter = sessions_button;
        CustomCountDownTimer.current_doing = current_doing;

        current_minutes =  minutes_work - 1;
        current_millis = 59;
        current_session = 0;
        counterSeconds = new MyCount(current_millis*1000, 1000);
        counterSeconds.start();
        stopped = false;
        paused = false;
        inWorkSession = true;
        current_session = 1;
        current_doing.setText("Work");
    }

    public static void pause(){
        counterSeconds.cancel();
        paused = true;
    }

    public static void resume(){
        counterSeconds = new MyCount(current_millis*1000, 1000);
        counterSeconds.start();
        paused = false;
    }

    public static void stop(){
        counterSeconds.cancel();
        current_millis = 0;
        current_session = 0;
        current_minutes = minutes_work;
        stopped = true;
        paused = true;
    }

    private static class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if(current_minutes > 0){
                restartCountdown(current_minutes);
            }else if(!long_break_enabled && inWorkSession){
                inWorkSession = false;
                restartCountdown(minutes_pause);
                current_doing.setText("Short Break");
                Log.i("status", "In short pause");
            }else if(!long_break_enabled && !inWorkSession){
                inWorkSession = true;
                restartCountdown(minutes_work);
                current_session += 1;
                session_counter.setText(String.valueOf(current_session-1));
                current_doing.setText("Work");
                Log.i("status", "In work");
            }else if(long_break_enabled && inWorkSession && (current_session % total_number_sessions != 0)){
                inWorkSession = false;
                restartCountdown(minutes_pause);
                current_doing.setText("Short Break");
                Log.i("status", "In short pause");
            }else if(long_break_enabled && inWorkSession && (current_session % total_number_sessions == 0)){
                inWorkSession = false;
                restartCountdown(minutes_long_break);
                current_doing.setText("Long Break");
                Log.i("status", "In long pause");
            }else if(long_break_enabled && !inWorkSession){
                inWorkSession = true;
                restartCountdown(minutes_work);
                current_session += 1;
                session_counter.setText(String.valueOf(current_session-1));
                current_doing.setText("Work");
                Log.i("status", "In work");
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {

            current_millis = (int) (millisUntilFinished / 1000);
            String toDisplay = "";
            if (current_minutes <= 9 && current_minutes>=0){
                toDisplay += "0" + Integer.toString(current_minutes);
            }else{
                toDisplay += Integer.toString(current_minutes);
            }
            toDisplay += ":";
            if (current_millis <= 9 && current_millis>=0){
                toDisplay += "0" + Integer.toString(current_millis);
            }else{
                toDisplay += Integer.toString(current_millis);
            }
            countView.setText( toDisplay );
        }
    }


    private static void restartCountdown(int minutes){
        current_minutes = minutes -  1;
        current_millis = 59;
        counterSeconds.cancel();
        counterSeconds = new MyCount(current_millis*1000, 1000);
        counterSeconds.start();
    }



}
