package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Class represents lexical analyzer for query language.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class QueryLexer {

	/** input string in chars */
	private char[] data;
	/** current token */
	private Token token;
	/** current data index to be processed */ 
	private int currentIndex;
	/** map of keywords */
	private static SimpleHashtable<String, Token> keywordsTokenMap = new SimpleHashtable<>();
	
	static {
		keywordsTokenMap.put("JMBAG", new Token(TokenType.KEYWORD, "JMBAG"));
		keywordsTokenMap.put("LASTNAME", new Token(TokenType.KEYWORD, "LASTNAME"));
		keywordsTokenMap.put("FIRSTNAME", new Token(TokenType.KEYWORD, "FIRSTNAME"));
		keywordsTokenMap.put("AND", new Token(TokenType.AND, null));
		keywordsTokenMap.put("LIKE", new Token(TokenType.OPERATOR, "LIKE"));
	}
	
	/**
	 * Constructor that creates new {@code QueryLexer} object.
	 * 
	 * @param query {@code String} that represents one query input that will be analyzed
	 */
	public QueryLexer(String query) {
		if (query == null) 
			throw new NullPointerException("Query input must not be null");
		
		data = query.toCharArray();
	}
	
	/**
	 * Method returns current token.
	 * 
	 * @return current {@link Token}
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Method that returns next token.
	 * 
	 * @return next {@link Token}
	 */
	public Token nextToken() {
		setNextToken();
		return token;
	}
	
	/**
	 * Method that does lexical analysis and sets next token
	 */
	private void setNextToken() {
		
		if (token != null && token.getType() == TokenType.EOF)
			throw new QueryLexerException("There is no more tokens");

		skipWhitespaces();
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		if (Character.isLetter(data[currentIndex])) {
			int beginningOfWord = currentIndex;
			currentIndex++;
			while(currentIndex < data.length && Character.isLetter(data[currentIndex])) {
				currentIndex++;
			}
			String word = new String(data, beginningOfWord, currentIndex - beginningOfWord);
			Token mappedToken = keywordsTokenMap.get(word.toUpperCase());
			if (mappedToken != null) {
				token = mappedToken;
				return;
			} else {
				throw new QueryLexerException("Word that you inputed is not keyword.\n You entered: " + word);
			}
		}
		
		
		if (data[currentIndex] == '\"') {
			currentIndex++;
			int beginningOfText = currentIndex;
			
			while (true) {
				if (currentIndex >= data.length)
					throw new QueryLexerException("Wrong input. Missing one double quote.");
				
				if (data[currentIndex] == '\"') 
					break;
				
				currentIndex++;
			}
			
			String text = new String(data, beginningOfText, currentIndex - beginningOfText);
			token = new Token(TokenType.TEXT, text);
			currentIndex++;
			return;
		}
		
		if (isOperator(data[currentIndex])) {
			setOperatorToken();
			return;
		}
		
		// everything else is invalid
		throw new QueryLexerException("Invalid input: Last character was: " + data[currentIndex]);
	}
	
	/**
	 * Helper method for setting operator token
	 */
	private void setOperatorToken() {
		
		if (currentIndex + 1 >= data.length)
			throw new QueryLexerException("Invalid input. After operator must come text");
		
		if (isOperator(data[currentIndex + 1]))  {
			// two symbol operator
				if(data[currentIndex + 1] != '=') 
					throw new QueryLexerException("Invalid operator.");
				
				if(data[currentIndex] == '=')
					throw new QueryLexerException("Invalid operator.");
				
				String operator = new String(data, currentIndex, 2);
				token = new Token(TokenType.OPERATOR, operator);
				currentIndex += 2;
		} else {
			// just one symbol operator
			if (data[currentIndex] == '!')
				throw new QueryLexerException("Invalid operator.");
			
			token = new Token(TokenType.OPERATOR, String.valueOf(data[currentIndex]));
			currentIndex++;
		}
	}
	
	/**
	 * Method that checks if given character is operator in query language.
	 * 
	 * @param c <code>char</code> that is tested
	 * @return  <code>true</code> if given character is operator, </br>
	 * 			<code>false</code> otherwise
	 */
	private boolean isOperator(char c) {
		return c == '<' || c == '=' || c == '>' || c == '!';
	}
	
	/**
	 * Private method for skipping any whitespaces.
	 * That includes: ' ', '\n', '\t', '\r'.
	 */
	private void skipWhitespaces() {
		while(currentIndex < data.length) {
			if (isWhitespace(data[currentIndex])) {
				currentIndex++;
				continue;
			}
			break;	
		}
	}
	
	/**
	 * Method checks if given character is a whitespace.
	 * That includes: ' ', '\n', '\t', '\r'.
	 * @param c character that is checked
	 * @return  <code>true</code> if <code>c</code> is a whitespace, otherwise <code>false</code>
	 */
	private boolean isWhitespace(char c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t';
	}
	
}
