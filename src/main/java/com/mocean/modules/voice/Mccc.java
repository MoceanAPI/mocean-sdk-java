package com.mocean.modules.voice;

import com.mocean.modules.voice.mccc.Collect;
import com.mocean.modules.voice.mccc.Dial;
import com.mocean.modules.voice.mccc.Play;
import com.mocean.modules.voice.mccc.Record;
import com.mocean.modules.voice.mccc.Say;
import com.mocean.modules.voice.mccc.Sleep;

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

    public static Dial dial() {
        return new Dial();
    }

    public static Dial dial(String to) {
        return new Dial().setTo(to);
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

    public static Record record() {
        return new Record();
    }
}
