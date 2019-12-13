package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.OrderItem;
import com.example.demo.domain.OrderTopping;

/**
 * 注文商品を操作するリポジトリ.
 * 
 * @author takahiro.suzuki
 *
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 注文商品オブジェクトを格納するローマッパー.
	 */
	private final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (rs, i) -> {
		List<OrderTopping> orderToppingList = new ArrayList<>();
		OrderItem orderItem = new OrderItem(rs.getInt("id"), rs.getInt("item_id"), rs.getInt("order_id"),
				rs.getInt("quantity"), rs.getString("size").charAt(0), itemRepository.load(rs.getInt("item_id")),
				orderToppingList);
		return orderItem;
	};

	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	/**
	 * 注文商品のインサートもしくはアップデート.
	 * 
	 * @param orderItem 注文商品
	 * @return 注文商品
	 */
	public OrderItem save(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		if (orderItem.getId() == null) {
			Number key = insert.executeAndReturnKey(param);
			orderItem.setId(key.intValue());
		} else {
			String sql = "UPDATE order_items SET id=:id, item_id=:itemId, order_id=:orderId, quantity=:quantity, size=:size WHERE id=:id;";
			template.update(sql, param);
		}
		return orderItem;
	}

	/**
	 * 注文商品の一件検索.
	 * 
	 * @param id ID
	 * @return 注文商品
	 */
	public OrderItem load(Integer id) {
		try {
			String sql = "SELECT id, item_id, order_id, quantity, size FROM order_items WHERE id = :id;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
			OrderItem orderItem = template.queryForObject(sql, param, ORDER_ITEM_ROW_MAPPER);
			return orderItem;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 注文商品をitemIdとorderIdで検索.
	 * 
	 * @param itemId  商品ID
	 * @param orderId 注文ID
	 * @return 注文商品
	 */
	public OrderItem findByItemIdAndOrderId(Character size, Integer itemId, Integer orderId) {
		try {
			String sql = "SELECT id, item_id, order_id, quantity, size FROM order_items WHERE size=:size AND item_id=:itemId AND order_id=:orderId;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("size", size).addValue("itemId", itemId)
					.addValue("orderId", orderId);
			return template.queryForObject(sql, param, ORDER_ITEM_ROW_MAPPER);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deleteOrderItem(Integer id) {
		String sql = "DELETE FROM order_items WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

}
