package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;

/**
 * ユーザ情報を操作するリポジトリ.
 * 
 * @author takahiro.suzuki
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * ユーザー情報を格納するローマッパー.
	 */
	private final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"),
				rs.getString("zipcode"), rs.getString("address"), rs.getString("telephone"), rs.getBoolean("isadmin"));
		return user;
	};

	/**
	 * ユーザー情報の一件検索.
	 * 
	 * @param id ユーザーのPK
	 * @return ユーザーの一件検索情報
	 */
	public User load(Integer id) {
		try {
			String sql = "SELECT id, name, email, password, zipcode, address, telephone, isadmin FROM users WHERE id = :id;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			User user = template.queryForObject(sql, param, USER_ROW_MAPPER);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ユーザー情報をメールアドレスで検索.
	 * 
	 * @param email メールアドレス
	 * @return 検索されたユーザー情報
	 */
	public User findByMailAddress(String email) {
		try {
			String sql = "SELECT id, name, email, password, zipcode, address, telephone, isadmin FROM users WHERE email = :email;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
			User user = template.queryForObject(sql, param, USER_ROW_MAPPER);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ユーザー情報をDBに保存.
	 * 
	 * idがあれば、update なければ、insert
	 * 
	 * @param user ユーザー
	 */
	public void save(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		if (user.getId() == null) {
			String sql = "INSERT INTO users (name, email, password, zipcode, address, telephone) VALUES ( :name, :email, :password, :zipcode, :address, :telephone);";
			template.update(sql, param);
		} else {
			String sql = "UPDATE users SET id=:id, name=:name, password=:password, zipcode=:zipcode, address=:address, telephone=:telephone WHERE id=:id";
			template.update(sql, param);
		}
	}
}
