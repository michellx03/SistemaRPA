package acessorestrito.angularrestspringsecurity.dao.auditoria;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import acessorestrito.angularrestspringsecurity.dao.JpaDao;

import acessorestrito.angularrestspringsecurity.entity.Auditoria;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaAuditoriaDao extends JpaDao<Auditoria, Long> implements AuditoriaDao {
	public JpaAuditoriaDao() {
		super(Auditoria.class);
	}

}
