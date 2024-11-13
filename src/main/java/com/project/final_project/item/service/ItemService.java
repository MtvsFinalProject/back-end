package com.project.final_project.item.service;

import com.project.final_project.item.domain.Item;
import com.project.final_project.item.dto.ItemDTO;
import com.project.final_project.item.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  public ItemDTO registerItem(ItemDTO dto) {
    Item item = new Item(dto);
    itemRepository.save(item);
    return dto;
  }

  public List<ItemDTO> getItemListByUserId(Integer userId) {
    return itemRepository.getItemListByUserId(userId).stream().map(ItemDTO::new).toList();
  }

  public List<ItemDTO> getItemListByUserIdAndItemType(Integer userId, String itemType) {
    return itemRepository.getItemListByUserIdAndItemType(userId, itemType);
  }

  @Transactional
  public ItemDTO updateInventory(ItemDTO dto) {

    Item foundItem = itemRepository.findById(dto.getId()).orElseThrow(
        () -> new IllegalStateException("not found item id : " + dto.getId()));

    if(dto.getItemName() != null){
      foundItem.setItemName(dto.getItemName());
    }

    if(dto.getCount() != null){
      foundItem.setCount(dto.getCount());
    }

    if(dto.getPrice() != null){
      foundItem.setPrice(dto.getPrice());
    }

    if(dto.getUserId() != null) {
      foundItem.setUserId(dto.getUserId());
    }

    if(dto.getItemType() != null){
      foundItem.setItemType(dto.getItemType());
    }

    return new ItemDTO(foundItem);
  }

  public void deleteItemById(Integer itemId) {
    itemRepository.deleteById(itemId);
  }
}
