package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
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
	@Autowired
	private AdministratorService administratorService;

	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	public String InsertAFormValidation(@Validated InsertAdministratorForm form, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			return toInsert(model);
		} else {
			return "employee";
		}
	}

	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	public String InsertAFormValidation(@Validated LoginForm form, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			return toLogin(model);
		} else {
			return "administrator/login";
		}
	}

	/**
	 * administrator/insertへフォワード.
	 * 
	 * @return administrator/insert
	 */
	@RequestMapping("/toInsert")
	public String toInsert(Model model) {
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

	/**
	 * ログイン画面を表示.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String toLogin(Model model) {
		return "administrator/login";
	}
}
