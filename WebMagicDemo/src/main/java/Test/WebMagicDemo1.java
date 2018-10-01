package Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

/**
 * ��������Demo1
 * @author Mr_Zhang
 *
 */
public class WebMagicDemo1 implements PageProcessor{
	 // ����һ��ץȡ��վ��������ã��������롢ץȡ��������Դ�����
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	
	public static void main(String[] args) {
		//System.setProperty("javax.net.debug", "all");
		
		Spider.create(new GithubRepoPageProcessor())
        //��"https://github.com/code4craft"��ʼץ
        .addUrl("https://github.com/code4craft")
        //����5���߳�ץȡ
        .thread(5)
        //��������
        .run();
    }


	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		// TODO Auto-generated method stub
		 // ���ֶ���������γ�ȡҳ����Ϣ������������
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
 
        // ����������ҳ�淢�ֺ�����url��ַ��ץȡ
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
       
	}

}
