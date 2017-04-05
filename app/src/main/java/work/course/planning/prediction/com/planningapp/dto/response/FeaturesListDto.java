package work.course.planning.prediction.com.planningapp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import work.course.planning.prediction.com.planningapp.dto.info.FeatureDto;
import work.course.planning.prediction.com.planningapp.dto.info.ModelInfoDto;

/**
 * Created by Oleh Yanivskyy on 05.04.2017.
 */

public class FeaturesListDto {
    private int code;

    @JsonProperty("data")
    private List<FeatureDto> features;

    private String errorCode;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<FeatureDto> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureDto> features) {
        this.features = features;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "FeaturesListDto{" +
                "code=" + code +
                ", features=" + features +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
