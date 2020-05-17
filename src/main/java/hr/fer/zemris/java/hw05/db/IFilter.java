package hr.fer.zemris.java.hw05.db;

/**
 * Interface represents general filter that accepts some {@link StudentRecord} </br>
 * and returns {@code boolean} expression which is true if given record satisfies some condition, otherwise false.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public interface IFilter {

	/**
	 * Method that accepts given record if it satisfies condition specified by filter.
	 * 
	 * @param record {@link StudentRecord} that is tested
	 * @return       {@code true} if given record satisfies condition, </br>
	 * 				 {@code false} otherwise
	 */
	boolean accepts(StudentRecord record);
	
}
