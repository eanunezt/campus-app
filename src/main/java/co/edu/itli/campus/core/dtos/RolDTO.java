package co.edu.itli.campus.core.dtos;
import io.swagger.annotations.ApiModelProperty;


public class RolDTO {
	
	@ApiModelProperty(position = 0)
	private long id;
  
	@ApiModelProperty(position = 1)
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}  

}