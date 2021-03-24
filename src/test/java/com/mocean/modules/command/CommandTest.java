package com.mocean.modules.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mocean.TestingUtils;
import com.mocean.exception.MoceanErrorException;
import com.mocean.exception.RequiredFieldException;
import com.mocean.modules.Transmitter;
import com.mocean.modules.command.mapper.SendMessageResponse;
import com.mocean.modules.command.mc.TgSendText;
import com.mocean.modules.command.mc.AbstractMc;
import com.mocean.modules.voice.mapper.HangupResponse;
import com.mocean.modules.voice.mapper.VoiceResponse;
import com.mocean.system.Mocean;
import com.mocean.utils.Utils;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mock.RuleAnswer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommandTest {
    @Test
    public void testSetterMethod() throws IOException, RequiredFieldException {
        Command command = TestingUtils.getMoceanObj().command();

        command.setEventUrl("test event url");
        assertNotNull(command.getParams().get("mocean-event-url"));
        assertEquals("test event url", command.getParams().get("mocean-event-url"));

        command.setRespFormat("json");
        assertNotNull(command.getParams().get("mocean-resp-format"));
        assertEquals("json", command.getParams().get("mocean-resp-format"));

        TgSendText tgSendText = Mc.TgSendText();
        tgSendText.from("test from");
        tgSendText.to("test to");
        tgSendText.content("test content");

        McBuilder mcBuilder = new McBuilder();
        mcBuilder.add(tgSendText);

        command.setMoceanCommand(mcBuilder);
        assertNotNull(command.getParams().get("mocean-command"));
        assertEquals(new ObjectMapper().writeValueAsString(
                mcBuilder.build()
        ), command.getParams().get("mocean-command"));

        //test overloading method
        command = TestingUtils.getMoceanObj().command();
        tgSendText.content("test content2");
        McBuilder builderParams = (new McBuilder()).add(tgSendText);
        command.setMoceanCommand(builderParams);
        assertNotNull(command.getParams().get("mocean-command"));
        assertEquals(new ObjectMapper().writeValueAsString(builderParams.build()), command.getParams().get("mocean-command"));
    }

    public void testJsonExecute() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/send-message"));
                return TestingUtils.getResponse("command.json", 200);
            }
        }));

        AbstractMc tgSendText = Mc.TgSendText().from("test from").to("test to").content("test content");

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        SendMessageResponse sendMessageResponse = mocean.command()
                .setMoceanCommand((new McBuilder()).add(tgSendText))
                .execute();
        assertEquals(sendMessageResponse.toString(), TestingUtils.getResponseString("command.json"));
        this.testObject(sendMessageResponse);
    }

    @Test
    public void testXmlExecute() throws IOException, MoceanErrorException {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                assertTrue(request.method().equalsIgnoreCase("post"));
                assertEquals(request.url().uri().getPath(), TestingUtils.getTestUri("2", "/send-message"));
                return TestingUtils.getResponse("command.xml", 200);
            }
        }));

        AbstractMc tgSendText = Mc.TgSendText().from("test from").to("test to").content("test content");

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);
        SendMessageResponse sendMessageResponse = mocean.command()
                .setMoceanCommand((new McBuilder()).add(tgSendText))
                .setRespFormat("xml")
                .execute();

        assertEquals(sendMessageResponse.toString(), TestingUtils.getResponseString("command.xml"));
        this.testObject(sendMessageResponse);
    }


    @Test
    public void testRequiredFieldNotSet() {
        Transmitter transmitterMock = new Transmitter(TestingUtils.getMockOkHttpClient(new RuleAnswer() {
            @Override
            public Response.Builder respond(Request request) {
                return TestingUtils.getResponse("command.json", 200);
            }
        }));

        Mocean mocean = TestingUtils.getMoceanObj(transmitterMock);

        assertThrows(RequiredFieldException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mocean.command().execute();
            }
        });
    }

    private void testObject(SendMessageResponse sendMessageResponse) {

        assertEquals(sendMessageResponse.getStatus(),"0");
        assertEquals(sendMessageResponse.getSessionUuid(),"xxxx-xxxx");

        assertTrue(Utils.isArray(sendMessageResponse.getMessages()));
        assertEquals(sendMessageResponse.getMessages()[0].getAction(), "xxxx-xxxx");
        assertEquals(sendMessageResponse.getMessages()[0].getMessageId(), "xxxx-xxxx");
        assertEquals(sendMessageResponse.getMessages()[0].getMcPosition(), "0");
        assertEquals(sendMessageResponse.getMessages()[0].getTotalMessageSegments(), "1");
    }
}
