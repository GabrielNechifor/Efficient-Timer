package com.example.efficienttimer.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.efficienttimer.R;

public class SettingsTimerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        PreferenceManager.setDefaultValues(this, R.xml.settings_preferences, true);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings_timer_preferences, rootKey);

            Preference preferenceSwitch = findPreference("long_break_enabled");
            preferenceSwitch.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener(){
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        if (newValue instanceof Boolean) {
                            boolean isChecked = (boolean) newValue;
                            if (isChecked){
                                Preference dependentPreference = findPreference("long_break_duration");
                                dependentPreference.setVisible(true);

                                dependentPreference = findPreference("session_number");
                                dependentPreference.setVisible(true);
                            }else{
                                Preference dependentPreference = findPreference("long_break_duration");
                                dependentPreference.setVisible(false);

                                dependentPreference = findPreference("session_number");
                                dependentPreference.setVisible(false);
                            }
                        }
                        return true;
                    }
                });

            androidx.preference.EditTextPreference work_session_duration = getPreferenceManager().findPreference("work_session_duration");
            work_session_duration.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
            });

            androidx.preference.EditTextPreference break_duration = getPreferenceManager().findPreference("break_duration");
            break_duration.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
            });

            androidx.preference.EditTextPreference session_number = getPreferenceManager().findPreference("session_number");
            session_number.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
            });

            androidx.preference.EditTextPreference long_break_duration = getPreferenceManager().findPreference("long_break_duration");
            long_break_duration.setOnBindEditTextListener(new androidx.preference.EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                }
            });

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public Context getContext(){
        Context context = SettingsTimerActivity.this;
        return context;
    }


    public SharedPreferences getPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences;
    }

}