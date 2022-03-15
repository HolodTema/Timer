package com.terabyte.timer001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    //sleep layout
    NumberPicker  numberPickerHours, numberPickerMinutes, numberPickerSeconds;
    //run layout
    TextView textRunningTimer;
    ProgressBar progressRunningTimer;
    int mode;
    TimerTask timerTask;
    TimeFrame timeFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.teal_700));

        // TODO: 11.02.2022 we set mod by timer running
        if(savedInstanceState==null) {
            SharedPreferences shPreferences = getSharedPreferences(Constant.SH_PREFERENCES_NAME, MODE_PRIVATE);
            mode = shPreferences.getInt(Constant.SH_PREFERENCES_KEY_MODE, Constant.MODE_SLEEP);
            if(mode == Constant.MODE_RUN | mode == Constant.MODE_PAUSED) {
                timeFrame = new TimeFrame(shPreferences.getInt(Constant.SH_PREFERENCES_KEY_HOURS, 0), shPreferences.getInt(Constant.SH_PREFERENCES_KEY_MINUTES, 0), shPreferences.getInt(Constant.SH_PREFERENCES_KEY_SECONDS, 0));
            }
        }
        else {
            mode = savedInstanceState.getInt(Constant.INTENT_KEY_MODE);
            if(mode == Constant.MODE_RUN | mode == Constant.MODE_PAUSED) {
                timeFrame = savedInstanceState.getParcelable(Constant.INTENT_KEY_TIME_FRAME);
            }

        }
        if(mode == Constant.MODE_RUN) {
            setRunLayout(timeFrame);
            timerTask = new TimerTask();
            timerTask.execute(timeFrame);
        }
        if(mode == Constant.MODE_PAUSED) {
            setPausedLayout(timeFrame);
        }
        if(mode==Constant.MODE_RING) {
            setRingLayout();
        }
        if(mode==Constant.MODE_SLEEP) {
            setSleepLayout();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mode==Constant.MODE_RUN) {
            timerTask.isRunning = false;
            timerTask = new TimerTask();
            timerTask.execute(timeFrame);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(Constant.INTENT_KEY_MODE, mode);
        if(mode == Constant.MODE_RUN | mode == Constant.MODE_PAUSED) {
            outState.putParcelable(Constant.INTENT_KEY_TIME_FRAME, timeFrame);
        }
        if(mode==Constant.MODE_RUN) {
            timerTask.isRunning = false;
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuItemChooseSound:
                SharedPreferences.Editor editor = getSharedPreferences(Constant.SH_PREFERENCES_NAME, MODE_PRIVATE).edit();
                editor.putInt(Constant.SH_PREFERENCES_KEY_MODE, mode);
                if(mode==Constant.MODE_RUN | mode==Constant.MODE_PAUSED) {
                    editor.putInt(Constant.SH_PREFERENCES_KEY_HOURS, timeFrame.hours);
                    editor.putInt(Constant.SH_PREFERENCES_KEY_MINUTES, timeFrame.minutes);
                    editor.putInt(Constant.SH_PREFERENCES_KEY_SECONDS, timeFrame.seconds);
                }
                editor.commit();
                startActivity(new Intent(getApplicationContext(), ChooseSoundActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickButtonStart(View view) {
        if(mode==Constant.MODE_SLEEP) {
            timeFrame = new TimeFrame(numberPickerHours.getValue(), numberPickerMinutes.getValue(), numberPickerSeconds.getValue());
            if(!timeFrame.isFinished()) {
                mode = Constant.MODE_RUN;
                setRunLayout(timeFrame);
                timerTask = new TimerTask();
                timerTask.execute(timeFrame);
            }
        }
    }

    public void onClickButtonPause(View view) {
        if(mode==Constant.MODE_RUN) {
            timerTask.isRunning = false;
            mode = Constant.MODE_PAUSED;
            setPausedLayout(timeFrame);
        }
    }

    public void onClickButtonResume(View view) {
        if(mode==Constant.MODE_PAUSED) {
            mode = Constant.MODE_RUN;
            setRunLayout(timeFrame);

            timerTask = new TimerTask();
            timerTask.execute(timeFrame);
        }
    }

    public void onClickButtonCancel(View view) {
        if(mode==Constant.MODE_RUN) {
            timerTask.isRunning = false;
            mode = Constant.MODE_SLEEP;
            setSleepLayout();
        }
        if(mode==Constant.MODE_PAUSED) {
            mode = Constant.MODE_SLEEP;
            setSleepLayout();
        }
    }

    private void setSleepLayout() {
        setContentView(R.layout.activity_main_sleep);
        numberPickerHours = findViewById(R.id.numberPickerHours);
        numberPickerMinutes = findViewById(R.id.numberPickerMinutes);
        numberPickerSeconds = findViewById(R.id.numberPickerSeconds);
        numberPickerHours.setMinValue(0);
        numberPickerMinutes.setMinValue(0);
        numberPickerSeconds.setMinValue(0);
        numberPickerHours.setMaxValue(99);
        numberPickerMinutes.setMaxValue(99);
        numberPickerSeconds.setMaxValue(99);
    }

    private void setRunLayout(TimeFrame timeFrame) {
        setContentView(R.layout.activity_main_run);
        textRunningTimer = findViewById(R.id.textRunningTimer);
        progressRunningTimer = findViewById(R.id.progressRunningTimer);

        textRunningTimer.setText(timeFrame.toString());
        progressRunningTimer.setMax(timeFrame.getProgress());
        progressRunningTimer.setProgress(timeFrame.getProgress());
    }

    private void setPausedLayout(TimeFrame timeFrame) {
        setContentView(R.layout.activity_main_paused);
        TextView textPausedTimer = findViewById(R.id.textPausedTimer);
        textPausedTimer.setText(timeFrame.toString());
    }

    private void setRingLayout() {
        setContentView(R.layout.activity_main_ring);
    }
    public void onClickButtonStopRinging(View view) {
        if(mode==Constant.MODE_RING) {
            MediaPlayerClient.getInstance(getApplicationContext(), R.raw.timer_sound1).stop();
            MediaPlayerClient.setNullMediaPlayerInstance();
            mode = Constant.MODE_SLEEP;
            setSleepLayout();
        }
    }

    class TimerTask extends AsyncTask<TimeFrame, TimeFrame, TimeFrame> {
        public boolean isRunning;
        private TimeFrame timeFrame;

        public TimerTask() {
            isRunning = true;
        }


        @Override
        protected TimeFrame doInBackground(TimeFrame... timeFrames) {
            timeFrame = timeFrames[0];
            long currTime1 = System.currentTimeMillis();
            while(isRunning) {
                long currTime2 = System.currentTimeMillis();
                if(currTime2-currTime1>=1000) {
                    currTime1 = System.currentTimeMillis();

                    timeFrame.decrementTime();
                    publishProgress(timeFrame);
                    if(timeFrame.isFinished()) {
                        isRunning = false;
                    }
                }
            }

            return timeFrame;
        }

        @Override
        protected void onProgressUpdate(TimeFrame... values) {
            super.onProgressUpdate(values);
            TimeFrame timeFrame = values[0];
            if(mode==Constant.MODE_RUN) {
                textRunningTimer.setText(timeFrame.toString());
                progressRunningTimer.setProgress(timeFrame.getProgress());
            }
        }

        @Override
        protected void onPostExecute(TimeFrame timeFrame) {
            super.onPostExecute(timeFrame);
            if(timeFrame.isFinished()) {
                //we are ringing
                mode = Constant.MODE_RING;
                setRingLayout();

                int soundNumber = getSharedPreferences(Constant.SH_PREFERENCES_SOUND_NAME, MODE_PRIVATE).getInt(Constant.SH_PREFERENCES_KEY_CHOSE_SOUND_NUMBER, 0);

                MediaPlayer mediaPlayer = MediaPlayerClient.getInstance(getApplicationContext(), Constant.SOUND_ARRAY[soundNumber]);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        }
    }
}