package File;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
 * ֮ǰ����֮�ҵ��μ�����ҳ��û�п����Ƿ�����������
 * ����������ȡ��ҳ��ֻ�ǿ�ͷһ���֣��ŷ��ֻ���һ����չȫ�ĵİ�ť���㿪�����ť֮�����ȫ��
 * ���ǵ㿪�����ť��������������һ��������֣�Ȼ�����˰���������һ��json�������ҵ��ˣ�����ƴ������
 * �����һ��Ѱ������ҳ������Ĺ��̣����ջ��ǽ�����������ҳ�ĵ�ַ�ҵ���
 * 
 * ���й��̿��ܻᱨ����Ϊ�е������ǹ�棬���ĵ�ַ��ʽ������ҳ��ͬ������ûӰ��
 */
public class Test2 implements PageProcessor {
	static int a = 0;
	String path="D:/data/qiche";
	//����ҳ��ĵ�ַ https://you.autohome.com.cn/details/111947/fbd5670102140e5eb30c8e5cd7ce15ef?handleType=1
	public static final String URL_POST = "https://you\\.autohome\\.com\\.cn/details/[0-9]{5,10}/\\w+\\?handleType=1";
	
	// ���������ĵ�ַ https://you.autohome.com.cn/details/getmenuinfo?tripId=111947
	public static final String URL_POST1 = "https://you\\.autohome\\.com\\.cn/details/getmenuinfo\\?tripId=[0-9]{5,10}";
	
	// ����б�ҳ�ĵ�ַ
	// https://you.autohome.com.cn/summary/getsearchresultlist?ps=20&pg=3&type=3&tagCode=&tagName=&sortType=3
	// https://you.autohome.com.cn/summary/getsearchresultlist?ps=20&pg=4&type=3&tagCode=&tagName=&sortType=3
	public static final String URL_LIST = "https://you\\.autohome\\.com\\.cn/summary/getsearchresultlist\\?ps=20&pg=\\d{1,4}&type=3&tagCode=&tagName=&sortType=3";
	
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
    	//�жϴ�����ַ�Ƿ�������ҳ�ĵ�ַ
        if (page.getUrl().regex(URL_POST).match()) {
			System.out.println("��������ҳ ");
			a++;
			//���������ҳ�� /html/body/div[7]/div[1]/div/div/div/div/span[2]
			page.putField("titles ��ȡ�� "+a,  // /html/body/div[7]/div[1]/div/div/div/div/span[2]
			        page.getHtml().xpath("/html/body/div[7]/div[1]/div/div/div/div/span[2]/text()").toString());
			String title = page.getHtml().xpath("/html/body/div[7]/div[1]/div/div/div/div/span[2]/text()").toString();
			if(title == null){
				title = page.getHtml().xpath("/html/body/div[7]/div[1]/div/div/div/div/span/text()").toString();
				if(title == null) {
					title = ""+a;
				}
			}
			String content = page.getHtml().xpath("//*[@class='main-container-left fl']//tidyText()").toString();
			
			//�����´浽�����ļ���
			String filename = title;
			
			File f1 = new File(path+"/"+filename+".txt"); 
			if (!f1.exists()) {
				try {
					f1.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
					FileWriter fw = new FileWriter(f1,false);
				    BufferedWriter bw = new BufferedWriter(fw);
				    bw.write(content);
				    bw.flush();  
				    bw.close();
				    fw.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
        }
        else if(page.getUrl().regex(URL_POST1).match()){
        	//����ǻ�ȡ����ҳ����������ֵ
        	List<String> urls = new JsonPathSelector("$.result[*].id").selectList(page.getRawText());
        	
        	String sui = urls.get(0);
        	System.out.println("ÿ��ҳ��������� "+sui);
        	//ƴ������������ҳ����
        	String jsonPath = page.getUrl().toString();
        	String shu = jsonPath.split("=")[1]; 
        	// https://you.autohome.com.cn/details/112358/267e8d9437a9dbdcc1274e4c74f07827?handleType=1
        	String url = "https://you.autohome.com.cn/details/"+shu+"/"+sui+"?handleType=1";
        	
        	//������ҳ�������ȡ����
        	page.addTargetRequest(url);
        	
        }
        else if(page.getUrl().regex(URL_LIST).match()){
        	
        	//����Ƿ�ҳ�����ӣ��ҵ���������ҳ��id,��ƴ��Ѱ����������ҳ�����ֵ�ĵ�ַ
        	
        	List<String> list = new ArrayList<String>();
        	List<String> list2 = new ArrayList<String>();
        	
        	//�ο����ͣ�http://webmagic.io/docs/zh/posts/chx-cases/js-render-page.html
        	List<String> urls = new JsonPathSelector("$.result.hitlist[*].url").selectList(page.getRawText());
        	System.out.println("��ַ "+urls.toString());
        	
        	if (CollectionUtils.isNotEmpty(urls)) {
        		
                for (String url : urls) {
                	System.out.println(url);
                	String tripId = url.split("/")[2];
                	System.out.println("�μ�id "+tripId);
                	//ƴ�ӳɻ�ȡ��������ҳ�����������
                	// https://you.autohome.com.cn/details/getmenuinfo?tripId=112358 
                	list2.add("https://you.autohome.com.cn/details/getmenuinfo?tripId="+tripId);
                }
            }
        	//����ȡ����ҳ����������Ӽ��ص�����ȡҳ��
            page.addTargetRequests(list2);
        }
        else {
        	System.out.println("�б�ҳ");
        	//��λ�б��е�����ҳ��ַ /html/body/div[3]/div[1]/div[2]/ul/li/div[1]/h4/a/@href
        	/*
        	page.addTargetRequests(
                    page.getHtml().xpath("//*[@id='app']/div[2]/div[2]/div[2]/div[2]/div/div/a/@href").all());
            */
        	
        	// ƴ���б�ҳ�����ӵ�ַ
        	//Ŀǰһ����2800ҳ��һֱѭ����2800
        	List<String> list3 = new ArrayList<String>();
        	for(int i=0;i<5;i++){
        		list3.add("https://you.autohome.com.cn/summary/getsearchresultlist?ps=20&pg="+i+"&type=3&tagCode=&tagName=&sortType=3"); 
        	}
        	//���б�ҳ��ַ���ص�����ȡ����
            page.addTargetRequests(list3);
            
        }
    }

    public static void main(String[] args) {
        Spider.create(new Test2()).addUrl("https://you.autohome.com.cn/index/searchkeyword")
                .addPipeline(new ConsolePipeline())
                .thread(5)
                .run();
    }
}
