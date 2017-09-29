package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ano database table.
 * 
 */
@javax.persistence.Entity
@NamedQuery(name="Ano.findAll", query="SELECT a FROM Ano a")
@Table (name = "ano", schema = "sistema")
public class Ano implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ano_id")
	@GeneratedValue
	private Integer anoId;

	@Column(name="ano_ano")
	private String anoAno;

	public Ano() {
	}

	public Integer getAnoId() {
		return this.anoId;
	}

	public void setAnoId(Integer anoId) {
		this.anoId = anoId;
	}

	public String getAnoAno() {
		return this.anoAno;
	}

	public void setAnoAno(String anoAno) {
		this.anoAno = anoAno;
	}

}