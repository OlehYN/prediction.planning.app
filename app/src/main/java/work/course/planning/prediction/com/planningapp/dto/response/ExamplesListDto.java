package work.course.planning.prediction.com.planningapp.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import work.course.planning.prediction.com.planningapp.dto.info.ExampleDto;

/**
 * Created by Oleh Yanivskyy on 08.04.2017.
 */

public class ExamplesListDto {

    @JsonProperty("data")
    private List<ExampleDto> exampleDtos;
    private int code;
    private String errorCode;

    public List<ExampleDto> getExampleDtos() {
        return exampleDtos;
    }

    public void setExampleDtos(List<ExampleDto> exampleDtos) {
        this.exampleDtos = exampleDtos;
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
        return "ExamplesListDto{" +
                "exampleDtos=" + exampleDtos +
                ", code=" + code +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
