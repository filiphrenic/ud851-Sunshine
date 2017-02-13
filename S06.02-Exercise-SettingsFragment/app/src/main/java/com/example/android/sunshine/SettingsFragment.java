package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import static android.R.attr.key;
import static android.R.attr.value;

// COMPLETED (10) Implement OnSharedPreferenceChangeListener from SettingsFragment

// COMPLETED (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference

// COMPLETED (5) Override onCreatePreferences and add the preference xml file using addPreferencesFromResource

// Do step 9 within onCreatePreference
// COMPLETED (9) Set the preference summary on each preference that isn't a CheckBoxPreference

// COMPLETED (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop

// COMPLETED (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart

// COMPLETED (11) Override onSharedPreferenceChanged to update non CheckBoxPreferences when they are changed

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);

        PreferenceScreen screen = getPreferenceScreen();
        SharedPreferences sharedPreferences = screen.getSharedPreferences();
        int count = screen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            setPreferenceSummary(sharedPreferences, screen.getPreference(i));
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        setPreferenceSummary(sharedPreferences, findPreference(key));
    }

    private void setPreferenceSummary(SharedPreferences sharedPreferences, Preference preference) {
        String value = sharedPreferences.getString(preference.getKey(), "");
        if (preference instanceof EditTextPreference) {
            preference.setSummary(value);
        } else if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int idx = listPreference.findIndexOfValue(value);
            if (idx >= 0) {
                listPreference.setSummary(listPreference.getEntries()[idx]);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
