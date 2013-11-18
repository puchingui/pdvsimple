package pdvsimple.actions;

import org.openxava.actions.*;
import org.openxava.util.*;
import org.openxava.view.*;

/***
 * 13.7 - 273
 * @author root
 *
 */
public class NuevaOrdenParaUsuarioActualAction extends NewAction {

	public void execute() throws Exception {
		super.execute();
		String usuario = Users.getCurrent();
		if (usuario == null) {
			getView().setEditable(false);
			addError("no_user_logged");
		}
		int codigoCliente = Integer.parseInt(usuario);
		View viewCliente = getView().getSubview("cliente");
		viewCliente.setValue("codigo", codigoCliente);
		viewCliente.findObject();
		viewCliente.setKeyEditable(false); 	//el usuario no puede cambiar el cliente
	}
}
