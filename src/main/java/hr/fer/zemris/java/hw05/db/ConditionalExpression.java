package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class accepts three objects that are representation of three basic parts of query language. </br>
 * Class is used for simplifying and storing one query conditional expression.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class ConditionalExpression {

	/** query field value getter */
	private IFieldValueGetter fieldValueGetter;
	/** query comparison operator */
	private IComparisonOperator comparisonOperator;
	/** literal that is used for comparing */
	private String literal;
	
	/**
	 * Constructor that creates new {@code ConditionalExpression} object
	 * 
	 * @param fieldValueGetter	 {@link IFieldValueGetter} query field value getter
	 * @param literal			 {@code String} query literal
	 * @param comparisonOperator {@link IComparisonOperator} query comparison operator
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String literal,
			IComparisonOperator comparisonOperator) {
		this.fieldValueGetter = Objects.requireNonNull(fieldValueGetter);
		this.comparisonOperator = Objects.requireNonNull(comparisonOperator);
		this.literal = Objects.requireNonNull(literal);
	}

	/**
	 * Method returns field value getter.
	 * 
	 * @return {@link IFieldValueGetter}
	 */
	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	/**
	 * Method returns comparison operator.
	 * 
	 * @return {@link IComparisonOperator}
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * String returns literal that is used for comparing.
	 * 
	 * @return {@code String} literal
	 */
	public String getLiteral() {
		return literal;
	}
	
}
