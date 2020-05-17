package hr.fer.zemris.java.hw05.db;

import org.junit.Assert;
import org.junit.Test;

public class FieldValueGettersTest {
	
	@Test
	public void testFirstNameGetter() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.FIRST_NAME;
		StudentRecord record = new StudentRecord("0036363636", "Parser", "Lexer", 2);
		String actualValue = fieldValueGetter.get(record);
		String expectedValue = "Lexer";
		
		Assert.assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testLastNameGetter() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.LAST_NAME;
		StudentRecord record = new StudentRecord("0036363636", "Parser", "Lexer", 2);
		String actualValue = fieldValueGetter.get(record);
		String expectedValue = "Parser";
		
		Assert.assertEquals(expectedValue, actualValue);
	}
	
	@Test
	public void testJmbagGetter() {
		IFieldValueGetter fieldValueGetter = FieldValueGetters.JMBAG;
		StudentRecord record = new StudentRecord("0036363636", "Parser", "Lexer", 2);
		String actualValue = fieldValueGetter.get(record);
		String expectedValue = "0036363636";
		
		Assert.assertEquals(expectedValue, actualValue);
	}
	
}
