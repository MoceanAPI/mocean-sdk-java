package com.mocean.modules.voice.mccc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordTest {
    @Test
    public void testIfActionAutoDefined() throws RequiredFieldException {
        Record record = new Record();

        assertEquals("record", record.getRequestData().get("action"));
    }
}
