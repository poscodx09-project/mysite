package com.poscodx.mysite07.service;


import com.poscodx.mysite07.repository.GuestbookLogRepository;
import com.poscodx.mysite07.repository.GuestbookRepository;
import com.poscodx.mysite07.vo.GuestbookVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GuestbookService {
	private final GuestbookRepository guestbookRepository;
	private final GuestbookLogRepository guestbookLogRepository;
	
	public GuestbookService(GuestbookLogRepository guestbookLogRepository, GuestbookRepository guestbookRepository) {
		this.guestbookRepository = guestbookRepository;
		this.guestbookLogRepository = guestbookLogRepository;
	}
	
	public List<GuestbookVo> getContentsList() {
		return guestbookRepository.findAll();
	}
	
	@Transactional
	public void deleteContents(Long id, String password) {
		GuestbookVo vo = guestbookRepository.findById(id);
		if(vo == null) {
			return;
		}
		
		int count = guestbookRepository.deleteByIdAndPassword(id, password);
		if(count == 1) {
			guestbookLogRepository.updateByRegDate(vo.getRegDate());
		}
	}
	
	@Transactional
	public void addContents(GuestbookVo vo) {
		int count = guestbookLogRepository.update();
		if(count == 0) {
			guestbookLogRepository.insert();
		}
		guestbookRepository.insert(vo);
	}
}
