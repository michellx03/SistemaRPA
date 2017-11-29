package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the procedimentos_ipasgo database table.
 * 
 */
@javax.persistence.Entity
@Table(name="procedimentos_ipasgo", schema = "sistema")
@NamedQuery(name="ProcedimentosIpasgo.findAll", query="SELECT p FROM ProcedimentosIpasgo p")
public class ProcedimentosIpasgo implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="prip_id")
	@GeneratedValue
	private Integer pripId;

	@Column(name="prip_auxiliar")
	private Integer pripAuxiliar;

	@Column(name="prip_codigo")
	private Integer pripCodigo;

	@Column(name="prip_descricao")
	private String pripDescricao;

	@Column(name="prip_porte")
	private Integer pripPorte;

	@Column(name="prip_valor")
	private double pripValor;

	public ProcedimentosIpasgo() {
	}

	public Integer getPripId() {
		return this.pripId;
	}

	public void setPripId(Integer pripId) {
		this.pripId = pripId;
	}

	public Integer getPripAuxiliar() {
		return this.pripAuxiliar;
	}

	public void setPripAuxiliar(Integer pripAuxiliar) {
		this.pripAuxiliar = pripAuxiliar;
	}

	public Integer getPripCodigo() {
		return this.pripCodigo;
	}

	public void setPripCodigo(Integer pripCodigo) {
		this.pripCodigo = pripCodigo;
	}

	public String getPripDescricao() {
		return this.pripDescricao;
	}

	public void setPripDescricao(String pripDescricao) {
		this.pripDescricao = pripDescricao;
	}

	public Integer getPripPorte() {
		return this.pripPorte;
	}

	public void setPripPorte(Integer pripPorte) {
		this.pripPorte = pripPorte;
	}

	public double getPripValor() {
		return this.pripValor;
	}

	public void setPripValor(double pripValor) {
		this.pripValor = pripValor;
	}

}