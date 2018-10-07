package Test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
/**
 * ����֮�� �μ���ȡ
 * @author Mr_Zhang
 *
 */
public class Test2_1 implements PageProcessor {
	 static int a = 0;
	public static final String URL_POST = "https://you\\.autohome\\.com\\.cn/details/[0-9]{6,10}";
	public static final String URL_LIST = "https://you\\.autohome\\.com\\.cn/summary/getsearchresultlist\\?ps=20&pg=\\d{1,4}&type=3&tagCode=&tagName=&sortType=3";
	// https://you.autohome.com.cn/summary/getsearchresultlist?ps=20&pg=0&type=3&tagCode=&tagName=&sortType=3
	// https://you.autohome.com.cn/summary/getsearchresultlist?ps=20&pg=1&type=3&tagCode=&tagName=&sortType=3
	// https://you.autohome.com.cn/summary/getsearchresultlist?ps=20&pg=2&type=3&tagCode=&tagName=&sortType=3
	// https://you.autohome.com.cn/summary/getsearchresultlist?ps=20&pg=3&type=3&tagCode=&tagName=&sortType=3
	// https://you.autohome.com.cn/summary/getsearchresultlist?ps=20&pg=4&type=3&tagCode=&tagName=&sortType=3
	private Site site = Site
			.me()
			.setRetryTimes(3)
			.setSleepTime(3000)
			.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
			;

    public Site getSite() {
        return site;
    }

    public void process(Page page) {
    						//�б�ҳ�е�����ҳ�ĵ�ַ������������ʽ
        if (page.getUrl().regex(URL_POST).match()) {
            System.out.println("����ҳ ");
            a++;
            //���������ҳ���������Ӽ��ص������ص�URL��
            page.putField("titles ��ȡ�� "+a,  // /html/body/div[7]/div[1]/div/div/div/div/span[2]
                    page.getHtml().xpath("/html/body/div[7]/div[1]/div/div/div/div/span[2]/text()").toString());
        
        }
        else {
        	System.out.println("�б�ҳ");
        	//��λ�б��е�����ҳ��ַ /html/body/div[3]/div[1]/div[2]/ul/li/div[1]/h4/a/@href
        	
        	System.out.println( page.getHtml().xpath("/html/body"));
        	page.addTargetRequests(
                    page.getHtml().xpath("//*[@id='app']/div[2]/div[2]/div[2]/div[2]/div/div/a/@href").all());
        }
    }

    public static void main(String[] args) {
        Spider.create(new Test2_1()).addUrl("https://you.autohome.com.cn/index/searchkeyword")
                .addPipeline(new ConsolePipeline()).thread(5).run();
    }
}
