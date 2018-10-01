package Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * ��������
 * @author Mr_Zhang
 *
 */
public class DemoSina implements PageProcessor{
	
	//�еķ�����Ҫת��
	//public static final String URL_LIST = "https://voice\\.hupu\\.com/nba/\\d";
    public static final String URL_POST = "https://voice\\.hupu\\.com/nba/[0-9]{7}\\.html";
    
    private Site site = Site
            .me()
            .setDomain("voice.hupu.com")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
    public void process(Page page) {
    	//System.out.println("page "+page.toString());
        //�ж��Ƿ�������ҳ������
        if (page.getUrl().regex(URL_POST).match()) {
        	
        	System.out.println("����ҳ");
        	//���������ҳ��ֱ����ȡ���� /html/body/div[4]/div[1]/div[1]/h1
        	page.putField("title", page.getHtml().xpath("//div[@class='artical-title']/h1/text()"));
            
            
        } else {
        	//�������������ҳ�����򣬾�˵������һ���б�ҳ��
        	//��ʱҪ��
        	//������б�ҳ�����б�ҳ��������ص�URL��
        	//page.addTargetRequests(page.getHtml().xpath("//div[@class=\"articleList\"]").links().regex(URL_POST).all());
            //page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            
        	//�Ѿ��õ�Ҫ��ȡurl��list��������ͨ��addTargetRequests�������뵽�����м��ɡ�
        	//ͨ��xPath����λҳ���б������µ�url
        	System.out.println("�б�ҳ");
        	page.addTargetRequests(
                    page.getHtml().xpath("/html/body/div[3]/div[1]/div[2]/ul/li/div[1]/h4/a/@href").all());
        	// /html/body/div[3]/div[1]/div[2]/ul/li[1]/div[1]/h4/a
        	// /html/body/div[3]/div[1]/div[2]/ul/li[2]/div[1]/h4/a
        	// /html/body/div[3]/div[1]/div[2]/ul/li[3]/div[1]/h4/a
        	// ��λ��ҳ��url
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
        Spider.create(new DemoSina()).addUrl("https://voice.hupu.com/nba/")
                .run();
    }

}
