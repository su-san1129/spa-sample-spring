package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;
import com.example.demo.domain.Topping;

@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 商品情報のローマッパー
	 */
	private final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		List<Topping> toppingList = toppingRepository.findAll();
		Item item = new Item(
				rs.getInt("id")
				, rs.getString("name")
				, rs.getString("description")
				, rs.getInt("price_m")
				, rs.getInt("price_l")
				, rs.getString("image_path")
				, rs.getBoolean("deleted")
				, toppingList);
		return item;
	};

	/**
	 * 商品の一件検索.
	 * 
	 * @param id ID
	 * @return IDで検索された商品 1件もない場合、例外が発生しnullになる
	 */
	public Item load(Integer id) {
		try {
			String sql = "SELECT id, name, description, price_m, price_l, image_path, deleted FROM items WHERE id = :id";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
			return item;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 商品の全件検索.
	 * 
	 * @return 商品の全件検索情報
	 */
	public List<Item> findAll() {
		String sql = "SELECT id, name, description, price_m, price_l, image_path, deleted FROM items ORDER BY id;";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 検索された名前から該当する商品を取り出す.
	 * 
	 * @param name 名前
	 * @return 商品リスト
	 */
	public List<Item> findByName(String name) {
		String sql = "SELECT id, name, description, price_m, price_l, image_path, deleted FROM items WHERE name LIKE :name ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		return template.query(sql, param, ITEM_ROW_MAPPER);
	}

}
