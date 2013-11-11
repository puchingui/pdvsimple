package pdvsimple.actions;

import java.util.*;

import javax.ejb.ObjectNotFoundException;

import org.openxava.actions.*;

/***
 * 10.22 - 178 Busqueda que excluye los objetos marcados como borrados
 * extends SearchExecutingOnChangeAction se cambio de SearchByViewkeyAction que es el estandar
 * 11.15 - 204 porque SearchExecutingOnChangeAction ejecuta automaticamente los eventos OnChange
 * mayores detalles en el cuadro 11.15 pagina 204
 * @author Javier Paniza
 *
 */
public class BusquedaExcluyendoEliminadoAction 
			extends SearchExecutingOnChangeAction
{
	/***
	 * pregunta si la entidad tiene una propiedad eliminado
	 * @return
	 */
	private boolean isEliminable() {
		return getView().getMetaModel().containsMetaProperty("eliminado");
	}
	
	/***
	 * coge los valores visualizados en la vista
	 * estos valores se usan como clave al buscar
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Map getValuesFromView() throws Exception {
		if (!isEliminable()) {		//si no es 'eliminable' usamos la logica estandar
			return super.getValuesFromView();
		}
		Map values = super.getValuesFromView();
		values.put("eliminado", false);	//llenamos la propiedad eliminado con false
		return values;
	}
	
	/***
	 * los miembros a leer de la entidad
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Map getMemberNames() throws Exception
	{
		if (!isEliminable()) {		//si no es 'eliminable' ejecutamos la logica estandar
			return super.getMemberNames();
		}
		Map members = super.getMemberNames();
		members.put("eliminado", null);		//queremos obtener la propiedad eliminado,
		return members;						//aunque no este en la vista
	}
	
	/***
	 * asigna los valores desde la entidad a la vista
	 */
	protected void setValuesToView(Map values) throws Exception {
		/* si tiene una propiedad eliminado y vale true lanzamos la misma 
		 * excepcion que OpenXava lanza cuando el objeto no se encuentra */
		if (isEliminable() && (Boolean) values.get("eliminado")) {
			throw new ObjectNotFoundException();
		}
		else {
			super.setValuesToView(values); 	//en caso contrario usamos la logica estandar
		}
	}
		
}
