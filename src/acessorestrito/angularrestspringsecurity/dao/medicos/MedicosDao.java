package acessorestrito.angularrestspringsecurity.dao.medicos;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import acessorestrito.angularrestspringsecurity.dao.Dao;
import acessorestrito.angularrestspringsecurity.entity.Medico;

public interface MedicosDao extends Dao<Medico, Integer> {

}
