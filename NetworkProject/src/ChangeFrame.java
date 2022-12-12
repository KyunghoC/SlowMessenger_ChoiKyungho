
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ChangeFrame extends JFrame implements ActionListener {

	ImageIcon icon;

	/* Panel */
	JPanel changePanel;

	/* PasswordField */
	JPasswordField pw;

	/* Button */
	JButton changeBtn;

	ChangeFrame() {
		icon = new ImageIcon("src/Image/ChangePanel.png");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(453, 245, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		changePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		setTitle("비밀번호 변경");

		/* PasswordField 크기 작업 */
		
		pw = new JPasswordField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		pw.setBounds(31, 130, 140, 28);
		changePanel.add(pw);
		pw.setOpaque(false);

		
		/* Button 크기 작업 */
		changeBtn = new JButton();
		changeBtn.setBounds(354, 206, 82, 33);
		changeBtn.setContentAreaFilled(false);
		changeBtn.setBorderPainted(false);
		changePanel.add(changeBtn);
		

		/* Panel 추가 작업 */
		add(changePanel);

		/* Button 이벤트 리스너 추가 */

		changeBtn.addActionListener(this);
		setSize(455, 280);
		setLocationRelativeTo(null);
		changePanel.setLayout(null);
		
		setResizable(false);
		setVisible(true);
	}

	/* Button 이벤트 리스너 */
	
	public void actionPerformed(ActionEvent e) {
		String upass = "";
		for (int i = 0; i < pw.getPassword().length; i++) {
			upass = upass + pw.getPassword()[i];
		}
		
		if (e.getSource() == changeBtn) {
			System.out.println("1");
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