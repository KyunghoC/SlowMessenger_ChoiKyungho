
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class MainFrame extends JFrame {
	ImageIcon icon; // 백그라운드 이미지
	ImageIcon icon2; // 홈패널 이미지
	ImageIcon icon3; // 찾기패널 이미지
	JPanel Main;
	JPanel ViewCard, WeatherPanel;

	JButton HomeButton, SearchButton, LogOutBtn, FriendSearchBtn, UserBtn;

	JLabel UserLabel, UserStateLabel;

	JScrollPane inscrollPane = new JScrollPane(); // online 친구창
	JScrollPane outscrollPane = new JScrollPane(); // offline 친구창
	JScrollPane fscrollPane = new JScrollPane(); // 친구 찾기 창

	WeatherPanel weather;

	CardLayout vc;

	User us = null;
	
	MainFrame(User _us) {
		
		us= _us;
		
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

		icon2 = new ImageIcon("src/Image/HomePanel.png");
		Image img2 = icon2.getImage();
		Image changeImg2 = img2.getScaledInstance(453, 773, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon2 = new ImageIcon(changeImg2);

		JPanel HomePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon2.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);

				// 친구 목록 창
				inscrollPane.setEnabled(false);
				inscrollPane.setBounds(22, 124, 420, 200);
				inscrollPane.setBackground(Color.WHITE);
				inscrollPane.getViewport().setBackground(Color.WHITE);
				outscrollPane.setEnabled(false);
				outscrollPane.setBounds(22, 387, 420, 200);
				outscrollPane.setBackground(Color.WHITE);
				outscrollPane.getViewport().setBackground(Color.WHITE);

			}
		};

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

		// 홈버튼
		HomePanel.add(UserBtn);
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
			vc.show(ViewCard, "hc");
		});

		SearchButton.addActionListener(event -> {
			vc.show(ViewCard, "sc");
		});

		UserBtn.addActionListener(event -> {
			// 프로필메시지 선택 할때 함수
		});

		LogOutBtn.addActionListener(event -> {
			New_Client.pw.println("52270#"+us.getUserID());
			JOptionPane.showMessageDialog(null, "로그아웃하였습니다");
			dispose();
			
		});

		setTitle("채팅 메신저");

		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
