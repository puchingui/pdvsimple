package pdvsimple.actions;

import org.openxava.actions.*;

/***
 * 11.14 - 202 Accion para mostrar/ocultar la accion crearFactura dinamicamente
 * org.openxava.actions es necesario para usar OnChangePropertyAction,
 * IShowActinAction y IHideActionAction
 * Extiende de OnChangePropertyBaseAction necesario para la anotacion OnChange(1)
 * @author Javier Paniza
 *
 */
public class MuestraOcultaCrearFacturaAction extends OnChangePropertyBaseAction
			implements IShowActionAction,	//para mostrar una accion
						IHideActionAction	//para ocultar una accion
{
	private boolean mostrar;	//i true la accion Orden.crearFactura se mostrara

	public void execute() throws Exception {
		mostrar = isOrdenCreada()		//establecemos el valor de 'mostrar'. Este valor
				&& isEntregado() 		//se usara en los metodos de abajo:
				&& !hasFactura();		//getActionToShow y getActionToHide (2) */
	}
	
	private boolean isOrdenCreada() {
		return getView().getValue("oid") != null;	//leemo el valor desde la vista
	}
	
	private boolean isEntregado() {
		Boolean entregado = (Boolean)		//leemos el valor desde la vista 
				getView().getValue("entregado");
		return entregado == null ? false : entregado;
	}
	
	private boolean hasFactura() {
		return getView().getValue("factura.oid") != null;
	}

	/***
	 * obligatorio por causa de IShowActionAction
	 */
	public String getActionToShow() {
		return mostrar ? "PDVSimple.crearFactura" : "";	//la accion a mostrar (3)
	}

	/***
	 * obligatorio por causa de IHideActionAction
	 */
	public String getActionToHide() {
		return !mostrar ? "PDVSimple.crearFactura" : "";//la accion a ocultar (3)
	}

}
