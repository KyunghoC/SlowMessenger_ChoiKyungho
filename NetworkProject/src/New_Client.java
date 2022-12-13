import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollBar;

public class New_Client {
//로그인 Tab과 서버 연결을 하기위한 클라이언트 프로그램의 중추적인 클래스
	private static String Client_Name; // 사용자 ID
	// ===============================================================서버 설정
	//final static String SERVER_ADDR = "127.0.0.1"; // 자바 채팅 서버 주소
	public static final String DBIP = "localhost"; // DB 접속 아이피 설정
	//final static int SERVER_PORT = 52273; // 자바 채팅서버 포트번호
	// ===============================================================
	public static Socket socket; // 서버와 연결하기 위한 포트
	public static PrintWriter pw; // 서버에게 데이터를 쓰기 위한 확장 스트림
	public static volatile BufferedReader br; // 서버로 부터 데이터를 받기 위한 스트림
	public static volatile InputStreamReader isr;
	private static Map<Integer, client_2> chat_room = new HashMap<>(); // 다수의 방을 효율적으로 관리하기 위한 콜렉션
	User us = null;

	/**
	 * @param args
	 * @throws IOException
	 */
	New_Client(User _us) // 기본적으로 메인 로그인 GUI를 생성
	{
		us = _us;
		new LoginFrame(us);
		try {
			String server_ip = null;
			String server_port = null;
			File file = new File("server_info.dat"); // 파일 객체 생성

			if (file.exists() == true) { // server_info.dat이 있다면
				FileReader filereader = new FileReader(file); // 입력 스트림 생성
				BufferedReader buffReader = new BufferedReader(filereader); // 입력 버퍼 생성
				String line = "";
				ArrayList<String> ServerInfo = new ArrayList<>(); // array 생성
				while ((line = buffReader.readLine()) != null) {
					ServerInfo.add(line); // readLine()을 통해 array에 한줄씩 저장
				}
				server_ip = ServerInfo.get(0); // 첫째 줄
				server_port = ServerInfo.get(1); // 둘째 줄

				buffReader.close();
			}

			socket = new Socket(server_ip, Integer.parseInt(server_port)); // 소켓 정보

			isr = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(isr); // 서버에서 받아오는 스트림
			pw = new PrintWriter(socket.getOutputStream(), true); // 서버로 보내기 위함 스트림

		} catch (FileNotFoundException e) {
		} catch (Exception e) {
		}

	}

	static void runClient() {

		try {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// =======================================로그인 차에서 구현
					pw.println("52273#!login"); // 로그인했을 경우
					pw.println(Client_Name); // 로그인 후 ID를 입력해야됨

					while (true) // 무한루프로 외부에서 연결하는 소켓을 서버와 연결
					{

						String line = null; // 문자열 초기화

						String[] temp = { "", "", "" }; // 문자배열 초기화
						try {
							line = br.readLine(); // 서버에서 메시지가 들어옴
							System.out.println("서버에서 들어온 메시지" + line);
							break;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println("서버와 클라이언트간의 스트림 오류");
							e.printStackTrace();
						}

						try {
							temp = line.split("#"); // 서버에서 들어온 메시지를 분석하기 위해 쪼갬.
						} catch (Exception e) {
							System.out.println("Split 오류");
							e.getMessage();
						}

						if (temp[1].equals("!invite")) // 다른 상대방으로부터 초대를 받았을 경우
						{

							System.out.println(line);
							client_2 df2 = new client_2();
							int tempRoomNumber = Integer.parseInt(temp[0]); // 초대받은 경우 방번호와 같이 메시지가 옵니다. 10#!invite 형식으로
							df2.setRoomNumber(tempRoomNumber); // 방번호를 해당 대화창 GUI의 방번호 변수에 저장합니다.
							chat_room.put(tempRoomNumber, df2); // 방의 GUI를 관리하기 위해 방번호와 GUI객체를 저장합니다.

						} else if (temp[0].charAt(0) == '!')// x번방의 대화목록 리스트를 서버에서 받아옴 !방번호#id1#id2#id3
						{

							System.out.println("대화목록이 왔어요~~");
							String[] users;

							users = line.substring(1).split("#");// 느낌표를 제거하고 공백문자열로 문자열들을 구분함 !아이디1 아이디2 아이디3
							String[] copyUsers = new String[10]; // 대화목록
							System.arraycopy(users, 1, copyUsers, 0, users.length - 1); // 복사할 배열,복사 시작 인덱스, 대상바열, 복사할
																						// 갯수
							// 이렇게 하는 이유는 서버에서 친구목록을 받아올때 방번호까지 같이 묶어서 오므로 인덱스 0의 자리를 빼야되기 ��문이다.
							int roomNum = Integer.parseInt(temp[0].substring(1));// 해당방번호를 받아옴

							client_2 temp1 = chat_room.get(roomNum);// 방번호에 맞는 GUI를 불러옴
							temp1.setChatList(copyUsers); // 해당 방의 GUI에 친구목록 설정

						} // 친구목록 불러오기 끝
						else {// 서버에서 받아온 다른사람들이 x번방에 보낸 메시지
							System.out.println(line);
							int roomNum = Integer.parseInt(temp[0]); // 방번호 따옴
							client_2 temp1 = chat_room.get(roomNum); // 해당 방번호의 GUI 객체 가져옴
							temp1.out_ar.append(temp[1] + "\n"); // 해당 방번호의 채팅방의 GUI에게 데이터를 나열
							JScrollBar scrollBar = temp1.qScroller.getVerticalScrollBar(); // 스크롤을 관리하기 위한 스크롤 생성
							scrollBar.setValue(scrollBar.getMaximum()); // 최대 위치로 스크롤바를 옮김
						}
						System.out.println("!!!!!!!!!!!!!!!!!!!!!");

					} // while end
				}
			}).start();

		} catch (NullPointerException e) {
			System.out.println("IO ERROR ::::: " + e.getMessage());
			return;
		} // catch end
	}

	static public Map getMap() {
		return chat_room; // 방을 나갔을때 GUI에서 접근할 수 있게 넘겨줌
	}

	static public String getClientName() {
		return Client_Name; // 로그인한 ID 뿌려줌
	}

	static public void setClientName(String tempName) {
		Client_Name = tempName;

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		User us = new User();
		new New_Client(us);

		/*
		 * pw.println("52273#!logout"); //완전히 프로그램을 껏을경우 pw.println("방번호#보낼메시지");
		 * //일반적으로 문자를 해당 방번호에게 보내고 싶을경우 pw.println("해당방번호#!exit"); //해당 방을 나갈려고 할 경우
		 * pw.println("해당방번호#!invite"); //로그인 후 처음으로 대화 요청을 받았을 경우
		 * pw.println("52274#Ting_Talk2"); //처음 친구에게 말을 걸었을 경우
		 * pw.println("52275#Ting_Talk2#1"); //이미 두명있는 방에서 다른 사람을 초대할경우
		 * 
		 */

	}

}
