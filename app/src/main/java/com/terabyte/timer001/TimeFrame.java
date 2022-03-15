package com.terabyte.timer001;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeFrame implements Parcelable {
    public int hours, minutes, seconds;

    public static final Creator<TimeFrame> CREATOR = new Creator<TimeFrame>() {
        @Override
        public TimeFrame createFromParcel(Parcel parcel) {
            return new TimeFrame(parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        @Override
        public TimeFrame[] newArray(int i) {
            return new TimeFrame[i];
        }
    };

    public TimeFrame(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public boolean isFinished() {
        return hours==0 & minutes==0 & seconds==0;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public int getProgress() {
        return hours*3600+minutes*60+seconds;
    }

    public void decrementTime() {
        if(seconds>0) {
            seconds--;
        }
        else {
            if(minutes>0) {
                minutes--;
                seconds = 59;
            }
            else {
                if(hours>0) {
                    hours--;
                    minutes = 59;
                    seconds = 59;
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(hours);
        parcel.writeInt(minutes);
        parcel.writeInt(seconds);
    }
}
