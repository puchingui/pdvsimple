package pdvsimple.model;

import javax.persistence.*;

@Entity
public class Categoria extends Identificable {

	@Column(length = 50)
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}