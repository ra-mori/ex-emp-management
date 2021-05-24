package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者関連処理の制御をおこなう.
 * 
 * @author shigeki.morishita
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	@ModelAttribute
	public InsertAdministratorForm setUpForm() {
		return new InsertAdministratorForm();
	}

	@Autowired
	private AdministratorService administratorService;

	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	/**
	 * administrator/insertへフォワード.
	 * 
	 * @return administrator/insert
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録する.
	 * 
	 * @param form
	 * @return toInsertへリダイレクト。
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator admin = new Administrator();
		admin.setName(form.getName());
		admin.setMailAddress(form.getMailAddress());
		admin.setPassword(form.getPassword());

		administratorService.insert(admin);

		return "redirect:/toInsert";
	}
}
