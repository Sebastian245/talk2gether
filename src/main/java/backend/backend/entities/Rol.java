package backend.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Date;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="rol")

public class Rol extends Base{	
	@Column(name="fechaHoraFinVigenciaRol")
	private Date fechaHoraFinVigenciaRol;
	
	@Column(name="fechaHoraAltaRol")
	private Date fechaHoraAltaRol;

	
	@Column(name="nombreRol",nullable=false,length = 50)
	@NotBlank(message = "El rol no puede estar vacio")
	@Size(min=3,max=50,message = "El rol debe estar entre los 3 y 50 caracteres")
	private String nombreRol;

	
	
	
	
}
