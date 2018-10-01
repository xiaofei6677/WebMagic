package Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ������ʽ��ϰ
 * @author Mr_Zhang
 *
 */

public class Regular {

	public static void main(String[] args) {
		
		//ƥ��
		check();
		checkTel();
		splitDemo();
		splitDemo1();
		replaceAllDemo();
		replaceAllDemo1();
		getDemo();
	}
	
	/*
	 * ƥ�䣺�ù���ƥ�������ַ�����
	 * ֻҪ��һ�������Ϲ��򣬾�ƥ�����������false
	 */
	public static void check(){
		String qq = "123456a789";
		String reg = "[1-9]\\d{4,14}";//�Ƿ�ȫ��������
		boolean b = qq.matches(reg);
		System.out.println(b);
	}
	
	public static void checkTel()
    {
        String tel = "16900001111";
        String telReg = "1[358]\\d{9}";
        System.out.println(tel.matches(telReg));
    }
	
	/**
	 * 
	 */
	public static void splitDemo(){
		String str = "avg   bb   geig   glsd   abc";
        String reg = " +";//���ն���ո��������и�
        String[] arr = str.split(reg);  
        System.out.println(arr.length);
        for(String s : arr)
        {
            System.out.println(s);
        }
	}
	public static void splitDemo1()
    {

	    String str = "erkktyqqquizzzzzo";
	    String reg ="(.)\\1+";//���յ����������и�
	        //���Խ������װ��һ���顣��()��ɡ���ĳ��ֶ��б�š�
	        //��1��ʼ�� ��Ҫʹ�����е������ͨ��  \n(n������ı��)����ʽ����ȡ��
	    String[] arr = str.split(reg);  
	    System.out.println(arr.length);
	    for(String s : arr)
	    {
	        System.out.println(s);
	    }
    }
	/**
	 * �滻:���ַ����з��Ϲ�����ַ��滻��ָ���ַ�
	 */
	public static void replaceAllDemo()
    {
    
        String str = "wer1389980000ty1234564uiod234345675f";//���ַ����е������滻��#��
 
        str = str.replaceAll("\\d{5,}","#");

        System.out.println(str);
    }
	public static void replaceAllDemo1()
    {
        String str = "erkktyqqquizzzzzo";//�������滻��$.  //���ص����ַ��滻�ɵ�����ĸ��zzzz->z
        str = str.replaceAll("(.)\\1+","$1");
        
        System.out.println(str);
    }
	
	public static void getDemo(){
		String str = "yin yu shi wo zui cai de yu yan";
		System.out.println(str);
		String reg = "\\b[a-z]{3}\\b";//ƥ��ֻ��������ĸ�ĵ���
		//�������װ�ɶ���
		Pattern p = Pattern.compile(reg);
		//����������Ҫ���õ��ַ������������ȡƥ��������
		Matcher m  = p.matcher(str); 
		while(m.find())
        {
            System.out.println(m.group());
            System.out.println(m.start()+"...."+m.end());
                // start()  �ַ��Ŀ�ʼ�±꣨������
                //end()  �ַ��Ľ����±꣨��������
        }
	}
}
