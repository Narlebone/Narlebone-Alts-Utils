package dev.narlebone.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class NarleboneAuthUtils {
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();
    private static final String URL = "https://auth.narlebone.dev/auth?code=";
    private static final String SUFFIX = "@narlebone.dev";

    private static JsonObject request(String code) throws IOException {
        HttpGet httpGet = new HttpGet(URL + code);
        httpGet.setHeader("user-agent", "Narlebone-Auth-Utils 1.0");

        CloseableHttpResponse response = httpclient.execute(httpGet);

        // 如果你使用新版Gson库，可以使用此代码
        // return (JsonObject) JsonParser.parseString(EntityUtils.toString(response.getEntity()));

        return (JsonObject) new JsonParser().parse(EntityUtils.toString(response.getEntity()));
    }

    public static NarleboneProfile authenticate(String code) throws NarleboneException {
        code = code.split(":")[0];

        if (code.endsWith(SUFFIX)) {
            code = code.substring(0, code.length() - SUFFIX.length());
        }

        try {
            JsonObject request = request(code);

            if (request != null) {
                if (request.get("code").getAsInt() == 200) {
                    JsonObject data = request.get("data").getAsJsonObject();
                    String username = data.get("username").getAsString();
                    String uuid = data.get("uuid").getAsString();
                    String token = data.get("accessToken").getAsString();

                    return new NarleboneProfile(username, uuid, token);
                } else {
                    throw new NarleboneException(request.get("message").getAsString());
                }
            }

        } catch (IOException e) {
            throw new NarleboneException(e.getMessage());
        }

        return null;
    }
}
