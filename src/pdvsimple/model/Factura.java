package pdvsimple.model;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@Views({
		@View(extendsView = "super.DEFAULT", members = "ordenes { ordenes }"),
		@View(name = "NoClienteNoOrdenes", members = "ano, codigo, fecha; detalles; notas") })
@Tabs({			//10.32 - 186 @Tabs es para definir varios tabs para la misma entidad
	@Tab(baseCondition = "eliminado = false"),		//Tab sin nombre, es el predeterminado
	@Tab(name="Eliminado", baseCondition = "eliminado = true")	//Tab con nombre
})
public class Factura extends DocumentoComercial {

	@OneToMany(mappedBy = "factura")
	@CollectionView("NoClienteNoFactura")
	private Collection<Orden> ordenes;
	
	public Collection<Orden> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(Collection<Orden> ordenes) {
		this.ordenes = ordenes;
	}
}
