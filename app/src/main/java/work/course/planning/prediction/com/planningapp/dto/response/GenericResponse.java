package work.course.planning.prediction.com.planningapp.dto.response;

/**
 * Created by Oleh Yanivskyy on 08.04.2017.
 */

public class GenericResponse<T> {
    private int code;

    private T data;

    private String errorCode;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "code=" + code +
                ", data=" + data +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
