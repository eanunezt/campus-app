package co.edu.itli.campus.core.model;

import org.hibernate.annotations.NaturalId;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eanunezt on 01/08/17.
 */

@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "usuario"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class Usuario extends EntidadAuditada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 0)
    @Column(length = 32)
    //@GeneratedValue(generator = "system-uuid")
    //@GenericGenerator(name = "system-uuid", strategy = "uuid")
    //String id;
    private Long id;


    @NotBlank
    @Size(max = 40)
    @ApiModelProperty(position = 1)
    private String nombre;

    @NotBlank
    @Size(max = 15)
    @ApiModelProperty(position = 2)
    private String usuario;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    @ApiModelProperty(position = 3)
    private String email;

    @NotBlank
    @Size(max = 100)
    @ApiModelProperty(position = 4)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_roles",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    @ApiModelProperty(position = 5)
    private Set<Rol> roles = new HashSet<>();

    public Usuario() {

    }

    public Usuario(String name, String username, String email, String password) {
        this.nombre = name;
        this.usuario = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }
    
    public boolean addRol(Rol rol) {
    	return this.roles.add(rol);
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuario() {
		return usuario;
	}
	@Transient
	public String getUsername() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
    
}