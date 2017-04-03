package work.course.planning.prediction.com.planningapp.service;

import java.io.IOException;

import work.course.planning.prediction.com.planningapp.dto.response.ModelsListDto;

/**
 * Created by Oleh Yanivskyy on 03.04.2017.
 */

public interface PlanningApiService {
    ModelsListDto getModels() throws IOException;
}
