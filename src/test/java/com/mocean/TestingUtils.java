package com.mocean;

import com.mocean.exception.MoceanErrorException;
import com.mocean.modules.Transmitter;
import com.mocean.system.Mocean;
import com.mocean.system.auth.Basic;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.mock.MockInterceptor;
import okhttp3.mock.Rule;
import okhttp3.mock.RuleAnswer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static okhttp3.mock.MediaTypes.MEDIATYPE_TEXT;

public class TestingUtils {
    public static Mocean getMoceanObj() {
        return TestingUtils.getMoceanObj(new Transmitter());
    }

    public static Mocean getMoceanObj(Transmitter transmitter) {
        try {
            return new Mocean(new Basic("test api key", "test api secret"), transmitter);
        } catch (MoceanErrorException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src", "test", "resources", fileName)), StandardCharsets.UTF_8);
    }

    public static Response.Builder getResponse(String fileName, int responseCode) {
        try {
            String responseStr = TestingUtils.getResponseString(fileName);

            return new Response.Builder()
                    .code(responseCode)
                    .body(ResponseBody.create(responseStr, null));
        } catch (IOException ex) {
            return new Response.Builder()
                    .code(responseCode)
                    .body(ResponseBody.create("", null));
        }
    }

    public static OkHttpClient getMockOkHttpClient(RuleAnswer testRequest) {
        MockInterceptor interceptor = new MockInterceptor();

        Rule.Builder ruleBuilder = interceptor.addRule()
                .get().or().post()
                .urlStarts("http");

        if (testRequest == null) {
            ruleBuilder.respond("testing only", MEDIATYPE_TEXT);
        } else {
            ruleBuilder.answer(testRequest);
        }

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    public static String getTestUri(String version, String uri) {
        return "/rest/" + version + uri;
    }

    public static HashMap<String, String> rewindBody(FormBody body) {
        HashMap<String, String> mapBody = new HashMap<>();
        for (int i = 0; i < body.size(); i++) {
            mapBody.put(body.name(i), body.value(i));
        }

        return mapBody;
    }
}
