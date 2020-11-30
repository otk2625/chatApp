package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket {
	private Socket socket;
	
	public MyClientSocket() {
		try {
			socket = new Socket("127.0.0.1",10000);
			
			//Ű����� ���� �Է� �޾Ƽ�
			Scanner sc = new Scanner(System.in);
			//���Ͽ� ���۴ޱ�(����)
			PrintWriter pw = new PrintWriter(socket.getOutputStream()); //�̷��Ե� ����
			BufferedWriter bw = 
					new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			while(true) {
				String input = sc.nextLine(); //���ڿ� �ޱ�
				//������ ����
//				bw.write(input + "\n");
//				bw.flush(); //���ۿ� ��á���� ���� ���ϴϱ�
				pw.write(input + "\n");
				pw.flush();
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new MyClientSocket();

	}

}
