package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Program that allows user to interact with student database. </br>
 * It uses simple query language for student information retrieval. </br>
 * To exit the program just type "exit".
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 */
public class StudentDB {
	
	/** string used to exit the program */
	private static final String exitString = "EXIT";
	
	/** message that is printed out when user exits the program */
	private static final String exitMessage = "Goodbye!";
	
	/** prompt symbol that program writes out before every query input */
	private static final String promptSymbol = "> ";
	
	/**
	 * Main method. Accepts no arguments.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String path = "./src/main/resources/database.txt";
		List<String> entries = null;
		
		try {
			entries = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			System.out.println("Unable to read the file. Path: " + path);
			e.printStackTrace();
		}
		
		StudentDatabase dataBase = new StudentDatabase(entries);
		getUserInput(sc, dataBase);
	}
	
	/**
	 * Method used for getting query input from user. </br>
	 * It prints out result of query input to the standard output.
	 * 
	 * @param sc       {@link Scanner} that is used for getting query inputs
	 * @param dataBase {@link StudentDatabase} from which student informations are obtained.
	 */
	private static void getUserInput(Scanner sc, StudentDatabase dataBase) {
		while (true) {
			try {
				printPromptSymbol();
				String userInput = sc.nextLine();

				if (userInput.toUpperCase().equals(exitString))
					break;

				String query = getQueryFromString(userInput);
				QueryParser parser = new QueryParser(query);

				if (parser.isDirectQuery()) {
					StudentRecord r = dataBase.forJMBAG(parser.getQueriedJMBAG());
					printInfoDirectQuery(r);
				} else {
					List<StudentRecord> records = dataBase.filter(new QueryFilter(parser.getQuery()));
					printInfoForRecordList(records);
				}
			} catch (IllegalArgumentException | QueryParserException ex) {
				System.out.println(ex.getMessage());
			}
		}
		printExitMessage();
	}
	
	/**
	 * Method used for printing out students record from given list of records.
	 * 
	 * @param records {@code List}<{@link StudentRecord}> student record list that is printed out
	 */
	private static void printInfoForRecordList(List<StudentRecord> records) {
		if (records.size() == 0) {
			System.out.println("Records selected: 0");
			return;
		}
		
		int maxX = 0, maxY = 0, maxZ = 0;
		for (StudentRecord record : records) {
			int x = record.getJmbag().length();
			int y = record.getLastName().length();
			int z = record.getFirstName().length();
			maxX = maxX < x ? x : maxX;
			maxY = maxY < y ? y : maxY;
			maxZ = maxZ < z ? z : maxZ;
		}
		
		printFrame(maxX, maxY, maxZ);
		for (StudentRecord record : records)
			printRecordInfo(record, maxX, maxY, maxZ);
		printFrame(maxX, maxY, maxZ);
		System.out.printf("Records selected: %d%n", records.size());
		
	}
	
	/**
	 * Method prints out student record info on standard output.
	 * 
	 * @param record {@link StudentRecord} that is printed
	 * @param x		 jmbag length
	 * @param y      last name length
	 * @param z      first name length
	 */
	private static void printRecordInfo(StudentRecord record, int x, int y, int z) {
		StringBuilder sb = new StringBuilder("| ");
		
		int jmbagLength = record.getJmbag().length();
		int lastNameLength = record.getLastName().length();
		int firstNameLength = record.getFirstName().length();
		int x2 = x - jmbagLength;
		int y2 = y - lastNameLength;
		int z2 = z - firstNameLength;
		
		sb.append(record.getJmbag());
		sb = appendString(sb, " ", x2);
		sb.append(" | ").append(record.getLastName());
		sb = appendString(sb, " ", y2);
		sb.append(" | ").append(record.getFirstName());
		sb = appendString(sb, " ", z2);
		sb.append(" | ").append(record.getFinalGrade());
		sb.append(" |");
		
		System.out.println(sb);
	}
	
	/**
	 * Method prints out student record that is got from Direct Query. </br>
	 * 
	 * @param record {@link StudentRecord} student record that is printed
	 */
	private static void printInfoDirectQuery(StudentRecord record) {
		System.out.println("Using index for record retrieval.");
		if (record == null) {
			System.out.println("Records selected: 0");
		} else {
			int x = record.getJmbag().length();
			int y = record.getLastName().length();
			int z = record.getFirstName().length();
			printFrame(x, y, z);
			printRecordInfo(record, x, y, z);
			printFrame(x, y, z);
			System.out.println("Records selected: 1");
		}
		
	}
	
	/**
	 * Method prints out the table frame.
	 * 
	 * @param x jmbag length
	 * @param y first name length
	 * @param z last name length
	 */
	private static void printFrame(int x, int y, int z) {
		StringBuilder sb = new StringBuilder("+");
		sb = appendString(sb, "=", x + 2).append("+");
		sb = appendString(sb, "=", y + 2).append("+");
		sb = appendString(sb, "=", z + 2).append("+");
		sb = appendString(sb, "=", 3).append("+");
		System.out.println(sb);
	}
	
	/**
	 * Helper method that concatenates given string {@code s} onto a {@link StringBuilder} {@code sb} given number of times.
	 * 
	 * @param sb            {@link StringBuilder} to which given string is concatenated
	 * @param s				{@link String} that is concatenated
	 * @param numberOfTimes number of times that is given string concatenated 
	 * @return
	 */
	private static StringBuilder appendString(StringBuilder sb, String s, int numberOfTimes) {
		for (int i = 0; i < numberOfTimes; i++) 
			sb.append(s);
		return sb;
	}
	
	/**
	 * Method used for getting the right query from user input.
	 * 
	 * @param input user input
	 * @return      {@code String} that represents right input for parsing
	 * @throws      IllegalArgumentException if given query input is invalid
	 */
	private static String getQueryFromString(String input) {
		String queryInput = input.trim();
		
		int indexOfQuery = input.indexOf("query");
		if (indexOfQuery == -1 || indexOfQuery != 0)
			throw new IllegalArgumentException("Invalid query input. Missing keyword 'query' at the beginning of statment");
		
		return queryInput.substring(5, queryInput.length()).trim();
	}
	
	/**
	 * Method prints out exit message to user.
	 */
	private static void printExitMessage() {
		System.out.println(exitMessage);
	}
	
	/**
	 * Method prints out prompt symbol.
	 */
	private static void printPromptSymbol() {
		System.out.print(promptSymbol);
	}

}
