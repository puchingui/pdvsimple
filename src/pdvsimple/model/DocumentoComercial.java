package pdvsimple.model;

import java.math.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.calculators.*;
import org.openxava.jpa.*;

import pdvsimple.calculators.*;

/*
 * pretendo crear instancias de Factura y Orden, pero no quiero crear instancias
 * de DocumentoComercial directamente, por eso la declaro abstracta
 */
@Entity
@View(members = "ano, codigo, fecha; data {cliente; detalles; importes [ porcentajeItbis, subTotal, itbis, total ]; notas}")
abstract public class DocumentoComercial extends Eliminable {

	@Column(length = 4)
	@DefaultValueCalculator(CurrentYearCalculator.class)
	// an~o actual
	private int ano;

	@Column(length = 6)
	@ReadOnly
	private int codigo;

	@Required
	@DefaultValueCalculator(CurrentDateCalculator.class)
	// fecha actual
	private Date fecha;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	// customer es obligatorio
	@ReferenceView("Simple")
	private Cliente cliente;

	@OneToMany(mappedBy = "padre", cascade = CascadeType.ALL)
	// factura indica donde se almacena la relacion en la tabla detalle
	@ListProperties("producto.codigo, producto.descripcion, cantidad, precioPorUnidad, importe")
	private Collection<Detalle> detalles = new ArrayList<Detalle>();

	@Stereotype("MEMO")
	private String notas;
	
	// Porcentaje del ITBIS que se usara para calcular el valor del ITBIS
	// @Digits(integerDigits=2, fractionalDigits=0) //para indicar su taman~o
	// @Required
	@DefaultValueCalculator(PorcentajeItbisCalculator.class)
	private BigDecimal porcentajeItbis;
	
	// Para calculos masivos de facturas 8.22 - 130
	@Stereotype("MONEY")
	private BigDecimal importe;
	
	//8.29 - 133 Formula en la base de datos
	@org.hibernate.annotations.Formula("IMPORTE * 0.10")	//el calculo usando SQL
	@Stereotype("MONEY")
	private BigDecimal beneficioEstimado;
	
	@Transient		//no se almacena en la tabla de la base de datos
	private boolean eliminando = false;	//indica si JPA esta borrando el documento ahora

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Collection<Detalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(Collection<Detalle> detalles) {
		this.detalles = detalles;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public BigDecimal getPorcentajeItbis() {
		return porcentajeItbis == null ? BigDecimal.ZERO : porcentajeItbis;
	}

	public void setPorcentajeItbis(BigDecimal porcentajeItbis) {
		this.porcentajeItbis = porcentajeItbis;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	//8.29 - 133 solo el getter es necesario
	public BigDecimal getBeneficioEstimado() {
		return beneficioEstimado;
	}

	// SubTotal Propiedad calculada dependiente de una coleccion 8.11
	@Stereotype("MONEY")
	public BigDecimal getSubTotal() {
		BigDecimal resultado = new BigDecimal("0.00");
		// iteramos por todas las lineas de detalle para acumular el importe
		for (Detalle detalle : getDetalles()) {
			resultado = resultado.add(detalle.getImporte());
		}
		return resultado;
	}
	
	// ITBIS propiedad calculada simple 8.13
	@Stereotype("MONEY")
	// cuando porcentajeItbis cambia se recalcula
	@Depends("porcentajeItbis")
	public BigDecimal getItbis() {
		// SubTotal * porcentajeItbis / 100
		return getSubTotal().multiply(getPorcentajeItbis()).divide(
				new BigDecimal("100"));
	}

	// Total importe - propiedad calculada 8.14
	@Stereotype("MONEY")
	@Depends("subTotal, itbis")
	public BigDecimal getTotal() {
		return getSubTotal().add(getItbis()); // subtotal + itbis
	}
	
	/* 8.20 pag 129 calcula un valor por defecto para multiples usuarios
	 * metodo @PrePersist calculateCodigo() de DocumentoComercial
	 */
	@PrePersist		//ejecutado justo ante de grabar el objeto por primera vez
	public void calculateCodigo() throws Exception {
		// de esta forma es valido para factura y orden
		Query query = XPersistence.getManager().createQuery(
				"select max(i.codigo) from " +
						getClass().getName().substring(16) +
						" i where i.ano = :ano");
		query.setParameter("ano", ano);
		Integer ultimoCodigo = (Integer) query.getSingleResult();
		this.codigo = ultimoCodigo == null ? 1 : ultimoCodigo + 1;
	}
	
	public void recalculaImporte() {
		setImporte(getTotal());
	}
	
	// 8.25 - 131
	boolean isElinando() {	//acceso paquete, no es accesible desde fuera
		return eliminando;
	}
	
	@PreRemove	//cuando el documento va a ser borrado marcamos eliminando como true
	private void marcaEliminando() {
		this.eliminando = true;
	}
	
	@PostRemove	//cuando el documento ha sido borrado marcamos eliminando como false
	private void desmarcaEliminando() {
		this.eliminando = false;
	}

}
