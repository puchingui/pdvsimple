package pdvsimple.model;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@View(name = "Simple", members = "codigo, nombre")
public class Cliente {

	@Id
	@Column(length = 6)
	// la longitud de columna se usa a nivel UI y a nivel DB
	private int codigo;

	@Column(length = 50)
	@Required
	private String nombre;

	// @NoFrame
	// no muestra el marco en la interfaz al rededor de la direccion
	@Embedded
	// referencia a una clase incrustable
	private Direccion direccion;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Direccion getDireccion() {
		if (direccion == null)
			direccion = new Direccion(); // asi nunca es nulo
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
}