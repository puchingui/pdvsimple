package pdvsimple.model;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import pdvsimple.calculators.*;

@Entity
@View(members = "producto; cantidad, precioPorUnidad, importe")
public class Detalle extends Identificable {

	@ManyToOne
	// sin lazy fetching porque falla al ser removido un detalle desde factura
	private DocumentoComercial padre;

	private int cantidad;

	// esta clase calcula el valor inicial
	@DefaultValueCalculator(value = PrecioPorUnidadCalculator.class,
	// la propiedad codigoProducto del calculador se llena con el valor de
	// producto.codigo
	properties = @PropertyValue(name = "codigoProducto", from = "producto.codigo"))
	private BigDecimal precioPorUnidad; // una propiedad persistente
										// convencional

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@ReferenceView("Simple")
	// el producto es desplegado usando una simple vista
	@NoFrame
	private Producto producto;

	public DocumentoComercial getPadre() {
		return padre;
	}

	public void setPadre(DocumentoComercial padre) {
		this.padre = padre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioPorUnidad() {
		// asi nunca devuelve nulo
		return precioPorUnidad == null ? BigDecimal.ZERO : precioPorUnidad;
	}

	public void setPrecioPorUnidad(BigDecimal precioPorUnidad) {
		this.precioPorUnidad = precioPorUnidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	// ejemplo propiedad calculada simple
	@Stereotype("MONEY")
	//esta propiedad se recalculara y se redibujara cada ves el usuario cambie de producto o cantidad
	@Depends("precioPorUnidad, cantidad")
	public BigDecimal getImporte() {
		// getPrecioPorUnidad en ves de producto.precio
		return new BigDecimal(cantidad).multiply(getPrecioPorUnidad());
	}

}
