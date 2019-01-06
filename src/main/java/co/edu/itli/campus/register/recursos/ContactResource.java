package co.edu.itli.campus.register.recursos;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.base.Preconditions;

import co.edu.itli.campus.core.dtos.ApiResponseDTO;
import co.edu.itli.campus.exception.ResourceNotFoundException;
import co.edu.itli.campus.register.model.Contacto;
import co.edu.itli.campus.register.services.IContactoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/contactos")
@Api(tags = "/api/v1/contactos")
public class ContactResource {
	
	  @Autowired
	  private IContactoService<Contacto> contactService;
	 
	  		@SuppressWarnings({ "rawtypes", "unchecked" })
	  		@RequestMapping(method = RequestMethod.POST)
	  		//@PreAuthorize("hasRole('R1') or hasRole('A2')")
	  		@ApiOperation(value = "Create Contact")
	  		@ApiResponses(value = {//
	        @ApiResponse(code = 400, message = "Something went wrong"), //
	        @ApiResponse(code = 401, message = "Unauthorized"),
	        @ApiResponse(code = 403, message = "Access denied"), //
	        @ApiResponse(code = 422, message = "Name is already in use"), //
	        @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	    public ResponseEntity<ApiResponseDTO> create(@Valid @ApiParam("New Contact") @RequestBody Contacto contactRequest) {
			
	  		ResponseEntity<ApiResponseDTO> _validate=validateContact(contactRequest);
	        if(_validate!=null) {
	            return validateContact(contactRequest);
	        }	      

	        Contacto contact = contactService.create(contactRequest);

	        URI location = ServletUriComponentsBuilder
	                .fromCurrentContextPath().path("/api/v1/contactos")
	                .buildAndExpand(contact.getId()).toUri();

	        return ResponseEntity.created(location).body(new ApiResponseDTO(true, "Contacto registrado correctamente."));
	    }
	  		
	  		  @DeleteMapping(value = "/{id}")
	  		  @PreAuthorize("hasRole('R1')")
	  		  @ApiOperation(value = "Delete Contact")
	  		  @ApiResponses(value = {//
	  		      @ApiResponse(code = 400, message = "Something went wrong"), //
	  		      @ApiResponse(code = 403, message = "Access denied"), //
	  		    @ApiResponse(code = 401, message = "Unauthorized"),
	  		      @ApiResponse(code = 404, message = "The contact doesn't exist"), //
	  		      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  		  public ResponseEntity<ApiResponseDTO> delete(@ApiParam("Id Contact ") @PathVariable Long idContact) {
	  			contactService.deleteById(idContact);
	  		    return ResponseEntity.ok().body(new ApiResponseDTO(true, "Contact deleted successfully"));
	  		  }
	  
	  		// read - one
	  		@PreAuthorize("hasRole('R1')")
	  	    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	  	    @ApiOperation(value = "Find by Id Contact")
	  	    @ApiResponses(value = {//
	  	    @ApiResponse(code = 400, message = "Something went wrong"), //
	  		@ApiResponse(code = 403, message = "Access denied"), //
	  		@ApiResponse(code = 404, message = "The contact doesn't exist"), //
	  		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  	    public ResponseEntity<Contacto> findById(@ApiParam("Id Contact ")@PathVariable("id") final Long id) {
	  	       
	  	    	Contacto prog=contactService.findById(id);
	  	    	
	  	        return  ResponseEntity.ok().body(prog);
	  	    }

	  	    // read - all

	  		@PreAuthorize("hasRole('R1')")
	  	    @GetMapping("/all")
	  	    @ResponseBody
	  	    @ApiOperation(value = "Find all Contacts")
	  	    @ApiResponses(value = {//
	  	    @ApiResponse(code = 400, message = "Something went wrong"), //
	  		@ApiResponse(code = 403, message = "Access denied"), //
	  		@ApiResponse(code = 401, message = "Unauthorized"),
	  		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  	    public List<Contacto> findAll() {
	  	        return contactService.findAll();
	  	    }
	  		
	  		@PreAuthorize("hasRole('R1')")
	  	    @RequestMapping(params = { "page", "size" }, method = RequestMethod.GET)
	  	    @ResponseBody
	  	    @ApiOperation(value = "Find all Contacts Paginated")
	  	    @ApiResponses(value = {//
	  	    @ApiResponse(code = 400, message = "Something went wrong"), //
	  		@ApiResponse(code = 403, message = "Access denied"), //
	  		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  	    public List<Contacto> findPaginated(@ApiParam("page and size") @RequestParam("page") @PathVariable final int page, 
	  	    		@RequestParam("size")  @PathVariable final int size) {
	  	        final Page<Contacto> resultPage = contactService.findPaginated(page, size);
	  	        if (page > resultPage.getTotalPages()) {
	  	            throw new ResourceNotFoundException("Contacts not found");
	  	        }
	  	        
	  	        return resultPage.getContent();
	  	    }
	  		
	  		@PreAuthorize("hasRole('R1')")
	  	    @GetMapping("/pageable")
	  	    @ResponseBody
	  	    @ApiOperation(value = "Find all Contacts Paginated")
	  	    @ApiResponses(value = {//
	  	    @ApiResponse(code = 400, message = "Something went wrong"), //
	  		@ApiResponse(code = 403, message = "Access denied"), //
	  		@ApiResponse(code = 401, message = "Unauthorized"),
	  		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  	    public List<Contacto> findPaginatedWithPageable(@Valid @ApiParam("Pageable object") @RequestBody Pageable pageable) {
	  	        final Page<Contacto> resultPage = contactService.findPaginated(pageable);
	  	        if (pageable.getPageNumber() > resultPage.getTotalPages()) {
	  	        	throw new ResourceNotFoundException("Contacts not found");
	  	        }
	  	       
	  	        return resultPage.getContent();
	  	    }

	  	    // write

	  	  
	  		@PreAuthorize("hasRole('R1')")
	  	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	  	    @ResponseStatus(HttpStatus.OK)
	  	    @ApiOperation(value = "Update Contacts Paginated")
	  	    @ApiResponses(value = {//
	  	    @ApiResponse(code = 400, message = "Something went wrong"), //
	  		@ApiResponse(code = 403, message = "Access denied"), //
	  		@ApiResponse(code = 401, message = "Unauthorized"),
	  		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  	    public  ResponseEntity<ApiResponseDTO> update(@Valid @ApiParam("id and object Contact") @PathVariable("id") final Long id, @RequestBody final Contacto contact) {
	  	        Preconditions.checkNotNull(contact);
	  	        
	  	     
	  	        
	  	      Contacto prog=contactService.findById(id);
	  	      
	  	    if(prog==null) {
	            return new ResponseEntity<ApiResponseDTO>(new ApiResponseDTO(false, "Contacto no existe"),
	                    HttpStatus.UNPROCESSABLE_ENTITY);
	        }
	  	    contact.setId(id);
	  	     ResponseEntity<ApiResponseDTO> _validate=validateContact(contact);
	        if(_validate!=null) {
	            return validateContact(contact);
	        }
	  	        contactService.update(contact);
	  	      return ResponseEntity.ok().body(new ApiResponseDTO(true, "Contact updated successfully")); 
	  	    }
	  	    
	  	    
	  	    @SuppressWarnings("unused")
			private ResponseEntity<ApiResponseDTO> validateContact(Contacto contactRequest) {
	  	    	
	  	    	 if(contactRequest.getNombre()==null) {
	 	            return new ResponseEntity<ApiResponseDTO>(new ApiResponseDTO(false, "Nombre es Requerido"),
	 	                    HttpStatus.UNPROCESSABLE_ENTITY);
	 	        }else if(contactRequest.getEmail()==null) {
	 	            return new ResponseEntity<ApiResponseDTO>(new ApiResponseDTO(false, "Email es Requerido"),
	 	                    HttpStatus.UNPROCESSABLE_ENTITY);
	 	        }
	 	        else if(contactRequest.getId()==null && contactRequest.getEmail()!=null && contactService.existEmail(contactRequest.getEmail())) {
	 	        	
	 	            return new ResponseEntity<ApiResponseDTO>(new ApiResponseDTO(false, "Email existe"),
	 	                    HttpStatus.UNPROCESSABLE_ENTITY);
	 	        }
				return null; 
	  	    }
	
}