package co.edu.itli.campus.core.dtos;

public class CatalogoDTO {
	
	  private Long id;
	  private String nameShort;
	  private String name;
	  private String description;
	  
	  
	public CatalogoDTO(Long id, String nameShort, String name, String description) {
		super();
		this.id = id;
		this.nameShort = nameShort;
		this.name = name;
		this.description = description;
	}
	
	
	public CatalogoDTO(Long id, String nameShort, String name) {
		super();
		this.id = id;
		this.nameShort = nameShort;
		this.name = name;
	}


	private CatalogoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNameShort() {
		return nameShort;
	}
	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	  
	  

}
