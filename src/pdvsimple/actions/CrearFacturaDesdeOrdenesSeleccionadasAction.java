package pdvsimple.actions;

import java.util.*;

import javax.ejb.*;
import javax.inject.*;

import org.openxava.actions.*;
import org.openxava.model.*;

import pdvsimple.model.*;


/***
 * 11.18 - 206 TabBaseAction es tipico para accione de lista
 * @author Javier Paniza
 *
 */
public class CrearFacturaDesdeOrdenesSeleccionadasAction extends TabBaseAction 
			implements IChangeModuleAction		//11.23 - 210 para cambiar a otro
{												//despues de la ejecucion
	
	/***
	 * 11.29 - 213 objeto de sesion global y accion on-init
	 * un campo privado sin getter ni setter
	 */
	@Inject
	private Map ClaveFacturaActual;
	
	public String getNextModule() {
		return "EditarFacturaActual";		//nombre de modulo como esta
	}										//definido en applicacion.xml

	public boolean hasReinitNextModule() {
		return true;		//11.23 asi el modulo se inicializa cada vez que cambiamos a el
	}

	public void execute() throws Exception {
		Collection<Orden> ordenes = getSelectedOrdenes();
		Factura factura = Factura.crearFacturaDesdeOrdenes(ordenes);
		addMessage("factura_creada_desde_ordenes", factura, ordenes);
		//11.30 - 214
		ClaveFacturaActual = toKey(factura);	//pone la clave de la recien creada
												//factura en el campo ClaveFacturaActual
												//por lo tanto tambien en el objeto de sesion
												//pdvsimple_ClaveFacturaActual
	}
	
	private Collection<Orden> getSelectedOrdenes() throws FinderException {
		Collection<Orden> resultado = new ArrayList<Orden>();
		for (Map key : getTab().getSelectedKeys()) {
			Orden orden = (Orden) MapFacade.findEntity("Orden", key);
			resultado.add(orden);
		}
		return resultado;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map toKey(Factura factura) {		//extrae la clave de la factura en formato mapa
		Map key = new HashMap();
		key.put("oid", factura.getOid());
		return key;
	}
	
	

}
