package acessorestrito.angularrestspringsecurity.dao.medicos;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import acessorestrito.angularrestspringsecurity.dao.JpaDao;
import acessorestrito.angularrestspringsecurity.dao.medicos.MedicosDao;
import acessorestrito.angularrestspringsecurity.entity.Medico;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMedicosDao extends JpaDao<Medico, Integer> implements MedicosDao {
	public JpaMedicosDao() {
		super(Medico.class);
	}
}
