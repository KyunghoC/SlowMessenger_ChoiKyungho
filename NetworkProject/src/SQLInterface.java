import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLInterface {
	private static String dburl = "jdbc:mysql://localhost/network";
	private static String dbUser = "root";
	private static String dbpasswd = "12345";

	public static String getSaltbyUID(String UID) throws ClassNotFoundException, SQLException { // get salt by UID
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "SELECT salt FROM client_list WHERE client_id = ?;";
		ps = conn.prepareStatement(sql);
		ps.setString(1, UID);
		ResultSet rs = ps.executeQuery();

		rs.next();
		String tmp = rs.getString(1);
		rs.close();
		ps.close();
		conn.close();
		return tmp;
	}

	public static User getuserbyUID(String UID) throws ClassNotFoundException, SQLException { // Input user and return
																								// User object
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "SELECT * FROM client_list WHERE client_id = ?;";
		ps = conn.prepareStatement(sql);
		ps.setString(1, UID);
		ResultSet rs = ps.executeQuery();

		User user = new User();
		rs.next();
		user.setUserID(rs.getString(1));
		user.setUserPassword(rs.getString(2));
		user.setUserName(rs.getString(3));
		user.setUserEmail(rs.getString(4));
		user.setUserPhoneNum(rs.getString(5));
		user.setUserNickname(rs.getString(6));
		user.setUserBitrhDate(rs.getDate(7));
		user.setStatMessage(rs.getString(8));
		user.setUserLastCon(rs.getTimestamp(9));
		user.setSalt(rs.getString(10));
		rs.close();
		ps.close();
		conn.close();
		return user;
	}

	public static void client_logout(String UID) throws SQLException, ClassNotFoundException { // logout function
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "UPDATE login_check SET log = FALSE WHERE client_id = ?;";

		ps = conn.prepareStatement(sql);
		ps.setString(1, UID);
		ps.executeUpdate();

		ps.close();
		conn.close();
	}

	public static Integer validLogin(String id, String pw) throws SQLException, ClassNotFoundException { // login valid
																											// check
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "SELECT client_password FROM client_list WHERE client_id = ?;";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			if (pw.equals(rs.getString(1))) { // login success
				rs.close();
				ps.close();
				conn.close();
				return 1;
			} else { // pw wrong
				rs.close();
				ps.close();
				conn.close();
				return 2;
			}
		}
		rs.close();
		ps.close();
		conn.close();
		return 3; // ID not found

	}

	public static void initUser(User user) throws SQLException, ClassNotFoundException { // join user
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "INSERT INTO client_list VALUES (?, ?, ?, ?, ?, ?, ? ,? ,? ,?);";
		ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUserID());
		ps.setString(2, user.getUserPassword());
		ps.setString(3, user.getUserName());
		ps.setString(4, user.getUserEmail());
		ps.setString(5, user.getUserPhoneNum());
		ps.setString(6, user.getUserNickname());
		ps.setDate(7, user.getUserBitrhDate());
		ps.setString(8, user.getStatMessage());
		ps.setTimestamp(9, user.getUserLastCon());
		ps.setString(10, user.getSalt());
		ps.executeUpdate();
		ps.close();
		System.out.println("Inserting Successfully!");
		conn.close();
	}

	public static Integer checkAccount(String id, String name, String phone)
			throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "SELECT client_id FROM client_list WHERE client_id = ? AND client_name=? AND client_phone=?;";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, name);
		ps.setString(3, phone);
		ResultSet rs = ps.executeQuery();

		int tmp;
		if (rs.next()) // if there is a result
			tmp = 1; // Find successfully
		else
			tmp = 4;

		rs.close();
		ps.close();
		conn.close();
		return tmp; // Find failed
	}

	public static void validPWChange(String id, String pw, String salt) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "UPDATE client_list SET client_password=?, salt=? WHERE client_id = ?;"; // update password
		ps = conn.prepareStatement(sql);
		ps.setString(1, pw);
		ps.setString(2, salt);
		ps.setString(3, id);
		ResultSet rs = ps.executeQuery();
		ps.close();

		rs.close();
		conn.close();

	}

	public static Integer getFriendNum(String UID) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "SELECT count(friend_id) FROM client_friend_list WHERE client_id = ?;";
		ps = conn.prepareStatement(sql);
		ps.setString(1, UID);
		ResultSet rs = ps.executeQuery();

		rs.next();
		Integer tmp = rs.getInt(1);
		rs.close();
		ps.close();
		conn.close();
		return tmp;
	}

	public static String[] getFriendInfo(String UID) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);

		Class.forName("com.mysql.cj.jdbc.Driver");

		PreparedStatement ps = null;

		String sql = "SELECT friend_id FROM client_friend_list WHERE client_id = ?;"; // check ID, name, and phone
		ps = conn.prepareStatement(sql);
		ps.setString(1, UID);
		ResultSet rs = ps.executeQuery();

		Integer friendNum = SQLInterface.getFriendNum(UID);
		String[] tmp = new String[friendNum];
		for (int i = 0; i < friendNum; i++) {
			rs.next();
			tmp[i] = rs.getString(1);
		}
		rs.close();
		ps.close();
		conn.close();
		return tmp;
	}
}
