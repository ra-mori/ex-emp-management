package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

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
	@Autowired
	private HttpSession session;

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
	 * @return ログイン画面にフォワード
	 */
	@RequestMapping("/")
	public String toLogin(Model model) {
		return "administrator/login";
	}

	/**
	 * ログイン情報のチェック.
	 * 
	 * @param form
	 * @param model モデル
	 * @return 従業員一覧へフォワード
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());

		if (administrator == null) {
			model.addAttribute("alert", "メールアドレスまたはパスワードが不正です。");
			return "administrator/login";
		} else {
			session.setAttribute("administratorName", administrator.getName());
			return "forward:/employee/showList";
		}

	}

	/**
	 * ログアウト処理.
	 * 
	 * @return ログイン画面に移動
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();

		return "redirect:/";
	}

}
