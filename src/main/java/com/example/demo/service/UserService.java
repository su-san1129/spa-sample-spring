package com.example.demo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.form.RegisterUserForm;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ユーザーを登録する処理.
	 * 
	 * @param form フォーム
	 */
	public void registerUser(RegisterUserForm form) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

}
