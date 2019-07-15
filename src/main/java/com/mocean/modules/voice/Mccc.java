package com.mocean.modules.voice;

import com.mocean.modules.voice.mccc.*;

public final class Mccc {
    private Mccc() {
    }

    public static Say say() {
        return new Say();
    }

    public static Say say(String text) {
        return new Say().setText(text);
    }

    public static Play play() {
        return new Play();
    }

    public static Play play(String file) {
        return new Play().setFiles(file);
    }

    public static Bridge bridge() {
        return new Bridge();
    }

    public static Bridge bridge(String to) {
        return new Bridge().setTo(to);
    }

    public static Collect collect() {
        return new Collect();
    }

    public static Collect collect(String eventUrl) {
        return new Collect().setEventUrl(eventUrl);
    }

    public static Sleep sleep() {
        return new Sleep();
    }

    public static Sleep sleep(int duration) {
        return new Sleep().setDuration(duration);
    }
}
