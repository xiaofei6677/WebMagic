package File;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
public class Test0 implements PageProcessor {
	static int a = 0;
	String path = "D:/data";
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
            
            //�����´浽�����ļ���
            String filename = page.getHtml().xpath("//*[@id='_j_cover_box']/div[3]/div[2]/div/h1/text()").toString();
            File f1 = new File(path+"/"+filename+".txt"); 
    		if (!f1.exists()) {
    			try {
					f1.createNewFile();
					
					FileWriter writer = null;
					writer = new FileWriter(f1,true);
					System.out.println("�ı� "+page.getHtml().xpath("//*[@class='view clearfix']/tidyText()").toString());
					writer.append(page.getHtml().xpath("//*[@class='view clearfix']/tidyText()").toString());
				    writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// ����Ŀ���ļ�
            } 
        	
        } else {
        	System.out.println("�б�ҳ��ʼ");
        	
        	//��λ�б��е�����ҳ��ַ 
        	page.addTargetRequests(
                    page.getHtml().xpath("//*[@class='post-list']/ul/li/div/a/@href").all());
        	
        	System.out.println("URL "+page.getHtml().xpath("//*[@class='post-list']/ul/li/div/a/@href"));
        	
        	//System.out.println("��ҳ "+page.getHtml().xpath("//*[@class='page-hotel']").toString());
        	System.out.println("�б�ҳ����");
        	
        	List<String> list = new ArrayList<String>();
        	for(int i=2;i<10;i++){
        		list.add("/yj/21536/1-0-"+i+".html");
        	}
        	//����ҳ�ĵ�ַ�������ȡҳ��
        	page.addTargetRequests(list);
        }
    }

    public static void main(String[] args) {
        Spider.create(new Test0()).addUrl("http://www.mafengwo.cn/yj/21536")
                .addPipeline(new ConsolePipeline()).thread(5).run();
    }
}
