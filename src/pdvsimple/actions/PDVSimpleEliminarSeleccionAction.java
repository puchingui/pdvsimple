package pdvsimple.actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;
import org.openxava.model.meta.*;
import org.openxava.validators.*;


/***
 * 10.26 - 181 Elimina registros en modo lista
 * TabBaseAction para trabajar con datos tabulares (lista) por medio de getTab()
 * IChainAction para encadenar con otra accion, indicada con getNextAction()
 * @author Javier Paniza
 */
public class PDVSimpleEliminarSeleccionAction extends TabBaseAction implements IChainAction 
{
	private String nextAction = null;		//para almacenar la siguiente accion
	private boolean restaurar;				//una nueva propiedad restaurar
	
	public void execute() throws Exception {
		if (!getMetaModel().containsMetaProperty("eliminado")) {
			nextAction = "CRUD.deleteSelected";		//"CRUD.deleteSelected se ejecutara
													//cuando esta accion finalice
			return;
		}
		markSelectedEntitiesAsDeleted();	//logica para marcar las filas
		//seleccionadas como objetos borrados
	}
	
	private MetaModel getMetaModel() {
		return MetaModel.get(getTab().getModelName());
	}

	//obligatorio por causa de IChainAction
	public String getNextAction() throws Exception {
		return nextAction;		//si es nulo no se encadena con ninguna accion
	}
	
	/***
	 * 10.27 - 182 Logica para marcar como borradas las entidades de modo lista
	 * Generalmente las acciones para modo lista extienden de TabBaseAction,
	 * asi puedes usar getTab() para obtener los objetos Tab asociados a la lista.
	 * Un Tab (de org.openxava.tab) te permite manipular los datos tabulares.
	 * Por ejemplo en el metodo getMetaModel() preguntamos al Tab el nombre del
	 * modelo para obtener el MetaModel correspondiente.
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private void markSelectedEntitiesAsDeleted() throws Exception {
		Map values = new HashMap();		//valores a asignar a cada entidad para marcarla
		values.put("eliminado", !isRestaurar());	//10.28 en lugar de un true fijo, usamos
												//el valor de la propiedad restaurar
		for (int row : getSelected()) {	//itera sobre todas las filas seleccionadas
			Map key = (Map) getTab().getTableModel().getObjectAt(row);
			try {		//seleccionadas. obtenemos la clave de cada entidad
				MapFacade.setValues(	//modificamos cada entidad
						getTab().getModelName(), 
						key, 
						values);
			}
			catch (ValidationException ex) {	//si se produce una Validation Exception...
				addError("no_delete_row", row + 1, key);
				addError(ex.getErrors().toString());	//...mostramos los mensajes
			}
			catch (Exception ex) {		//si se lanza cualquier otra excepcion, se an~ade
				addError("no_delete_row", row + 1, key);	//un mensaje generico
			}
		}
		getTab().deselectAll(); 	//despues de borrar deseleccionamos las filas
		resetDescriptionsCache();	//y reiniciamos el cache de los combos para este usuario
	}

	public boolean isRestaurar() {
		return restaurar;
	}

	public void setRestaurar(boolean restaurar) {
		this.restaurar = restaurar;
	}
}
