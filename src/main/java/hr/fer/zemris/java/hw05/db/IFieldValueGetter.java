package hr.fer.zemris.java.hw05.db;

/**
 * Interface represents general value getter from {@link StudentRecord}.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public interface IFieldValueGetter {

	/**
	 * Method returns value that is specified by {@code IFieldValueGetter} </br>
	 * from given student record.
	 * 
	 * @param record {@link StudentRecord} whose value is returned
	 * @return		 {@code String} value from given student record.
	 */
	String get(StudentRecord record);
	
}
