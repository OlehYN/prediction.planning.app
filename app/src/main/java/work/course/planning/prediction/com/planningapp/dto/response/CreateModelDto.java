package work.course.planning.prediction.com.planningapp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Oleh Yanivskyy on 05.04.2017.
 */

public class CreateModelDto {

    @JsonProperty("data")
    private ModelInfoDto modelInfoDto;
    private int code;
    private String errorCode;

    public ModelInfoDto getModelInfoDto() {
        return modelInfoDto;
    }

    public void setModelInfoDto(ModelInfoDto modelInfoDto) {
        this.modelInfoDto = modelInfoDto;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "CreateModelDto{" +
                "modelInfoDto=" + modelInfoDto +
                ", code=" + code +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
