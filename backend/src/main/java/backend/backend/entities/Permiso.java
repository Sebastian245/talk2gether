package backend.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="permiso")

public class Permiso extends Base{	
	@Column(name="fechaHoraAltaPermiso")
	private Date fechaHoraAltaPermiso;
	
	@Column(name="fechaHoraFinVigenciaPermiso")
	private Date fechaHoraFinVigenciaPermiso;
	
	@Column(name="nombrePermiso")
	private String nombrePermiso;
	
	@Column(name="url")
	private String url;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "permiso_roles",joinColumns = @JoinColumn(name="permiso_id"),inverseJoinColumns = @JoinColumn(name="rol_id"))
	private Set<Rol> listaRol = new HashSet<>();
	
}
