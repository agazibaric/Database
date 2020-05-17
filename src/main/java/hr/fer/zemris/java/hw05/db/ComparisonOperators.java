package hr.fer.zemris.java.hw05.db;

/**
 * Class contains {@link IComparisonOperator} implementations.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class ComparisonOperators {
	
	public static final String wildcard = "*";
	
	/** 
	 * Operator that checks if the first {@code String} value is less then second {@code String} value.
	 */
	public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;
	/** 
	 * Operator that checks if the first {@code String} value is less or equals then second {@code String} value.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;
	/** 
	 * Operator that checks if the first {@code String} value is greater then second {@code String} value.
	 */
	public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;
	/** 
	 * Operator that checks if the first {@code String} value is greater or equals then second {@code String} value.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;
	/** 
	 * Operator that checks if the first {@code String} value is equals to second {@code String} value.
	 */
	public static final IComparisonOperator EQUALS = (v1, v2) -> v1.compareTo(v2) == 0;
	/** 
	 * Operator that checks if the first {@code String} value is not equals to second {@code String} value.
	 */
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> v1.compareTo(v2) != 0;
	
	/** 
	 * Operator that checks if the first {@code String} value satisfies pattern 
	 * given by the second {@code String} value.
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		@Override
		public boolean satisfied(String value1, String value2) {
			if (!value2.contains(wildcard))
				return value1.equals(value2);
			if (value1.length() < value2.length() - 1)
				return false;
			
			int indexOfWildcard = value2.indexOf(wildcard);
			return value1.startsWith(value2.substring(0, indexOfWildcard)) &&
				   value1.endsWith(value2.substring(indexOfWildcard + 1, value2.length()));
		}
	};

}
