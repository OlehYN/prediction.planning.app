package work.course.planning.prediction.com.planningapp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Oleh Yanivskyy on 07.04.2017.
 */

public class CreateFeatureResultDto {
    @JsonProperty("data")
    private Long id;
    private int code;
    private String errorCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "CreateFeatureDto{" +
                "id=" + id +
                ", code=" + code +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
