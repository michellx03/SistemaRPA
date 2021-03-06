package acessorestrito.angularrestspringsecurity.dao.tipo_atendimento;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import acessorestrito.angularrestspringsecurity.dao.JpaDao;

import acessorestrito.angularrestspringsecurity.entity.TipoAtendimento;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;


public class JpaTipoAtendimentoDao extends JpaDao<TipoAtendimento, Integer> implements TipoAtendimentoDao {
	
	public JpaTipoAtendimentoDao() {
		super(TipoAtendimento.class);
	}
}
