package pdvsimple.actions;

import org.openxava.actions.*;

//12.5 - 230 Logica estandar para buscar una referencia
public class BuscarFacturaDesdeOrdenAction extends ReferenceSearchAction 
{
	public void execute() throws Exception {
		super.execute(); 	//ejecuta la logica estandar, la cual muestra un dialogo
		int codigoCliente =	
				getPreviousView()		//getPreviousView es la vista principal (getView es el dialogo)
					.getValueInt("cliente.codigo");		//lee de la vista el 
														//codigo del cliente del pedido actual
		if (codigoCliente > 0) {		//si hay cliente lo usamos para filtrar
			getTab().setBaseCondition("${cliente.codigo} = " + codigoCliente);
		}
	}
}
