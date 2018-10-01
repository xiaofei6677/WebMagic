package Test;

import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

/**
 * Э�����
 * @author Mr_Zhang
 *
 */
public class Demo4 implements PageProcessor{

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

   
    public void process(Page page) {
	    page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
	    page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+)").all());
	    //������author�������������ձ��浽ResultItems��
	    page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
	    page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
	    if (page.getResultItems().get("name")==null){
	        //����skip֮�����ҳ��Ľ�����ᱻPipeline����
	        page.setSkip(true);
	    }
	    page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();   
    
    }
    
}
