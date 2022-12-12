
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class JoinFrame extends JFrame implements ActionListener {

	ImageIcon icon;

	/* Panel */
	JPanel signup;

	/* TextField */
	JTextField id;
	JTextField name;
	JTextField nickname;
	JTextField phoneNumber;
	JTextField birth;
	JPasswordField pw;

	/* Button */
	JButton suBtn;

	JoinFrame() {
		icon = new ImageIcon("src/Image/signupFrame.png");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(434, 549, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		signup = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		setTitle("회원가입");

		/* TextField 크기 작업 */

		id = new JTextField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		id.setBounds(73, 156, 327, 15);
		signup.add(id);
		id.setOpaque(false);
		id.setColumns(10);

		pw = new JPasswordField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		pw.setBounds(85, 207, 316, 15);
		signup.add(pw);
		pw.setOpaque(false);

		name = new JTextField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		name.setBounds(57, 106, 149, 15);
		signup.add(name);
		name.setOpaque(false);
		name.setColumns(10);

		nickname = new JTextField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		nickname.setBounds(275, 106, 123, 15);
		signup.add(nickname);
		nickname.setOpaque(false);
		nickname.setColumns(10);
		
		phoneNumber = new JTextField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		phoneNumber.setBounds(83, 257, 315, 16);
		signup.add(phoneNumber);
		phoneNumber.setOpaque(false);
		phoneNumber.setColumns(10);

		birth = new JTextField("20000520") {
			@Override
			public void setBorder(Border border) {
			}
		};
		birth .setBounds(80, 329, 308, 14);
		signup.add(birth);
		birth.setOpaque(false);
		birth.setColumns(10);

		/* Button 크기 작업 */
		suBtn = new JButton();
		suBtn.setBounds(119, 504, 195, 38);
		suBtn.setContentAreaFilled(false);
		suBtn.setBorderPainted(false);
		signup.add(suBtn);

		/* Panel 추가 작업 */
		add(signup);

		/* Button 이벤트 리스너 추가 */

		suBtn.addActionListener(this);

		setSize(440, 590);
		setLocationRelativeTo(null);
		signup.setLayout(null);
		setResizable(false);
		setVisible(true);
	}

	/* Button 이벤트 리스너 */
	
	public void actionPerformed(ActionEvent e) {
		String uid = id.getText();
		String upass = "";
		for (int i = 0; i < pw.getPassword().length; i++) {
			upass = upass + pw.getPassword()[i];
		}
		if (e.getSource() == suBtn) {
			if (uid.equals("") || upass.equals("")) {
				JOptionPane.showMessageDialog(null, "모든 정보를 기입해주세요", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
				System.out.println("회원가입 실패 > 회원정보 미입력");
			}

			else if (!uid.equals("") && !upass.equals("")) {
				if (joinCheck(uid, upass)) {
					System.out.println("회원가입 성공");
					JOptionPane.showMessageDialog(null, "회원가입에 성공하였습니다");
					dispose();
				} else {
					System.out.println("회원가입 실패");
					JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다");
					id.setText("");
					pw.setText("");
				}
			}
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