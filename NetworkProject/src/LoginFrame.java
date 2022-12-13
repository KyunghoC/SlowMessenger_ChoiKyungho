import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class LoginFrame extends JFrame implements ActionListener {

	ImageIcon icon; // 백그라운드 이미지

	/* TextField */
	JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();

	/* Button */
	JButton loginBtn = new JButton();
	JButton joinBtn;
	JButton changeBtn;

	JoinFrame jf;
	CheckFrame cf;
	User us = null;

	LoginFrame(User _us) {

		us = _us;

		// 이미지 패널
		icon = new ImageIcon("src/Image/login2.png");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(422, 472, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		JPanel login = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		setTitle("로그인");

		/* Panel 크기 작업 */
		setSize(430, 480);
		login.setSize(422, 472);
		login.setLayout(null);

		/* Field 작업 */
		id = new JTextField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		id.setBounds(130, 209, 220, 16);
		login.add(id);
		id.setOpaque(false);
		id.setColumns(10);

		pw = new JPasswordField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		pw.setBounds(145, 263, 201, 13);
		login.add(pw);
		pw.setOpaque(false);

		// pw.addKeyListener(new LogindAction4());

		/* Button 작업 */

		loginBtn = new JButton();
		loginBtn.setBounds(176, 308, 74, 42);
		login.add(loginBtn);
		loginBtn.setBorderPainted(false);
		loginBtn.setContentAreaFilled(false);
		loginBtn.setFocusPainted(false);

		joinBtn = new JButton();
		joinBtn.setBounds(81, 396, 94, 15);
		joinBtn.setBorderPainted(false);
		joinBtn.setContentAreaFilled(false);
		login.add(joinBtn);

		changeBtn = new JButton();
		changeBtn.setBounds(222, 396, 128, 14);
		changeBtn.setBorderPainted(false);
		changeBtn.setContentAreaFilled(false);
		login.add(changeBtn);

		/* Button 이벤트 리스너 추가 */

		loginBtn.addActionListener(new LoginCheck());
		joinBtn.addActionListener(this);
		changeBtn.addActionListener(this);

		// 기타작업
		getContentPane().add(login);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/* Button 이벤트 리스너 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == joinBtn) {
			jf = new JoinFrame(us);
		}

		else if (e.getSource() == changeBtn) {
			cf = new CheckFrame(us);
		}

		else {
			System.out.println("오류입니다");
		}

	}

	// loginAction

	public class LoginCheck implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			/* TextField에 입력된 아이디와 비밀번호를 변수에 초기화 */
			if (e.getSource() == loginBtn) {
				String uid = id.getText();
				String upass = "";
				for (int i = 0; i < pw.getPassword().length; i++) {
					upass = upass + pw.getPassword()[i];
				}

				if (uid.equals("") || upass.equals("")) {
					JOptionPane.showMessageDialog(null, "아이디와 비밀번호 모두 입력해주세요", "로그인 실패", JOptionPane.ERROR_MESSAGE);
					System.out.println("로그인 실패 > 로그인 정보 미입력");
				}

				else if (uid != null && upass != null) {
					New_Client.pw.println("52272#" + uid + "#" + upass);
					try {
						int check = Integer.parseInt(New_Client.br.readLine());
						if (check==1) { // 이 부분이 데이터베이스에 접속해 로그인 정보를 확인하는 부분이다.
							System.out.println("로그인 성공");
							JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다");
							
							us.setUserID(uid);
							new MainFrame(us);
							dispose();
						} else if(check ==2) {
							System.out.println("로그인 실패 > 로그인 정보 불일치");
							JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.");
						}
						else
						{
							System.out.println("로그인 실패 > 로그인 정보 불일치");
							JOptionPane.showMessageDialog(null, "ID가 존재하지 않습니다.");
						}
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		}

	}

}// Listener end
