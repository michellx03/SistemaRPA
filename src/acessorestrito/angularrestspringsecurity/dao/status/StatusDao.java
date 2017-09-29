package acessorestrito.angularrestspringsecurity.dao.status;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import acessorestrito.angularrestspringsecurity.dao.Dao;
import acessorestrito.angularrestspringsecurity.entity.Status;

public interface StatusDao extends Dao<Status, Integer> {
	
}