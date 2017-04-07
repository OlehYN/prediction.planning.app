package work.course.planning.prediction.com.planningapp.dto.response;

import java.util.Date;
import java.util.List;


public class ExampleDto {
	private String outputLabel;
	private Date creationDate;
	private Long id;

	private List<ExampleInstanceDto> exampleInstances;

	public String getOutputLabel() {
		return outputLabel;
	}

	public void setOutputLabel(String outputLabel) {
		this.outputLabel = outputLabel;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<ExampleInstanceDto> getExampleInstances() {
		return exampleInstances;
	}

	public void setExampleInstances(List<ExampleInstanceDto> exampleInstances) {
		this.exampleInstances = exampleInstances;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ExampleDto [outputLabel=" + outputLabel + ", creationDate=" + creationDate + ", id=" + id
				+ ", exampleInstances=" + exampleInstances + "]";
	}

}
