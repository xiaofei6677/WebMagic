package Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

public class Demo2 implements PageProcessor{

	// ץȡ��վ��������ã��������롢ץȡ��������Դ�����
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static int count =0;


    public Site getSite() {
        return site;
    }

    public void process(Page page) {
        //�ж������Ƿ����http://www.cnblogs.com/�����������ĸ-/p/7������.html��ʽ
        if(!page.getUrl().regex("http://www.cnblogs.com/[a-z 0-9 -]+/p/[0-9]{7}.html").match()){
            //������������������
            page.addTargetRequests(
                    page.getHtml().xpath("//*[@id=\"post_list\"]/div/div[@class='post_item_body']/h3/a/@href").all());
        }else{
            //��ȡҳ����Ҫ������
            System.out.println("ץȡ�����ݣ�"+
                    page.getHtml().xpath("//*[@id=\"Header1_HeaderTitle\"]/text()").get()
            );
            count ++;
        }
    }

    public static void main(String[] args) {
        long startTime, endTime;
        System.out.println("��ʼ��ȡ...");
        startTime = System.currentTimeMillis();
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://www.cnblogs.com/").thread(5).run();
        endTime = System.currentTimeMillis();
        System.out.println("��ȡ��������ʱԼ" + ((endTime - startTime) / 1000) + "�룬ץȡ��"+count+"����¼");
    }
}
