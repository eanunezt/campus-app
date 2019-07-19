package co.edu.itli.campus.core.dtos;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ProfileTO {
	
	@ApiModelProperty(position = 0)
	private String ID;
	@ApiModelProperty(position = 1)
	private String username;
	@ApiModelProperty(position = 2)	
	private String name;
	
	@ApiModelProperty(position = 3)
	private String email;
	
	@ApiModelProperty(position = 4)
	List<String> roles;
	
	
	public ProfileTO(String iD, String username, String name, String email, List<String> roles) {
		super();
		ID = iD;
		this.username = username;
		this.name = name;
		this.email = email;
		this.roles = roles;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}	
}
