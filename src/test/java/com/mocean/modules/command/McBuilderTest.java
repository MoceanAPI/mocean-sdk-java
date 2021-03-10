package com.mocean.modules.command;

import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.command.mc.TgSendText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class McBuilderTest {
    @Test
    public void testAdd() throws RequiredFieldException {
        TgSendText tgSendText = new TgSendText();
        tgSendText.from("test from");
        tgSendText.to("test to");
        tgSendText.content("test content");

        McBuilder builder = new McBuilder();
        builder.add(tgSendText);
        assertEquals(1, builder.build().size());
        assertEquals(tgSendText.getRequestData(), builder.build().get(0));

        tgSendText.content("test content2");
        builder.add(tgSendText);
        assertEquals(2, builder.build().size());
        assertEquals(tgSendText.getRequestData(), builder.build().get(1));
    }
}
