package hr.fer.zemris.java.hw05.db;

/**
 * Class contains {@link IFieldValueGetter} implementations.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class FieldValueGetters {
	
	/**
	 * {@code IFieldValueGetter} that returns first name from given {@link StudentRecord}
	 */
	public static final IFieldValueGetter FIRST_NAME = r -> r.getFirstName();
	/**
	 * {@code IFieldValueGetter} that returns last name from given {@link StudentRecord}
	 */
	public static final IFieldValueGetter LAST_NAME = r -> r.getLastName();
	/**
	 * {@code IFieldValueGetter} that returns jmbag from given {@link StudentRecord}
	 */
	public static final IFieldValueGetter JMBAG = r -> r.getJmbag();
	
	
}
