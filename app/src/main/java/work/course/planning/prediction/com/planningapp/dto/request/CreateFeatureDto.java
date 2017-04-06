package work.course.planning.prediction.com.planningapp.dto.request;

import java.util.List;

/**
 * Created by Oleh Yanivskyy on 07.04.2017.
 */

public class CreateFeatureDto {

    private String name;
    private boolean isCategory;
    private List<String> values;
    private Long modelId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCategory() {
        return isCategory;
    }

    public void setCategory(boolean isCategory) {
        this.isCategory = isCategory;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    @Override
    public String toString() {
        return "CreateFeatureDto [name=" + name + ", isCategory=" + isCategory + ", values=" + values + ", modelId="
                + modelId + "]";
    }

}
