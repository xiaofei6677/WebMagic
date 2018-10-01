package Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class Demo5 implements PageProcessor {
	
	public static final String URL_POST = "https://www\\.cnblogs\\.com/justcooooode/p/[0-9]{7}\\.html";
	public static final String URL_LIST = "https://www\\.cnblogs\\.com/justcooooode/\\d";
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public Site getSite() {
        return site;
    }

    public void process(Page page) {
    						//�б�ҳ�е�����ҳ�ĵ�ַ������������ʽ
        if (page.getUrl().regex(URL_POST).match()) {
            System.out.println("����ҳ ");
            //���������ҳ���������Ӽ��ص������ص�URL��
            page.putField(page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/text()").toString(),
                    page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/@href").toString());
        
        	} else {
        	System.out.println("�б�ҳ");
        	//��λ�б��е�����ҳ��ַ /html/body/div[3]/div[1]/div[2]/ul/li/div[1]/h4/a/@href
        	// //*[@id="homepage1_HomePageDays_DaysList_ctl00_DayList_TitleUrl_0"]
        	// //*[@id="homepage1_HomePageDays_DaysList_ctl01_DayList_TitleUrl_0"]
        	page.addTargetRequests(
                    //page.getHtml().xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/div[2]/a/@href").all());
        			page.getHtml().xpath("//*[@id='homepage1_HomePageDays_DaysList_ctl01_DayList_TitleUrl_0']/@href").all());
            System.out.println("URL  "+ page.getHtml().xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/div[2]/a/@href").all());
        	// ��λ��ҳ��url
            page.addTargetRequests(
                    page.getHtml().xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div[11]/div[1]/a/@href").all());
            /*
        	page.addTargetRequests(
                    page.getHtml().xpath("//*[@id=\"mainContent\"]/div/div/div[@class=\"postTitle\"]/a/@href").all());
            */
        }
    }

    public static void main(String[] args) {
        Spider.create(new Demo5()).addUrl("http://www.cnblogs.com/justcooooode/")
                .addPipeline(new ConsolePipeline()).run();
    }
}
