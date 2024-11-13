package com.project.final_project.item.controller;

import static com.project.final_project.common.global.HttpResponseEntity.success;

import com.project.final_project.common.global.HttpResponseEntity.ResponseResult;
import com.project.final_project.item.dto.ItemDTO;
import com.project.final_project.item.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

  private final ItemService itemService;

  @PostMapping
  public ResponseResult<ItemDTO> registerItem(@RequestBody ItemDTO dto){
    return success(itemService.registerItem(dto));
  }

  @GetMapping("/list/{userId}")
  public List<ItemDTO> getItemListByUserId(@PathVariable("userId") Integer userId){
    return itemService.getItemListByUserId(userId);
  }

  @GetMapping("/list/{userId}/{itemType}")
  public List<ItemDTO> getItemListByUserIdAndItemType(
      @PathVariable("userId") Integer userId,
      @PathVariable("itemType") String itemType
  ) {
    return itemService.getItemListByUserIdAndItemType(userId, itemType);
  }

  @PatchMapping
  public ItemDTO updateItem(@RequestBody ItemDTO dto){
    return itemService.updateInventory(dto);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteItemById(@RequestParam("itemId") Integer itemId) {
    itemService.deleteItemById(itemId);
    return ResponseEntity.noContent().build();
  }

}
