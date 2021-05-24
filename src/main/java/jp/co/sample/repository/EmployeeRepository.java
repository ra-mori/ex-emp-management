package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author shigeki.morishita
 *
 */
@Repository
public class EmployeeRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final String TABLE_NAME = "Employees";

	/**
	 * employeeオブジェクトを生成するローマッパー
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();

		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};

	/**
	 * メンバー一覧情報を年齢順で取得します.
	 * 
	 * @return 全メンバー一覧 メンバーが存在しない場合はサイズ0件のメンバー一覧を返します
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM "
				+ TABLE_NAME + " ORDER BY hire_date DESC;";

		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return employeeList;
	}

	/**
	 * 主キー検索を行います.
	 * 
	 * @param id 検索したい主キーの値
	 * @return メンバー情報(検索されなかった場合は非検査例外が発生します)
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM "
				+ TABLE_NAME + " WHERE id = :id;";
		System.out.println(sql);
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return employee;
	}

	/**
	 * メンバー情報を更新.
	 * 
	 * @param employee
	 */
	public void update(Employee employee) {
		String sql = "UPDATE" + TABLE_NAME + " SET name=:name, image=:image, gender=:gender, hireDate=:hireDate, "
				+ "mailAddress=:mailAddress, zipCode=:zipCode, address=:address, telephone=:telephone, salay=:salay, characteristics=:characteristics, dependentsCount=:dependentsCount"
				+ " WHERE id=:id;";

		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		template.update(sql, param);
	}
}
