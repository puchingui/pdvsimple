package pdvsimple.actions;

import org.openxava.actions.*;

/***
 * 11.26 - 212 Volviendo al modulo anterior, accion de grabar la factura
 * y vuelve al modulo que llamo.
 * @author Javier Paniza
 *
 */
public class GrabarFacturaAction extends SaveAction implements IChangeModuleAction 
{

	public String getNextModule() {
		return PREVIOUS_MODULE;		//vuelve al modulo que llamo, Orden en este caso
	}

	public boolean hasReinitNextModule() {
		return false;		//no queremos inicializar el modulo orden
	}

}
