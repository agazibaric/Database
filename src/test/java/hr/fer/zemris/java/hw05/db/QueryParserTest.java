package hr.fer.zemris.java.hw05.db;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class QueryParserTest {

	@Test
	public void testDirectQuery() {
		String query = "jmbag = \"0036363636\" ";
		QueryParser parser = new QueryParser(query);
		
		boolean condition = parser.isDirectQuery();
		String actualJmbag = parser.getQueriedJMBAG();
		String expectedJmbag = "0036363636";
		
		Assert.assertTrue(condition);
		Assert.assertEquals(expectedJmbag, actualJmbag);
	}
	
	@Test
	public void testNotDirectQuery() {
		String query = "jmbag <= \"0036363636\" ";
		QueryParser parser = new QueryParser(query);
		
		boolean condition = !parser.isDirectQuery();
		Assert.assertTrue(condition);
	}
	
	@Test (expected = IllegalStateException.class)
	public void testNotDirectQueryException() {
		String query = "jmbag <= \"0036363636\" ";
		QueryParser parser = new QueryParser(query);
		parser.getQueriedJMBAG();
	}
	
	
	@Test
	public void testConditionalExpressionsSize() {
		String query = "jmbag = \"0036363636\" AnD lastName LIKE \"B*\" ";
		QueryParser parser = new QueryParser(query);
		
		boolean condition = !parser.isDirectQuery();
		List<ConditionalExpression> expressions = parser.getQuery();
		int actualNumOfExpressions = expressions.size();
		int expectedNumOfExpressions = 2;
		
		Assert.assertTrue(condition);
		Assert.assertEquals(expectedNumOfExpressions, actualNumOfExpressions);
	}
	
	@Test
	public void testConditionalExpressionsStructure() {
		String query = "jmbag != \"0036363636\" AnD firstName LIKE \"B*\" ";
		QueryParser parser = new QueryParser(query);
		
		List<ConditionalExpression> expressions = parser.getQuery();
		ConditionalExpression[] expectedExpressions = new ConditionalExpression[] {
			new ConditionalExpression(FieldValueGetters.JMBAG, "0036363636", ComparisonOperators.NOT_EQUALS),
			new ConditionalExpression(FieldValueGetters.FIRST_NAME, "B*", ComparisonOperators.LIKE)
		};
		
		for (int i = 0; i < expressions.size(); i++) {
			ConditionalExpression expression = expressions.get(i);
			IComparisonOperator actualOperator = expression.getComparisonOperator();
			IFieldValueGetter actualValueGetter = expression.getFieldValueGetter();
			String actualLiteral = expression.getLiteral();
			
			Assert.assertEquals(expectedExpressions[i].getComparisonOperator(), actualOperator);
			Assert.assertEquals(expectedExpressions[i].getFieldValueGetter(), actualValueGetter);
			Assert.assertEquals(expectedExpressions[i].getLiteral(), actualLiteral);
		}
	}
	
	
}
