package pdvsimple.model;

import java.util.*;

import javax.persistence.*;

import org.apache.commons.beanutils.*;
import org.hibernate.validator.*;
import org.openxava.annotations.*;
import org.openxava.jpa.*;
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
@Tabs({			//10.32 - 186 @Tabs es para definir varios tabs para la misma entidad
	@Tab(baseCondition = "eliminado = false"),		//Tab sin nombre, es el predeterminado
	@Tab(name="Eliminado", baseCondition = "eliminado = true")	//Tab con nombre
})
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
	
	/***
	 * cuadro 11.5 - 197
	 * @throws Exception para tener un codigo mas simple de momento
	 */
	public void crearFactura() throws Exception {
		Factura factura = new Factura();	//instancia una nueva factura
		BeanUtils.copyProperties(factura, this); 	//y copia el estado del pedido actual
		factura.setOid(null); 	//para que JPA sepa que esta entidad todavia no existe
		factura.setFecha(new Date());
		factura.setDetalles(new ArrayList());	//borra la coleccion de detalles
		XPersistence.getManager().persist(factura);
		copiaDetallesAFactura(factura);		//rellena la coleccion de detalles
		this.factura = factura;		//siempre despues de persist()
	}
	
	/***
	 * cuadro 11.8 - 198 copiar una coleccion de entidad a entidad
	 * @param factura
	 * @throws Exception
	 */
	private void copiaDetallesAFactura(Factura factura) throws Exception {
		for (Detalle detalleOrden : getDetalles()) {	//itera por los detalles del pedido actual
			Detalle detalleFactura =		//clona el detalle (1) 
					(Detalle) BeanUtils.cloneBean(detalleOrden);
			detalleFactura.setOid(null); 	//para ser grabada como una nueva entidad (2)
			detalleFactura.setPadre(factura); 	//el punto clave: poner un nuevo padre (3)
			XPersistence.getManager().persist(detalleFactura); 	//(4)
		}
	}

}
