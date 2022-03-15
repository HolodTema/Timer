package com.terabyte.timer001;

import java.util.Calendar;

public class Constant {
    public static final int MODE_SLEEP = 0;
    public static final int MODE_RUN = 1;
    public static final int MODE_PAUSED = 2;
    public static final int MODE_RING = 3;

    public static final String INTENT_KEY_MODE = "intentKeyMode";
    public static final String INTENT_KEY_TIME_FRAME = "intentKeyTimeFrame";

    public static final String SH_PREFERENCES_NAME = "sharedPreferences";
    public static final String SH_PREFERENCES_KEY_MODE = "shPreferencesKeyMode";
    public static final String SH_PREFERENCES_KEY_HOURS = "shPreferencesKeyHours";
    public static final String SH_PREFERENCES_KEY_MINUTES = "shPreferencesKeyMinutes";
    public static final String SH_PREFERENCES_KEY_SECONDS = "shPreferencesKeySeconds";

    public static final String SH_PREFERENCES_SOUND_NAME = "sharedPreferencesSound";
    public static final String SH_PREFERENCES_KEY_CHOSE_SOUND_NUMBER = "shPreferencesKeyChoseSound";

    public static final int[] SOUND_ARRAY = {R.raw.timer_sound1, R.raw.timer_sound2, R.raw.timer_sound3, R.raw.timer_sound4, R.raw.timer_sound5};
}
