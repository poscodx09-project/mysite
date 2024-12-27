package mysite.service;

import mysite.repository.GuestBookRepository;
import mysite.vo.GuestbookVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestBookService {

    private final GuestBookRepository guestBookRepository;
    public GuestBookService(GuestBookRepository guestBookRepository){
        this.guestBookRepository = guestBookRepository;
    }

    public List<GuestbookVo> getContentsList() {
        List<GuestbookVo> guestbookVos = guestBookRepository.findAll();
        return guestbookVos;
    }

    public void deleteContents(Long id, String password) {
        guestBookRepository.deleteByIdAndPassword(id,password);
    }

    public void addContents(GuestbookVo vo) {
        guestBookRepository.insert(vo);
    }
}
