package com.mocean.modules.voice;

import com.mocean.modules.voice.mc.Collect;
import com.mocean.modules.voice.mc.Dial;
import com.mocean.modules.voice.mc.Play;
import com.mocean.modules.voice.mc.Record;
import com.mocean.modules.voice.mc.Say;
import com.mocean.modules.voice.mc.Sleep;

public final class Mc {
    private Mc() {
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
