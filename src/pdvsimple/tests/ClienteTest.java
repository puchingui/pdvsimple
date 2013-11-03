package pdvsimple.tests;

import org.openxava.tests.*;

public class ClienteTest extends ModuleTestBase {

	public ClienteTest(String testName) {
		super(testName, "PDVSimple", "Cliente");
	}

	public void testCreateReadUpdateDelete() throws Exception {

		// Create
		execute("CRUD.new");
		setValue("codigo", "77");
		setValue("nombre", "JUNIT Cliente");
		setValue("direccion.calle", "JUNIT calle");
		setValue("direccion.numero", "77555");
		setValue("direccion.sector", "La calle de JUNIT");
		setValue("direccion.ciudad", "La ciudad de JUNIT");
		setValue("direccion.provincia", "La provincia de JUNIT");

		execute("CRUD.save");
		assertNoErrors();
		assertValue("codigo", "");
		setValue("direccion.calle", "");
		assertValue("direccion.numero", "");
		assertValue("direccion.sector", "");
		assertValue("direccion.ciudad", "");
		assertValue("direccion.provincia", "");

		// Read
		setValue("codigo", "77");
		execute("CRUD.refresh");
		assertValue("codigo", "77");
		assertValue("nombre", "JUNIT Cliente");
		assertValue("direccion.calle", "JUNIT calle");
		assertValue("direccion.numero", "77555");
		assertValue("direccion.sector", "La calle de JUNIT");
		assertValue("direccion.ciudad", "La ciudad de JUNIT");
		assertValue("direccion.provincia", "La provincia de JUNIT");

		// Update
		setValue("nombre", "JUNIT Cliente MODIFICADO");
		execute("CRUD.save");
		assertNoErrors();
		assertValue("codigo", "");
		assertValue("nombre", "");

		// Verify if modified
		setValue("codigo", "77");
		execute("CRUD.refresh");
		assertValue("codigo", "77");
		assertValue("nombre", "JUNIT Cliente MODIFICADO");

		// Delete
		execute("CRUD.delete");
		// assertMessage("Customer deleted susscefully");
		assertMessage("Cliente borrado satisfactoriamente");
	}
}