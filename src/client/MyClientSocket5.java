package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyClientSocket5 {

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private Scanner sc;
	
	public MyClientSocket5() {
		try {
			socket = new Socket("localhost",10000);
			
			Thread st = new SocketThread(); //서버로 부터 받음
			st.start();
			
			sc = new Scanner(System.in);
			writer = new PrintWriter(socket.getOutputStream(),true);//자동 flush
			
			while(true) { //서버로 입력
				// ALL:안녕, MSG:ssar1:안녕
				String keyBorad = sc.nextLine();
				writer.println(keyBorad);
				writer.flush();
			}
			
		}  catch (Exception e) {
			System.out.println("서버 연결 실패");
			e.printStackTrace();
		}
	}
	
	class SocketThread extends Thread {
		@Override
		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String input = null;
				
				while((input = reader.readLine()) != null) {
					System.out.println(input);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new MyClientSocket5();
	}

}
