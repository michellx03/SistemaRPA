package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the conta_demonstrativo database table.
 * 
 */
@javax.persistence.Entity
@Table(name="conta_demonstrativo", schema = "sistema")
@NamedQuery(name="ContaDemonstrativo.findAll", query="SELECT c FROM ContaDemonstrativo c")
public class ContaDemonstrativo implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="code_id")
	@GeneratedValue
	private Integer codeId;

	@Column(name="code_convenio")
	private String codeConvenio;

	@Column(name="code_data_atendimento")
	private Integer codeDataAtendimento;

	@Column(name="code_medico")
	private String codeMedico;

	@Column(name="code_numero_guia")
	private Integer codeNumeroGuia;

	@Column(name="code_paciente")
	private String codePaciente;

	@Column(name="code_refe_id")
	private Integer codeRefeId;

	@Column(name="code_tipo_atendimento")
	private String codeTipoAtendimento;

	public ContaDemonstrativo() {
	}

	public Integer getCodeId() {
		return this.codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public String getCodeConvenio() {
		return this.codeConvenio;
	}

	public void setCodeConvenio(String codeConvenio) {
		this.codeConvenio = codeConvenio;
	}

	public Integer getCodeDataAtendimento() {
		return this.codeDataAtendimento;
	}

	public void setCodeDataAtendimento(Integer codeDataAtendimento) {
		this.codeDataAtendimento = codeDataAtendimento;
	}

	public String getCodeMedico() {
		return this.codeMedico;
	}

	public void setCodeMedico(String codeMedico) {
		this.codeMedico = codeMedico;
	}

	public Integer getCodeNumeroGuia() {
		return this.codeNumeroGuia;
	}

	public void setCodeNumeroGuia(Integer codeNumeroGuia) {
		this.codeNumeroGuia = codeNumeroGuia;
	}

	public String getCodePaciente() {
		return this.codePaciente;
	}

	public void setCodePaciente(String codePaciente) {
		this.codePaciente = codePaciente;
	}

	public Integer getCodeRefeId() {
		return this.codeRefeId;
	}

	public void setCodeRefeId(Integer codeRefeId) {
		this.codeRefeId = codeRefeId;
	}

	public String getCodeTipoAtendimento() {
		return this.codeTipoAtendimento;
	}

	public void setCodeTipoAtendimento(String codeTipoAtendimento) {
		this.codeTipoAtendimento = codeTipoAtendimento;
	}

}