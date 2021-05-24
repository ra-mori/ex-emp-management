package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * Administratorsテーブルを操作するリポジトリ.
 * 
 * @author shigeki.morishita
 *
 */
@Repository
public class AdministratorRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final String TABLE_NAME = "administrators";

	/**
	 * Administratorsオブジェクトを生成するローマッパー
	 */
	private static final RowMapper<Administrator> ADMIN_ROW_MAPPER = (rs, i) -> {
		Administrator admin = new Administrator();
		admin.setId(rs.getInt("id"));
		admin.setName(rs.getString("name"));
		admin.setMailAddress(rs.getString("mail_address"));
		admin.setPassword(rs.getString("password"));
		return admin;
	};

	/**
	 * 管理者情報を挿入します.
	 * 
	 * @param administrator
	 */
	public void insert(Administrator administrator) {
		String sql = "INSERT INTO " + TABLE_NAME
				+ " (name,mail_address,password) VALUES (:name,:mailAddress,:password);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		template.update(sql, param);
	}

	/**
	 * 管理者情報を挿入します.
	 * 
	 * @param mailAddress
	 * @param password
	 * @return 管理者情報
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String sql = "SELECT id,name,mailAddress,password FROM" + TABLE_NAME
				+ "WHERE mail_address = :mailAdress && password = :password";

		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",
				password);

		List<Administrator> administratorList = template.query(sql, param, ADMIN_ROW_MAPPER);

		if (administratorList.size() == 0) {
			return null;
		}
		return administratorList.get(0);
	}

}
