package pdvsimple.actions;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import pdvsimple.model.*;

public class CrearFacturaDesdeOrdenAction extends ViewBaseAction	//para usar getView()
{

	public void execute() throws Exception {
		Orden orden = XPersistence.getManager().find(	//usamos JPA para obtener
				Orden.class,							//entidad Orden visualizada en la vista
				getView().getValue("oid"));
		orden.crearFactura();		//el trabajo de verdad lo delegamos en la entidad
		getView().refresh(); 		//para ver la factura creada en la pestan~a 'Factura'
		addMessage("factura_creada_desde_orden",	//mensaje de confirmacion
				orden.getFactura());
	}

}
