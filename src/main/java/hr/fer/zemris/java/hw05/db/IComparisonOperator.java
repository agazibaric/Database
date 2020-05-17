package hr.fer.zemris.java.hw05.db;

/**
 * Interface that represents a way for comparing two {@code String} objects.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public interface IComparisonOperator {

	/**
	 * Method that accepts and compares two {@code String} values. </br>
	 * It returns <code>true</code> if given values satisfy condition that is specified by operator, </br>
	 * <code>false</code> otherwise.
	 * 
	 * @param value1 first {@code String} object
	 * @param value2 second {@code String} object
	 * @return       <code>true</code> if given values satisfy condition that is specified by operator, </br>
	 * 				 <code>false</code> otherwise.
	 */
	public boolean satisfied(String value1, String value2);
	
}
