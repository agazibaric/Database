package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Class that represents data base which stores {@link StudentRecord} objects. </br>
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class StudentDatabase {

	/** map for mapping student's jmbag with its record */
	private SimpleHashtable<String, StudentRecord> records;
	/** list of student records */
	private List<StudentRecord> recordsList;
	/** represents number of student informations in one entry if student has only one last name */
	private static final int numOfPartsOneLastName = 4;
	
	/**
	 * Constructor that creates new {@code StudentDatabase} object.
	 * 
	 * @param entries {@code List<String>} that represents students records
	 */
	public StudentDatabase(List<String> entries) {
		records = new SimpleHashtable<>();
		recordsList = new ArrayList<>();
		createStudentRecords(entries);
	}
	
	/**
	 * Method that extracts entries one by one </br>
	 * and sends them into process of creating student records
	 * 
	 * @param entries {@code List<String>} that represents students records
	 */
	private void createStudentRecords(List<String> entries) {
		for (String entry : entries) {
			getStudentRecord(entry);
		}
	}
	
	/**
	 * Method that creates and adds into data base new student record from given entry.
	 * 
	 * @param entry {@code String} that represents one student record
	 */
	private void getStudentRecord(String entry) {
		String[] parts = entry.split("\\s+");
		try {
			// jmbag must be at index 0
			String jmbag = parts[0];
			
			StringBuilder lastNameBuilder = new StringBuilder();
			if (parts.length == numOfPartsOneLastName) {
				// one last name
				lastNameBuilder.append(parts[1]);
			} else if (parts.length > numOfPartsOneLastName){
				// more last names
				int numOfLastNames = parts.length - 3; // 3 for jmbag + first name + grade
				for (int index = 1; index <= numOfLastNames; index++) {
					lastNameBuilder.append(parts[index]).append(" ");
				}
			} else {
				throw new IllegalArgumentException("Invalid student record information. Record was: " + entry);
			}
			String lastName = lastNameBuilder.toString().trim();
			
			// first name is placed before the last index
			String firstName = parts[parts.length - 2];
			
			// final grade is at last index
			int finalGrade = Integer.parseInt(parts[parts.length - 1]);
			
			// put the new student record into records map
			StudentRecord newStudentRecord = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			records.put(jmbag, newStudentRecord);
			recordsList.add(newStudentRecord);
			
		} catch(NumberFormatException | IndexOutOfBoundsException ex) {
			throw new IllegalArgumentException("Invalid student record information. Record was: " + entry);
		}
	}
	
	/**
	 * Method returns student record from given {@code jmbag} in complexity O(1).
	 * 
	 * @param jmbag {@code String} jmbag whose student record is returned
	 * @return      {@link StudentRecord} that has given jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return records.get(jmbag);
	}
	
	/**
	 * Method that filters data base and returns list of student records </br>
	 * that satisfies condition specified by given filter.
	 * 
	 * @param filter {@link IFilter} that is used for filtering data base
	 * @return		 {@code List<StudentRecord>} which satisfies given condition specified by {@code filter}
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredList = new ArrayList<>();
		
		for (StudentRecord record : recordsList) {
			if (filter.accepts(record))
				filteredList.add(record);
		}
		return filteredList;
	}
	
}
