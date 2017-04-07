package work.course.planning.prediction.com.planningapp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Oleh Yanivskyy on 07.04.2017.
 */

public class RemoveExampleResultDto {
    private int code;
    private String errorCode;

    @JsonProperty("data")
    private Boolean success;

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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "RenameFeatureDto{" +
                "code=" + code +
                ", errorCode='" + errorCode + '\'' +
                ", success=" + success +
                '}';
    }
}
