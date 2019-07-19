package co.edu.itli.campus.core.recursos;


import java.net.URI;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.edu.itli.campus.core.dtos.ApiResponseDTO;
import co.edu.itli.campus.core.dtos.JwtAuthenticationResponseDTO;
import co.edu.itli.campus.core.dtos.RolDTO;
import co.edu.itli.campus.core.dtos.SignInTO;
import co.edu.itli.campus.core.dtos.UserDataDTO;
import co.edu.itli.campus.core.model.Rol;
import co.edu.itli.campus.core.model.Usuario;
import co.edu.itli.campus.core.respositorios.RolRepository;
import co.edu.itli.campus.core.respositorios.UserRepository;
import co.edu.itli.campus.core.seguridad.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@RestController
@RequestMapping("/api/auth")
@Api(tags = "auth")
public class AuthResource {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @PostMapping("/signin")
    @ApiOperation(value = "${UserController.signin}")   
    @ApiResponses(value = {//
        @ApiResponse(code = 400, message = "Something went wrong"), //
        @ApiResponse(code = 422, message = "Invalid username/password supplied"),
        @ApiResponse(code = 401, message = "Invalid username/password supplied")})
    public ResponseEntity<JwtAuthenticationResponseDTO> login(//
    		@Valid  @ApiParam("SignIn") @RequestBody SignInTO signin) {
  	  
    	try {
  	  Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		signin.getUsername(),
                		signin.getPassword()
                )
        );
  	  SecurityContextHolder.getContext().setAuthentication(authentication);

      String jwt = tokenProvider.generateToken(authentication);
      return ResponseEntity.ok(new JwtAuthenticationResponseDTO(jwt));
    	}catch(AuthenticationException e) {
    		
    		throw new BadCredentialsException("Usuario o Password Incorrectos.");
    		
    	}

      
    }
    
    
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
  @PostMapping("/signup")
    @ApiOperation(value = "${UserController.signup}")
    @ApiResponses(value = {//
        @ApiResponse(code = 400, message = "Something went wrong"), //
        @ApiResponse(code = 403, message = "Access denied"), //
        @ApiResponse(code = 422, message = "Username is already in use"), //
        @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public ResponseEntity<ApiResponseDTO> registerUser(@Valid @ApiParam("Signup User") @RequestBody UserDataDTO signUpRequest) {
        if(userRepository.existsByUsuario(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponseDTO(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponseDTO(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        
        
        Usuario user = new Usuario(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        List<RolDTO>  rols=signUpRequest.getRoles();
        
     for (Iterator iterator = rols.iterator(); iterator.hasNext();) {
		RolDTO rolDTO = (RolDTO) iterator.next();
		user.addRol(new Rol(rolDTO.getId()));
		
	}
        

       /* Rol userRole = roleRepository.findById(id)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));*/

        Usuario result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsuario()).toUri();

        return ResponseEntity.created(location).body(new ApiResponseDTO(true, "User registered successfully"));
    }
    

}
