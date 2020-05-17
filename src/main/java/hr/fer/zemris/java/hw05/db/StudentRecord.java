package hr.fer.zemris.java.hw05.db;

/**
 * Class represents one student record that can store </br>
 * student's jmbag, first and last name and his final grade.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class StudentRecord {
	
	/** student's jmbag */
	private String jmbag;
	/** student's last name*/
	private String lastName;
	/** student's first name */
	private String firstName;
	/** student's final grade */
	private int finalGrade;
	
	/**
	 * Constructor for creating new {@code StudentRecord} object.
	 * 
	 * @param jmbag		 student's jmbag
	 * @param lastName   student's last name
	 * @param firstName  student's first name
	 * @param finalGrade student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Method returns student's jmbag
	 * 
	 * @return {@code String} student's jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Method returns student's last name
	 * 
	 * @return {@code String} student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method returns student's first name 
	 * 
	 * @return {@code String} student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method returns student's final grade
	 * 
	 * @return {@code String} student's final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return jmbag + " " + lastName + " " + firstName + " " + finalGrade;
	}
	
}
