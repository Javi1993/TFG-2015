package javabeans.tfg.eprail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {

	private int uid;
	private String firstName;
	private String familyName;
	private String email;
	private byte isValidate;
	private Timestamp dateRegistration;
	private boolean loggedIn;
	private String password;

	public User(){

	}

	public User(int uid, String firstName, String familyName, String email, byte isValidate, Timestamp dateRegistration, String password){

		this.setUid(uid);
		this.setFirstName(firstName);
		this.setFamilyName(familyName);
		this.setEmail(email);
		this.setIsValidate(isValidate);
		this.setDateRegistration(dateRegistration);
		this.setPassword(password);

	}

	/**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the familyName
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the isValidate
	 */
	public byte getIsValidate() {
		return isValidate;
	}

	/**
	 * @param isValidate the isValidate to set
	 */
	public void setIsValidate(byte isValidate) {
		this.isValidate = isValidate;
	}

	/**
	 * @return the dateRegistration
	 */
	public Timestamp getDateRegistration() {
		return dateRegistration;
	}

	/**
	 * @param dateRegistration the dateRegistration to set
	 */
	public void setDateRegistration(Timestamp dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	/**
	 * @return the loggedIn
	 */
	public boolean getLoggedIn() {
		return loggedIn;
	}

	/**
	 * @param loggedIn the isLogin to set
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return
	 */
	public boolean doLogin(ResultSet rs) {
		try {
			rs.beforeFirst();
			while(rs.next())
			{
				uid = rs.getInt(1);
				firstName = rs.getString(2);
				familyName = rs.getString(3);
				isValidate = rs.getByte(5);
				dateRegistration = rs.getTimestamp(6);
			}

			setLoggedIn(true);
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripción: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
			setLoggedIn(false);
		}
		return getLoggedIn();
	}
}
