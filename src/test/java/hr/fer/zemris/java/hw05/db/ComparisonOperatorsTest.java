package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class ComparisonOperatorsTest {

	@Test
	public void testForOperatorLess() {
		IComparisonOperator operator = ComparisonOperators.LESS;
		String value1 = "Lexer";
		String value2 = "Parser";
		
		Assert.assertTrue(operator.satisfied(value1, value2));
		Assert.assertFalse(operator.satisfied(value2, value1));
		Assert.assertFalse(operator.satisfied(value1, value1));
	}
	
	@Test
	public void testForOperatorLessOrEquals() {
		IComparisonOperator operator = ComparisonOperators.LESS_OR_EQUALS;
		String value1 = "Lexer";
		String value2 = "Parser";
		
		Assert.assertTrue(operator.satisfied(value1, value2));
		Assert.assertTrue(operator.satisfied(value1, value1));
		Assert.assertFalse(operator.satisfied(value2, value1));
	}
	
	@Test
	public void testForOperatorGreater() {
		IComparisonOperator operator = ComparisonOperators.GREATER;
		String value1 = "Parser";
		String value2 = "Lexer";
		
		Assert.assertTrue(operator.satisfied(value1, value2));
		Assert.assertFalse(operator.satisfied(value2, value1));
		Assert.assertFalse(operator.satisfied(value1, value1));
	}
	
	@Test
	public void testForOperatorGreaterOrEquals() {
		IComparisonOperator operator = ComparisonOperators.GREATER_OR_EQUALS;
		String value1 = "Parser";
		String value2 = "Lexer";
		
		Assert.assertTrue(operator.satisfied(value1, value2));
		Assert.assertTrue(operator.satisfied(value1, value1));
		Assert.assertFalse(operator.satisfied(value2, value1));
	}
	
	@Test
	public void testForOperatorEquals() {
		IComparisonOperator operator = ComparisonOperators.EQUALS;
		String value1 = "Parser";
		String value2 = "Lexer";
		
		Assert.assertFalse(operator.satisfied(value1, value2));
		Assert.assertTrue(operator.satisfied(value1, value1));
		Assert.assertFalse(operator.satisfied(value2, value1));
	}
	
	@Test
	public void testForOperatorNotEquals() {
		IComparisonOperator operator = ComparisonOperators.NOT_EQUALS;
		String value1 = "Parser";
		String value2 = "Lexer";
		
		Assert.assertTrue(operator.satisfied(value1, value2));
		Assert.assertFalse(operator.satisfied(value1, value1));
		Assert.assertTrue(operator.satisfied(value2, value1));
	}
	
	@Test
	public void testForOperatorLike1() {
		IComparisonOperator operator = ComparisonOperators.LIKE;
		String value1 = "Parser";
		String value2 = "Lexer";
		
		Assert.assertFalse(operator.satisfied(value1, value2));
		Assert.assertTrue(operator.satisfied(value1, value1));
		Assert.assertFalse(operator.satisfied(value2, value1));
	}
	
	@Test
	public void testForOperatorLike2() {
		IComparisonOperator operator = ComparisonOperators.LIKE;
		String value1 = "Parser";
		String value2 = "P*";
		
		Assert.assertTrue(operator.satisfied(value1, value2));
	}

	@Test
	public void testForOperatorLike3() {
		IComparisonOperator operator = ComparisonOperators.LIKE;
		String value1 = "Parser";
		String value2 = "Parser*";
		
		Assert.assertTrue(operator.satisfied(value1, value2));
	}
	
	@Test
	public void testForOperatorLike4() {
		IComparisonOperator operator = ComparisonOperators.LIKE;
		String value1 = "Parser";
		String value2 = "*er";
		
		Assert.assertTrue(operator.satisfied(value1, value2));
	}
	
	@Test
	public void testForOperatorLike5() {
		IComparisonOperator operator = ComparisonOperators.LIKE;
		String value1 = "Parser";
		String value2 = "*Parser";
		
		Assert.assertTrue(operator.satisfied(value1, value2));
	}
	
	@Test
	public void testForOperatorLike6() {
		IComparisonOperator operator = ComparisonOperators.LIKE;
		String value1 = "Zagreb";
		String value2 = "Aba*";
		
		Assert.assertFalse(operator.satisfied(value1, value2));
	}
	
	@Test
	public void testForOperatorLike7() {
		IComparisonOperator operator = ComparisonOperators.LIKE;
		String value1 = "AAA";
		String value2 = "AA*AA";
		
		Assert.assertFalse(operator.satisfied(value1, value2));
	}

	@Test
	public void testForOperatorLike8() {
		IComparisonOperator operator = ComparisonOperators.LIKE;
		String value1 = "AAAA";
		String value2 = "AA*AA";
		
		Assert.assertTrue(operator.satisfied(value1, value2));
	}
	
	@Test
	public void testForOperatorLike9() {
		IComparisonOperator operator = ComparisonOperators.LIKE;
		String value1 = "ACstratocasterDC";
		String value2 = "AC*DC";
		
		Assert.assertTrue(operator.satisfied(value1, value2));
	}
	
}
