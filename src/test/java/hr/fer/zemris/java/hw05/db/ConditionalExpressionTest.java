package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class ConditionalExpressionTest {
	
	
	@Test
	public void testLastNameLike() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.LAST_NAME;
		String literal = "P*";
		IComparisonOperator comparisonOperator = ComparisonOperators.LIKE;
		StudentRecord record = new StudentRecord("0036363636", "Parser", "Lexer", 2);
		ConditionalExpression expression = new ConditionalExpression(fieldValueGetter, literal, comparisonOperator);
		
		boolean condition = expression.getComparisonOperator().satisfied(
							expression.getFieldValueGetter().get(record), 
							expression.getLiteral()
							);
		Assert.assertTrue(condition);
	}

	@Test
	public void testFirstNameLike() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.FIRST_NAME;
		String literal = "*er";
		IComparisonOperator comparisonOperator = ComparisonOperators.LIKE;
		StudentRecord record = new StudentRecord("0036363636", "Parser", "Lexer", 2);
		ConditionalExpression expression = new ConditionalExpression(fieldValueGetter, literal, comparisonOperator);
		
		boolean condition = expression.getComparisonOperator().satisfied(
							expression.getFieldValueGetter().get(record), 
							expression.getLiteral()
							);
		Assert.assertTrue(condition);
	}
	
	@Test
	public void testJmbagLessThen() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.JMBAG;
		String literal = "0036999999";
		IComparisonOperator comparisonOperator = ComparisonOperators.LESS;
		StudentRecord record = new StudentRecord("0036363636", "Parser", "Lexer", 2);
		ConditionalExpression expression = new ConditionalExpression(fieldValueGetter, literal, comparisonOperator);
		
		boolean condition = expression.getComparisonOperator().satisfied(
							expression.getFieldValueGetter().get(record), 
							expression.getLiteral()
							);
		Assert.assertTrue(condition);
	}
	
	@Test
	public void testJmbagEqualsTo() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.JMBAG;
		String literal = "0036363636";
		IComparisonOperator comparisonOperator = ComparisonOperators.EQUALS;
		StudentRecord record = new StudentRecord("0036363636", "Parser", "Lexer", 2);
		ConditionalExpression expression = new ConditionalExpression(fieldValueGetter, literal, comparisonOperator);
		
		boolean condition = expression.getComparisonOperator().satisfied(
							expression.getFieldValueGetter().get(record), 
							expression.getLiteral()
							);
		Assert.assertTrue(condition);
	}
	
	@Test
	public void testJmbagNotEqualsTo() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.JMBAG;
		String literal = "0036363637";
		IComparisonOperator comparisonOperator = ComparisonOperators.NOT_EQUALS;
		StudentRecord record = new StudentRecord("0036363636", "Parser", "Lexer", 2);
		ConditionalExpression expression = new ConditionalExpression(fieldValueGetter, literal, comparisonOperator);
		
		boolean condition = expression.getComparisonOperator().satisfied(
							expression.getFieldValueGetter().get(record), 
							expression.getLiteral()
							);
		Assert.assertTrue(condition);
	}
	
}
