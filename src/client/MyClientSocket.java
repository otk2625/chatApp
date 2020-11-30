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
			
			//키보드로 부터 입력 받아서
			Scanner sc = new Scanner(System.in);
			//소켓에 버퍼달기(쓰기)
			PrintWriter pw = new PrintWriter(socket.getOutputStream()); //이렇게도 가능
			BufferedWriter bw = 
					new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			while(true) {
				String input = sc.nextLine(); //문자열 받기
				//서버에 전송
//				bw.write(input + "\n");
//				bw.flush(); //버퍼에 꽉찼는지 알지 못하니까
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
