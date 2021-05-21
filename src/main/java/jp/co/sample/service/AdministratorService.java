package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.repository.AdministratorRepository;

/**
 * 管理者関連機能の業務処理を行うサービス
 * 
 * @author shigeki.morishita
 *
 */
@Service
@Transactional
public class AdministratorService {
	@Autowired
	private AdministratorRepository repository;

//	public void insert(Administrator administrator) {
//		repository.insert(administrator);
//	}
//
//	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
//		return repository.findByMailAddressAndPassword(mailAddress, password);
//	}
}