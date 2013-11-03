package pdvsimple.model;

import javax.persistence.*;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@MappedSuperclass
public class Identificable {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@Hidden
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String oid;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
}
