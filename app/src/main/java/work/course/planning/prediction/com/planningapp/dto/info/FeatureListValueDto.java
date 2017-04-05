package work.course.planning.prediction.com.planningapp.dto.info;

/**
 * Created by Oleh Yanivskyy on 05.04.2017.
 */

public class FeatureListValueDto {
    private Long id;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FeatureListValueDto{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
