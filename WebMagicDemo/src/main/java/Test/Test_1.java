package Test;

/**
 * ����� �μ� ��ȡ
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Test_1 implements PageProcessor{
	
	//�еķ�����Ҫת��
	//public static final String URL_LIST = "https://www\\.mafengwo\\.cn/";
    public static final String URL_POST = "http://www\\.mafengwo\\.cn/i/[0-9]{6,8}\\.html";
    private Site site = Site
            .me()
            .setDomain("www.mafengwo.cn")
            .setSleepTime(4000)
            .setUserAgent(
                    "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
            ;
    //Site.me().setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0").setRetryTimes(3).setSleepTime(1000);
    public void process(Page page) {
        
        if (page.getUrl().regex(URL_POST).match()) {
        	System.out.println("����ҳ");
        	// /html/body/div[2]/div[2]/div[1]/div[1]/h1
        	
        	// //*[@id="_j_cover_box"]/div[3]/div[2]/div/h1
        	// //*[@id="_j_cover_box"]/div[3]/div[2]/div/h1
        	page.putField("���� ", page.getHtml().xpath("//*[@id='_j_cover_box']/div[3]/div[2]/div/h1/text()"));
        	
        } else {//�б�ҳ
        	System.out.println("�б�ҳ��ʼ");
        	//��λ�б�ҳ�е�����ҳ��URL
        	// //*[@id="_j_tn_content"]/div[1]/div/div[2]/dl/dt/a
        	// /html/body/div[2]/div[4]/div/div[3]/div/ul/li[1]/div[1]/a
        	// /html/body/div[2]/div[4]/div/div[3]/div/ul/li[3]/h2/a[2]
        	// /html/body/div[2]/div[4]/div/div[3]/div/ul/li[1]/h2/a
        	// /html/body/div[2]/div[4]/div/div[3]/div/ul/li[4]/h2/a
        	// /html/body/div[2]/div[4]/div/div[3]/div/ul/li[3]/div[1]/a
        	// /html/body/div[2]/div[4]/div/div[3]/div/ul/li[2]/div[1]/a
        	// /html/body/div[2]/div[4]/div/div[3]/div/ul/li[3]/div[1]/a
        	// /html/body/div[2]/div[4]/div/div[3]/div/ul/li[12]/div[1]/a
        	page.addTargetRequests(
                    page.getHtml().xpath("/html/body/div[2]/div[4]/div/div[3]/div/ul/li/div[1]/a/@href").all());
        	System.out.println(page.getHtml().xpath("/html/body/div[2]/div[4]/div/div[3]/div/ul/li/div[1]/a/@href").toString());
        	
        	System.out.println("�б�ҳ����");
        			// /html/body/div[2]/div[2]/div[1]/div[2]/div[2]/a
        			// /html/body/div[2]/div[2]/div[1]/div[2]/div[3]/a
        			// /html/body/div[2]/div[2]/div[1]/div[2]/div[3]/a
            		// /html/body/div[2]/div[2]/div[1]/div[2]/div[5]/a
        			// /html/body/div[2]/div[2]/div[1]/div[2]/div[1]/a
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
        Spider.create(new Test_1())
        	.addUrl("http://www.mafengwo.cn/yj/21536/1-0-2.html")
            .run();
    }

}
