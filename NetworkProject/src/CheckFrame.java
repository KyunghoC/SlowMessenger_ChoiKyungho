
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
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

		if (e.getSource() == goBtn) {
			if (uid.equals("") || uName.equals("") || uPN.equals("")) {
				JOptionPane.showMessageDialog(null, "모든 정보를 기입해주세요", "조회 실패", JOptionPane.ERROR_MESSAGE);
				System.out.println("조회 실패 > 회원정보 미입력");
			}

			else if (!uid.equals("") && !uName.equals("") && !uPN.equals("")) {
				New_Client.pw.println("52268#" + uid +"#" +uName + "#" + uPN);
				System.out.println("3");
				try {
					int check = Integer.parseInt(New_Client.br.readLine());
					if(check==1)
					{
						JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다");
						new ChangeFrame(us);
						dispose();
					}
					else if (check == 4)
					{
						JOptionPane.showMessageDialog(null, "계정을 찾지 못했습니다.", "조회 실패", JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
		if (e.getSource() == ExitBtn) {
			dispose();
		}

		else {
			System.out.println("오류입니다");
		}

	}

}