package Test;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * ����������μ���ȡ
 * @author Mr_Zhang
 *
 */
public class Test0_5 implements PageProcessor {
	static int a = 0;
	public static final String URL_POST = "http://www\\.mafengwo\\.cn/i/[0-9]{6,8}\\.html";
	//public static final String URL_LIST = "https://www\\.cnblogs\\.com/justcooooode/\\d";
	
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
    	//�ж��б�ҳ�е�����ҳ�ĵ�ַ������������ʽ
        if (page.getUrl().regex(URL_POST).match()) {
            System.out.println("����ҳ ");
            //���������ҳ����ȡҳ����Ϣ
            a++;
            page.putField("title ��ȡ��"+a,
                    page.getHtml().xpath("//*[@id='_j_cover_box']/div[3]/div[2]/div/h1/text()").toString());
        
        	} else {
        	System.out.println("�б�ҳ��ʼ");
        	
        	//��λ�б��е�����ҳ��ַ 
        	page.addTargetRequests(
                    page.getHtml().xpath("//*[@class='post-list']/ul/li/div/a/@href").all());
        	
        	System.out.println("URL "+page.getHtml().xpath("//*[@class='post-list']/ul/li/div/a/@href"));
        	//System.out.println(page.getHtml().toString());
        	
        	//System.out.println("��ҳ "+page.getHtml().xpath("//*[@class='page-hotel']").toString());
        	System.out.println("�б�ҳ����");
        	
        	List<String> list = new ArrayList<String>();
        	for(int i=2;i<10;i++){
        		list.add("/yj/21536/1-0-"+i+".html");
        	}
        	//��λ��ҳ��url
        	page.addTargetRequests(list);
        }
    }

    public static void main(String[] args) {
        Spider.create(new Test0_5()).addUrl("http://www.mafengwo.cn/yj/21536")
                .addPipeline(new ConsolePipeline()).thread(5).run();
    }
}
