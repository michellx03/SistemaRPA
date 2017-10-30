package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the conta database table.
 * 
 */
@javax.persistence.Entity
@NamedQuery(name="Conta.findAll", query="SELECT c FROM Conta c")
@Table (name = "conta", schema = "sistema")
public class Conta implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="conta_id")
	@GeneratedValue
	private Integer contaId;

	@Column(name="conta_convenio")
	private String contaConvenio;

	@Temporal(TemporalType.DATE)
	@Column(name="conta_data_atendimento")
	private Date contaDataAtendimento;

	@Column(name="conta_medico")
	private String contaMedico;

	@Column(name="conta_numero_guia")
	private double contaNumeroGuia;

	@Column(name="conta_paciente")
	private String contaPaciente;

	@Column(name="conta_refe_id")
	private Integer contaRefeId;

	@Column(name="conta_stre_id")
	private Integer contaStreId;

	@Column(name="conta_tipo_atendimento")
	private String contaTipoAtendimento;

	public Conta() {
	}

	public Integer getContaId() {
		return this.contaId;
	}

	public void setContaId(Integer contaId) {
		this.contaId = contaId;
	}

	public String getContaConvenio() {
		return this.contaConvenio;
	}

	public void setContaConvenio(String contaConvenio) {
		this.contaConvenio = contaConvenio;
	}

	public Date getContaDataAtendimento() {
		return this.contaDataAtendimento;
	}

	public void setContaDataAtendimento(Date contaDataAtendimento) {
		this.contaDataAtendimento = contaDataAtendimento;
	}

	public String getContaMedico() {
		return this.contaMedico;
	}

	public void setContaMedico(String contaMedico) {
		this.contaMedico = contaMedico;
	}

	public double getContaNumeroGuia() {
		return this.contaNumeroGuia;
	}

	public void setContaNumeroGuia(double contaNumeroGuia) {
		this.contaNumeroGuia = contaNumeroGuia;
	}

	public String getContaPaciente() {
		return this.contaPaciente;
	}

	public void setContaPaciente(String contaPaciente) {
		this.contaPaciente = contaPaciente;
	}

	public Integer getContaRefeId() {
		return this.contaRefeId;
	}

	public void setContaRefeId(Integer contaRefeId) {
		this.contaRefeId = contaRefeId;
	}

	public Integer getContaStreId() {
		return this.contaStreId;
	}

	public void setContaStreId(Integer contaStreId) {
		this.contaStreId = contaStreId;
	}

	public String getContaTipoAtendimento() {
		return this.contaTipoAtendimento;
	}

	public void setContaTipoAtendimento(String contaTipoAtendimento) {
		this.contaTipoAtendimento = contaTipoAtendimento;
	}

}