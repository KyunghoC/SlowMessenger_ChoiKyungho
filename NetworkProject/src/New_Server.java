import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class New_Server {
	// 클라이언트에게서 오는 데이터를 처리하고 전달 하기 위한 클래스 가장 중요한 역할
	// 로그인, 로그아웃, 친구초대, 대화방, 대화방 참여 목록 관리 역할을 맡음.
	/**
	 * @param args
	 */
	private final static int PORT_NO = 52273;
	private ServerSocket listener; // 서버 소켓 생성
	// =================================바뀌는 부분
	Map<Integer, String[]> client_room_list = new HashMap<>(); // 방관리
	Map<String, Connections> client_all = new HashMap<>(); // 전체 관리
	// =================================바뀌는 부분
	Socket socket;

	public Integer SERVER_GIVE_NUMBER = 1; // 방생성시 필요한 방번호

	New_Server() throws IOException, ClassNotFoundException, SQLException {
		InetAddress address = InetAddress.getLocalHost();
		PrintWriter server_info = new PrintWriter("server_info.dat");
		server_info.write("127.0.0.1"); // ip 주소 받아오기
		server_info.write("\n" + PORT_NO);
		server_info.close();

		listener = new ServerSocket(PORT_NO);// 초기화
		System.out.println("ON PORT : " + PORT_NO);
		
		runServer();
	}

	// 객체를 만들때 기본적인 초기화 과정
	void runServer() {
		try {
			while (true) // 무한루프로 외부에서 연결하는 소켓을 서버와 연결
			{
				socket = listener.accept(); // 외부에서 소켓이 연결할때까지 대기함
				Connections con = new Connections(socket); // 소켓을 인자로 넘김
				
				con.start(); // 생성된 객체 Thread를 시작 시킴

			} // while end
		} catch (Exception e) {
			System.out.println("IO ERROR ::::: " + e.getMessage());
			return;
		} finally {
		} // catch end
	}

	////////////////////////////////////////////////////////
	private class Connections extends Thread // Thread를 상속받음
	{
		private volatile BufferedReader br;
		private volatile PrintWriter pw;
		private String clientName; // 입장한 회원이름
		private String client_ip; // 접속한 회원의 ip
		private InetAddress client_inet; // ip얻기 위한 객체 생성

		Connections(Socket s) throws IOException {

			// client_inet=s.getInetAddress();
			// client_ip=client_inet.getHostAddress(); //ip 얻어오기
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream(), true);
		}

		public void run() {
			String line = null;

			try {

				while ((line = br.readLine()) != null) { // 소켓이 끊길때까지 연결 시킴 그리고 line 배열에 입력받은 메시지를 저장
					// ==============================================로그 입력
					String[] division = line.split("#"); // 클라이언트 측으로 들어온 메시지를 기능별로 구분하기 위함

					int divisionNum = Integer.parseInt(division[0]);
					if (divisionNum == 52273) {
						switch (division[1]) {
						case "!login": // ========================================로그인
							clientName = br.readLine();// 클라이언트의 ID를 받아옴
							System.out.println("로그인 성공");
							// log.log_out(clientName+"로그인");//로그 기록 남기기
							synchronized (client_all) {
								client_all.put(this.getClientName(), this); // 회원의 ID를 키값 그리고 해당 회원의 쓰레드를 Value로 설정
								System.out.println("접속" + client_all.get(this.clientName).getClientName());

							}
							break;
						case "!logout": // =======================================로그아웃
							System.out.println(clientName + ":" + "LoGOUT"); // 결국 클라이언트가 연결을 해체하면 메시지가 나옴
							// log.log_out(clientName+"로그아웃");
							synchronized (client_all) {
								// Logout out = new Logout();
								// out.client_logout(this.getClientName()); // 데이터베이스에 로그아웃을 갱신
								client_all.remove(this.getClientName()); // 회원 관리에서 데이터에서 삭제

							} // synchronized end
							break;
						}// switch end
					} else if (divisionNum == 52274)// 사용자가 누군가를 초대함 //============================================초대 기능
					{
						synchronized (SERVER_GIVE_NUMBER) {
							synchronized (client_all) {

								if (!client_all.containsKey(division[1])) { // 키값을 찾아서 있는 경우 True이므로 !으로 역 으로 하여 폴스일 경우
																			// True로 변경
									send(division[1] + "님의 회원은 없습니다.");
									continue;
								} // if end
							} // sync end
							Connections inviteUser = client_all.get(division[1]);

							synchronized (client_room_list) {

								// 만약에 첫번째로 만든 방이면 false가 나옴 //새로운 방을 생성했을 경우 발새
								String[] roomUser = { this.clientName, division[1] }; // 초대한 유저와 초대 받은 유저를 둘다 문자배열에 입력
								client_room_list.put(SERVER_GIVE_NUMBER, roomUser);
								String tempStr = SERVER_GIVE_NUMBER + "#!invite"; // 서버가 준 방번호와 초대했다는걸 알리기 위한 초대받은
																					// 클라이언트에게 메시지를 같이 보냄
								this.send(tempStr); // 대화 요청을 신청한 사람도 대화창떠야하기 때문에
								inviteUser.send(tempStr);
								sendClientsList(SERVER_GIVE_NUMBER);
								SERVER_GIVE_NUMBER++; // 방을하나 생성했으니 ++
							} // sync end
						} // SERVER_GIVE_NUMBER End
					} // else if end
					else if (divisionNum == 52275)// 52275#id#방번호 //이 경우는 이미 방이 생성되어 있고 2명이 있는 상태에서 다른 사람을 불러올 경우에 발생
					{
						synchronized (client_all) {

							if (!client_all.containsKey(division[1])) { // 키값을 찾아서 있는 경우 True이므로 !으로 역 으로 하여 폴스일 경우
																		// True로 변경
								send(division[1] + "님의 회원은 없습니다.");
								continue;
							} // if end
						} // sync end
						Connections inviteUser = client_all.get(division[1]);// 접속한 회원의 객체(Thread)를 얻어옴
						synchronized (client_room_list) {
							int tempDivision = Integer.parseInt(division[2]); // 해당방번호 추출
							System.out.println("초대한 방번호 = " + tempDivision);
							if (client_room_list.containsKey(tempDivision))// 해당방번호가 존재하는지 확인
							{ // 이미 생성된 방이 있는 경우에 Ture
								String[] tempUsers;

								tempUsers = client_room_list.get(tempDivision); // 해당방의 대화목록을 불러옴

								StringBuilder sb = new StringBuilder();
								for (int i = 0; i < tempUsers.length; i++) {

									if (!tempUsers[i].equals("")) {
										sb.append(tempUsers[i]);// 해당방번호의 리스트를 id#id#으로 만듬
										sb.append("#");
									}
								}
								sb.append(division[1]); // 마지막엔 초대당한 상대의 ID를 맨뒤에 붙임 id1#id2#초대당한 id
								sb.append("#");

								String[] sbTemp = sb.toString().split("#"); // 그리고 다시 #단위로 끊어서 배열로만듬
								client_room_list.put(tempDivision, sbTemp); // 해당방의 리스트를 갱신

								String tempStr = tempDivision + "#!invite"; // 서버가 준 방번호와 초대했다는걸 알리기 위한 초대받은 클라이언트에게
																			// 메시지를 같이 보냄
								inviteUser.send(tempStr); // 초대당한 사람에게 초대메시지를 보냄.

								sendClientsList(tempDivision); // 해당방의 대화목록을 갱신
							} // if end
						}
					}

					else if (division[0].equals("52273")) // 채팅방 접속
					{
						runServer();
						break;
					}

					else if (division[0].equals("52268")) // 계정 확인
					{
						int check = SQLInterface.checkAccount(division[1], division[2], division[3]);
						System.out.println("check is " + check);
						pw.println(check);
					}

					else if (division[0].equals("52272")) // 로그인
					{
						System.out.println(division[1] + division[2]);
						int check = SQLInterface.validLogin(division[1], division[2]);
						System.out.println(check);
						pw.println(check);
					}

					else if (division[0].equals("52270")) // 로그아웃
					{
						SQLInterface.client_logout(division[1]);
					}

					else if (division[0].equals("52269")) // 이건 친구목록 리스트 가져오기
					{
						String[] temp = null;
						temp = getSearch(division[1]);
						System.out.println(temp.length);

						for (int i = 0; i < temp.length; i++) {
							pw.println(temp[i]);
						}
						pw.println("-1"); // -1은 종료값으로 전달
					}

					else if (division[0].equals("52282")) {
						SQLInterface.validPWChange(division[1], division[2], division[3]);
					}

					else if (division[0].equals("59982")) {
						int check = signup(division[1],division[2],division[3],division[4],division[5],division[6],division[7]);
						pw.println(check);
					}

					else { // ================================================================================해당
							// 방에게만 메시지를 전송.
						if (division[1].equals("!exit"))// ==================================방을 나갔을경우 방번호#!exit
						{
							synchronized (client_room_list) {
								String[] updateStr = client_room_list.get(divisionNum);
								StringBuilder sb = new StringBuilder(); // String과 다르게 메모리 공간을 자동 조절함 String과 비슷하기는함
								for (int i = 0; i < updateStr.length; i++) {
									if (!updateStr[i].equals(this.clientName)) // 자신의 ID와 동일한 인덱스를 찾아서 빈칸으로 채움
									{
										sb.append(updateStr[i]);
										sb.append("#");
									} // if end
								} // for end

								client_room_list.put(divisionNum, sb.toString().split("#"));
								sendClientsList(divisionNum); // 방 대화목록 다시 업데이트
							} // sync end
						} // !exit end
						else {// =================================해당 방번호를 추출해서 해당 방번호에게만 메시지를 전송 시킴
							broadcast(line, divisionNum);
							System.out.println(line);
						} // broadcast else end
					} // else end
				}
			} catch (Exception e) {
				System.out.println("ERROR 100: " + e.getMessage()); // 비 정상적으로 프로그램을 껐을 경우.
				synchronized (client_all) {
					// Logout out = new Logout();
					// out.client_logout(this.getClientName()); // 데이터베이스에 로그아웃을 갱신
					// client_all.remove(this.getClientName()); // 회원 관리에서 데이터에서 삭제

				} // synchronized end
			} finally {

			} // finally end

		}

		public String getClientName() // 해당 객체의 회원이름 반환
		{
			return this.clientName;
		}

		private void broadcast(String msg, int roomNum) // 해당 방에만 있는 클라이언트에게만 메시지 전송
		{
			System.out.println("broadcast msg : " + msg);
			synchronized (client_room_list) {
				String[] tempUsers = client_room_list.get(roomNum);
				synchronized (client_all) {
					for (int i = 0; i < tempUsers.length; i++) {
						if (!tempUsers[i].equals("")) {
							Connections tempUser = client_all.get(tempUsers[i]);
							tempUser.send(msg);
						}
					}
				} // sync end
			} // synchronized end
		}// broadcast end

		private void send(String msg) // 단순히 소켓에 연결된 스트림에게 문자열을 보냄
		{
			pw.println(msg);
		}// send ENd

		private void sendClientsList(int inRoomNum) // 해당 방의 친구목록을 업데이트 시키기 위한 메소드
		{
			StringBuilder sb = new StringBuilder(); // String과 다르게 메모리 공간을 자동 조절함 String과 비슷하기는함

			synchronized (client_room_list) {
				String[] temp_room_list = client_room_list.get(inRoomNum);

				for (int i = 0; i < temp_room_list.length; i++) {
					if (!(temp_room_list[i] == null))
						sb.append(temp_room_list[i]);
					sb.append("#");
				}
				broadcast("!" + inRoomNum + "#" + sb.toString(), inRoomNum); // 문자열을 전송 (클라이언트 측에서는 친구 목록이 변경된 사실을 알아야됨.
				// !방번호#id1#id2#
				// !10#id1#id2#
			} // sync end

		}

	}// Connections End

	public String[] getSearch(String client_id) {
		// TODO Auto-generated method stub
		Connection conn;
		String url = "jdbc:mysql://localhost/network?serverTimezone=Asia/Seoul"; // network 스키마
		String user = "root"; // 데이터베이스 아이디
		String passwd = "12345"; // 데이터베이스 비번
		String[] frList = new String[20];
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("---------------------");
			System.out.println("친구 찾기 DB접속 성공");

			String sql = "select client_friend_list.client_id,client_friend_list.friend_id from client_friend_list  join login_check on(client_friend_list.friend_id = login_check.client_id) where client_friend_list.client_id=? and login_check.log='login';";
			// 물음표에는 동적으로 변화하는 값을 넣기 위함
			// 로그인된 친구만 리스트에서 나옴
			java.sql.PreparedStatement pstmt = conn.prepareStatement(sql); // 매 검색시 변화하는 값을 검색하기 위한 PreparedStatement
																			// 클래스
			pstmt.setString(1, client_id); // 동적으로 변화하는 값을 전달 만약 전달하는 값이 정수이면 setInt(index,정수) 이런 식으로 하면됨.
			java.sql.ResultSet result = pstmt.executeQuery();

			String data[] = new String[2];
			String line = "";

			int i = 0;
			// result.beforeFirst();
			while (result.next()) {
				data[0] = result.getString("client_id");

				data[1] = result.getString("friend_id");

				frList[i] = data[1];
				i++;

			}
			System.out.println("---------------------");

			conn.close(); // 연결 끊기
		} catch (Exception e) {
			System.out.println("DB접속 오류 " + e);
		}

		return frList; // 친구목록전송

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Server Running");
			new New_Server();
			// new New_Server().runServer(); // 서버 실행

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static Integer signup(String name, String nickname, String id, String pw, String pn, String birth, String email) {
		Connection con = null;
		Statement stmt = null;
		String url = "jdbc:mysql://localhost/network?serverTimezone=Asia/Seoul"; // network 스키마
		String user = "root"; // 데이터베이스 아이디
		String passwd = "12345"; // 데이터베이스 비번

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, passwd);
			stmt = con.createStatement();
			String insertStr = "INSERT INTO client_list (client_id, client_password, client_name, client_email, client_phone, client_nick, client_birth) VALUES ('"
					+ id + "','" + pw + "','" + name + "','" + email + "','" + pn + "','" + nickname + "','" + birth
					+ "')";
			stmt.executeUpdate(insertStr);
			insertStr = "INSERT INTO login_check VALUES('" + id + "', 'logout')";
			stmt.executeUpdate(insertStr);
			int k = 1;
			return k;

		} catch (Exception e) {
			int k = 0;
			System.out.println("회원가입 실패 > " + e.toString());
			return 0;
		}
	}
}
