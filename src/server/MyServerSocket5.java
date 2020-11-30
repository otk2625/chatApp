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
	Vector<SocketThread> vc; // ��⿭ ť

	public MyServerSocket5() {
		try {
			serverSocket = new ServerSocket(10000);
			vc = new Vector<>();

			while (true) {
				System.out.println("��û �����...");
				Socket socket = serverSocket.accept();
				// 1. ���ο� ���� ���� socket
				// 2. ���ο� ������ ����
				// 3. ���ο� ���������� socket���� �ѱ��
				// 4. ���ο� ������ ��ü�� vc�� ���
				System.out.println("��û����...");
				SocketThread st = new SocketThread(socket);
				st.start();
				vc.add(st);
			}

		} catch (Exception e) {
			System.out.println("�������� ����");
			e.printStackTrace();
		}
	}

	class SocketThread extends Thread {
		private Socket socket; // ��������
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
				writer = new PrintWriter(socket.getOutputStream(), true); // �ڵ� flush

				String input = null; // ���� �޽���
				while ((input = reader.readLine()) != null) {
					// Routing(����� �ϱ�)
					String split[] = input.split(":");
					
					if (split[0].equals(Chat.ID)) {
						// ������ id����
						this.id = split[1];
						writer.println("ID�����Ϸ�");
						writer.flush();
					}
					
					if (id == null) {
						writer.println("ID�� �����ϼ���");
						writer.flush();
						continue;
						
					} else {
						if (split[0].equals(Chat.All)) {
							for (int i = 0; i < vc.size(); i++) {
								if (vc.get(i).id == null) {
									vc.get(i).writer.println("ID�� �����ϼ���");
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
									vc.get(i).writer.println("ID�� �����ϼ���");
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
				 * catch (NullPointerException e) { this.writer.println("ID�Է¹ٶ�"); }
				 */ catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new MyServerSocket5();
	}

}
