package pdvsimple.model;

import javax.persistence.*;

import org.hibernate.validator.*;
import org.openxava.annotations.*;
import org.openxava.util.*;

/***
 * Antes de facturar una orden la orden debe estar entregada
 * @author
 * 
 */

@Entity
@Views({
		@View(extendsView = "super.DEFAULT", members = "entregado; factura { factura }"),
		@View(name = "NoClienteNoFactura", members = "ano, codigo, fecha; detalles; notas") })
public class Orden extends DocumentoComercial {

	@ManyToOne
	@ReferenceView("NoClienteNoOrdenes")
	private Factura factura;
	
	/* 9.1 - 142 delivered como alternativas de validacion
	 * Un usuario no puede asignar pedidos a una factura 
	 * si los pedidos no han sido entregados
	 */
	private boolean entregado;

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	public boolean isEntregado() {
		return entregado;
	}
	
	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}
	
	@AssertTrue		//antes de grabar confirma que el metodo devuelve true, si no lanza una excepcion
	private boolean isEntregadoParaSerFacturado() {
		return factura == null || isEntregado();	//la logica de validacion
	}
	
	@PreRemove		//justo antes de borrar la entidad
	private void validateOnRemove() {
		if (factura != null) {		//la logica de validacion
			throw new IllegalStateException(	//lanza una excepcion runtime
				XavaResources.getString(		//para obtener un mensaje de texto i18n
						"no_se_puede_eliminar_orden_con_factura"));
		}
	}
	
	public void setEliminado(boolean eliminado) {
		if (eliminado) validateOnRemove();		//llamamos a la validacion explicitamente
		super.setEliminado(eliminado);
	}

}
