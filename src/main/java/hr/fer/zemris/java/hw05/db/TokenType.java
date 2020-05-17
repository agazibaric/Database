package hr.fer.zemris.java.hw05.db;

/**
 * Class represents different types of {@link Token}.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 */
public enum TokenType {
	/**
	 * represents end of query input
	 */
	EOF,
	/**
	 * represents literal input
	 */
	TEXT,
	/**
	 * represents query keyword
	 */
	KEYWORD,
	/**
	 * represents keyword for separating conditional expressions
	 */
	AND,
	/**
	 * represents query operator
	 */
	OPERATOR
}