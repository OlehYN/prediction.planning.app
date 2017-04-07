package work.course.planning.prediction.com.planningapp.service;

import java.io.IOException;
import java.util.List;

import work.course.planning.prediction.com.planningapp.dto.request.AddExampleDto;
import work.course.planning.prediction.com.planningapp.dto.request.CreateFeatureDto;
import work.course.planning.prediction.com.planningapp.dto.response.AddExampleResultDto;
import work.course.planning.prediction.com.planningapp.dto.response.AddListValueFeatureDto;
import work.course.planning.prediction.com.planningapp.dto.response.CreateFeatureResultDto;
import work.course.planning.prediction.com.planningapp.dto.response.CreateModelDto;
import work.course.planning.prediction.com.planningapp.dto.response.DeleteModelDto;
import work.course.planning.prediction.com.planningapp.dto.response.FeaturesListDto;
import work.course.planning.prediction.com.planningapp.dto.response.ModelsListDto;
import work.course.planning.prediction.com.planningapp.dto.response.RenameFeatureDto;

/**
 * Created by Oleh Yanivskyy on 03.04.2017.
 */

public interface PlanningApiService {
    ModelsListDto getModels() throws IOException;

    CreateModelDto createModel(String name) throws IOException;

    DeleteModelDto deleteModel(Long id) throws IOException;

    FeaturesListDto getFeatures(Long id) throws  IOException;

    CreateFeatureResultDto createFeature(CreateFeatureDto createFeatureDto) throws  IOException;

    RenameFeatureDto renameFeature(String newName, Long featureId) throws  IOException;

    AddListValueFeatureDto addListValues(List<String> values, Long featureId) throws  IOException;

    AddExampleResultDto addExample(AddExampleDto addExampleDto) throws  IOException;
}
