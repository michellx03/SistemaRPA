package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the descontos database table.
 * 
 */
@javax.persistence.Entity
@Table(name="descontos", schema = "sistema")
@NamedQuery(name="Desconto.findAll", query="SELECT d FROM Desconto d")
public class Desconto implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="desc_id")
	@GeneratedValue
	private Integer descId;

	@Column(name="desc_convenio")
	private String descConvenio;

	@Column(name="desc_valor")
	private Integer descValor;

	public Desconto() {
	}

	public Integer getDescId() {
		return this.descId;
	}

	public void setDescId(Integer descId) {
		this.descId = descId;
	}

	public String getDescConvenio() {
		return this.descConvenio;
	}

	public void setDescConvenio(String descConvenio) {
		this.descConvenio = descConvenio;
	}

	public Integer getDescValor() {
		return this.descValor;
	}

	public void setDescValor(Integer descValor) {
		this.descValor = descValor;
	}

}