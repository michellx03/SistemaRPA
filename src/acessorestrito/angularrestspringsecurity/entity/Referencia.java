package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the referencia database table.
 * 
 */
@javax.persistence.Entity
@NamedQuery(name="Referencia.findAll", query="SELECT r FROM Referencia r")
@Table (name = "referencia", schema = "sistema")
public class Referencia implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="refe_id")
	@GeneratedValue
	private Integer refeId;

	@Column(name="refe_ano")
	private Integer refeAno;

	@Column(name="refe_referencia")
	private String refeReferencia;

	@Column(name="refe_status")
	private Integer refeStatus;

	public Referencia() {
	}

	public Integer getRefeId() {
		return this.refeId;
	}

	public void setRefeId(Integer refeId) {
		this.refeId = refeId;
	}

	public Integer getRefeAno() {
		return this.refeAno;
	}

	public void setRefeAno(Integer refeAno) {
		this.refeAno = refeAno;
	}

	public String getRefeReferencia() {
		return this.refeReferencia;
	}

	public void setRefeReferencia(String refeReferencia) {
		this.refeReferencia = refeReferencia;
	}

	public Integer getRefeStatus() {
		return this.refeStatus;
	}

	public void setRefeStatus(Integer refeStatus) {
		this.refeStatus = refeStatus;
	}

}