package pdvsimple.actions;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;

import pdvsimple.model.*;

/***
 * 11.11 - 201 Validacion desde la accion para preguntar por el estado de la vista
 * @author Javier Paniza
 *
 */
public class CrearFacturaDesdeOrdenAction extends ViewBaseAction	//para usar getView()
				implements IHideActionAction
{
	
	private boolean ocultarAccion = false;		//11.16 - 204 para ocultar una accion

	public void execute() throws Exception {
		Object oid = getView().getValue("oid");
		//si el oid es nulo el pedido actual no se ha grabado todavia
		if (oid == null) {
			addError("imposible_crear_factura_orden_no_existe");
			return;
		}
		MapFacade.setValues("Orden",		//11.11 - 201 si el pedido existe lo grabamos (2)
				getView().getKeyValues(),
				getView().getValues());
		Orden orden = XPersistence.getManager().find(	//usamos JPA para obtener
				Orden.class,							//entidad Orden visualizada en la vista
				getView().getValue("oid"));
		orden.crearFactura();		//el trabajo de verdad lo delegamos en la entidad
		getView().refresh(); 		//para ver la factura creada en la pestan~a 'Factura'
		addMessage("factura_creada_desde_orden",	//mensaje de confirmacion
				orden.getFactura());
		ocultarAccion = true;	//todo a funcionado a la perfeccion, asi que ocultamos la accion
	}

	public String getActionToHide() {
		return ocultarAccion ? "Orden.crearFactura" : null;
	}

}
