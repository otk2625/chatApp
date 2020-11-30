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
			// 서버 소켓 생성 65536번 중에 0~1023(well known port)를 제외한 모든 포트
			serverSocket = new ServerSocket(10000);
			System.out.println("클라이언트 요청 대기중학교...");
			vc = new Vector<>();
			sc = new Scanner(System.in);
			socket = serverSocket.accept();
			System.out.println("요청 받음");
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
//			Thread wt = new Thread(new WriteThread(socket));
//			wt.start();
			
			//메인쓰레드는 소켓을 accept()하고 vector에 담는 역할을 함.
			while(true) {
//				System.out.println("요청 대기");
//				Socket socket = serverSocket.accept(); //클라이언트 요청을 받음.
//				System.out.println("요청 받음");
				SocketThread st = new SocketThread(socket);
				
				st.start();
				vc.add(st); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//소켓정보 + 타겟(run) + 식별자(id)
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
	
	// 키보드로부터 입력받은 후 쓰는 쓰레드
		class WriteThread implements Runnable {
			Socket socket;
			String id;
			
			public WriteThread(Socket socket) {
				this.socket = socket;
			}
			@Override
			public void run() {
				while (true) {
					System.out.print("보내기>> ");
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
