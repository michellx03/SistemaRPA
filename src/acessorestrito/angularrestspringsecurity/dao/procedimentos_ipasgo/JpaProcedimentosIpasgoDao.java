package acessorestrito.angularrestspringsecurity.dao.procedimentos_ipasgo;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import acessorestrito.angularrestspringsecurity.dao.JpaDao;
import acessorestrito.angularrestspringsecurity.dao.procedimentos_ipasgo.ProcedimentosIpasgoDao;
import acessorestrito.angularrestspringsecurity.entity.ContaFaturamento;
import acessorestrito.angularrestspringsecurity.entity.ProcedimentosIpasgo;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaProcedimentosIpasgoDao extends JpaDao<ProcedimentosIpasgo, Integer> implements ProcedimentosIpasgoDao {
	
	public JpaProcedimentosIpasgoDao() {
		super(ProcedimentosIpasgo.class);
	}
}
