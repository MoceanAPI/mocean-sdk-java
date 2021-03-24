package com.mocean.modules.command;

import com.mocean.modules.command.mc.*;

public class Mc {

    public Mc() {}
    public static TgSendText TgSendText() { return new TgSendText(); }

    public static TgSendAudio TgSendAudio () {
        return new TgSendAudio();
    }

    public static TgSendAnimation TgSendAnimation() {
        return new TgSendAnimation();
    }

    public static TgSendDocument TgSendDocument() {
        return new TgSendDocument();
    }

    public static TgSendPhoto TgSendPhoto() {
        return new TgSendPhoto();
    }

    public static TgSendVideo TgSendVideo() {
        return new TgSendVideo();
    }

    public static TgRequestContact TgRequestContact() {
        return new TgRequestContact();
    }

    public static SendSMS SendSMS() { return new SendSMS(); }
}
