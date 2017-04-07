package work.course.planning.prediction.com.planningapp.dto.request;

import java.util.List;

public class AddExampleDto {
	private Integer outputLabel;
	private Long modelId;

	private List<AddExampleInstanceDto> examples;

	public Integer getOutputLabel() {
		return outputLabel;
	}

	public void setOutputLabel(Integer outputLabel) {
		this.outputLabel = outputLabel;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public List<AddExampleInstanceDto> getExamples() {
		return examples;
	}

	public void setExamples(List<AddExampleInstanceDto> examples) {
		this.examples = examples;
	}

	@Override
	public String toString() {
		return "AddExampleDto [outputLabel=" + outputLabel + ", modelId=" + modelId + ", examples=" + examples + "]";
	}

}
