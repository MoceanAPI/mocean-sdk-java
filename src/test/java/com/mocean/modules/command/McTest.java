package com.mocean.modules.command;

import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.command.mc.*;
import com.mocean.modules.command.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class McTest {

    @Test
    public void testMcTgRequestContact() throws RequiredFieldException {

        TgRequestContact tgRequestContact = new TgRequestContact();
        tgRequestContact.from("test from");
        tgRequestContact.to("test to");
        tgRequestContact.content("test content");
        tgRequestContact.button("test button text");

        TgRequestContact mc = Mc.TgRequestContact();
        mc.from("test from");
        mc.to("test to");
        mc.content("test content");
        mc.button("test button text");

        assertEquals(tgRequestContact.getRequestData().get("from"), mc.getRequestData().get("from"));
        assertEquals(tgRequestContact.getRequestData().get("to"), mc.getRequestData().get("to"));
        assertEquals(tgRequestContact.getRequestData().get("content"), mc.getRequestData().get("content"));
        assertEquals(tgRequestContact.getRequestData().get("tg_keyboard"), mc.getRequestData().get("tg_keyboard"));
    }

    @Test
    public void testMcTgSendAnimation() throws RequiredFieldException {

        TgSendAnimation tgSendAnimation = new TgSendAnimation();
        tgSendAnimation.from("test from");
        tgSendAnimation.to("test to");
        tgSendAnimation.content("test url", "test content");

        TgSendAnimation mc = Mc.TgSendAnimation();
        mc.from("test from");
        mc.to("test to");
        mc.content("test url", "test content");

        assertEquals(tgSendAnimation.getRequestData().get("from"), mc.getRequestData().get("from"));
        assertEquals(tgSendAnimation.getRequestData().get("to"), mc.getRequestData().get("to"));
        assertEquals(tgSendAnimation.getRequestData().get("content"), mc.getRequestData().get("content"));
    }

    @Test
    public void testMcTgSendAudio() throws RequiredFieldException {

        TgSendAudio tgSendAudio = new TgSendAudio();
        tgSendAudio.from("test from");
        tgSendAudio.to("test to");
        tgSendAudio.content("test url", "test content");

        TgSendAudio mc = Mc.TgSendAudio();
        mc.from("test from");
        mc.to("test to");
        mc.content("test url", "test content");

        assertEquals(tgSendAudio.getRequestData().get("from"), mc.getRequestData().get("from"));
        assertEquals(tgSendAudio.getRequestData().get("to"), mc.getRequestData().get("to"));
        assertEquals(tgSendAudio.getRequestData().get("content"), mc.getRequestData().get("content"));
    }

    @Test
    public void testMcTgSendDocument() throws RequiredFieldException {

        TgSendDocument tgSendDocument = new TgSendDocument();
        tgSendDocument.from("test from");
        tgSendDocument.to("test to");
        tgSendDocument.content("test url", "test content");

        TgSendDocument mc = Mc.TgSendDocument();
        mc.from("test from");
        mc.to("test to");
        mc.content("test url", "test content");

        assertEquals(tgSendDocument.getRequestData().get("from"), mc.getRequestData().get("from"));
        assertEquals(tgSendDocument.getRequestData().get("to"), mc.getRequestData().get("to"));
        assertEquals(tgSendDocument.getRequestData().get("content"), mc.getRequestData().get("content"));
    }

    @Test
    public void testMcTgSendPhoto() throws RequiredFieldException {

        TgSendPhoto tgSendPhoto = new TgSendPhoto();
        tgSendPhoto.from("test from");
        tgSendPhoto.to("test to");
        tgSendPhoto.content("test url", "test content");

        TgSendPhoto mc = Mc.TgSendPhoto();
        mc.from("test from");
        mc.to("test to");
        mc.content("test url", "test content");

        assertEquals(tgSendPhoto.getRequestData().get("from"), mc.getRequestData().get("from"));
        assertEquals(tgSendPhoto.getRequestData().get("to"), mc.getRequestData().get("to"));
        assertEquals(tgSendPhoto.getRequestData().get("content"), mc.getRequestData().get("content"));
    }

    @Test
    public void testMcTgSendVideo() throws RequiredFieldException {

        TgSendVideo tgSendVideo = new TgSendVideo();
        tgSendVideo.from("test from");
        tgSendVideo.to("test to");
        tgSendVideo.content("test url", "test content");

        TgSendVideo mc = Mc.TgSendVideo();
        mc.from("test from");
        mc.to("test to");
        mc.content("test url", "test content");

        assertEquals(tgSendVideo.getRequestData().get("from"), mc.getRequestData().get("from"));
        assertEquals(tgSendVideo.getRequestData().get("to"), mc.getRequestData().get("to"));
        assertEquals(tgSendVideo.getRequestData().get("content"), mc.getRequestData().get("content"));
    }

    @Test
    public void testMcSendSMS() throws RequiredFieldException {

        SendSMS sendSMS = new SendSMS();
        sendSMS.from("test from");
        sendSMS.to("test to");
        sendSMS.content("test content");

        SendSMS mc = Mc.SendSMS();
        mc.from("test from");
        mc.to("test to");
        mc.content("test content");

        assertEquals(sendSMS.getRequestData().get("from"), mc.getRequestData().get("from"));
        assertEquals(sendSMS.getRequestData().get("to"), mc.getRequestData().get("to"));
        assertEquals(sendSMS.getRequestData().get("content"), mc.getRequestData().get("content"));
    }

}
