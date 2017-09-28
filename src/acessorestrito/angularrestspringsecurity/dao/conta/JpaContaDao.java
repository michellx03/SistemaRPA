package acessorestrito.angularrestspringsecurity.dao.conta;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import acessorestrito.angularrestspringsecurity.dao.JpaDao;

import acessorestrito.angularrestspringsecurity.entity.Conta;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaContaDao extends JpaDao<Conta, Integer> implements ContaDao {
	public JpaContaDao() {
		super(Conta.class);
	}

}
