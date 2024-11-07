package com.project.final_project.lockerguestbook.controller;

import com.project.final_project.lockerguestbook.domain.LockerGuestBook;
import com.project.final_project.lockerguestbook.dto.LockerGuestBookDTO;
import com.project.final_project.lockerguestbook.dto.LockerGuestBookRegisterDTO;
import com.project.final_project.lockerguestbook.service.LockerGuestBookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locker-guest-book")
@RequiredArgsConstructor
public class LockerGuestBookController {

  private final LockerGuestBookService lockerGuestBookService;

  @PostMapping
  public LockerGuestBookDTO registerLockerGuestBook(@RequestBody LockerGuestBookRegisterDTO dto){
    return lockerGuestBookService.registerLockerGuestBook(dto);
  }

  @GetMapping("/list")
  public List<LockerGuestBookDTO> getAllLockerGuestBooks() {
    return lockerGuestBookService.getAllLockerGuestBooks();
  }

  @DeleteMapping
  public ResponseEntity<?> removeLockerGuestBookById(@RequestParam("lockerGuestBookId") Integer lockerGuestBookId){
    lockerGuestBookService.removeLockerGuestBookById(lockerGuestBookId);
    return ResponseEntity.noContent().build();
  }

}
