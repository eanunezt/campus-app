package co.edu.itli.campus.core.dtos;

import io.swagger.annotations.ApiModelProperty;

public class JwtAuthenticationResponseDTO {
	@ApiModelProperty(position = 0)
    private String accessToken;
	@ApiModelProperty(position = 0)
    private String tokenType = "Bearer";

    public JwtAuthenticationResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}