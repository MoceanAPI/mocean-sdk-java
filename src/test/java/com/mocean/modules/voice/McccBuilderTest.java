package com.mocean.modules.voice;

import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.voice.mccc.Play;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class McccBuilderTest {
    @Test
    public void testAdd() throws RequiredFieldException {
        Play play = new Play();
        play.setFiles("testing file");

        McccBuilder builder = new McccBuilder();
        builder.add(play);
        assertEquals(1, builder.build().size());
        assertEquals(play.getRequestData(), builder.build().get(0));

        play.setFiles("testing file2");
        builder.add(play);
        assertEquals(2, builder.build().size());
        assertEquals(play.getRequestData(), builder.build().get(1));
    }
}
