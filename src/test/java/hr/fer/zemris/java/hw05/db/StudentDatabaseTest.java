package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StudentDatabaseTest {

	private static IFilter allTrue = r -> true;
	private static IFilter allFalse = r -> false;
	private static String path = "./src/main/resources/database.txt";
	
	@Test
	public void testAcceptAll() {
		List<String> entries = null;
		try {
			entries = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		StudentDatabase dataBase = new StudentDatabase(entries);
		
		List<StudentRecord> records = dataBase.filter(allTrue);
		int numOfRecordsExpected = 63;
		int numOFRecordsActual = records.size();
		Assert.assertEquals(numOfRecordsExpected, numOFRecordsActual);
	}

	@Test
	public void testRejectAll() {
		List<String> entries = null;
		try {
			entries = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		StudentDatabase dataBase = new StudentDatabase(entries);
		
		List<StudentRecord> records = dataBase.filter(allFalse);
		int numOfRecordsExpected = 0;
		int numOFRecordsActual = records.size();
		Assert.assertEquals(numOfRecordsExpected, numOFRecordsActual);
	}
	
}
