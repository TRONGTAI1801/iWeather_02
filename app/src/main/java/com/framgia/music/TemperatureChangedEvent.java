package com.framgia.music;

/**
 * Created by trong_tai on 07/02/2018.
 */

public class TemperatureChangedEvent {
    private String mNewText;

    public TemperatureChangedEvent(String newText) {
        mNewText = newText;
    }

    public String getNewText() {
        return mNewText;
    }
}
