package pdvsimple.model;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@Views({
		@View(extendsView = "super.DEFAULT", members = "ordenes { ordenes }"),
		@View(name = "NoClienteNoOrdenes", members = "ano, codigo, fecha; detalles; notas") })
public class Factura extends DocumentoComercial {

	@OneToMany(mappedBy = "factura")
	@CollectionView("NoClienteNoFactura")
	private Collection<Orden> ordenes;
	
	@Hidden
	private boolean eliminado;		//indica si esta factura esta eliminada

	public Collection<Orden> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(Collection<Orden> ordenes) {
		this.ordenes = ordenes;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
}
