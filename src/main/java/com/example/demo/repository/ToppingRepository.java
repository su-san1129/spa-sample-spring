package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Topping;

@Repository
public class ToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * トッピングのローマッパー.
	 */
	private final RowMapper<Topping> TOPPING_ROW_MAPPER = (rs, i) -> {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		Integer priceM = rs.getInt("price_m");
		Integer priceL = rs.getInt("price_l");
		Topping topping = new Topping(id, name, priceM, priceL);
		return topping;
	};

	/**
	 * トッピングの全件検索.
	 * 
	 * @return トッピングの全件検索結果
	 */
	public List<Topping> findAll() {
		String sql = "SELECT id, name, price_m, price_l FROM toppings ORDER BY id;";
		List<Topping> toppingList = template.query(sql, TOPPING_ROW_MAPPER);
		return toppingList;
	}

	/**
	 * トッピングの一件検索.
	 * 
	 * @param id トッピングのPK
	 * @return idから検索されたトッピング
	 */
	public Topping load(Integer id) {
		try {
			String sql = "SELECT id, name, price_m, price_l FROM toppings WHERE id = :id;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			Topping topping = template.queryForObject(sql, param, TOPPING_ROW_MAPPER);
			return topping;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
