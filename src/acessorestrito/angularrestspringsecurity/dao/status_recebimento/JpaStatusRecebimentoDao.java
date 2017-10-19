package acessorestrito.angularrestspringsecurity.dao.status_recebimento;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import acessorestrito.angularrestspringsecurity.dao.JpaDao;

import acessorestrito.angularrestspringsecurity.entity.StatusRecebimento;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaStatusRecebimentoDao extends JpaDao<StatusRecebimento, Integer> implements StatusRecebimentoDao {
	public JpaStatusRecebimentoDao() {
		super(StatusRecebimento.class);
	}

}