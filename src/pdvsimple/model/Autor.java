package pdvsimple.model;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
public class Autor extends Identificable {

	@Column(length = 49)
	@Required
	private String nombre;

	@OneToMany(mappedBy = "autor")
	@ListProperties("codigo, descripcion, precio")
	private Collection<Producto> productos;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Producto> getProductos() {
		return productos;
	}

	public void setProductos(Collection<Producto> productos) {
		this.productos = productos;
	}

}
