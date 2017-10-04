package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the conta_faturamento database table.
 * 
 */
@javax.persistence.Entity
@Table(name="conta_faturamento", schema = "sistema")
@NamedQuery(name="ContaFaturamento.findAll", query="SELECT c FROM ContaFaturamento c")
public class ContaFaturamento implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cofa_id")
	@GeneratedValue
	private Integer cofaId;

	@Column(name="cofa_convenio")
	private String cofaConvenio;

	@Column(name="cofa_data_atendimento")
	private Integer cofaDataAtendimento;

	@Column(name="cofa_medico")
	private String cofaMedico;

	@Column(name="cofa_numero_guia")
	private Integer cofaNumeroGuia;

	@Column(name="cofa_paciente")
	private String cofaPaciente;

	@Column(name="cofa_refe_id")
	private Integer cofaRefeId;

	@Column(name="cofa_tipo_atendimento")
	private String cofaTipoAtendimento;

	public ContaFaturamento() {
	}

	public Integer getCofaId() {
		return this.cofaId;
	}

	public void setCofaId(Integer cofaId) {
		this.cofaId = cofaId;
	}

	public String getCofaConvenio() {
		return this.cofaConvenio;
	}

	public void setCofaConvenio(String cofaConvenio) {
		this.cofaConvenio = cofaConvenio;
	}

	public Integer getCofaDataAtendimento() {
		return this.cofaDataAtendimento;
	}

	public void setCofaDataAtendimento(Integer cofaDataAtendimento) {
		this.cofaDataAtendimento = cofaDataAtendimento;
	}

	public String getCofaMedico() {
		return this.cofaMedico;
	}

	public void setCofaMedico(String cofaMedico) {
		this.cofaMedico = cofaMedico;
	}

	public Integer getCofaNumeroGuia() {
		return this.cofaNumeroGuia;
	}

	public void setCofaNumeroGuia(Integer cofaNumeroGuia) {
		this.cofaNumeroGuia = cofaNumeroGuia;
	}

	public String getCofaPaciente() {
		return this.cofaPaciente;
	}

	public void setCofaPaciente(String cofaPaciente) {
		this.cofaPaciente = cofaPaciente;
	}

	public Integer getCofaRefeId() {
		return this.cofaRefeId;
	}

	public void setCofaRefeId(Integer cofaRefeId) {
		this.cofaRefeId = cofaRefeId;
	}

	public String getCofaTipoAtendimento() {
		return this.cofaTipoAtendimento;
	}

	public void setCofaTipoAtendimento(String cofaTipoAtendimento) {
		this.cofaTipoAtendimento = cofaTipoAtendimento;
	}

}