package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import protocol.Chat;

public class MyServerSocket5 {
	private ServerSocket serverSocket;
	Vector<SocketThread> vc; // 대기열 큐

	public MyServerSocket5() {
		try {
			serverSocket = new ServerSocket(10000);
			vc = new Vector<>();

			while (true) {
				System.out.println("요청 대기중...");
				Socket socket = serverSocket.accept();
				// 1. 새로운 소켓 생성 socket
				// 2. 새로운 스레드 생성
				// 3. 새로운 스레드한테 socket변수 넘기기
				// 4. 새로운 스레드 자체를 vc에 담기
				System.out.println("요청받음...");
				SocketThread st = new SocketThread(socket);
				st.start();
				vc.add(st);
			}

		} catch (Exception e) {
			System.out.println("서버연결 오류");
			e.printStackTrace();
		}
	}

	class SocketThread extends Thread {
		private Socket socket; // 콤포지션
		private String id;
		private PrintWriter writer;
		private BufferedReader reader;

		public SocketThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true); // 자동 flush

				String input = null; // 받은 메시지
				while ((input = reader.readLine()) != null) {
					// Routing(라우팅 하기)
					String split[] = input.split(":");
					
					if (split[0].equals(Chat.ID)) {
						// 변수에 id저장
						this.id = split[1];
						writer.println("ID생성완료");
						writer.flush();
					}
					
					if (id == null) {
						writer.println("ID를 생성하세요");
						writer.flush();
						continue;
						
					} else {
						if (split[0].equals(Chat.All)) {
							for (int i = 0; i < vc.size(); i++) {
								if (vc.get(i).id == null) {
									vc.get(i).writer.println("ID를 생성하세요");
									writer.flush();
									continue;
								}
								if (vc.get(i) != this) {
									vc.get(i).writer.println(id + "-->" + split[1]);
									writer.flush();
								} 
							}
						} else if (split[0].equals(Chat.MSG)) {
							for (int i = 0; i < vc.size(); i++) {
								if (vc.get(i).id == null) {
									vc.get(i).writer.println("ID를 생성하세요");
									writer.flush();
									continue;
								}
								if (vc.get(i).id.equals(split[1])) {
									vc.get(i).writer.println(id + "-->" + split[2]);
									writer.flush();
								} 
							}
						} 
					}
				}
			} /*
				 * catch (NullPointerException e) { this.writer.println("ID입력바람"); }
				 */ catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new MyServerSocket5();
	}

}
