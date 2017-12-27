package acessorestrito.angularrestspringsecurity.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the medicos database table.
 * 
 */
@javax.persistence.Entity
@Table(name="medicos", schema = "sistema")
@NamedQuery(name="Medico.findAll", query="SELECT m FROM Medico m")
public class Medico implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="medi_id")
	@GeneratedValue
	private Integer mediId;

	@Column(name="medi_cpf")
	private String mediCpf;

	@Column(name="medi_crm")
	private Integer mediCrm;

	@Column(name="medi_especialidade")
	private String mediEspecialidade;

	@Column(name="medi_nome")
	private String mediNome;

	public Medico() {
	}

	public Integer getMediId() {
		return this.mediId;
	}

	public void setMediId(Integer mediId) {
		this.mediId = mediId;
	}

	public String getMediCpf() {
		return this.mediCpf;
	}

	public void setMediCpf(String mediCpf) {
		this.mediCpf = mediCpf;
	}

	public Integer getMediCrm() {
		return this.mediCrm;
	}

	public void setMediCrm(Integer mediCrm) {
		this.mediCrm = mediCrm;
	}

	public String getMediEspecialidade() {
		return this.mediEspecialidade;
	}

	public void setMediEspecialidade(String mediEspecialidade) {
		this.mediEspecialidade = mediEspecialidade;
	}

	public String getMediNome() {
		return this.mediNome;
	}

	public void setMediNome(String mediNome) {
		this.mediNome = mediNome;
	}

}