package pdvsimple.actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;
import org.openxava.view.*;

import pdvsimple.model.*;

/**
 * 12.8 - 233 Accion para buscar la factura al teclear an~o y codigo
 * OnChangeSearchAction logica estandar para buscar una referencia (1) 
 * @author Javier Paniza
 *
 */
public class OnChangeSearchFacturaAction extends OnChangeSearchAction {

	public void execute() throws Exception {
		super.execute();	//ejecuta la logica estandar (2)
		Map keyValues = getView()	//getView aqui es la de la referencia, no la principal (3)
				.getKeyValuesWithValue();
		if (keyValues.isEmpty()) return;	//si la clave esta vacia no se ejecuta mas logica
		Factura factura = (Factura)			//buscamos la factura usando la clave tecleada (4)
				MapFacade.findEntity(getView().getModelName(), keyValues);
		View viewCliente = getView().getRoot().getSubview("cliente");	//(5)
		int codigoCliente = viewCliente.getValueInt("codigo");
		if (codigoCliente == 0) {		//si no hay cliente lo llenamos (6)
			viewCliente.setValue("codigo", factura.getCliente().getCodigo());
			viewCliente.refresh();
		}
		else { 		//si ya hay un cliente verificamos que coincida con el cliente de la factura (7)
			if (codigoCliente != factura.getCliente().getCodigo()) {
				addError("cliente_factura_no_corresponde",
						factura.getCliente().getCodigo(), factura, codigoCliente);
				getView().clear();
			}
		}
	}
}
