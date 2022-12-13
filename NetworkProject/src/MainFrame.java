
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class MainFrame extends JFrame implements ListSelectionListener {
	ImageIcon icon; // 백그라운드 이미지
	ImageIcon icon2; // 홈패널 이미지
	ImageIcon icon3; // 찾기패널 이미지
	JPanel Main;
	JPanel ViewCard, WeatherPanel;

	JButton HomeButton, SearchButton, LogOutBtn, FriendSearchBtn, UserBtn;

	JLabel UserLabel, UserStateLabel;

	JScrollPane inscrollPane = null; // online 친구창
	JScrollPane outscrollPane = new JScrollPane(); // offline 친구창
	JScrollPane fscrollPane = new JScrollPane(); // 친구 찾기 창

	WeatherPanel weather;

	CardLayout vc;

	User us = null;

	static JList FriendList; // 친구목록
	private DefaultListModel model;

	MainFrame(User _us) {

		us = _us;

		icon = new ImageIcon("src/Image/MainFrame.png");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(511, 773, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		Main = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		Main.setLayout(null);
		setSize(520, 800);
		getContentPane().add(Main);

		try {
			weather = new WeatherPanel("날씨");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] temp = new String[5];
		temp = weather.get();

		// 날씨 공공데이터 추가
		WeatherPanel = new JPanel();
		WeatherPanel.setBounds(70, 602, 420, 135);
		WeatherPanel.setBackground(new Color(255, 255, 0));

		WeatherPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel(temp[0]);
		lblNewLabel.setBounds(12, 10, 324, 15);
		WeatherPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(temp[1]);
		lblNewLabel_1.setBounds(12, 31, 324, 15);
		WeatherPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel(temp[2]);
		lblNewLabel_2.setBounds(12, 56, 324, 15);
		WeatherPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel(temp[3]);
		lblNewLabel_3.setBounds(12, 79, 324, 15);
		WeatherPanel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel(temp[4]);
		lblNewLabel_4.setBounds(12, 104, 324, 15);
		WeatherPanel.add(lblNewLabel_4);

		Main.add(WeatherPanel);

		// 홈버튼
		HomeButton = new JButton();
		HomeButton.setBounds(12, 18, 33, 29);
		HomeButton.setContentAreaFilled(false);
		// HomeButton.setBorderPainted(false);
		HomeButton.setFocusPainted(false);
		Main.add(HomeButton);

		// 검색 버튼 -> 게시글 검색해서 찾기
		SearchButton = new JButton();
		SearchButton.setBounds(15, 66, 30, 24);
		SearchButton.setContentAreaFilled(false);
		// SearchButton.setBorderPainted(false);
		SearchButton.setFocusPainted(false);
		Main.add(SearchButton);
		final JPopupMenu FFindpopUp = new JPopupMenu();
		model = new DefaultListModel();
		FriendList = new JList(model);
		FriendList.addListSelectionListener(this);

		icon2 = new ImageIcon("src/Image/HomePanel.png");
		Image img2 = icon2.getImage();
		Image changeImg2 = img2.getScaledInstance(453, 773, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon2 = new ImageIcon(changeImg2);

		JPanel HomePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon2.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);

			}
		};

		// 친구 목록 리스트 설정

		// 친구 목록 창
//		FriendList.setBounds(22, 124, 420, 200);
//		FriendList.setVisible(true);
		inscrollPane = new JScrollPane(FriendList);
		inscrollPane.setEnabled(false);
		inscrollPane.setBounds(22, 124, 420, 200);
		inscrollPane.setBackground(Color.WHITE);
		inscrollPane.getViewport().setBackground(Color.WHITE);
		outscrollPane.setEnabled(false);
		outscrollPane.setBounds(22, 387, 420, 200);
		outscrollPane.setBackground(Color.WHITE);
		outscrollPane.getViewport().setBackground(Color.WHITE);

		HomePanel.setLayout(null);
		// 유저 이름 라벨
		UserLabel = new JLabel("유저 이름");
		UserLabel.setBounds(63, 24, 82, 24);

		// 유저 상태메시지 라벨
		UserStateLabel = new JLabel("유저 상태메시지");
		UserStateLabel.setBounds(63, 48, 300, 20);

		// 유저 프로필 버튼
		UserBtn = new JButton();
		UserBtn.setBounds(22, 34, 33, 30);
		UserBtn.setContentAreaFilled(false);
		// HomeButton.setBorderPainted(false);
		UserBtn.setFocusPainted(false);

		model.addElement("Hi there"); // TODO: 이렇게 추가!

		// 홈버튼
		HomePanel.add(UserBtn);
//		HomePanel.add(FriendList);
		HomePanel.add(inscrollPane);
		HomePanel.add(outscrollPane);
		HomePanel.add(UserLabel);
		HomePanel.add(UserStateLabel);

		icon3 = new ImageIcon("src/Image/Search.png");
		Image img3 = icon3.getImage();
		Image changeImg3 = img3.getScaledInstance(457, 600, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon3 = new ImageIcon(changeImg3);

		JPanel SearchPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon3.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);

				// 친구 찾기 목록창
				fscrollPane.setEnabled(false);
				fscrollPane.setBounds(17, 54, 400, 450);
				fscrollPane.setBackground(Color.WHITE);
				fscrollPane.getViewport().setBackground(Color.WHITE);

			}
		};

		SearchPanel.setLayout(null);
		SearchPanel.add(fscrollPane);

		// 친구 검색 창
		JTextField ftxt = new JTextField();
		ftxt.setBounds(17, 13, 330, 30);
		SearchPanel.add(ftxt);

		// 친구 찾기 버튼 -> 누르면 친구 찾음
		FriendSearchBtn = new JButton(new ImageIcon("src/Image/btn.png")); // 버튼 배경 이미지 설정
		FriendSearchBtn.setBounds(370, 13, 30, 30);

		// 친구 찾기 버튼을 눌렀을 때 발생
		FriendSearchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		// 로그아웃 버튼
		LogOutBtn = new JButton();
		LogOutBtn.setBounds(15, 110, 30, 24);
		LogOutBtn.setContentAreaFilled(false);
		// LogOutBtn.setBorderPainted(false);
		LogOutBtn.setFocusPainted(false);
		Main.add(LogOutBtn);

		SearchPanel.add(FriendSearchBtn);

		ViewCard = new JPanel();
		vc = new CardLayout();
		ViewCard.setLayout(vc);
		ViewCard.setBounds(50, 0, 457, 600);
		Main.add(ViewCard);

		JPanel homeCard = HomePanel;
		JPanel searchCard = SearchPanel;

		ViewCard.add("hc", homeCard);
		ViewCard.add("sc", searchCard);

		vc.show(ViewCard, "hc");

		HomeButton.addActionListener(event -> {
			New_Client.pw.println("52269#" + us.getUserID());
			System.out.println("1");
			String[] d = new String[100];

			while (true) {
				int i = 0;
				String temp1 = null;
				try {
					temp1 = New_Client.br.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (temp1.equals("-1")) {
					break;
				}
				d[i] = temp1;
				System.out.println(temp1);
				i++;

			}
			FriendList.setListData(d);
			vc.show(ViewCard, "hc");

		});

		SearchButton.addActionListener(event -> {
			vc.show(ViewCard, "sc");
		});

		UserBtn.addActionListener(event -> {
			// 프로필메시지 선택 할때 함수
		});

		LogOutBtn.addActionListener(event -> {
			New_Client.pw.println("52270#" + us.getUserID());
			JOptionPane.showMessageDialog(null, "로그아웃하였습니다");
			dispose();

		});

		// 친구목록 액션 리스너

		/*
		 * ActionListener actionListener = new ActionListener() { public void
		 * actionPerformed(ActionEvent actionEvent) {
		 * 
		 * 
		 * if (actionEvent.getActionCommand() == "대화하기") {
		 * 
		 * String inviteFriendID = (String) FriendList.getSelectedValue(); if
		 * (!(inviteFriendID == null)) { // 초대할 사람이 공백이이 않을 경우 초대
		 * New_Client.pw.println("52274#" + inviteFriendID); // 대화요청
		 * System.out.println("대화하기"); } }
		 * 
		 * else if (actionEvent.getActionCommand() == "친구정보") {
		 * 
		 * int num = FriendList.getSelectedIndex();
		 * 
		 * String SelFrdID = (String) FriendList.getSelectedValue(); if (!(SelFrdID ==
		 * null)) { System.out.println("친구 정보 보기"); String Friend_Id = null; String
		 * Friend_Name = null; String Friend_Email = null; String Friend_Phone = null;
		 * 
		 * /// Connection con1 = null;
		 * 
		 * try { String url =
		 * "jdbc:mysql://localhost/network?serverTimezone=Asia/Seoul"; // network 스키마
		 * String user = "root"; // 데이터베이스 아이디 String passwd = "12345"; // 데이터베이스 비번
		 * Class.forName("com.mysql.cj.jdbc.Driver"); con1 =
		 * DriverManager.getConnection(url, user, passwd);
		 * 
		 * PreparedStatement ps1 = null; ResultSet rs1 = null; String sql1 =
		 * "select * from client_list where client_id=?";
		 * 
		 * ps1 = con1.prepareStatement(sql1); ps1.setString(1, SelFrdID); // 리스트에 선택된
		 * 친구의 ID(또는 이름) rs1 = ps1.executeQuery();
		 * 
		 * while (rs1.next()) {
		 * 
		 * Friend_Id = rs1.getString("client_ID"); Friend_Name =
		 * rs1.getString("client_name"); Friend_Email = rs1.getString("client_email");
		 * Friend_Phone = rs1.getString("client_phone"); } }
		 * 
		 * catch (SQLException sqex) { System.out.println("SQLException: " +
		 * sqex.getMessage()); System.out.println("SQLState: " + sqex.getSQLState()); }
		 * catch (ClassNotFoundException e) { e.printStackTrace(); }
		 * 
		 * JFrame a = new JFrame(); // BorderLayout f = new BorderLayout();
		 * 
		 * Label ShowFrdName = new Label(" 이름 : " + Friend_Name); Label ShowFrdEmail =
		 * new Label(" E-mail : " + Friend_Email); Label ShowFrdPhone = new
		 * Label(" 연락처 : " + Friend_Phone); Label ShowFrdID = new Label(" ID : " +
		 * Friend_Id); Label ShowFrdInfo = new Label("< 친구 정보  >");
		 * 
		 * a.setLayout(null);
		 * 
		 * ShowFrdName.setBounds(30, 50, 100, 30); ShowFrdID.setBounds(30, 100, 100,
		 * 30); ShowFrdEmail.setBounds(30, 150, 200, 30); ShowFrdPhone.setBounds(30,
		 * 200, 200, 30); ShowFrdInfo.setBounds(30, 10, 100, 30);
		 * 
		 * a.add(ShowFrdName); a.add(ShowFrdID); a.add(ShowFrdEmail);
		 * a.add(ShowFrdPhone); a.add(ShowFrdInfo);
		 * 
		 * a.setVisible(true); a.setSize(300, 300); FrameLocation.setLocation(a);
		 * a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); } // if end }
		 * 
		 * else if (actionEvent.getActionCommand() == "친구삭제") {
		 * 
		 * int num = FriendList.getSelectedIndex(); String SelFrdID = (String)
		 * FriendList.getSelectedValue(); String url =
		 * "jdbc:mysql://localhost/network?serverTimezone=Asia/Seoul"; // network 스키마
		 * String user = "root"; // 데이터베이스 아이디 String passwd = "12345"; // 데이터베이스 비번 if
		 * (!(SelFrdID == null)) { try { Class.forName("com.mysql.cj.jdbc.Driver"); con1
		 * = DriverManager.getConnection(url, user, passwd);
		 * 
		 * PreparedStatement ps2 = null; int rs2; String sql2 =
		 * "delete from client_friend_list where client_id=? and friend_id=?";
		 * 
		 * ps2 = con1.prepareStatement(sql2); String MyId = New_Client.getClientName();
		 * ps2.setString(1, MyId); ps2.setString(2, SelFrdID); rs2 =
		 * ps2.executeUpdate();
		 * 
		 * System.out.println("삭제된 친구 수" + rs2);
		 * 
		 * // String SetFriend_ID[] = new String[100];
		 * 
		 * int f_count = 0;
		 * 
		 * PreparedStatement ps3 = null; ResultSet rs3 = null; String sql =
		 * "select * from client_friend_list where client_id=?"; ps3 =
		 * con1.prepareStatement(sql); ps3.setString(1, Login_ID); rs3 =
		 * ps3.executeQuery();
		 * 
		 * while (rs3.next()) { String str = rs3.getString("friend_id");
		 * System.out.println(str);
		 * 
		 * Friend_ID[f_count] = str; f_count++;
		 * 
		 * } Friend_ID[f_count] = null; FriendList.setListData(Friend_ID);
		 * 
		 * }
		 * 
		 * catch (SQLException sqex) { System.out.println("SQLException: " +
		 * sqex.getMessage()); System.out.println("SQLState: " + sqex.getSQLState()); }
		 * catch (ClassNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } // if end } else if (actionEvent.getActionCommand()
		 * == "상세정보") {
		 * 
		 * System.out.println("Hi"); String My_Id = null; String My_Name = null; String
		 * My_Email = null; String My_Phone = null; String My_password = null;
		 * 
		 * /// Connection con1 = null; String url =
		 * "jdbc:mysql://localhost/network?serverTimezone=Asia/Seoul"; // network 스키마
		 * String user = "root"; // 데이터베이스 아이디 String passwd = "12345"; // 데이터베이스 비번 try
		 * { Class.forName("com.mysql.cj.jdbc.Driver"); con1 =
		 * DriverManager.getConnection(url, user, passwd);
		 * 
		 * PreparedStatement ps1 = null; ResultSet rs1 = null; String sql1 =
		 * "select * from client_list where client_id=?";
		 * 
		 * ps1 = con1.prepareStatement(sql1); String MyId = New_Client.getClientName();
		 * ps1.setString(1, MyId); // 리스트에 선택된 친구의 ID(또는 이름) rs1 = ps1.executeQuery();
		 * 
		 * while (rs1.next()) {
		 * 
		 * My_Id = rs1.getString("client_ID"); My_Name = rs1.getString("client_name");
		 * My_Email = rs1.getString("client_email"); My_Phone =
		 * rs1.getString("client_phone"); My_password =
		 * rs1.getString("client_password"); } }
		 * 
		 * catch (SQLException sqex) { System.out.println("SQLException: " +
		 * sqex.getMessage()); System.out.println("SQLState: " + sqex.getSQLState()); }
		 * catch (ClassNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * } } };
		 */

		JMenuItem CreateTalk = new JMenuItem("대화하기");
		// CreateTalk.addActionListener(actionListener);
		FFindpopUp.add(CreateTalk);
		JMenuItem InfoFriend = new JMenuItem("친구정보");
		// InfoFriend.addActionListener(actionListener);
		FFindpopUp.add(InfoFriend);

		JMenuItem DeleteFriend = new JMenuItem("친구삭제");
		// DeleteFriend.addActionListener(actionListener);
		FFindpopUp.add(DeleteFriend);

		FriendList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == e.BUTTON3) {
					JList c = (JList) e.getComponent();
					int x = e.getX();
					int y = e.getY();
					if (!FriendList.isSelectionEmpty()
							&& FriendList.locationToIndex(e.getPoint()) == FriendList.getSelectedIndex()) {
						int count = c.getModel().getSize();
						int cal = count * 18;
						if (y <= cal) {
							FFindpopUp.show(FriendList, x, y);
						}
					}
				}
			}
		});

		setTitle("채팅 메신저");

		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// ListSelectionListener
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			System.out.println("selected :" + FriendList.getSelectedValue());
		}
	}
}
