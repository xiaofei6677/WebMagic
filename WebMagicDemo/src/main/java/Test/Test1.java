package Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Я�������μ���ȡ
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * Я������ �μ���ȡ
 * @author Mr_Zhang
 *
 */
public class Test1 implements PageProcessor{
	static int a = 0;
	//�еķ�����Ҫת��
	// http://you.ctrip.com/TravelSite/Home/IndexTravelListHtml?p=13&Idea=0&Type=100&Plate=0
	public static final String URL_LIST = "http://you\\.ctrip\\.com/TravelSite/Home/IndexTravelListHtml\\?p=\\d{1,3}&Idea=0&Type=100&Plate=0";
    public static final String URL_POST = "http://you\\.ctrip\\.com/travels/\\w+/[0-9]{7}\\.html";
    public static final String URL_LIST1 = "http://you\\.ctrip\\.com/travels/";
    
    private Site site = Site
            .me()
            .setDomain("you.ctrip.com")
            .setSleepTime(3000)
            .setUserAgent(
            		"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
    public void process(Page page) {
    
        //�ж��Ƿ�������ҳ������
        if (page.getUrl().regex(URL_POST).match()) {
        	System.out.println("����ҳ");//   
        	//���������ҳ��ֱ����ȡ���� 
        	a++;            									//ctd_head_con cf   ctd_content
        	page.putField("title1 ��ȡ"+a, page.getHtml().xpath("//*[@class='ctd_head_con cf']/h1/text()"));
            
        }else if(page.getUrl().regex(URL_LIST).match()){
        	System.out.println("JS ��õ�ҳ��");
        	
        	page.addTargetRequests(
                    page.getHtml().xpath("/html/body/div/a/@href").all());
        }
        else if(page.getUrl().regex(URL_LIST1).match()){
        	//ͨ��xPath����λҳ���б������µ�url
        	System.out.println("�б�ҳ");
        	//�ҵ��б�ҳ�е�����ҳ��ַ�����������ȡ����
        	page.addTargetRequests(
                    page.getHtml().xpath("//*[@id='photo-fall']/div/div[2]/a[2]/@href").all());
            // ��λ��ҳ��url��������������ȡҳ��
        	List<String> list = new ArrayList<String>();
        	for(int i=0;i<20;i++){
        		list.add("http://you.ctrip.com/TravelSite/Home/IndexTravelListHtml?p="+i+"&Idea=0&Type=100&Plate=0");
        	}
        	page.addTargetRequests(list);
        	/*
            page.addTargetRequests(
                    page.getHtml().xpath("/html/body/div[3]/div[1]/div[3]/a[@class='page-btn-prev']/@href").all());
            */
        }
    }
    
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        Spider.create(new Test1()).addUrl("http://you.ctrip.com/travels/")
        .thread(5)
        .run();
        System.out.println("һ����ȡ��"+a+"������");
    }

}
