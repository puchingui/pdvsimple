package pdvsimple.model;

import javax.persistence.*;

import org.openxava.annotations.*;

@Embeddable
// define una clase incrustable
public class Direccion {

	@Required
	@Column(length = 30)
	private String calle;

	@Required
	@Column(length = 5)
	private int numero;

	@Required
	@Column(length = 30)
	private String sector;

	@Required
	@Column(length = 20)
	private String ciudad;

	@Required
	@Column(length = 30)
	private String provincia;

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

}
