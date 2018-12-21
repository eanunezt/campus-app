package co.edu.itli.campus.core.services;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.itli.campus.core.model.Autorizacion;
import co.edu.itli.campus.core.model.Rol;
import co.edu.itli.campus.core.model.RolAutorizacion;
import co.edu.itli.campus.core.model.Usuario;
import co.edu.itli.campus.core.respositorios.UserRepository;
import co.edu.itli.campus.core.seguridad.JwtTokenProvider;
import co.edu.itli.campus.core.seguridad.UserPrincipal;
import co.edu.itli.campus.exception.AppException;
import co.edu.itli.campus.exception.ResourceNotFoundException;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    JwtTokenProvider tokenProvider;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        Usuario user = userRepository.findByUsuarioOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
        );

        return create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Usuario user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return create(user);
    }
    
private UserPrincipal create(Usuario user) {
    	
    	Set<Rol> roles=user.getRoles();
    	Map<Long,GrantedAuthority> authoritiesMap=new HashMap<Long,GrantedAuthority>();
    	
    	for (Iterator<?> iterator = roles.iterator(); iterator.hasNext();) {
			Rol rol = (Rol) iterator.next();
			
			if(rol.getAutorizaciones()!=null) {
				Iterator<RolAutorizacion> iteratorAUTH = rol.getAutorizaciones().iterator();
				while (iteratorAUTH.hasNext()) {
					Autorizacion object = (Autorizacion) iteratorAUTH.next().getAutorizacion();
					authoritiesMap.put(object.getId(), new SimpleGrantedAuthority("A"+object.getId()));
				}
				
			}
			
		}
    	
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->                
        new SimpleGrantedAuthority("R"+role.getId())
        ).collect(Collectors.toList());
        
        authorities.addAll(authoritiesMap.values());
        
        return new UserPrincipal(
                user.getId(),
                user.getNombre(),
                user.getUsuario(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

public Usuario findByUsername(String username) {
    return userRepository.findByUsuario(username).orElseThrow(() -> new AppException("User not found."));
  }

public Usuario whoami(HttpServletRequest req) {
    return userRepository.findByUsuario(tokenProvider.getUsername(tokenProvider.resolveToken(req))).orElseThrow(() -> new AppException("User not found."));
  }

public Usuario save(Usuario user) {
    return userRepository.save(user);
  }

}