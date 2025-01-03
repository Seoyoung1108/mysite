package mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mysite.repository.SiteRepository;
import mysite.vo.SiteVo;

@Service
public class SiteService {
	private SiteRepository siteRepository;
	
	public SiteService(SiteRepository siteRepository) {
		this.siteRepository=siteRepository;
	}
	
	public SiteVo getSite() {
		SiteVo vo = siteRepository.findRecent();
		return vo;
	}
	
	public void updateSite(SiteVo vo) {
		siteRepository.insert(vo);
	}
}
