package work.course.planning.prediction.com.planningapp.service;

import java.io.IOException;
import java.util.List;

import work.course.planning.prediction.com.planningapp.dto.info.ModelInfoDto;
import work.course.planning.prediction.com.planningapp.dto.request.AddExampleDto;
import work.course.planning.prediction.com.planningapp.dto.request.CreateFeatureDto;
import work.course.planning.prediction.com.planningapp.dto.response.ExamplesListDto;
import work.course.planning.prediction.com.planningapp.dto.response.FeaturesListDto;
import work.course.planning.prediction.com.planningapp.dto.response.GenericResponse;
import work.course.planning.prediction.com.planningapp.dto.response.ModelsListDto;

/**
 * Created by Oleh Yanivskyy on 03.04.2017.
 */

public interface PlanningApiService {
    ModelsListDto getModels() throws IOException;

    GenericResponse<ModelInfoDto> createModel(String name) throws IOException;

    GenericResponse<Boolean> deleteModel(Long id) throws IOException;

    FeaturesListDto getFeatures(Long id) throws  IOException;

    GenericResponse<Long> createFeature(CreateFeatureDto createFeatureDto) throws  IOException;

    GenericResponse<Boolean> renameFeature(String newName, Long featureId) throws  IOException;

    GenericResponse<Boolean> addListValues(List<String> values, Long featureId) throws  IOException;

    GenericResponse<Boolean> addExample(AddExampleDto addExampleDto) throws  IOException;

    ExamplesListDto getExamples(Long modelId) throws  IOException;

    GenericResponse<Boolean> deleteExample(Long exampleId) throws  IOException;

    GenericResponse<Boolean> updateModel(Long modelId) throws  IOException;
}
