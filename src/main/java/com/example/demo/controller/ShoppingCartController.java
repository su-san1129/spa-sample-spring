package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.LoginUser;
import com.example.demo.form.OrderItemForm;
import com.example.demo.service.ShoppingCartService;

@Controller
@RequestMapping("/cart_list")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private HttpSession session;

	/**
	 * カート一覧を表示.
	 * 
	 * @return カート一覧画面
	 */
	@RequestMapping("")
	public String cartList(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		return "cart_list";
	}

	/**
	 * ショッピングカートに商品を追加する.
	 * 
	 * @param form      フォーム
	 * @param loginUser ログインしているユーザー
	 * @return ショッピングカート一覧
	 */
	@RequestMapping("/addCart")
	public String addCart(OrderItemForm form, @AuthenticationPrincipal LoginUser loginUser) {
		System.err.println(form);
		Integer userId = session.getId().hashCode(); // ユーザーIDを仮で設定
		if (loginUser != null) {
			// ログインしていたら、ユーザーIDを差し替える
			userId = loginUser.getUser().getId();
		} else {
			session.setAttribute("userId", userId);
		}
		shoppingCartService.addShoppingCart(form, userId, loginUser);
		return "redirect:/cart_list";
	}

	/**
	 * 商品を削除する.
	 * 
	 * @param id ID
	 * @return ショッピングカート一覧
	 */
	@RequestMapping("/deleteCart")
	public String deleteCart(Integer id) {
		shoppingCartService.deleteCart(id);
		return "redirect:/cart_list";
	}

}
