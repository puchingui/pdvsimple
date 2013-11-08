package pdvsimple.model;

import javax.persistence.*;

import org.openxava.annotations.*;

@MappedSuperclass
public class Eliminable extends Identificable {

	@Hidden
	private boolean eliminado;

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

}
