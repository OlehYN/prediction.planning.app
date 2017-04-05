package work.course.planning.prediction.com.planningapp.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import work.course.planning.prediction.com.planningapp.dto.response.RequestResponse;
import work.course.planning.prediction.com.planningapp.service.SendRequestService;

/**
 * Created by Oleh Yanivskyy on 03.04.2017.
 */

public class SendRequestServiceImpl implements SendRequestService {

    @Override
    public RequestResponse sendRequest(String host, int port, String path, Map<String, String> urlParameters, String body) {
        try {
            String stringUrl = host + ":" + port + "/" + path;
            if (!urlParameters.isEmpty()) {
                stringUrl += "?";
                for (Map.Entry<String, String> entry : urlParameters.entrySet()) {
                    stringUrl += entry.getKey() + "=" + entry.getValue() + "&";
                }
                stringUrl = stringUrl.substring(0, stringUrl.length() - 1);
            }
            URL url = new URL(stringUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            os.flush();

            int code = conn.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String fullOutput = "";
            while ((output = br.readLine()) != null) {
                fullOutput += output;
            }

            conn.disconnect();

            return new RequestResponse(code, fullOutput);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException();
    }
}
