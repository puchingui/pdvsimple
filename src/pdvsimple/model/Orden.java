package pdvsimple.model;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@Views({
		@View(extendsView = "super.DEFAULT", members = "factura { factura }"),
		@View(name = "NoClienteNoFactura", members = "ano, codigo, fecha; detalles; notas") })
public class Orden extends DocumentoComercial {

	@ManyToOne
	@ReferenceView("NoClienteNoOrdenes")
	private Factura factura;

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

}
