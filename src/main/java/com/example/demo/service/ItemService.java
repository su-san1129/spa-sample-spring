package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Item;
import com.example.demo.repository.ItemRepository;

/**
 * 商品を操作するサービスクラス.
 * 
 * @author takahiro.suzuki
 *
 */
@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品詳細を表示.
	 * 
	 * @param id ID
	 * @return 商品詳細情報
	 */
	public Item showItemDetail(Integer id) {
		return itemRepository.load(id);
	}

	/**
	 * 全商品情報を表示する.
	 * 
	 * @return 商品全件検索情報
	 */
	public List<Item> showItemList() {
		return itemRepository.findAll();
	}

	/**
	 * 表示する数量.
	 * 
	 * @return 数量の配列
	 */
	public int[] quantity() {
		int[] quantity = new int[10];
		for (int i = 0; i < quantity.length; i++) {
			quantity[i] = i + 1;
		}
		return quantity;
	}

	/**
	 * 名前で検索された商品を表示。何も送信されていない場合は、全件検索を返す.
	 * 
	 * @param name 商品名
	 * @return 商品一覧
	 */
	public List<Item> fizzySearchOrFindAllItemList(String name) {
		if (name == null) {
			return itemRepository.findAll();
		} else {
			return itemRepository.findByName(name);
		}
	}

	/**
	 * 名前だけを取り出してリストに格納.
	 * 
	 * @return 商品の名前一覧
	 */
	public List<String> itemNameList() {
		List<String> nameList = new ArrayList<String>();
		itemRepository.findAll().forEach(item -> {
			nameList.add(item.getName());
		});
		return nameList;
	}

}
