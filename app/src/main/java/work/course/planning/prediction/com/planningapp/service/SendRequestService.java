package work.course.planning.prediction.com.planningapp.service;

import java.util.Map;

import work.course.planning.prediction.com.planningapp.dto.response.RequestResponse;

/**
 * Created by Oleh Yanivskyy on 03.04.2017.
 */

public interface SendRequestService {
    RequestResponse sendRequest(String host, int port, String path, Map<String, String> urlParameters, String body);
}
