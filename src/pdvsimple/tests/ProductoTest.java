package pdvsimple.tests;

import static org.openxava.jpa.XPersistence.commit;
import static org.openxava.jpa.XPersistence.getManager;

import java.math.*;

import org.openxava.tests.*;

import pdvsimple.model.*;

public class ProductoTest extends ModuleTestBase {

	private Autor autor; // Declaramos la entidades a crear
	private Categoria categoria; // como miembros de instancia para
	private Producto producto1; // que estén disponibles en todos los métodos de
								// prueba
	private Producto producto2; // y puedan ser borradas al final de cada prueba

	public ProductoTest(String nameTest) {
		super(nameTest, "PDVSimple", "Producto");
		// TODO Auto-generated constructor stub
	}

	// setUp() se ejecuta siempre antes de cada prueba
	@Override
	protected void setUp() throws Exception {
		crearProductos(); // Crea los datos usados en las pruebas
		super.setUp();
	}

	// tearDown() se ejecuta siempre después de cada prueba
	@Override
	protected void tearDown() throws Exception {
		super.tearDown(); // Necesario, ModuleTestBase cierra recursos aquí
		eliminarProductos(); // Se borran los datos usado para pruebas
	}

	public void testRemoveFromList() throws Exception {

		setConditionValues( // Establece los valores para filtrar los datos
		new String[] { "", "JUNIT" });

		setConditionComparators( // Pone los comparadores para filtrar los datos
		new String[] { "=", "contains_comparator" });

		execute("List.filter"); // Pulsa el botón para filtrar
		assertListRowCount(2); // Verifica que hay 2 filas
		checkRow(1); // Seleccionamos la fila 1 (que resulta ser la segunda)
		execute("CRUD.deleteSelected"); // Pulsa en el botón para borrar
		assertListRowCount(1); // Verifica que ahora solo hay una fila
	}

	public void testCambiarPrecio() throws Exception {
		// Buscar product1
		execute("CRUD.new");
		setValue("codigo", Integer.toString(producto1.getCodigo())); // (1)

		execute("CRUD.refresh");
		assertValue("precio", "10.00");

		// Cambiar el precio
		setValue("precio", "12.00");
		execute("CRUD.save");
		assertNoErrors();
		assertValue("precio", "");

		// Verificar
		setValue("codigo", Integer.toString(producto1.getCodigo())); // (1)
		execute("CRUD.refresh");
		assertValue("precio", "12.00");
	}

	private void crearProductos() {
		// Crear objetos Java
		autor = new Autor(); // Se crean objetos de Java convencionales
		autor.setNombre("JUNIT Autor"); // Usamos setters como se suele hacer
										// con Java
		categoria = new Categoria();
		categoria.setDescripcion("JUNIT Categoria");

		producto1 = new Producto();
		producto1.setCodigo(900000001);
		producto1.setDescripcion("JUNIT Producto 1");
		producto1.setAutor(autor);
		producto1.setCategoria(categoria);
		producto1.setPrecio(new BigDecimal("10"));

		producto2 = new Producto();
		producto2.setCodigo(900000002);
		producto2.setDescripcion("JUNIT Producto 2");
		producto2.setAutor(autor);
		producto2.setCategoria(categoria);
		producto2.setPrecio(new BigDecimal("20"));

		// Marcar los objetos como persistentes
		getManager().persist(autor); // getManager() es de XPersistence
		// commit(); // El contexto persistente actual se termina, y author pasa
		// a
					// estar desasociado
		// getManager().remove(autor); // Falla porque author está desasociado
		// autor = getManager().merge(autor); // Reasocia author al contexto
												// actual
		// getManager().remove(autor); // Funciona


		getManager().persist(categoria); // persist() marca el objeto como
										// persistente
		getManager().persist(producto1); // para que se grabe en la base de
											// datos
		getManager().persist(producto2);

		// Confirma los cambios en la base de datos
		commit(); // commit() es de XPersistence. Graba todos los objetos en la
					// base de datos
		// y confirma la transacción

	}

	private void eliminarProductos() {// Llamado desde tearDown()
		// por tanto ejecutado después de cada prueba
		remove(producto1, producto2, autor, categoria); // remove() borra
		commit(); // Confirma los datos en la base de datos, en este caso
					// borrando datos
	}

	private void remove(Object... entities) { // Usamos argumentos varargs
		for (Object entity : entities) { // Iteramos por todos los argumentos
			getManager().remove(getManager().merge(entity)); // Borrar(1)
		}
	}
}