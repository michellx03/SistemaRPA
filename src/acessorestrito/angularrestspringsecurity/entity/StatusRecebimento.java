package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the status_recebimento database table.
 * 
 */
@javax.persistence.Entity
@Table(name="status_recebimento", schema = "sistema")
@NamedQuery(name="StatusRecebimento.findAll", query="SELECT s FROM StatusRecebimento s")
public class StatusRecebimento implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="stre_id")
	@GeneratedValue
	private Integer streId;

	@Column(name="stre_status")
	private String streStatus;

	public StatusRecebimento() {
	}

	public Integer getStreId() {
		return this.streId;
	}

	public void setStreId(Integer streId) {
		this.streId = streId;
	}

	public String getStreStatus() {
		return this.streStatus;
	}

	public void setStreStatus(String streStatus) {
		this.streStatus = streStatus;
	}

}