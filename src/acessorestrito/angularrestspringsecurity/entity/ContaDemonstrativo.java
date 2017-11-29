package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


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

	@Column(name="code_carteira")
	private Integer codeCarteira;

	@Column(name="code_codigo")
	private Integer codeCodigo;

	@Column(name="code_convenio")
	private String codeConvenio;

	@Temporal(TemporalType.DATE)
	@Column(name="code_data_atendimento")
	private Date codeDataAtendimento;

	@Column(name="code_numero_guia")
	private double codeNumeroGuia;

	@Column(name="code_paciente")
	private String codePaciente;

	@Column(name="code_prip_codigo")
	private Integer codePripCodigo;

	@Column(name="code_refe_id")
	private Integer codeRefeId;

	@Column(name="code_valor")
	private double codeValor;

	public ContaDemonstrativo() {
	}

	public Integer getCodeId() {
		return this.codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public Integer getCodeCarteira() {
		return this.codeCarteira;
	}

	public void setCodeCarteira(Integer codeCarteira) {
		this.codeCarteira = codeCarteira;
	}

	public Integer getCodeCodigo() {
		return this.codeCodigo;
	}

	public void setCodeCodigo(Integer codeCodigo) {
		this.codeCodigo = codeCodigo;
	}

	public String getCodeConvenio() {
		return this.codeConvenio;
	}

	public void setCodeConvenio(String codeConvenio) {
		this.codeConvenio = codeConvenio;
	}

	public Date getCodeDataAtendimento() {
		return this.codeDataAtendimento;
	}

	public void setCodeDataAtendimento(Date codeDataAtendimento) {
		this.codeDataAtendimento = codeDataAtendimento;
	}

	public double getCodeNumeroGuia() {
		return this.codeNumeroGuia;
	}

	public void setCodeNumeroGuia(double codeNumeroGuia) {
		this.codeNumeroGuia = codeNumeroGuia;
	}

	public String getCodePaciente() {
		return this.codePaciente;
	}

	public void setCodePaciente(String codePaciente) {
		this.codePaciente = codePaciente;
	}

	public Integer getCodePripCodigo() {
		return this.codePripCodigo;
	}

	public void setCodePripCodigo(Integer codePripCodigo) {
		this.codePripCodigo = codePripCodigo;
	}

	public Integer getCodeRefeId() {
		return this.codeRefeId;
	}

	public void setCodeRefeId(Integer codeRefeId) {
		this.codeRefeId = codeRefeId;
	}

	public double getCodeValor() {
		return this.codeValor;
	}

	public void setCodeValor(double codeValor) {
		this.codeValor = codeValor;
	}

}