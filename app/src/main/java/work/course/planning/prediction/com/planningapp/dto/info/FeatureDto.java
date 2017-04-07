package work.course.planning.prediction.com.planningapp.dto.info;

import java.util.List;

/**
 * Created by Oleh Yanivskyy on 05.04.2017.
 */

public class FeatureDto {
    private Long id;
    private String name;
    private boolean isCategory;
    private List<FeatureListValueDto> values;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCategory() {
        return isCategory;
    }

    public void setCategory(boolean category) {
        isCategory = category;
    }

    public List<FeatureListValueDto> getValues() {
        return values;
    }

    public void setValues(List<FeatureListValueDto> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return name;
    }
}
