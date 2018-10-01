package Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * ѡ�ɱ�<br>
 * ʹ��Selenium��ҳ�涯̬��Ⱦ��<br>
 * @author code4crafter@gmail.com <br>
 * Date: 13-7-26 <br>
 * Time: ����4:08 <br>
 * "C:/Users/Mr_Zhang/AppData/Local/Google/Chrome/Application/chromedriver"
 */
public class Demo6_2 implements PageProcessor {

    private Site site;

    
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("https://xuangubao\\.cn/.*").all());
        if (page.getUrl().toString().contains("pins")) {
        	//����ҳ
        	System.out.println("����ҳ");
        	// //*[@id="news353440"]/div[2]/div/div[1]/div[1]/a
        	// //*[@id="news353439"]/div[2]/div/div[1]/div[1]
        	// //*[@id="news353444"]/div[2]/div/div[1]/div[1]/a
            page.putField("title", page.getHtml().xpath("//*[@id='news353444']/div[2]/div/div[1]/div[1]/a/@href").toString());
        } else {
        	//�б�ҳ
        	System.out.println("�б�ҳ");
            page.getResultItems().setSkip(true);
        }
    }

    
    public Site getSite() {
        if (null == site) {
            site = Site.me().setDomain("xuangubao.cn").setSleepTime(0);
        }
        return site;
    }
//undefined
    public static void main(String[] args) {
        Spider.create(new Demo6_2()).thread(5)
                .addPipeline(new FilePipeline("/data/webmagic/test/"))
                .setDownloader(new SeleniumDownloader("C:/Users/Mr_Zhang/AppData/Local/Google/Chrome/Application/chromedriver.exe"))
                .addUrl("https://xuangubao.cn/")
                .runAsync();
    }
}