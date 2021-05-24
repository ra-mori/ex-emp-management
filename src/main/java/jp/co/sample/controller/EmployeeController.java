package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報の検索処理をするコントローラ.
 * 
 * @author shigeki.morishita
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	/**
	 * 従業員一覧情報を出力.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面へフォワード
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();

		model.addAttribute("employeeList", employeeList);

		return "employee/list";
	}

	/**
	 * 従業員情報を検索する.
	 * 
	 * @param id
	 * @param model
	 * @return 従業員詳細画面にフォワード
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		int updateEmployeeForm_id = Integer.parseInt(id);

		Employee employee = employeeService.showDetail(updateEmployeeForm_id);
		model.addAttribute("employee", employee);

		return "employee/detail";
	}

	/**
	 * 扶養人数を更新する.
	 * 
	 * @return 従業員一覧画面へリダイレクト
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		int updateEmployeeForm_id = Integer.parseInt(form.getId());
		Employee employee = employeeService.showDetail(updateEmployeeForm_id);

		int update_depCount = Integer.parseInt(form.getDependentsCount());
		employee.setDependentsCount(update_depCount);

		employeeService.update(employee);

		return "redirect:/employee/showList";
	}

}
