package work.course.planning.prediction.com.planningapp.service;

import java.io.IOException;

import work.course.planning.prediction.com.planningapp.dto.request.CreateFeatureDto;
import work.course.planning.prediction.com.planningapp.dto.response.CreateFeatureResultDto;
import work.course.planning.prediction.com.planningapp.dto.response.CreateModelDto;
import work.course.planning.prediction.com.planningapp.dto.response.DeleteModelDto;
import work.course.planning.prediction.com.planningapp.dto.response.FeaturesListDto;
import work.course.planning.prediction.com.planningapp.dto.response.ModelsListDto;

/**
 * Created by Oleh Yanivskyy on 03.04.2017.
 */

public interface PlanningApiService {
    ModelsListDto getModels() throws IOException;

    CreateModelDto createModel(String name) throws IOException;

    DeleteModelDto deleteModel(Long id) throws IOException;

    FeaturesListDto getFeatures(Long id) throws  IOException;

    CreateFeatureResultDto createFeature(CreateFeatureDto createFeatureDto) throws  IOException;
}
