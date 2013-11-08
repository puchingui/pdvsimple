package pdvsimple.actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;

/***
 * 
 * @author Javier Paniza
 *
 */
public class PDVSimpleEliminarAction extends ViewBaseAction {

	/***
	 * 10.10 - 169
	 */
	@SuppressWarnings("unchecked")
	public void execute() throws Exception {	//la logica de la accion
		if (getView()
			.getKeyValuesWithValue()		//en lugar de getValue("oid")
			.isEmpty()) 
		{
			addError("no_delete_not_exists");
			return;
		}
		
		if (!getView().getMetaModel()		//metadatos de la entidad actual
			.containsMetaProperty("eliminado"))	//tiene una propiedad eliminado?
		{
			addMessage(
					"No eliminado, no existe la propiedad "
					+ "eliminado en esta entidad");
			return;
		}
		
		@SuppressWarnings("rawtypes")
		Map valores = new HashMap();		//los valores a modificar en la entidad
		valores.put("eliminado", true);		//asignamos true a la propiedad eliminado
		MapFacade.setValues(				//modifica los valores de la entidad indicada
				getModelName(),				//un metodo de ViewBaseAction
				getView().getKeyValues(),	//la clave de la entidad a modificar 
				valores);					//los valores a cambiar
		resetDescriptionsCache();			//reinicia los caches para los combos
		addMessage("object_deleted", getModelName());
		getView().clear();
		getView().setEditable(false); 		//dejamos la vista como no editable
	}

}
