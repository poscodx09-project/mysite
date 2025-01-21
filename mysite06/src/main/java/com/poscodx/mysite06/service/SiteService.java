package com.poscodx.mysite06.service;


import com.poscodx.mysite06.repository.SiteRepository;
import com.poscodx.mysite06.vo.SiteVo;
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
