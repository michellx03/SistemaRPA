package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


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

	@Temporal(TemporalType.DATE)
	@Column(name="cofa_data_atendimento")
	private Date cofaDataAtendimento;

	@Column(name="cofa_descricao_atendimento")
	private String cofaDescricaoAtendimento;

	@Column(name="cofa_matriculla")
	private Integer cofaMatriculla;

	@Column(name="cofa_medico")
	private Integer cofaMedico;

	@Column(name="cofa_numero_guia")
	private Integer cofaNumeroGuia;

	@Column(name="cofa_paciente")
	private String cofaPaciente;

	@Column(name="cofa_refe_id")
	private Integer cofaRefeId;

	@Column(name="cofa_status_pagamento")
	private Integer cofaStatusPagamento;

	@Column(name="cofa_tipo_atendimento")
	private Integer cofaTipoAtendimento;

	@Column(name="cofa_valor")
	private double cofaValor;

	public ContaFaturamento() {
	}

	public Integer getCofaId() {
		return this.cofaId;
	}

	public void setCofaId(Integer cofaId) {
		this.cofaId = cofaId;
	}

	public Date getCofaDataAtendimento() {
		return this.cofaDataAtendimento;
	}

	public void setCofaDataAtendimento(Date cofaDataAtendimento) {
		this.cofaDataAtendimento = cofaDataAtendimento;
	}

	public String getCofaDescricaoAtendimento() {
		return this.cofaDescricaoAtendimento;
	}

	public void setCofaDescricaoAtendimento(String cofaDescricaoAtendimento) {
		this.cofaDescricaoAtendimento = cofaDescricaoAtendimento;
	}

	public Integer getCofaMatriculla() {
		return this.cofaMatriculla;
	}

	public void setCofaMatriculla(Integer cofaMatriculla) {
		this.cofaMatriculla = cofaMatriculla;
	}

	public Integer getCofaMedico() {
		return this.cofaMedico;
	}

	public void setCofaMedico(Integer cofaMedico) {
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

	public Integer getCofaStatusPagamento() {
		return this.cofaStatusPagamento;
	}

	public void setCofaStatusPagamento(Integer cofaStatusPagamento) {
		this.cofaStatusPagamento = cofaStatusPagamento;
	}

	public Integer getCofaTipoAtendimento() {
		return this.cofaTipoAtendimento;
	}

	public void setCofaTipoAtendimento(Integer cofaTipoAtendimento) {
		this.cofaTipoAtendimento = cofaTipoAtendimento;
	}

	public double getCofaValor() {
		return this.cofaValor;
	}

	public void setCofaValor(double cofaValor) {
		this.cofaValor = cofaValor;
	}

}