
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
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
	JTextField em;

	/* Button */
	JButton suBtn;
	User us = null;
	

	JoinFrame(User _us) {

		us = _us;

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
		id.setBounds(73, 156, 130, 15);
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
		birth.setBounds(80, 329, 308, 14);
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
		getContentPane().add(signup);

		/* Button 이벤트 리스너 추가 */

		suBtn.addActionListener(this);

		setSize(440, 590);
		setLocationRelativeTo(null);
		signup.setLayout(null);
		
		em = new JTextField(){
			@Override
			public void setBorder(Border border) {
			}
		};
		em.setBounds(278, 158, 127, 18);
		signup.add(em);
		em.setColumns(10);
		setResizable(false);
		setVisible(true);
	}

	// JOptionPane.showMessageDialog(null, "회원가입에 성공하였습니다");
	// dispose();

	/* Button 이벤트 리스너 */

	public void actionPerformed(ActionEvent e) {
		String uid = id.getText();
		String upass = "";
		String uName = name.getText();
		String uNN = nickname.getText();
		String uPN = phoneNumber.getText();
		String ubirth = birth.getText();
		String ema = em.getText();

		for (int i = 0; i < pw.getPassword().length; i++) {
			upass = upass + pw.getPassword()[i];
		}
		if (e.getSource() == suBtn) {
			if (uid.equals("") || upass.equals("") || uPN.equals("") || uName.equals("") || uNN.equals("") || ema.equals("")
					|| ubirth.equals("")) {
				JOptionPane.showMessageDialog(null, "모든 정보를 기입해주세요", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
				System.out.println("회원가입 실패 > 회원정보 미입력");
			}

			else if (!uid.equals("") && !upass.equals("") && !uPN.equals("") && !ema.equals("") && !uName.equals("") && !uNN.equals("")
					&& !ubirth.equals("")) {
				New_Client.pw.println("59982#" + uName + "#" + uNN + "#" + uid + "#" + upass + "#" + uPN + "#" + ubirth + "#" + ema);
				
				try {
					int check = Integer.parseInt(New_Client.br.readLine());
					
					if(check==1)
					{
						JOptionPane.showMessageDialog(null, "회원가입에 성공하였습니다");
						dispose();
					}
					
					if(check==0)
					{
						JOptionPane.showMessageDialog(null, "오류입니다.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
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

		else {
			System.out.println("오류입니다");
		}

	}
}