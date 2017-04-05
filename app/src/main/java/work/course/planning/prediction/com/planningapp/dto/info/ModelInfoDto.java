package work.course.planning.prediction.com.planningapp.dto.info;

import java.util.Date;

public class ModelInfoDto {
    private Long id;
    private String name;
    private Integer examplesQuantity;
    private Date creationDate;
    private String errorCode;

    public ModelInfoDto() {
    }

    public ModelInfoDto(Long id, String name, Integer examplesQuantity) {
        super();
        this.id = id;
        this.name = name;
        this.examplesQuantity = examplesQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExamplesQuantity() {
        return examplesQuantity;
    }

    public void setExamplesQuantity(Integer examplesQuantity) {
        this.examplesQuantity = examplesQuantity;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ModelInfoDto [id=" + id + ", name=" + name + ", examplesQuantity=" + examplesQuantity
                + ", creationDate=" + creationDate + "]";
    }

}
