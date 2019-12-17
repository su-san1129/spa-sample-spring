package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Item;
import com.example.demo.domain.LoginUser;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderTopping;
import com.example.demo.domain.Topping;
import com.example.demo.form.RequestOrderTopping;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderToppingRepository;
import com.example.demo.repository.ToppingRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.ShoppingCartService;

@RestController
@RequestMapping("/getRes")
public class ApiController {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private HttpSession session;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private ToppingRepository toppingRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	@Autowired
	private OrderService orderService;

	/**
	 * rootパスにGETリクエストを送ると、JSON形式で商品リストを返す.
	 * 
	 * @return 商品リストのJSON
	 */
	@RequestMapping("")
	public ResponseEntity<List<Item>> findAll() {
		List<Item> itemList = itemRepository.findAll();
		return new ResponseEntity<>(itemList, HttpStatus.OK);
	}

	/**
	 * 一件検索.
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getDetail/{id}")
	public ResponseEntity<Item> load(@PathVariable Integer id) {
		Item item = itemRepository.load(id);
		return new ResponseEntity<>(item, HttpStatus.OK);
	}

	@RequestMapping("/cartItem")
	public Order cartList(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = session.getId().hashCode();
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		Order order = shoppingCartService.showOrderByUserIdAndStatus(userId, 0);
		order.setTotalPrice(order.getCalcTotalPrice());
		order.getOrderItemList().forEach(oi -> oi.setSubTotal(oi.subTotal()));
		return order;
	}

	@RequestMapping("/deleteOrderItem/{id}")
	public ResponseEntity<HttpStatus> deleteOrderItem(@PathVariable Integer id) {
		System.err.println("デリートメソッド");
		shoppingCartService.deleteCart(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping("/deleteOrderTopping/{id}")
	public ResponseEntity<HttpStatus> deleteOrderTopping(@PathVariable Integer id) {
		shoppingCartService.deleteOrderTopping(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/createOrderTopping/")
	public ResponseEntity<HttpStatus> createOrderTopping(@RequestBody RequestOrderTopping req) {
		List<Integer> orderToppingList = req.getOrderToppings();
		if (orderToppingList != null) {
			orderToppingList.forEach(i -> {
				if (orderToppingRepository.findByIdAndOrderItemId(i, req.getOrderItemId()) == null) {
					System.err.println(i + "を保存します。");
					OrderTopping orderTopping = new OrderTopping();
					orderTopping.setToppingId(i);
					orderTopping.setOrderItemId(req.getOrderItemId());
					orderToppingRepository.save(orderTopping);
				} else {
					System.err.println(i + "が重複したため保存しません。");
				}
			});
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}

	@GetMapping("/toppings")
	public List<Topping> toppingList() {
		return toppingRepository.findAll();
	}

	@GetMapping("/orderList")
	public Order order(@AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = loginUser.getUser().getId();
		Order order = orderService.showOrderList(userId, 0);
		return order;
	}

}
