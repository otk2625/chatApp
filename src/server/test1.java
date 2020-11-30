package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import server.MyServerSocket2.WriteThread;

public class test1 {
	
	ServerSocket serverSocket;
	Vector<SocketThread> vc;
	private Scanner sc;
	private BufferedWriter writer;
	private Socket socket;
	
	public test1() {
		try {
			// ���� ���� ���� 65536�� �߿� 0~1023(well known port)�� ������ ��� ��Ʈ
			serverSocket = new ServerSocket(10000);
			System.out.println("Ŭ���̾�Ʈ ��û ������б�...");
			vc = new Vector<>();
			sc = new Scanner(System.in);
			socket = serverSocket.accept();
			System.out.println("��û ����");
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
//			Thread wt = new Thread(new WriteThread(socket));
//			wt.start();
			
			//���ξ������ ������ accept()�ϰ� vector�� ��� ������ ��.
			while(true) {
//				System.out.println("��û ���");
//				Socket socket = serverSocket.accept(); //Ŭ���̾�Ʈ ��û�� ����.
//				System.out.println("��û ����");
				SocketThread st = new SocketThread(socket);
				
				st.start();
				vc.add(st); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//�������� + Ÿ��(run) + �ĺ���(id)
	class SocketThread extends Thread {
		
		Socket socket;
		String id;
		BufferedReader reader;
		PrintWriter writer;
		
		public SocketThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
				String line = null;
				while((line = reader.readLine()) != null) {
					System.out.println("from client : "+line);
					for (SocketThread socketThread : vc) {
						socketThread.writer.println(line);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// Ű����κ��� �Է¹��� �� ���� ������
		class WriteThread implements Runnable {
			Socket socket;
			String id;
			
			public WriteThread(Socket socket) {
				this.socket = socket;
			}
			@Override
			public void run() {
				while (true) {
					System.out.print("������>> ");
					String input = sc.nextLine();
					try {
						for (SocketThread socketThread : vc) {
							socketThread.writer.println(input);
						}
						writer.write(input + "\n");
						writer.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	public static void main(String[] args) {
		new test1();
	}

}
