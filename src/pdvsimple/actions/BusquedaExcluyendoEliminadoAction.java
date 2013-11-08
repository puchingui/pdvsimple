package pdvsimple.actions;

import java.util.*;

import javax.ejb.ObjectNotFoundException;

import org.openxava.actions.*;

/***
 * 10.22 - 178 Busqueda que excluye los objetos marcados como borrados
 * @author Javier Paniza
 *
 */
public class BusquedaExcluyendoEliminadoAction 
			extends SearchByViewKeyAction		//accion estandar de OpenXava para buscar
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
		Map valores = super.getValuesFromView();
		valores.put("eliminado", false);	//llenamos la propiedad eliminado con false
		return valores;
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
		Map miembros = super.getMemberNames();
		miembros.put("eliminado", null);		//queremos obtener la propiedad eliminado,
		return miembros;						//aunque no este en la vista
	}
	
	/***
	 * asigna los valores desde la entidad a la vista
	 */
	protected void setValuesToView(Map valores) throws Exception {
		/* si tiene una propiedad eliminado y vale true lanzamos la misma 
		 * excepcion que OpenXava lanza cuando el objeto no se encuentra */
		if (isEliminable() && (Boolean) valores.get("eliminado")) {
			throw new ObjectNotFoundException();
		}
		else {
			super.setValuesToView(valores); 	//en caso contrario usamos la logica estandar
		}
	}
		
}
