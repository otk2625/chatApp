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
	//heap변수에 저장하기 위한 정의
	private Socket socket;
	private Scanner sc;
	private BufferedWriter write;
	private PrintWriter print;
	private BufferedReader reader;
	
	public MyClientSocket2() {
		try {
			socket = new Socket("113.198.238.56",10000);
			
			//키보드로 부터 입력 받아서
			sc = new Scanner(System.in);
			//소켓에 버퍼달기(쓰기)
			print = new PrintWriter(socket.getOutputStream()); //이렇게도 가능
			write = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					String input = null;
					try {
						while((input = reader.readLine()) != null) {
							System.out.println("서버로부터 온 메시지 : " + input);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			
			//메인쓰레드는 키보드로부터 입력을 받은 뒤 클라이언트 소켓에 전송
			while(true) {
				System.out.print("보내기>> ");
				String input = sc.nextLine(); //문자열 받기
				//서버에 전송
//				write.write(input + "\n");
//				write.flush(); //버퍼에 꽉찼는지 알지 못하니까
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
