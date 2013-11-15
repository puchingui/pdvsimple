package pdvsimple.actions;

import java.rmi.*;
import java.util.*;

import javax.ejb.*;

import org.openxava.actions.*;
import org.openxava.model.*;
import org.openxava.util.*;
import org.openxava.validators.*;

import pdvsimple.model.*;

/***
 * 12.16 - 239 Logica estandar para an~adir elementos a la coleccion
 * @author Javier Paniza
 *
 */
public class AgregarOrdenesAFactura extends AddElementsToCollectionAction {
	
	public void execute() throws Exception {
		super.execute();		//usamo la logica etandar tal cual
		getView().refresh();	//para visualizar datos frescos, incluyendo los importes
								//recalculados, que dependen de la linea de detalle
	}
	
	/***
	 * el metodo llamado para asociar cada entidad a la principal, 
	 * en este caso para asociar cada pedido a la factura
	 */
	protected void associateEntity(Map keyValues) 
				throws ValidationException, XavaException, ObjectNotFoundException, 
						FinderException, RemoteException 
	{
		super.associateEntity(keyValues); 		//ejecuta la logica estandar (1)
		Orden orden = (Orden) MapFacade.findEntity("Orden", keyValues);  //(2)
		orden.copiaDetallesAFactura(); 		//delega el trabajo principal a la entidad (3)
	}
}
