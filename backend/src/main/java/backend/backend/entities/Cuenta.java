package backend.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="cuenta")

public class Cuenta extends Base implements UserDetails{	
	
	@Column(name="cantidadReferidos")
	private int cantidadReferidos;
	
	@Column(name="contrasenia",nullable = false,length = 100)
	private String contrasenia;
	
	@Column(name="correo",nullable = false,length = 100,unique=true)
	private String correo;
	
	@Column(name="cuentaCreada")
	private Date cuentaCreada;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="fk_cuentaEliminada")
	private CuentaEliminada cuentaEliminada;
	
	@Column(name="cuentaVerificada")
	private Date cuentaVerificada;
	
	
	@Column(name="ultimaConexion")
	private Date ultimaConexion;
	
	@Column(name="urlFoto",nullable = false)
	private String urlFoto;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Seguidos> listaSeguidos = new ArrayList<Seguidos>();
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Seguidores> listaSeguidores = new ArrayList<Seguidores>();
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="fk_rol")
	private Rol rol;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(rol.getNombreRol());
		return Collections.singletonList(authority);
	}

	@Override
	public String getPassword() {
		return contrasenia;
	}

	@Override
	public String getUsername() {
		return correo;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		if(cuentaEliminada==null){
			return true;
		} else {
			throw new LockedException("La cuenta está bloqueada.");
		}
		
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		if(cuentaVerificada!=null){
			return true;
		} else {
			throw new DisabledException("La cuenta aún no está verificada, revisa tu mail");
		}
	}
	
	
}
