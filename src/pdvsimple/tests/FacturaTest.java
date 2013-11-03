package pdvsimple.tests;

import static org.openxava.jpa.XPersistence.getManager; // Para usar JPA

import java.text.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.tests.*;
import org.openxava.util.*;


public class FacturaTest extends ModuleTestBase {

	private String codigo; // Para almacenar el número de la factura que
							// probamos

	public FacturaTest(String testName) {
		super(testName, "PDVSimple", "Factura");
	}

	public void testCreateFactura() throws Exception { // El método de prueba
		verifyDefaultValues();
		elegirCliente();
		agregarDetalles();
		setOtrasPropiedades();
		salvar();
		verifyCreated();
		remove();
	}

	private void verifyDefaultValues() throws Exception {
		execute("CRUD.new");
		assertValue("ano", getCurrentYear());
		assertValue("codigo", getCodigo());
		assertValue("fecha", "30/10/2013"); // getCurrentDate());
	}

	private void elegirCliente() throws Exception {
		setValue("cliente.codigo", "1");
		assertValue("cliente.nombre", "Listin Diario");
	}

	private void agregarDetalles() throws Exception {
		// Añadir una línea de detalle
		assertCollectionRowCount("detalles", 0); // La colección esta vacía

		// Pulsa en el botón para añadir un nuevo elemento
		// viewObject es necesario para determinar
		// a que colección nos referimos
		execute("Collection.new", "viewObject=xava_view_details");
		setValue("producto.codigo", "1");
		assertValue("producto.descripcion", "Aprenda OpenXava con Ejemplos");
		setValue("cantidad", "2");

		// Graba el elemento de la colección sin cerrar el diálogo
		execute("Collection.saveAndStay");

		// No hay errores al grabar el detalle
		assertNoErrors();
		// Añadir otro detalle
		setValue("producto.codigo", "2");
		assertValue("producto.descripcion", "Play Framework Cookbook");
		setValue("cantidad", "1");

		// Graba el elemento de la colección cerrando el diálogo
		execute("Collection.save");
		assertNoErrors();
		assertCollectionRowCount("detalles", 2); // Ahora tenemos 2 filas
	}

	private void setOtrasPropiedades() throws Exception {
		setValue("notas", "Esta es una prueba JUNIT");
	}

	private void salvar() throws Exception {
		execute("CRUD.save");
		assertNoErrors();
		assertValue("cliente.codigo", "");
		assertCollectionRowCount("detalles", 0);
		assertValue("notas", "");
	}

	private void verifyCreated() throws Exception {
		setValue("ano", getCurrentYear()); // El año actual en el campo año
		setValue("codigo", getCodigo()); // El número de la factura usada en la
											// prueba
		execute("CRUD.refresh"); // Carga la factura desde la DB
		// En el resto de la prueba confirmamos que los valores son los
		// correctos
		assertValue("ano", getCurrentYear());
		assertValue("codigo", getCodigo());
		assertValue("fecha", getCurrentDate());
		assertValue("cliente.codigo", "1");
		assertValue("cliente.nombre", "Listin Diario");
		assertCollectionRowCount("detalles", 2);
		// Fila 0
		assertValueInCollection("detalles", 0, "producto.codigo", "1");
		assertValueInCollection("detalles", 0, "producto.descripcion",
				"Aprenda OpenXava con Ejemplos");
		assertValueInCollection("detalles", 0, "cantidad", "2");
		// Fila 1
		assertValueInCollection("detalles", 1, "producto.codigo", "2");
		assertValueInCollection("detalles", 1, "producto.descripcion",
				"Play Framework Cookbook");
		assertValueInCollection("detalles", 1, "cantidad", "1");
		assertValue("notas", "Esta es una prueba JUNIT");
	}

	private void remove() throws Exception {
		execute("CRUD.delete");
		assertNoErrors();
	}

	// Año actual en formato cadena
	private String getCurrentYear() {
		// La forma típica de hacerlo con Java
		return new SimpleDateFormat("yyyy").format(new Date());
	}

	// Fecha actual como una cadena en formato corto
	private String getCurrentDate() {
		// La forma típica de hacerlo con Java
		return DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
	}

	// El número de factura para una factura nueva
	private String getCodigo() {
		// Usamos inicialización vaga
		if (codigo == null) {
			// Una consulta JPA para obtener el último número
			Query query = getManager().createQuery(
					"select max(f.codigo) from Factura f where f.ano = :ano");
			// Dates es una utilidad de OpenXava
			query.setParameter("ano", Dates.getYear(new Date()));
			Integer lastNumber = (Integer) query.getSingleResult();
			if (lastNumber == null)
				lastNumber = 0;
			// Añadimos 1 al último número de factura
			codigo = Integer.toString(lastNumber + 1);
		}
		return codigo;
	}

}