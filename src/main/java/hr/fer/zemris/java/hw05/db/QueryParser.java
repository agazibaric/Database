package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

/**
 * Class represents a syntax analyzer for query language. </br>
 * It accepts query input and offers methods for checking if the query was Direct Query </br>
 * and method for returning jmbag in that case. </br>
 * If the query was not Direct Query, it offers method for getting list of {@link ConditionalExpression} </br>
 * that given query contains.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class QueryParser {

	/** lexical analyzer */
	private QueryLexer lexer;
	/** flag that shows if it's direct query */
	private boolean isDirectQuery = false;
	/** helper variable for storing jmbag if it's direct query */
	private String jmbagValue;
	/** list of conditional expression */
	private List<ConditionalExpression> expressions = new LinkedList<>();
	
	/**
	 * Constructor for creating new {@code QueryParser} object.
	 * 
	 * @param query {@code String} that is analyzed
	 */
	public QueryParser(String query) {
		lexer = new QueryLexer(query);
		parseQuery();
	}
	
	/**
	 * Method that analyzes input query
	 */
	private void parseQuery() {
		try {
			lexer.nextToken();

			if (!isTokenOfType(TokenType.KEYWORD))
				throw new QueryParserException("Keyword is expected.");

			if (lexer.getToken().getValue().toString().toUpperCase().equals("JMBAG")) {
				getJMBAG();

				if(isTokenOfType(TokenType.EOF))
					return;

				if (!isTokenOfType(TokenType.AND))
					throw new QueryParserException("AND keyword was expected");
				
				lexer.nextToken();
				
				if(!isTokenOfType(TokenType.KEYWORD))
					throw new QueryParserException("Keyword was expected.");

				loadConditionalExpressions();
			} else {
				loadConditionalExpressions();
			}
		} catch (QueryLexerException ex) {
			throw new QueryParserException(ex.getMessage());
		}
		
	}
	
	/**
	 * Helper method for analyzing jmbag query
	 */
	private void getJMBAG() {
		lexer.nextToken();
		
		if (!isTokenOfType(TokenType.OPERATOR))
			throw new QueryParserException("Operator was expected.");
		
		if (lexer.getToken().getValue().toString().equals("=")) {
			lexer.nextToken();
			
			if(!isTokenOfType(TokenType.TEXT))
				throw new QueryParserException("JMBAG number was expected. Must be in double quotes.");
			
			jmbagValue = (String) lexer.getToken().getValue();
			lexer.nextToken();
			
			// if there's nothing else then it's direct query
			if(isTokenOfType(TokenType.EOF)) {
				isDirectQuery = true;
			} else {
				IFieldValueGetter fieldValueGetter = FieldValueGetters.JMBAG;
				IComparisonOperator comparisonOperator = ComparisonOperators.EQUALS;
				expressions.add(new ConditionalExpression(fieldValueGetter, jmbagValue, comparisonOperator));
			}
		} else {
			// if it's not direct query; operator is not '='
			IFieldValueGetter fieldValueGetter = FieldValueGetters.JMBAG;
			String operator = (String) lexer.getToken().getValue();
			IComparisonOperator comparisonOperator = getComparisonOperator(operator);
			lexer.nextToken();
			
			if(!isTokenOfType(TokenType.TEXT))
				throw new QueryParserException("JMBAG number was expected. Must be in double quotes.");
			
			String literal = (String) lexer.getToken().getValue();
			
			expressions.add(new ConditionalExpression(fieldValueGetter, literal, comparisonOperator));
			lexer.nextToken();
		}
	}
	
	/**
	 * Helper method for analyzing and loading conditional expressions from query
	 */
	private void loadConditionalExpressions() {
		while (true) {
			getConditionalExpression();
			lexer.nextToken();
			
			if(isTokenOfType(TokenType.EOF))
				break;
			
			if(!isTokenOfType(TokenType.AND))
				throw new QueryParserException("AND was expected");
			
			lexer.nextToken();
			
			if(!isTokenOfType(TokenType.KEYWORD))
				throw new QueryParserException("Keyword was expected");
		}
	}
	
	/**
	 * Helper method for analyzing and getting one conditional expression from query
	 */
	private void getConditionalExpression() {
		
		String keyword = (String) lexer.getToken().getValue();
		IFieldValueGetter fieldValueGetter = getFieldValue(keyword);
		
		lexer.nextToken();
		
		if (!isTokenOfType(TokenType.OPERATOR))
			throw new QueryParserException("Invalid input. Operator was expected.");
		
		String operator = (String) lexer.getToken().getValue();
		IComparisonOperator comparisonOperator = getComparisonOperator(operator);
		
		lexer.nextToken();
		
		if (!isTokenOfType(TokenType.TEXT))
			throw new QueryParserException("Invalid input. Text was expected.");
		
		String literal = (String) lexer.getToken().getValue();
		
		expressions.add(new ConditionalExpression(fieldValueGetter, literal, comparisonOperator));
	}
	
	/**
	 * Method that returns proper comparison operator from given string
	 * 
	 * @param operator {@code String} for which comparison operator is returned
	 * @return         {@link IComparisonOperator} that represents given {@code operator}
	 * @throws         QueryParserException if given {@code operator} is not valid
	 */
	private IComparisonOperator getComparisonOperator(String operator) {
		switch (operator) {
		case "<":
			return ComparisonOperators.LESS;
		case ">":
			return ComparisonOperators.GREATER;
		case "=":
			return ComparisonOperators.EQUALS;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			throw new QueryParserException("Invalid operator. You entered: " + operator);
			
		}
	}
	
	/**
	 * Helper method that returns proper field value getter form given string.
	 * 
	 * @param keyword {@code String} for which field value getter is returned.
	 * @return        {@link IFieldValueGetter} that represents given {@code keyword}
	 * @throws        QueryParserException if given {@code keyword} is not valid
	 */
	private IFieldValueGetter getFieldValue(String keyword) {
		switch(keyword.toUpperCase()) {
		case "LASTNAME":
			return FieldValueGetters.LAST_NAME;
		case "FIRSTNAME":
			return FieldValueGetters.FIRST_NAME;
		case "JMBAG":
			return FieldValueGetters.JMBAG;
		default:
			throw new QueryParserException("Invalid keyword. You entered: " + keyword);
		}
	}
	
	/**
	 * Method that shows if given query input is direct query.
	 * 
	 * @return <code>true</code> if given query is direct query, </br>
	 * 		   <code>false</code> otherwise
	 */
	public boolean isDirectQuery() {
		return isDirectQuery;
	}
	
	/**
	 * Method that returns jmbag from given direct query.
	 * 
	 * @return {@code String} that represents jmbag in direct query.
	 * @throws IllegalStateException if called when given query is not direct query
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery)
			throw new IllegalStateException("It was not Direct Query.");
		
		return jmbagValue;
	}
	
	/**
	 * Method that returns conditional expression from given query input.
	 * 
	 * @return {@code List}<{@link ConditionalExpression}> that is given in inputed query
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}
	
	/**
	 * Method that checks if a current token is of a given type.
	 * 
	 * @param type {@link TokenType} whose equality is checked
	 * @return     <code>true</code> if current token type is equal to given token type, </br>
	 * 			   <code>false</code> otherwise
	 */
	private boolean isTokenOfType(TokenType type) {
		return lexer.getToken().getType() == type;
	}
	
}
