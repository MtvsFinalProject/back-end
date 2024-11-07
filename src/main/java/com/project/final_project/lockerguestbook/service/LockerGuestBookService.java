package com.project.final_project.lockerguestbook.service;

import com.project.final_project.lockerguestbook.domain.LockerGuestBook;
import com.project.final_project.lockerguestbook.dto.LockerGuestBookDTO;
import com.project.final_project.lockerguestbook.dto.LockerGuestBookRegisterDTO;
import com.project.final_project.lockerguestbook.repository.LockerGuestBookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LockerGuestBookService {

  private final LockerGuestBookRepository lockerGuestBookRepository;

  public LockerGuestBookDTO registerLockerGuestBook(LockerGuestBookRegisterDTO dto) {
    LockerGuestBook lockerGuestBook = LockerGuestBook.createLockerGuestBook(dto);
    return new LockerGuestBookDTO(lockerGuestBookRepository.save(lockerGuestBook));
  }

  public List<LockerGuestBookDTO> getAllLockerGuestBooks() {
    return lockerGuestBookRepository.findAll().stream().map(LockerGuestBookDTO::new).toList();
  }

  public void removeLockerGuestBookById(Integer lockerGuestBookId) {
    lockerGuestBookRepository.deleteById(lockerGuestBookId);
  }
}
