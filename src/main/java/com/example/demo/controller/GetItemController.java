package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;

@RestController
public class GetItemController {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * rootパスにGETリクエストを送ると、JSON形式で商品リストを返す.
	 * 
	 * @return 商品リストのJSON
	 */
	@RequestMapping("/getRes")
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

}
