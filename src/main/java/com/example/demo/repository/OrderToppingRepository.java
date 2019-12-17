package com.example.demo.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.OrderTopping;
import com.example.demo.domain.Topping;

/**
 * 注文トッピングを操作するリポジトリ.
 * 
 * @author takahiro.suzuki
 *
 */
@Repository
public class OrderToppingRepository {

	private static final Logger logger = LoggerFactory.getLogger(OrderToppingRepository.class);

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 注文トッピングオブジェクトを格納するローマッパー.
	 */
	private final RowMapper<OrderTopping> ORDER_TOPPING_ROW_MAPPER = (rs, i) -> {
		Integer id = rs.getInt("id");
		Integer toppingId = rs.getInt("topping_id");
		Integer orderItemId = rs.getInt("order_item_id");
		Topping topping = toppingRepository.load(rs.getInt("topping_id"));
		OrderTopping orderTopping = new OrderTopping(id, toppingId, orderItemId, topping);
		return orderTopping;
	};

	/**
	 * 注文トッピングの一件検索.
	 * 
	 * @param id ID
	 * @return 検索された注文トッピング 0件の場合は例外発生後、nullを返す。
	 */
	public OrderTopping load(Integer id) {
		try {
			String sql = "SELECT id, topping_id, order_item_id FROM order_toppings WHERE id=:id;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			OrderTopping orderTopping = template.queryForObject(sql, param, ORDER_TOPPING_ROW_MAPPER);
			return orderTopping;
		} catch (Exception e) {
			logger.info("loadにてエラーが発生しました。");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * オーダートッピングをDBに保存.
	 * 
	 * @param orderTopping オーダートッピング
	 */
	public void save(OrderTopping orderTopping) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		if (orderTopping.getId() == null) {
			String sql = "INSERT INTO order_toppings (topping_id, order_item_id) VALUES( :toppingId, :orderItemId);";
			template.update(sql, param);
		} else {
			String sql = "UPDATE order_toppings SET id=:id, topping_id=:toppingId, order_item_id=:orderItemId WHERE id=:id";
			template.update(sql, param);
		}
	}

	public void deleteByOrderItemId(Integer orderItemId) {
		String sql = "DELETE FROM order_toppings WHERE order_item_id = :orderItemId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
		template.update(sql, param);
	}

	/**
	 * PKで注文トッピングを削除する.
	 * 
	 * @param id ID
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM order_toppings WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

	public OrderTopping findByIdAndOrderItemId(Integer toppingId, Integer orderItemId) {
		try {
			String sql = "SELECT id, topping_id, order_item_id FROM order_toppings WHERE topping_id=:toppingId AND order_item_id=:orderItemId;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("toppingId", toppingId)
					.addValue("orderItemId", orderItemId);
			return template.queryForObject(sql, param, ORDER_TOPPING_ROW_MAPPER);
		} catch (Exception e) {
			logger.info("findByIdAndOrderItemIdにてエラーが発生しました。");
			return null;
		}
	}

}
