package work.course.planning.prediction.com.planningapp.dto.response;


public class ExampleInstanceDto {
	private String name;
	private String value;

	public ExampleInstanceDto() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ExampleInstanceDto [name=" + name + ", value=" + value + "]";
	}

}
