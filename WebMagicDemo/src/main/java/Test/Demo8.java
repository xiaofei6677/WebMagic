package Test;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class Demo8 implements PageProcessor{
    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Windows NT 10.0; ��e/59.0.3071.109 Safari/537.36")
            .setRetryTimes(3)
            .setTimeOut(30000)
            .setSleepTime(1800)
            .setCycleRetryTimes(3)
            .setUseGzip(true)
            .addHeader("Host","search.bilibili.com")
            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
            .addHeader("Accept-Encoding","gzip, deflate, br");
    private static final String man = "Pierre Bensusan";
    private static final String defineUrl = "https://search.bilibili.com/api/search?search_type=video&keyword="+man+"&from_source=banner_search&order=totalrank&duration=0&tids=0";
    //"����ʥ��","�����","Ѻβ��̫��","��������","�ɾ��ӹ�","С��ԭ��","֣�ɺ�","depapepe","Pierre Bensusan","TOMMY EMMANUEL","Daniel Padim","andy mckee"};
    public void process(Page page) {
        int numPage = Integer.parseInt(page.getJson().jsonPath("$.numPages").get());
        for (int i=0;i<numPage;i++) {
            page.addTargetRequest(defineUrl+"&page="+(i+2));
        }
        //up��
        List<String> ups = page.getJson().jsonPath("$..author").all();
        page.putField("author", ups);
        //����
        List<String> titles = page.getJson().jsonPath("$..title").all();
        page.putField("title", titles);
        //����
        List<String> srcLinks = page.getJson().jsonPath("$..arcurl").all();
        page.putField("srcLinks", srcLinks);
        //ʱ��
        List<String> durations = page.getJson().jsonPath("$..duration").all();
        page.putField("duration", durations);
        //�ۿ���
        List<String> watchNums = page.getJson().jsonPath("$..play").all();
        page.putField("watchNum", watchNums);
        //�ϴ�ʱ��  2017-08-09  1502222633
        // 2016-09-28  1475053142
        //2018-05-18 1526650568
        List<String> uploadTimes = page.getJson().jsonPath("$..pubdate").all();
        page.putField("uploadTime", uploadTimes);
        //review
        List<String> reviews = page.getJson().jsonPath("$..review").all();
        //video_review
        List<String> video_reviews = page.getJson().jsonPath("$..video_review").all();
        //favorite
        List<String> favorites = page.getJson().jsonPath("$..favorites").all();
        //��Ƶ˵��
        List<String> description = page.getJson().jsonPath("$..description").all();
        page.putField("description", description);
        /*
        for (int i=0;i<ups.size();i++) {
            BiliVideosDao.save(man,ups.get(i),SimpleUtil.cutHtml(titles.get(i)),srcLinks.get(i),durations.get(i),watchNums.get(i)
            , SimpleUtil.convert2String(1000*Long.parseLong(uploadTimes.get(i))),
                    reviews.get(i),video_reviews.get(i),favorites.get(i),description.get(i));
        }
        */
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new Demo8())
                //.addUrl(urls)
                .addUrl(defineUrl)
                .addPipeline(new ConsolePipeline())
                .thread(5)
                .run();
    }

}
