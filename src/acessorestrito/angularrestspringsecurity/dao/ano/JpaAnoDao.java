package acessorestrito.angularrestspringsecurity.dao.ano;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import acessorestrito.angularrestspringsecurity.dao.JpaDao;

import acessorestrito.angularrestspringsecurity.entity.Ano;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaAnoDao extends JpaDao<Ano, Integer> implements AnoDao {
	public JpaAnoDao() {
		super(Ano.class);
	}

}
