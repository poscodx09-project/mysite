package mysite.service;

import mysite.repository.GuestBookLogRepository;
import mysite.repository.GuestBookRepository;
import mysite.vo.GuestbookVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GuestBookService {

    private GuestBookRepository guestBookRepository;
    private GuestBookLogRepository guestBookLogRepository;
    public GuestBookService(GuestBookRepository guestBookRepository, GuestBookLogRepository guestBookLogRepository){
        this.guestBookRepository = guestBookRepository;
        this.guestBookLogRepository = guestBookLogRepository;
    }

    public List<GuestbookVo> getContentsList() {
        List<GuestbookVo> guestbookVos = guestBookRepository.findAll();
        return guestbookVos;
    }

    @Transactional
    public void deleteContents(Long id, String password) {
        guestBookRepository.deleteByIdAndPassword(id,password);
    }

    @Transactional
    public void addContents(GuestbookVo vo) {
        guestBookRepository.insert(vo);
    }
}
