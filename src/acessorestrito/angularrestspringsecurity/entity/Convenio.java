package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the convenio database table.
 * 
 */
@javax.persistence.Entity
@Table(name="convenio", schema = "sistema")
@NamedQuery(name="Convenio.findAll", query="SELECT c FROM Convenio c")
public class Convenio implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="conv_id")
	@GeneratedValue
	private Integer convId;

	@Column(name="conv_convenio")
	private String convConvenio;

	public Convenio() {
	}

	public Integer getConvId() {
		return this.convId;
	}

	public void setConvId(Integer convId) {
		this.convId = convId;
	}

	public String getConvConvenio() {
		return this.convConvenio;
	}

	public void setConvConvenio(String convConvenio) {
		this.convConvenio = convConvenio;
	}

}