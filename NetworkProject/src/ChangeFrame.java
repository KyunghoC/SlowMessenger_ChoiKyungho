
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

	User us = null;

	ChangeFrame(User _us) {

		us = _us;

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
		getContentPane().add(changePanel);

		/* Button 이벤트 리스너 추가 */

		changeBtn.addActionListener(this);
		setSize(455, 280);
		setLocationRelativeTo(null);
		changePanel.setLayout(null);

		JLabel userName = new JLabel() {
			@Override
			public void setBorder(Border border) {
			}
		};
		userName.setBounds(269, 145, 65, 14);
		userName.setText(us.getUserName());
		changePanel.add(userName);

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
			if (!upass.equals("")) {
				New_Client.pw.println("52282#" + us.getUserID() + "#" + upass + "#" + us.getSalt());
				JOptionPane.showMessageDialog(null, "변경되었습니다");
				dispose();
			}
			else if(upass.equals(""))
			{
				JOptionPane.showMessageDialog(null, "공백으로 둘 수 없습니다.", "변경 실패", JOptionPane.ERROR_MESSAGE);
			}
		}

		else {
			System.out.println("오류입니다");
		}

	}
}