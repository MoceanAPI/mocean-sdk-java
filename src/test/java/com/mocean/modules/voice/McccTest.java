package com.mocean.modules.voice;

import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.voice.mccc.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class McccTest {
    @Test
    public void testMcccSay() throws RequiredFieldException {
        Say say = Mccc.say();
        say.setText("testing text");
        assertEquals("testing text", say.getRequestData().get("text"));

        assertEquals("testing text2", Mccc.say("testing text2").getRequestData().get("text"));
    }

    @Test
    public void testMcccBridge() throws RequiredFieldException {
        Bridge bridge = Mccc.bridge();
        bridge.setTo("testing to");
        assertEquals("testing to", bridge.getRequestData().get("to"));

        assertEquals("testing to2", Mccc.bridge("testing to2").getRequestData().get("to"));
    }

    @Test
    public void testMcccCollect() throws RequiredFieldException {
        Collect collect = Mccc.collect();
        collect.setEventUrl("testing event url");
        assertEquals("testing event url", collect.getRequestData().get("event-url"));

        assertEquals("testing event url2", Mccc.collect("testing event url2").getRequestData().get("event-url"));
    }

    @Test
    public void testMcccPlay() throws RequiredFieldException {
        Play play = Mccc.play();
        play.setFiles("testing file");
        assertEquals("testing file", play.getRequestData().get("file"));

        assertEquals("testing file2", Mccc.play("testing file2").getRequestData().get("file"));
    }

    @Test
    public void testMcccSleep() throws RequiredFieldException {
        Sleep sleep = Mccc.sleep();
        sleep.setDuration(10000);
        assertEquals(10000, sleep.getRequestData().get("duration"));

        assertEquals(20000, Mccc.sleep(20000).getRequestData().get("duration"));
    }
}
