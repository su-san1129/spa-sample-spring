package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.LoginUser;
import com.example.demo.form.RegisterUserForm;
import com.example.demo.service.ShoppingCartService;
import com.example.demo.service.UserService;

/**
 * ユーザー情報を操作するコントローラー.
 * 
 * @author takahiro.suzuki
 *
 */
@Controller
@RequestMapping("")
public class UserController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserService userService;

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	private RegisterUserForm setUpRegisterUserForm() {
		return new RegisterUserForm();
	}

	/**
	 * ログイン画面を表示する.
	 * 
	 * @param model モデル
	 * @param error エラー
	 * @return ログイン画面
	 */
	@RequestMapping("/login")
	public String login(Model model, @RequestParam(required = false) String error) {
		if (error != null) {
			model.addAttribute("loginError", "メールアドレスまたはパスワードが違います。");
		}

		if (request.getHeader("referer") == null) {
			return "login";
		}
		String url = request.getHeader("referer").substring(21);
		// String url = request.getHeader("referer").substring(39);// デプロイ用
		if (!("/login".equals(url)) && !"/register_user".equals(url) && !"/register".equals(url)) {
			session.setAttribute("url", url);
		}
		System.err.println(url + " sessionURL -> " + session.getAttribute("url"));
		return "login";
	}

	/**
	 * ユーザー登録画面を表示.
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("/register_user")
	public String toRegister() {
		return "register_user";
	}

	/**
	 * ユーザー新規登録処理.
	 * 
	 * @param form フォーム
	 * @return ユーザーログイン画面
	 */
	@RequestMapping("/register")
	public String register(RegisterUserForm form) {
		userService.registerUser(form);
		System.err.println("登録が完了しました。");
		return "login";
	}

	/**
	 * 成功時のパス.
	 * 
	 * @return ログインや登録処理以外の場合は、以前のURLへ遷移。それ以外ならトップページ
	 */
	@RequestMapping("/successPath")
	public String successPath(@AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId != null) {
			shoppingCartService.changeOrder(userId, loginUser);
		}
		String url = (String) session.getAttribute("url");
		if (url == null) {
			return "forward:/";
		}
		return "redirect:" + url;

	}

}
