package acessorestrito.angularrestspringsecurity.dao.auditoria;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import acessorestrito.angularrestspringsecurity.dao.Dao;
import acessorestrito.angularrestspringsecurity.entity.Auditoria;

public interface AuditoriaDao extends Dao<Auditoria,Long> {
	
}
