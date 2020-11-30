package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket2 {
	//heap������ �����ϱ� ���� ����
	private Socket socket;
	private Scanner sc;
	private BufferedWriter write;
	private PrintWriter print;
	private BufferedReader reader;
	
	public MyClientSocket2() {
		try {
			socket = new Socket("113.198.238.56",10000);
			
			//Ű����� ���� �Է� �޾Ƽ�
			sc = new Scanner(System.in);
			//���Ͽ� ���۴ޱ�(����)
			print = new PrintWriter(socket.getOutputStream()); //�̷��Ե� ����
			write = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					String input = null;
					try {
						while((input = reader.readLine()) != null) {
							System.out.println("�����κ��� �� �޽��� : " + input);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			
			//���ξ������ Ű����κ��� �Է��� ���� �� Ŭ���̾�Ʈ ���Ͽ� ����
			while(true) {
				System.out.print("������>> ");
				String input = sc.nextLine(); //���ڿ� �ޱ�
				//������ ����
//				write.write(input + "\n");
//				write.flush(); //���ۿ� ��á���� ���� ���ϴϱ�
				print.write(input + "\n");
				print.flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new MyClientSocket2();
	}
} 
