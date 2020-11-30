package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServerSocket2 {
	private ServerSocket serverSocket;
	private Socket socket;
	private Scanner sc;
	private BufferedWriter writer;
	private BufferedReader reader;

	public MyServerSocket2() {
		try {
			serverSocket = new ServerSocket(10000);
			System.out.println("Ŭ���̾�Ʈ ��û ������б�...");
			socket = serverSocket.accept(); // ���, ����(������)
			System.out.println("��û�� ������");

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			sc = new Scanner(System.in);

			String input = null;
			
			Thread wt = new Thread(new WriteThread());
			wt.start();
			
			
			while ((input = reader.readLine()) != null) {
				System.out.println("Ŭ���̾�Ʈ �޽��� : " + input);
			}

			reader.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Ű����κ��� �Է¹��� �� ���� ������
	class WriteThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				String input = sc.nextLine();
				try {
					System.out.print("������>> ");
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
		new MyServerSocket2();
	}
}