package work.course.planning.prediction.com.planningapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import work.course.planning.prediction.com.planningapp.config.PlanningApplication;
import work.course.planning.prediction.com.planningapp.dto.response.CreateModelDto;
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

    @Override
    public CreateModelDto createModel(String name) throws IOException {
        Map<String, String> requestParameters = minArgs();
        requestParameters.put("name", name);

        RequestResponse requestResponse = sendRequestService.sendRequest(host, port, "createModel", requestParameters, "");
        return objectMapper.readValue(requestResponse.getOutput(),
                CreateModelDto.class);
    }

    private Map<String, String> minArgs() {
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
