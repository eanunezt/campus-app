/**
 * 
 */
package co.edu.itli.campus.core.dtos;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Usuario
 *
 */
public class SignInTO {
	@ApiModelProperty(position = 0)
	private String username;
	@ApiModelProperty(position = 1)
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


}
