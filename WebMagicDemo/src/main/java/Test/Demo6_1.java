package Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Demo6_1 {

	public static void main(String[] args) {
        // ��һ���� ����chromedriver��ַ��һ��Ҫָ��������λ�á�
        System.setProperty("webdriver.chrome.driver",
        		"C:/Users/Mr_Zhang/AppData/Local/Google/Chrome/Application/chromedriver.exe");
        // �ڶ�������ʼ������
        WebDriver driver = new ChromeDriver();
        // ����������ȡĿ����ҳ
        driver.get("https://www.mafengwo.cn/yj/21536/");
        // ���Ĳ������������¾Ϳ��Խ��н��ˡ�ʹ��webMagic��jsoup�Ƚ��б�Ҫ�Ľ�����
        System.out.println("Page title is: " + driver.getTitle());
        //System.out.println("Page title is: " + driver.getPageSource());
        System.out.println("CurrentUrl : " + driver.getCurrentUrl());
    }
}
