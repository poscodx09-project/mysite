package mysite.service;
import org.springframework.stereotype.Service;
import mysite.vo.SiteVo;
@Service
public class SiteService {

    private SiteVo siteVo = new SiteVo(); // 임시 저장소

    public SiteService() {
        // 초기값 설정
        siteVo.setTitle("EunJae's Site");
        siteVo.setDescription("Welcome to My Site");
        siteVo.setProfile("/assets/images/luffy.avif");
        siteVo.setWelcome("안녕하세요! 이은재의 사이트입니다!");
    }

    public SiteVo getSite() {
        return siteVo; // 현재 저장된 사이트 정보 반환
    }

    public void updateSite(SiteVo siteVo) {
        this.siteVo.setTitle(siteVo.getTitle());
        this.siteVo.setDescription(siteVo.getDescription());
        this.siteVo.setProfile(siteVo.getProfile());
        System.out.println("Updated Site Information: " + this.siteVo);
    }
}
