package com.terabyte.timer001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChooseSoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sound);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.teal_700));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.menu_choose_sound));

        RadioGroup radioGroupSounds = findViewById(R.id.radioGroupSounds);
        int savedNumberRadioButton = getSharedPreferences(Constant.SH_PREFERENCES_SOUND_NAME, MODE_PRIVATE).getInt(Constant.SH_PREFERENCES_KEY_CHOSE_SOUND_NUMBER, 0);
        RadioButton radioButton = (RadioButton) radioGroupSounds.getChildAt(savedNumberRadioButton);
        radioButton.setChecked(true);

        radioGroupSounds.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                SharedPreferences.Editor editor = getSharedPreferences(Constant.SH_PREFERENCES_SOUND_NAME, MODE_PRIVATE).edit();
                switch(i) {
                    case R.id.radioButtonSound1:
                        editor.putInt(Constant.SH_PREFERENCES_KEY_CHOSE_SOUND_NUMBER, 0);
                        break;
                    case R.id.radioButtonSound2:
                        editor.putInt(Constant.SH_PREFERENCES_KEY_CHOSE_SOUND_NUMBER, 1);
                        break;
                    case R.id.radioButtonSound3:
                        editor.putInt(Constant.SH_PREFERENCES_KEY_CHOSE_SOUND_NUMBER, 2);
                        break;
                    case R.id.radioButtonSound4:
                        editor.putInt(Constant.SH_PREFERENCES_KEY_CHOSE_SOUND_NUMBER, 3);
                        break;
                    case R.id.radioButtonSound5:
                        editor.putInt(Constant.SH_PREFERENCES_KEY_CHOSE_SOUND_NUMBER, 4);
                        break;
                }
                editor.commit();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}