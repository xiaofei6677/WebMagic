package Test;

/**
 * ����֮��  �Լ�����̳
 * https://club.autohome.com.cn/bbs/forum-o-200042-1.html
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class Test3 implements PageProcessor {
	
	public static final String URL_POST = "https://club\\.autohome\\.com\\.cn/bbs/thread/\\w{16}/[0-9]{8}-1\\.html";
	//public static final String URL_LIST = "https://www\\.cnblogs\\.com/justcooooode/\\d";
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public Site getSite() {
        return site;
    }
    //����ҳ��ַ 
    // https://club.autohome.com.cn/bbs/thread/e88ef7561e261e5f/76323604-1.html
    // https://club.autohome.com.cn/bbs/thread/f72f6dce01f0cbd5/64877752-1.html
    // https://club.autohome.com.cn/bbs/thread/5553a82dfea0455b/76265346-1.html
    // https://club.autohome.com.cn/bbs/thread/99fe5e656ba210f6/76334790-1.html
    public void process(Page page) {
    						//�б�ҳ�е�����ҳ�ĵ�ַ������������ʽ
        if (page.getUrl().regex(URL_POST).match()) {
            System.out.println("����ҳ ");
            //���������ҳ�� // //*[@id="F0"]/div[2]/div[2]/h1/div  
            //                  //*[@id="F0"]/div[2]/div[2]/h1/div
            //					//*[@id="F0"]/div[2]/div[2]/h1/div
            page.putField("title ",page.getHtml().xpath("//*[@id='F0']/div[2]/div[2]/h1/div/text()").toString());
        
        	} else {
        	System.out.println("�б�ҳ");
        	//��λ�б��е�����ҳ��ַ /html/body/div[3]/div[1]/div[2]/ul/li/div[1]/h4/a/@href
        	// //*[@id="subcontent"]/dl[3]/dt/a[1]
        	// //*[@id="subcontent"]/dl[2]/dt/a[1]
        	// //*[@id="subcontent"]/dl[4]/dt/a[1]
        	// //*[@id="subcontent"]/dl[6]/dt/a[1]
        	page.addTargetRequests(
                    page.getHtml().xpath("//*[@id='subcontent']/dl/dt/a[1]/@href").all());
            // ��λ��ҳ��url
        	/*
        	page.addTargetRequests(
                    page.getHtml().xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div[11]/div[1]/a/@href").all());
            */
        }
    }

    public static void main(String[] args) {
    	System.out.println("������");
        Spider.create(new Test3()).addUrl("https://club.autohome.com.cn/bbs/forum-o-200042-1.html")
                .addPipeline(new ConsolePipeline()).run();
    }
}
