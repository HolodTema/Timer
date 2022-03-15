package com.terabyte.timer001;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerClient {
    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getInstance(Context context, int soundSource) {
        if(mediaPlayer==null) {
            mediaPlayer = MediaPlayer.create(context, soundSource);
        }
        return mediaPlayer;
    }

    public static void setNullMediaPlayerInstance() {
        if(mediaPlayer!=null) {
            mediaPlayer.release();
        }
        mediaPlayer = null;
    }
}
