package work.course.planning.prediction.com.planningapp.dto.request;

import java.util.List;

public class PredictDto {
	private Long modelId;

	private List<AddExampleInstanceDto> examples;

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
		return "AddExampleDto [modelId=" + modelId + ", examples=" + examples + "]";
	}

}
