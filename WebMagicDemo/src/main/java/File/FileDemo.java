package File;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileDemo {

	public static void main(String[] args) throws IOException {
		String path = "D:/data";
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		File f1 = new File(path+"/1.txt"); 
		if (!f1.exists()) {
			f1.createNewFile();// ����Ŀ���ļ�
        }
		//���FileWriter�Ĺ������Ϊtrue����ô�ͽ�������׷��;
        //���FileWriter�Ĺ������Ϊfalse,��ô�ͽ������ݵĸ���;
		
		FileWriter writer = null;
		writer = new FileWriter(f1,true);
		writer.append("your content");
	    writer.flush();
	    
		
		/*
		FileWriter fw = new FileWriter(file,false);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("�����Ǹ��ǳ�������");
        bw.flush();  
        bw.close();
        fw.close();
		*/
		
	}

}
