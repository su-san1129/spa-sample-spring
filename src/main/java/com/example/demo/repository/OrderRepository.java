package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.OrderTopping;

/**
 * オーダー情報を操作するリポジトリ.
 * 
 * @author takahiro.suzuki
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ToppingRepository toppingRepository;

	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("orders");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	/**
	 * オーダー情報オブジェクトを生成するリザルトセットエクストラクター.
	 */
	private final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		Integer preOrderId = 0; // 一行前のオーダーID
		Integer preOrderItemId = 0; // 一行前のオーダーアイテムID
		List<Order> orderList = new ArrayList<>(); // 返却するオーダーリスト
		List<OrderItem> orderItemList = null; // 注文商品リスト（オーダーオブジェクトが所持）
		List<OrderTopping> orderToppingList = null; // 注文トッピングリスト(注文商品オブジェクトが所持)
		while (rs.next()) {
			if (rs.getInt("id") != preOrderId) {
				orderItemList = new ArrayList<>();
				Order order = new Order(
						rs.getInt("id")
						, rs.getInt("user_id")
						, rs.getInt("status")
						, rs.getInt("total_price")
						, rs.getDate("order_date")
						, rs.getString("destination_name")
						, rs.getString("destination_email")
						, rs.getString("destination_zipcode")
						, rs.getString("destination_address")
						, rs.getString("destination_tel")
						, rs.getTimestamp("delivery_time")
						, rs.getInt("payment_method")
						, userRepository.load(rs.getInt("user_id"))
						, orderItemList);
				orderList.add(order);
			}
			preOrderId = rs.getInt("id"); // 一行前のIDを現在のIDに更新
			if (rs.getInt("order_item_id") != preOrderItemId) {
				orderToppingList = new ArrayList<>();
				OrderItem orderItem = new OrderItem(
						rs.getInt("order_item_id")
						, rs.getInt("item_id")
						, rs.getInt("id")
						, rs.getInt("quantity")
						, rs.getString("size").charAt(0)
						, itemRepository.load(rs.getInt("item_id"))
						, orderToppingList
						);
				orderItemList.add(orderItem);
			}
			preOrderItemId = rs.getInt("order_item_id"); // 一行前のIDを現在のIDに更新
			if (rs.getInt("order_topping_id") != 0) {
				OrderTopping orderTopping = new OrderTopping(
						rs.getInt("order_topping_id")
						, rs.getInt("topping_id")
						, rs.getInt("order_item_id")
						, toppingRepository.load(rs.getInt("topping_id"))
						);
				orderToppingList.add(orderTopping);
			}
		}
		return orderList;
	};

	/**
	 * ユーザーIdと状態からオーダーを検索.
	 * 
	 * @param userId ユーザーID
	 * @param status 状態
	 * @return 検索されたオーダーオブジェクト
	 */
	public Order findByUserIdAndStatus(Integer userId, Integer status) {
		String sql = "SELECT o.id, o.user_id, o.status, o.total_price, o.order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method, oi.id AS order_item_id, oi.item_id, oi.quantity, oi.size , t.id AS order_topping_id, t.topping_id FROM orders o LEFT OUTER JOIN order_items oi ON o.id = oi.order_id LEFT OUTER JOIN order_toppings t ON oi.id = t.order_item_id WHERE user_id = :userId AND status = :status ORDER BY o.id, oi.id, t.id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		List<Order> orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		if (orderList.size() > 0) {
			return orderList.get(0);
		}
		return null;
	}

	public Order save(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		if (order.getId() == null) {
			Number key = insert.executeAndReturnKey(param);
			order.setId(key.intValue());
		} else {
			String sql = "UPDATE orders SET " 
					+ "id = :id, " 
					+ "user_id = :userId, " 
					+ "status = :status, "
					+ "total_price = :totalPrice, " 
					+ "order_date = :orderDate, "
					+ "destination_name = :destinationName, " 
					+ "destination_email = :destinationEmail, "
					+ "destination_zipcode = :destinationZipcode, " 
					+ "destination_address = :destinationAddress, "
					+ "destination_tel = :destinationTel, " 
					+ "delivery_time = :deliveryTime, "
					+ "payment_method = :paymentMethod " 
					+ "WHERE id = :id";
			template.update(sql, param);
		}
		return order;
	}

	/**
	 * 一件削除.
	 * 
	 * @param id ID
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM orders WHERE id = :id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

}
