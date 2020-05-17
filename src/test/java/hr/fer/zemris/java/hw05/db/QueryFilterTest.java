package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class QueryFilterTest {

	
	@Test
	public void testFilterForJmbagAndFirstName() {
		String query = "jmbag = \"0036363636\" AnD firstName LIKE \"L*\" ";
		QueryParser parser = new QueryParser(query);
		IFilter filter = new QueryFilter(parser.getQuery());
		StudentRecord record1 = new StudentRecord("0036363636", "Parser", "Lexer", 2);
		StudentRecord record2 = new StudentRecord("0036666666", "Parser", "Lexer", 3);
		
		boolean condition1 = filter.accepts(record1);
		boolean condition2 = filter.accepts(record2);
		Assert.assertTrue(condition1);
		Assert.assertFalse(condition2);
	}
	
	@Test
	public void testFilterForFirstAndLastName() {
		String query = "lastName LIKE \"*er\" AnD firstName LIKE \"L*\" ";
		QueryParser parser = new QueryParser(query);
		IFilter filter = new QueryFilter(parser.getQuery());
		StudentRecord record1 = new StudentRecord("0036363636", "Parser", "Lexer", 2);
		StudentRecord record2 = new StudentRecord("0036666666", "Parserr", "Lexer", 3);
		
		boolean condition1 = filter.accepts(record1);
		boolean condition2 = filter.accepts(record2);
		Assert.assertTrue(condition1);
		Assert.assertFalse(condition2);
	}
	
	@Test
	public void testFilterForJmbagFirstAndLastName() {
		String query = "jmbag  \t   >= \"0033333333\" and \n lastName LIKE \"*er\" AnD firstName LIKE \"L*\" ";
		QueryParser parser = new QueryParser(query);
		IFilter filter = new QueryFilter(parser.getQuery());
		StudentRecord record1 = new StudentRecord("0036363636", "Parser", "Lexer", 2);
		StudentRecord record2 = new StudentRecord("0011111111", "Parser", "Lexer", 3);
		
		boolean condition1 = filter.accepts(record1);
		boolean condition2 = filter.accepts(record2);
		Assert.assertTrue(condition1);
		Assert.assertFalse(condition2);
	}
	
}
