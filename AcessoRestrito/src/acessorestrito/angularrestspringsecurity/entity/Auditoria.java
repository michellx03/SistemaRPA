package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the auditoria database table.
 * 
 */
@javax.persistence.Entity
@NamedQuery(name = "Auditoria.findAll", query = "SELECT a FROM Auditoria a")
public class Auditoria implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AUDITORIA_AUDIID_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDITORIA_AUDIID_GENERATOR")
	@Column(name = "audi_id")
	private Integer audiId;

	@Column(name = "audi_data_hora_inicio")
	private Timestamp audiDataHoraInicio;

	@Column(name = "audi_url")
	private String audiUrl;

	@Column(name = "audi_usua_id")
	private Long audiUsuaId;

	public Auditoria() {
	}

	public Integer getAudiId() {
		return this.audiId;
	}

	public void setAudiId(Integer audiId) {
		this.audiId = audiId;
	}

	public Timestamp getAudiDataHoraInicio() {
		return this.audiDataHoraInicio;
	}

	public void setAudiDataHoraInicio(Timestamp audiDataHoraInicio) {
		this.audiDataHoraInicio = audiDataHoraInicio;
	}

	public String getAudiUrl() {
		return this.audiUrl;
	}

	public void setAudiUrl(String audiUrl) {
		this.audiUrl = audiUrl;
	}

	public Long getAudiUsuaId() {
		return this.audiUsuaId;
	}

	public void setAudiUsuaId(Long audiUsuaId) {
		this.audiUsuaId = audiUsuaId;
	}

}