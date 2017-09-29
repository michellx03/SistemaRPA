package acessorestrito.angularrestspringsecurity.dao.referencia;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import acessorestrito.angularrestspringsecurity.dao.JpaDao;

import acessorestrito.angularrestspringsecurity.entity.Referencia;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaReferenciaDao extends JpaDao<Referencia, Integer> implements ReferenciaDao {
	public JpaReferenciaDao() {
		super(Referencia.class);
	}

}
