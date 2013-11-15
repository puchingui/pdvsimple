package pdvsimple.actions;

import java.util.*;

import javax.inject.*;

import org.openxava.actions.*;

/***
 * 11.32 - 215 Accion para cargar la ultima factura en el modulo EditarFacturaActual
 * SearchByViewKeyAction para llenar la vista a partir de la clave
 * @author Javier Paniza
 *
 */
public class CargarFacturaActualAction extends SearchByViewKeyAction {

	/***
	 * para coger el valor del objeto de sesion pdvsimple_ClaveFacturaActual
	 * llenado en el modulo Orden
	 */
	@Inject
	private Map ClaveFacturaActual;
	
	public void execute() throws Exception {
		getView().setValues(ClaveFacturaActual); 	//pone la clave en la vista
		super.execute(); 	//llena toda la vista a partir de los campos clave
	}

}
