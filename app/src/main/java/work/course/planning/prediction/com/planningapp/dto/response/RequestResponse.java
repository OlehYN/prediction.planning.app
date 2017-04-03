package work.course.planning.prediction.com.planningapp.dto.response;

/**
 * Created by Oleh Yanivskyy on 03.04.2017.
 */

public class RequestResponse {
    private int code;
    private String output;

    public RequestResponse(int code, String output) {
        this.code = code;
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "RequestResponse{" +
                "code=" + code +
                ", output='" + output + '\'' +
                '}';
    }
}
