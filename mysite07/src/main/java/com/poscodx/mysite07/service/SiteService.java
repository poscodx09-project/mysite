package com.poscodx.mysite07.service;


import com.poscodx.mysite07.repository.SiteRepository;
import com.poscodx.mysite07.vo.SiteVo;
import org.springframework.stereotype.Service;

@Service
public class SiteService {
	private SiteRepository siteRepository;
	
	public SiteService(SiteRepository siteRepository) {
		this.siteRepository = siteRepository;
	}
	
	public SiteVo getSite() {
		return siteRepository.find();
	}
	
	public void updateSite(SiteVo vo) {
		siteRepository.update(vo);
	}
}
