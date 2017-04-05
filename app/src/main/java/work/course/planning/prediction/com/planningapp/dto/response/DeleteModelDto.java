package work.course.planning.prediction.com.planningapp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Oleh Yanivskyy on 05.04.2017.
 */

public class DeleteModelDto {
    @JsonProperty("data")
    private Boolean result;
    private int code;
    private String errorCode;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
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
        return "DeleteModelDto{" +
                "result=" + result +
                ", code=" + code +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
