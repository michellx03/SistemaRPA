package acessorestrito.angularrestspringsecurity.dao.conta_demonstrativo;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import acessorestrito.angularrestspringsecurity.dao.JpaDao;

import acessorestrito.angularrestspringsecurity.entity.ContaDemonstrativo;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaContaDemonstrativoDao extends JpaDao<ContaDemonstrativo, Integer> implements ContaDemonstrativoDao {
	public JpaContaDemonstrativoDao() {
		super(ContaDemonstrativo.class);
	}

}
