package hr.fer.zemris.java.hw05.db;

/**
 * Class represents exception that can be used by {@link QueryParser} </br>
 * if given query was invalid.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class QueryParserException extends RuntimeException {

	/**
	 * default serial number
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor that accepts message about situation that occurred during process of parsing
	 * 
	 * @param message {@code String} that describes what went wrong during process of parsing
	 */
	public QueryParserException(String message) {
		super(message);
	}
	
}
