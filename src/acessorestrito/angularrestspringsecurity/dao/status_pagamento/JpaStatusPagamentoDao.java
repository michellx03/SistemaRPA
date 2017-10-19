package acessorestrito.angularrestspringsecurity.dao.status_pagamento;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import acessorestrito.angularrestspringsecurity.dao.JpaDao;

import acessorestrito.angularrestspringsecurity.entity.StatusPagamento;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaStatusPagamentoDao extends JpaDao<StatusPagamento, Integer> implements StatusPagamentoDao {
	public JpaStatusPagamentoDao() {
		super(StatusPagamento.class);
	}

}