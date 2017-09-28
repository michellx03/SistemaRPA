package acessorestrito.angularrestspringsecurity.dao.conta;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import acessorestrito.angularrestspringsecurity.dao.Dao;
import acessorestrito.angularrestspringsecurity.entity.Conta;

public interface ContaDao extends Dao<Conta, Integer> {

}
