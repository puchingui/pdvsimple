package pdvsimple.model;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@View(name = "Simple", members = "codigo, descripcion")
public class Producto {

	@Id
	@Column(length = 9)
	private int codigo;

	@Column(length = 50)
	@Required
	private String descripcion;

	@ManyToOne(fetch = FetchType.LAZY)
	@DescriptionsList
	private Autor autor;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	// carga registro segun se necesite y la referencia siempre debe tener valor
	@DescriptionsList
	// la referencia es desplegada como un combo
	private Categoria categoria;

	@Stereotype("MONEY")
	// usado para almacenar dinero
	private BigDecimal precio; // BigDecimal por lo regular es usado para dinero

	@Stereotype("PHOTO")
	// el usuario puede ver y cambiar una foto
	private byte[] foto;

	@Stereotype("IMAGES_GALLERY")
	// una galeria de imagenes disponible
	@Column(length = 32)
	// un string de longitud 32 byte almacena la llave de la galeria
	private String masFotos;

	@Stereotype("MEMO")
	// Esto es para texto grande, un area para texto que puede ser usado
	private String observacion;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getMasFotos() {
		return masFotos;
	}

	public void setMasFotos(String masFotos) {
		this.masFotos = masFotos;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}
