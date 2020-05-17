package hr.fer.zemris.java.hw05.db;

/**
 * Class represents exception that lexer uses when it encounters
 * invalid input during lexical analysis.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class QueryLexerException extends RuntimeException {

	/**
	 * Default serial number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public QueryLexerException() {
		super();
	}
	
	/**
	 * Constructor that accepts message about situation that occurred during lexical analysis
	 * 
	 * @param message message that describes what went wrong during lexical analysis
	 */
	public QueryLexerException(String message) {
		super(message);
	}
}
