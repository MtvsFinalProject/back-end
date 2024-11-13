package com.project.final_project.item.dto;

import com.project.final_project.item.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
  private Integer id;
  private String itemName;
  private Integer count;
  private Integer price;
  private Integer userId;
  private String itemType;

  public ItemDTO(Item item) {
    this.itemName = item.getItemName();
    this.count = item.getCount();
    this.price = item.getPrice();
    this.userId = item.getUserId();
    this.itemType = item.getItemType();
  }
}
