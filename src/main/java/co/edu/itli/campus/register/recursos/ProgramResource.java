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
import co.edu.itli.campus.core.dtos.CatalogoDTO;
import co.edu.itli.campus.exception.ResourceNotFoundException;
import co.edu.itli.campus.register.model.Programa;
import co.edu.itli.campus.register.services.IProgramaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/programas")
@Api(tags = "/api/v1/programas")
public class ProgramResource {
	
	  @Autowired
	  private IProgramaService<Programa> programService;
	 
	  		@SuppressWarnings({ "rawtypes", "unchecked" })
	  		@RequestMapping(method = RequestMethod.POST)
	  		@PreAuthorize("hasRole('R1') or hasRole('A2')")
	  		@ApiOperation(value = "Create Program")
	  		@ApiResponses(value = {//
	        @ApiResponse(code = 400, message = "Something went wrong"), //
	        @ApiResponse(code = 401, message = "Unauthorized"),
	        @ApiResponse(code = 403, message = "Access denied"), //
	        @ApiResponse(code = 422, message = "Name is already in use"), //
	        @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	    public ResponseEntity<ApiResponseDTO> create(@Valid @ApiParam("New Program") @RequestBody Programa programRequest) {
			
		
	        if(programRequest.getNombre()==null) {
	            return new ResponseEntity(new ApiResponseDTO(false, "Name is Required"),
	                    HttpStatus.UNPROCESSABLE_ENTITY);
	        }

	      

	        Programa program = programService.create(programRequest);

	        URI location = ServletUriComponentsBuilder
	                .fromCurrentContextPath().path("/api/v1/programs")
	                .buildAndExpand(program.getId()).toUri();

	        return ResponseEntity.created(location).body(new ApiResponseDTO(true, "Program registered successfully"));
	    }
	  		
	  		  @DeleteMapping(value = "/{id}")
	  		  @PreAuthorize("hasRole('R1') or hasRole('A2')")
	  		  @ApiOperation(value = "Delete Program")
	  		  @ApiResponses(value = {//
	  		      @ApiResponse(code = 400, message = "Something went wrong"), //
	  		      @ApiResponse(code = 403, message = "Access denied"), //
	  		    @ApiResponse(code = 401, message = "Unauthorized"),
	  		      @ApiResponse(code = 404, message = "The program doesn't exist"), //
	  		      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  		  public ResponseEntity<ApiResponseDTO> delete(@ApiParam("Id Program ") @PathVariable Long idProgram) {
	  			programService.deleteById(idProgram);
	  		    return ResponseEntity.ok().body(new ApiResponseDTO(true, "Program deleted successfully"));
	  		  }
	  
	  		// read - one

	  	    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	  	    @ApiOperation(value = "Find by Id Program")
	  	    @PreAuthorize("hasRole('R1') or hasRole('A2')")
	  	    @ApiResponses(value = {//
	  	    @ApiResponse(code = 400, message = "Something went wrong"), //
	  		@ApiResponse(code = 403, message = "Access denied"), //
	  		@ApiResponse(code = 404, message = "The program doesn't exist"), //
	  		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  	    public ResponseEntity<Programa> findById(@ApiParam("Id Program ")@PathVariable("id") final Long id) {
	  	       
	  	    	Programa prog=programService.findById(id);
	  	    	
	  	        return  ResponseEntity.ok().body(prog);
	  	    }

	  	    // read - all

	  	    @RequestMapping(method = RequestMethod.GET)
	  	    @PreAuthorize("hasRole('R1') or hasRole('A2')")
	  	    @ResponseBody
	  	    @ApiOperation(value = "Find all Programs")
	  	    @ApiResponses(value = {//
	  	    @ApiResponse(code = 400, message = "Something went wrong"), //
	  		@ApiResponse(code = 403, message = "Access denied"), //
	  		@ApiResponse(code = 401, message = "Unauthorized"),
	  		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  	    public List<Programa> findAll() {
	  	        return programService.findAll();
	  	    }
	  	    
	  	    
	  	    @GetMapping("/catalogo")
	  	    @ResponseBody
	  	    @ApiOperation(value = "Return all Catalog of Actives Programs")
	  	    @ApiResponses(value = {//
	  	    @ApiResponse(code = 400, message = "Something went wrong"), //
	  		@ApiResponse(code = 403, message = "Access denied"), //
	  		@ApiResponse(code = 401, message = "Unauthorized")})
	  	    public List<CatalogoDTO> getCatalog() {
	  	        return programService.getCatalog();
	  	    }

	  	    @RequestMapping(params = { "page", "size" }, method = RequestMethod.GET)
	  	    @ResponseBody
	  	    @PreAuthorize("hasRole('R1') or hasRole('A2')")
	  	    @ApiOperation(value = "Return all Programs (Paginated)")
	  	    @ApiResponses(value = {//
	  	    @ApiResponse(code = 400, message = "Something went wrong"), //
	  		@ApiResponse(code = 403, message = "Access denied"), //
	  		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  	    public List<Programa> findPaginated(@ApiParam("page and size") @RequestParam("page") @PathVariable final int page, 
	  	    		@RequestParam("size")  @PathVariable final int size) {
	  	        final Page<Programa> resultPage = programService.findPaginated(page, size);
	  	        if (page > resultPage.getTotalPages()) {
	  	            throw new ResourceNotFoundException("Programs not found");
	  	        }
	  	        
	  	        return resultPage.getContent();
	  	    }
	  	    
	  	    @GetMapping("/pageable")
	  	    @PreAuthorize("hasRole('R1') or hasRole('A2')")
	  	    @ResponseBody
	  	    @ApiOperation(value = "Find all Programs (Paginated)")
	  	    @ApiResponses(value = {//
	  	    @ApiResponse(code = 400, message = "Something went wrong"), //
	  		@ApiResponse(code = 403, message = "Access denied"), //
	  		@ApiResponse(code = 401, message = "Unauthorized"),
	  		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  	    public List<Programa> findPaginatedWithPageable(@Valid @ApiParam("Pageable object") @RequestBody Pageable pageable) {
	  	        final Page<Programa> resultPage = programService.findPaginated(pageable);
	  	        if (pageable.getPageNumber() > resultPage.getTotalPages()) {
	  	        	throw new ResourceNotFoundException("Programs not found");
	  	        }
	  	       
	  	        return resultPage.getContent();
	  	    }

	  	    // write

	  	  
	  	    @PreAuthorize("hasRole('R1') or hasRole('A2')")
	  	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	  	    @ResponseStatus(HttpStatus.OK)
	  	    @ApiOperation(value = "Update Programs Paginated")
	  	    @ApiResponses(value = {//
	  	    @ApiResponse(code = 400, message = "Something went wrong"), //
	  		@ApiResponse(code = 403, message = "Access denied"), //
	  		@ApiResponse(code = 401, message = "Unauthorized"),
	  		@ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	  	    public  ResponseEntity<ApiResponseDTO> update(@Valid @ApiParam("id and object Program") @PathVariable("id") final Long id, @RequestBody final Programa program) {
	  	        Preconditions.checkNotNull(program);
	  	        
	  	      Programa prog=programService.findById(id);
	  	      
	  	    if(prog==null) {
	            return new ResponseEntity<ApiResponseDTO>(new ApiResponseDTO(false, "Program not exist"),
	                    HttpStatus.UNPROCESSABLE_ENTITY);
	        }
	  	      
	  	        
	  	        
	  	        programService.update(program);
	  	      return ResponseEntity.ok().body(new ApiResponseDTO(true, "Program updated successfully")); 
	  	    }
	
}