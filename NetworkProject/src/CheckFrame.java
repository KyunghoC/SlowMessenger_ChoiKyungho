
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CheckFrame extends JFrame implements ActionListener {

	ImageIcon icon;

	/* Panel */
	JPanel checkPanel;

	/* TextField */
	JTextField id;
	JTextField name;
	JTextField phoneNumber;

	/* Button */
	JButton goBtn;
	JButton ExitBtn;
	User us = null;

	CheckFrame(User _us) {

		us = _us;

		icon = new ImageIcon("src/Image/FindAccount.png");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(451, 326, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		checkPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		setTitle("계정 찾기");

		/* TextField 크기 작업 */

		id = new JTextField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		id.setBounds(83, 170, 327, 15);
		checkPanel.add(id);
		id.setOpaque(false);
		id.setColumns(10);

		name = new JTextField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		name.setBounds(70, 124, 149, 15);
		checkPanel.add(name);
		name.setOpaque(false);
		name.setColumns(10);

		phoneNumber = new JTextField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		phoneNumber.setBounds(101, 215, 315, 16);
		checkPanel.add(phoneNumber);
		phoneNumber.setOpaque(false);
		phoneNumber.setColumns(10);

		/* Button 크기 작업 */
		goBtn = new JButton();
		goBtn.setBounds(377, 280, 59, 32);
		goBtn.setContentAreaFilled(false);
		goBtn.setBorderPainted(false);
		checkPanel.add(goBtn);

		ExitBtn = new JButton();
		ExitBtn.setBounds(309, 281, 61, 32);
		ExitBtn.setContentAreaFilled(false);
		ExitBtn.setBorderPainted(false);
		checkPanel.add(ExitBtn);

		/* Panel 추가 작업 */
		add(checkPanel);

		/* Button 이벤트 리스너 추가 */

		goBtn.addActionListener(this);
		ExitBtn.addActionListener(this);
		setSize(455, 360);
		setLocationRelativeTo(null);
		checkPanel.setLayout(null);

		setResizable(false);
		setVisible(true);
	}

	/* Button 이벤트 리스너 */

	public void actionPerformed(ActionEvent e) {
		String uid = id.getText();
		String uName = name.getText();
		String uPN = phoneNumber.getText();
		/*
		 * if (e.getSource() == goBtn) { if (uid.equals("") || upass.equals("")) {
		 * JOptionPane.showMessageDialog(null, "모든 정보를 기입해주세요", "회원가입 실패",
		 * JOptionPane.ERROR_MESSAGE); System.out.println("회원가입 실패 > 회원정보 미입력"); }
		 * 
		 * else if (!uid.equals("") && !uName.equals("") && !uPN.equals("")) { if (함수
		 * 들어가야함) { } } }
		 */
		if (e.getSource() == ExitBtn) {
			dispose();
		}

		else {
			System.out.println("오류입니다");
		}

	}

	boolean joinCheck(String _i, String _p) {
		boolean flag = false;

		String id = _i;
		String pw = _p;
		Connection con = null;
		Statement stmt = null;
		String url = "jdbc:mysql://localhost/network?serverTimezone=Asia/Seoul"; // network 스키마
		String user = "root"; // 데이터베이스 아이디
		String passwd = "12345"; // 데이터베이스 비번

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, passwd);
			stmt = con.createStatement();
			String insertStr = "INSERT INTO client_list (client_id, client_password) VALUES('" + id + "', '" + pw
					+ "')";
			stmt.executeUpdate(insertStr);
			insertStr = "INSERT INTO login_check VALUES('" + id + "', 'logout')";
			stmt.executeUpdate(insertStr);
			flag = true;
			System.out.println("회원가입 성공");
		} catch (Exception e) {
			flag = false;
			System.out.println("회원가입 실패 > " + e.toString());
		}

		return flag;
	}
}