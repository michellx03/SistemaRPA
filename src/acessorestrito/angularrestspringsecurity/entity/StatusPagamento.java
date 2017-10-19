package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the status_pagamento database table.
 * 
 */
@javax.persistence.Entity
@Table(name="status_pagamento", schema = "sistema")
@NamedQuery(name="StatusPagamento.findAll", query="SELECT s FROM StatusPagamento s")
public class StatusPagamento implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="stpa_id")
	@GeneratedValue
	private Integer stpaId;

	@Column(name="stpa_status")
	private String stpaStatus;

	public StatusPagamento() {
	}

	public Integer getStpaId() {
		return this.stpaId;
	}

	public void setStpaId(Integer stpaId) {
		this.stpaId = stpaId;
	}

	public String getStpaStatus() {
		return this.stpaStatus;
	}

	public void setStpaStatus(String stpaStatus) {
		this.stpaStatus = stpaStatus;
	}

}