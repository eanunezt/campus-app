package co.edu.itli.campus.core.recursos;
import java.net.URI;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.edu.itli.campus.core.dtos.ApiResponseDTO;
import co.edu.itli.campus.core.dtos.ProfileTO;
import co.edu.itli.campus.core.dtos.UserDataDTO;
import co.edu.itli.campus.core.dtos.UserResponseDTO;
import co.edu.itli.campus.core.model.Rol;
import co.edu.itli.campus.core.model.Usuario;
import co.edu.itli.campus.core.respositorios.RolRepository;
import co.edu.itli.campus.core.respositorios.UserRepository;
import co.edu.itli.campus.core.seguridad.JwtTokenProvider;
import co.edu.itli.campus.core.services.CustomUserDetailsService;
import co.edu.itli.campus.exception.AppException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/users")
@Api(tags = "users")
//@CrossOrigin(origins = "*")//TODO
public class UserAdminResource {

  @Autowired
  private CustomUserDetailsService userService;

  @Autowired
  private ModelMapper modelMapper;
  
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
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
@PostMapping("/register")
  @ApiOperation(value = "${UserController.signup}")
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 422, message = "Username is already in use"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public ResponseEntity<?> registerUser(@Valid @ApiParam("Signup User") @RequestBody UserDataDTO signUpRequest) {
      if(userRepository.existsByUsuario(signUpRequest.getUsername())) {
          return new ResponseEntity(new ApiResponseDTO(false, "Usuario en uso!"),
                  HttpStatus.BAD_REQUEST);
      }

      if(userRepository.existsByEmail(signUpRequest.getUsername())) {
          return new ResponseEntity(new ApiResponseDTO(false, "Email en uso!"),
                  HttpStatus.BAD_REQUEST);
      }

      // Creating user's account
      Usuario user = new Usuario(signUpRequest.getName(), signUpRequest.getUsername(),
              signUpRequest.getEmail(), signUpRequest.getPassword());

      user.setPassword(passwordEncoder.encode(user.getPassword()));

      Rol userRole = roleRepository.findByNombre("INVITADO")
              .orElseThrow(() -> new AppException("User Role not set."));

      user.setRoles(Collections.singleton(userRole));

      Usuario result = userRepository.save(user);

      URI location = ServletUriComponentsBuilder
              .fromCurrentContextPath().path("/users/{username}")
              .buildAndExpand(result.getUsuario()).toUri();

      return ResponseEntity.created(location).body(new ApiResponseDTO(true, "User registered successfully"));
  }

  @DeleteMapping(value = "/{username}")
  @PreAuthorize("hasRole('R1')")
  @ApiOperation(value = "${UserController.delete}")
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 404, message = "The user doesn't exist"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public String delete(@ApiParam("Username") @PathVariable String username) {
	  //userRepository.delete(username);
    return username;
  }

  @GetMapping(value = "/{username}")
  @PreAuthorize("hasRole('R1')") //TODO
  @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class)
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 404, message = "The user doesn't exist"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public UserResponseDTO search(@ApiParam("Username") @PathVariable String username, @RequestHeader HttpHeaders headers) {
    return modelMapper.map(userService.findByUsername(username), UserResponseDTO.class);
  }

  @GetMapping(value = "/me")
  @PreAuthorize("hasRole('R1')")
  @ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class)
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public UserResponseDTO whoami(HttpServletRequest req) {	  
	  req.isUserInRole("R1");
    return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
  }
  //userService.getProfileUser(username);
  
  @GetMapping(value = "/profile/{username}")
  @PreAuthorize("hasRole('R1')") //TODO
  @ApiOperation(value = "${UserController.getProfileUser}", response = UserResponseDTO.class)
  @ApiResponses(value = {//
      @ApiResponse(code = 400, message = "Something went wrong"), //
      @ApiResponse(code = 403, message = "Access denied"), //
      @ApiResponse(code = 404, message = "The user doesn't exist"), //
      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
  public ProfileTO getProfileUser(@ApiParam("Username") @PathVariable String username) {
    return userService.getProfileUser(username);
  }
  
  
  @GetMapping(value = "/online")    
  public String online(HttpServletRequest req) {	  
    return "online";
  }

}
