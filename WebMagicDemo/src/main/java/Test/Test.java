package Test;

/**
 * ����� �μ� ��ȡ
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Test implements PageProcessor{
	
	//�еķ�����Ҫת��
	//public static final String URL_LIST = "https://www\\.mafengwo\\.cn/";
    //public static final String URL_POST = "http://www\\.mafengwo\\.cn/gonglve/ziyouxing/\\.*";
    public static final String URL_POST = "https://www\\.mafengwo\\.cn/i/[0-9]{7,10}\\.html";
    private Site site = Site
            .me()
            .setDomain("www.mafengwo.cn")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
    public void process(Page page) {
        
        if (page.getUrl().regex(URL_POST).match()) {
        	System.out.println("����ҳ");
        	//*[@id="_j_cover_box"]/div[3]/div[2]/div/h1
        	//*[@id="_j_cover_box"]/div[3]/div[2]/div/h1
        	page.putField("���� ", page.getHtml().xpath("//div[@class='vi_con']/h1/text()"));
        	
        } else {//�б�ҳ
        	System.out.println("�б�ҳ");
        	//��λ�б�ҳ�е�����ҳURL//*[@id="_j_tn_content"]/div[1]/div/div[2]/dl/dt/a
        	page.addTargetRequests(
                    page.getHtml().xpath("/html/body/div[2]/div[2]/div[1]/div[2]/div/div/a/@href").all());
        			// /html/body/div[2]/div[2]/div[1]/div[2]/div/div/a
        			// /html/body/div[2]/div[2]/div[1]/div[2]/div/div/a
            //��λ�б�ҳ�е��б�URL
        	/*
            page.addTargetRequests(page.getHtml()
            		.xpath("/html/body/div[3]/div[1]/div[2]/div[3]/div[1]/div[1]/div[1]/div/div[2]/dl/dt/a/@href").all());
            */
        	
        }
    }
    
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
    	System.out.println();
    									//�б�ҳ��ַ
        Spider.create(new Test())
        	.addUrl("https://www.mafengwo.cn/")
            .run();
    }

}
