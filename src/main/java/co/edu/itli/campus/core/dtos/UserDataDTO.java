package co.edu.itli.campus.core.dtos;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;


public class UserDataDTO {
 @ApiModelProperty(position = 0)
 private String name;
 
 @ApiModelProperty(position = 1)
 private String phone;
  
  @ApiModelProperty(position = 2)
  private String username;
  
  @ApiModelProperty(position = 3)
  private String email;
  @ApiModelProperty(position = 4)
  private String password;
  @ApiModelProperty(position = 5)
  List<RolDTO> roles;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  
  public List<RolDTO> getRoles() {
    return roles;
  }

  public void setRoles(List<RolDTO> roles) {
    this.roles = roles;
  }

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

}