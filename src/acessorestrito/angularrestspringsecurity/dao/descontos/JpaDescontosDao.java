package acessorestrito.angularrestspringsecurity.dao.descontos;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import acessorestrito.angularrestspringsecurity.dao.JpaDao;
import acessorestrito.angularrestspringsecurity.dao.descontos.DescontosDao;
import acessorestrito.angularrestspringsecurity.entity.Desconto;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaDescontosDao extends JpaDao<Desconto, Integer> implements DescontosDao {
	public JpaDescontosDao() {
		super(Desconto.class);
	}
}
