package pdvsimple.actions;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import pdvsimple.model.*;

/***
 * 
 * @author Javier Paniza
 *
 */
public class EliminarFacturaAction extends ViewBaseAction {

	public void execute() throws Exception {	//la logica de la accion
		if (getView().getValue("oid") == null) {	//hay un objeto en la vista?
			addError("no_delete_not_exists");
			return;
		}
		
		Factura factura = XPersistence.getManager()
				.find(Factura.class, getView().getValue("oid")); //llemos el id de la vista
		factura.setEliminado(true); 		//modificamos el estado de la entidad
		addMessage("object_deleted", "Factura");	//mensaje de confirmacion borrado
		//addMessage("Factura codigo: " + factura.getCodigo() + " ha sido borrada");
		getView().clear(); 		//limpiamos la vista		
	}

}
