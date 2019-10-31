package com.mocean.modules.voice.mccc;

import com.mocean.exception.RequiredFieldException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecordTest {
    @Test
    public void testIfActionAutoDefined() throws RequiredFieldException {
        Record record = new Record();

        assertEquals("record", record.getRequestData().get("action"));
    }
}
