package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the status database table.
 * 
 */
@javax.persistence.Entity
@NamedQuery(name="Status.findAll", query="SELECT s FROM Status s")
@Table (name = "status", schema = "sistema")
public class Status implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="stat_id")
	@GeneratedValue
	private Integer statId;

	@Column(name="stat_status")
	private String statStatus;

	public Status() {
	}

	public Integer getStatId() {
		return this.statId;
	}

	public void setStatId(Integer statId) {
		this.statId = statId;
	}

	public String getStatStatus() {
		return this.statStatus;
	}

	public void setStatStatus(String statStatus) {
		this.statStatus = statStatus;
	}

}