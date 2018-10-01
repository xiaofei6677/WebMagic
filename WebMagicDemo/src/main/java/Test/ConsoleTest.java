package Test;

import java.util.Map;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class ConsoleTest implements Pipeline{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void process(ResultItems resultItems, Task task) {
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        //�������н�������������̨�����������е�"author"��"name"��"readme"����һ��key���������Ƕ�Ӧ��value
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    }

}
