package work.course.planning.prediction.com.planningapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import work.course.planning.prediction.com.planningapp.config.PlanningApplication;
import work.course.planning.prediction.com.planningapp.dto.response.ModelsListDto;
import work.course.planning.prediction.com.planningapp.dto.response.RequestResponse;
import work.course.planning.prediction.com.planningapp.service.PlanningApiService;
import work.course.planning.prediction.com.planningapp.service.SendRequestService;

/**
 * Created by Oleh Yanivskyy on 03.04.2017.
 */

public class PlanningApiServiceImpl implements PlanningApiService {

    private SendRequestService sendRequestService = new SendRequestServiceImpl();
    private ObjectMapper objectMapper = new ObjectMapper();

    private String host = PlanningApplication.getPlanningApplication().getHost();
    private int port = PlanningApplication.getPlanningApplication().getPort();
    private String token = PlanningApplication.getPlanningApplication().getToken();

    @Override
    public ModelsListDto getModels() throws IOException {
        RequestResponse requestResponse = sendRequestService.sendRequest(host, port, "list", minArgs(), "");

        return objectMapper.readValue(requestResponse.getOutput(),
                ModelsListDto.class);
    }

    private Map<String, String> minArgs() {
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
