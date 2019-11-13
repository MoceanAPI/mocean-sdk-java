package com.mocean.modules.voice;

import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.voice.mc.Collect;
import com.mocean.modules.voice.mc.Dial;
import com.mocean.modules.voice.mc.Play;
import com.mocean.modules.voice.mc.Say;
import com.mocean.modules.voice.mc.Sleep;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class McTest {
    @Test
    public void testMcSay() throws RequiredFieldException {
        Say say = Mc.say();
        say.setText("testing text");
        assertEquals("testing text", say.getRequestData().get("text"));

        assertEquals("testing text2", Mc.say("testing text2").getRequestData().get("text"));
    }

    @Test
    public void testMcDial() throws RequiredFieldException {
        Dial dial = Mc.dial();
        dial.setTo("testing to");
        assertEquals("testing to", dial.getRequestData().get("to"));

        assertEquals("testing to2", Mc.dial("testing to2").getRequestData().get("to"));
    }

    @Test
    public void testMcCollect() throws RequiredFieldException {
        Collect collect = Mc.collect();
        collect.setEventUrl("testing event url");
        collect.setMin(1);
        collect.setMax(1);
        collect.setTimeout(500);
        assertEquals("testing event url", collect.getRequestData().get("event-url"));

        collect = Mc.collect("testing event url2");
        collect.setMin(1);
        collect.setMax(1);
        collect.setTimeout(500);
        assertEquals("testing event url2", collect.getRequestData().get("event-url"));
    }

    @Test
    public void testMcPlay() throws RequiredFieldException {
        Play play = Mc.play();
        play.setFiles("testing file");
        assertEquals("testing file", play.getRequestData().get("file"));

        assertEquals("testing file2", Mc.play("testing file2").getRequestData().get("file"));
    }

    @Test
    public void testMcSleep() throws RequiredFieldException {
        Sleep sleep = Mc.sleep();
        sleep.setDuration(10000);
        assertEquals(10000, sleep.getRequestData().get("duration"));

        assertEquals(20000, Mc.sleep(20000).getRequestData().get("duration"));
    }

    @Test
    public void testMcRecord() throws RequiredFieldException {
        assertEquals("record", Mc.record().getRequestData().get("action"));
    }
}
