package pdvsimple.calculators;

import javax.persistence.*;

import org.openxava.calculators.*;
import org.openxava.jpa.*;

@SuppressWarnings("serial")
public class ProximoCodigoParaAnoCalculator implements ICalculator {

	private int ano;		//este valor sera injectado (usando su setter) antes de ser calculado
	
	public Object calculate() throws Exception {

		Query query = XPersistence.getManager()		//un JPA query
				.createQuery(
						"select max(dc.codigo) from DocumentoComercial dc where dc.ano=:ano"); // el
																				// query
																				// retornara
																				// el
																				// maximo
																				// numero
																				// de
																				// factura
																				// indicado
																				// segun
																				// el
																				// an~o
		query.setParameter("ano", ano);	//aqui ponemos el an~o actual como parametro del query
		
		Integer lastNumber = (Integer) query.getSingleResult();
		return lastNumber == null ? 1 : lastNumber + 1;		//retorna el ultimo numero de invoice con relacion al an~o + 1 o retornara 1 si no existe ninguna factura
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
	
}
