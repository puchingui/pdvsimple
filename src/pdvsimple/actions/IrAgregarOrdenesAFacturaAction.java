package pdvsimple.actions;

import org.openxava.actions.*;

/*
 * 12.13 - 237 Accion personalizada para ir a an~adir pedidos desde una factura
 */
public class IrAgregarOrdenesAFacturaAction extends GoAddElementsToCollectionAction {

	public void execute() throws Exception {
		super.execute();
		//getPreviousView es la vista principal (estamos en un dialogo)
		int codigoCliente = getPreviousView().getValueInt("cliente.codigo");
		getTab().setBaseCondition("${cliente.codigo} = " + codigoCliente + 
				"and ${entregado} = true and ${factura.oid} is null");
	}
	
	//12.14 - 238 Para copiar las lineas de detalles de las ordenes a la factura
	public String getNextController() {
		return "AgregarOrdenesAFactura";
	}
}
