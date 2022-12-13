import java.sql.Date;
import java.sql.Timestamp;

public class User {

	private String userID; // ID
	private String userName; // name
	private String userPhoneNum; // phone_num
	private String userEmail; // email
	private String userPassword; // password
	private String userNickname; // Nickname
	private Date userBitrhDate; // Birth
	private String salt; // salt
	private Timestamp userLastCon; // Last connection time
	private String statMessage;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhoneNum() {
		return userPhoneNum;
	}

	public void setUserPhoneNum(String userPhoneNum) {
		this.userPhoneNum = userPhoneNum;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public Date getUserBitrhDate() {
		return userBitrhDate;
	}

	public void setUserBitrhDate(Date userBitrhDate) {
		this.userBitrhDate = userBitrhDate;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Timestamp getUserLastCon() {
		return userLastCon;
	}

	public void setUserLastCon(Timestamp userLastCon) {
		this.userLastCon = userLastCon;
	}

	public String getStatMessage() {
		return statMessage;
	}

	public void setStatMessage(String statMessage) {
		this.statMessage = statMessage;
	}

}
