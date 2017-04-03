package work.course.planning.prediction.com.planningapp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Oleh Yanivskyy on 04.04.2017.
 */

public class ModelsListDto {
    private int code;

    @JsonProperty("data")
    private List<ModelInfoDto> models;

    private String status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ModelInfoDto> getModels() {
        return models;
    }

    public void setModels(List<ModelInfoDto> models) {
        this.models = models;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ModelsListDto{" +
                "code=" + code +
                ", models=" + models +
                ", status='" + status + '\'' +
                '}';
    }
}
