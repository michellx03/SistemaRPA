package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tipo_atendimento database table.
 * 
 */
@javax.persistence.Entity
@Table(name="tipo_atendimento", schema = "sistema")
@NamedQuery(name="TipoAtendimento.findAll", query="SELECT t FROM TipoAtendimento t")
public class TipoAtendimento implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tiat_id")
	@GeneratedValue
	private Integer tiatId;

	@Column(name="tiat_tipo")
	private String tiatTipo;

	public TipoAtendimento() {
	}

	public Integer getTiatId() {
		return this.tiatId;
	}

	public void setTiatId(Integer tiatId) {
		this.tiatId = tiatId;
	}

	public String getTiatTipo() {
		return this.tiatTipo;
	}

	public void setTiatTipo(String tiatTipo) {
		this.tiatTipo = tiatTipo;
	}

}