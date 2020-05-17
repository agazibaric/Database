package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Class represents filter used for query language. </br>
 * It accepts through constructor {@link ConditionalExpression} objects </br>
 * that are used in {@code accepts} method.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class QueryFilter implements IFilter {
	
	/** conditions that tested in accepts method */
	private List<ConditionalExpression> expressions;

	/**
	 * Constructor that creates new {@code QueryFilter} object.
	 * 
	 * @param expressions {@code List<ConditionalExpression>} that are used for testing student record
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression condition : expressions) {
			if (!condition.getComparisonOperator().satisfied(condition.getFieldValueGetter().get(record), condition.getLiteral()))
				return false;
		}
		return true;
	}
}
