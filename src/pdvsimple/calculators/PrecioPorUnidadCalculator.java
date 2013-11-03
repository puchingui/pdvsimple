package pdvsimple.calculators;

import static org.openxava.jpa.XPersistence.getManager;		//para usar getManager

import org.openxava.calculators.*;

import pdvsimple.model.*;

@SuppressWarnings("serial")
public class PrecioPorUnidadCalculator implements ICalculator {

	private int codigoProducto;		//en calculate() contendra el codigo producto
	
	public Object calculate() throws Exception {
		Producto producto = getManager().find(Producto.class, codigoProducto);
		
		return producto.getPrecio(); // retorna su precio original
	}

	public int getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

}
