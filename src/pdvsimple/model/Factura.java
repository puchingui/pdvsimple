package pdvsimple.model;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.validators.*;

@Entity
@Views({
		@View(extendsView = "super.DEFAULT", members = "ordenes { ordenes }"),
		@View(name = "NoClienteNoOrdenes", members = "ano, codigo, fecha; detalles; notas") })
@Tabs({			//10.32 - 186 @Tabs es para definir varios tabs para la misma entidad
	@Tab(baseCondition = "eliminado = false",		//Tab sin nombre, es el predeterminado
			properties="ano, codigo, fecha, cliente.codigo, cliente.nombre, " +
						"beneficioEstimado, subTotal, " +
						"itbis, total, notas"),		
	@Tab(name="Eliminado", baseCondition = "eliminado = true")	//Tab con nombre
})
public class Factura extends DocumentoComercial {

	@OneToMany(mappedBy = "factura")
	@CollectionView("NoClienteNoFactura")
	@NewAction("Factura.agregarOrdenes")	//12.11 - 236 define nuestra propia accion para an~adir pedidos
	private Collection<Orden> ordenes;
	
	public Collection<Orden> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(Collection<Orden> ordenes) {
		this.ordenes = ordenes;
	}
	
	public static Factura crearFacturaDesdeOrdenes(Collection<Orden> ordenes) 
				throws ValidationException
	{
		Factura factura = null;
		for (Orden orden : ordenes) {
			if (factura == null) {		//la primera vez, el primer pedido
				orden.crearFactura();	//reutilizamos la logica para
										//crear una factura a partir de un pedido
				factura = orden.getFactura();	//y cogemos la factura recien creada
			}
			else {		//para el resto de los pedidos la factura ya esta creada
				orden.setFactura(factura); 		//asigna la factura
				orden.copiaDetallesAFactura(factura );
			}
		}
		if (factura == null) {		//si no hay pedidos
			throw new ValidationException("imposible_crear_factura_orden_no_especificada");
		}
		return factura;
	}
}
