package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class represents map-like data storage that is based on hash table. </br>
 * It stores values in couple form where given value is associated to the given key. </br>
 * It doesn't allow key to be {@code null}, but that is allowed for given value.
 * 
 * @author Ante Gazibarić
 * @version 1.0
 *
 * @param <K> key
 * @param <V> value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/** initial capacity of hash table */
	private static final int initialCapacity = 16;
	/** multiplier used for extending capacity of hash table */
	private static final int capacityMultiplier = 2;
	/** maximum percentage to which the table can be filled before it's resized */
	private static final double maxPercentage = 0.75;

	/** array of table entries used for storing keys and values*/
	private TableEntry<K, V>[] hashTable;
	/** number of entries in table */
	private int size;
	/** number used for counting modifications in hash table */
	private int modificationCount;

	/**
	 * Default constructor that creates new {@code SimpleHashtable} object.
	 */
	public SimpleHashtable() {
		this(initialCapacity);
	}

	/**
	 * Constructor that creates new {@code SimpleHashtable} object with given {@code capacity}.
	 * 
	 * @param capacity number of entries that hash table can store
	 */
	public SimpleHashtable(int capacity) {
		initSimpleHashTable(getValidCapacity(capacity));
	}

	/**
	 * Method used for initializing {@code SimpleHashtable}.
	 * 
	 * @param capacity number of entries that hash table can store
	 */
	@SuppressWarnings("unchecked")
	private void initSimpleHashTable(int capacity) {
		hashTable = (TableEntry<K, V>[]) new TableEntry[capacity];
		size = 0;
	}

	/**
	 * Method that returns capacity that is power of number 2.
	 * 
	 * @param capacity capacity that is transformed into first greater or equals power of number 2
	 * @return
	 */
	private int getValidCapacity(int capacity) {
		int c = 1;
		while (c < capacity)
			c <<= 1;
		return c;
	}

	/**
	 * Private class used for storing key and value as a couple. </br>
	 * It also has reference to the next {@link TableEntry}.
	 * 
	 * @author Ante Gazibarić
	 * @version 1.0
	 *
	 * @param <K> key
	 * @param <V> value
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;

		/**
		 * Constructor for creating new {@code TableEntry} object.
		 * 
		 * @param key   key to which given value is associated
		 * @param value value that is associated to the given key
		 * @param next  {@link TableEntry} reference to the next table entry object.
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = Objects.requireNonNull(key);
			this.value = value;
			this.next = next;
		}

		/**
		 * Method returns value of table entry.
		 * 
		 * @return value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Method sets value of this table entry.
		 * 
		 * @param value new value of table entry
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Method returns key of this table entry.
		 * 
		 * @return key of table entry
		 */
		public K getKey() {
			return key;
		}
		
	}

	/**
	 * Method that calculates address for hash table from given key.
	 * 
	 * @param key key which address is calculated
	 * @return    address that is calculated from given key
	 */
	private int getaddress(Object key) {
		int address = key.hashCode() % hashTable.length;
		return address < 0 ? -address : address;
	}

	/**
	 * Method that adds given key and value that is associated to the given key in {@code SimpleHashtable}.
	 * 
	 * @param key   key to which value is associated
	 * @param value value that is associated to the given key
	 * @throws      NullPointerException if given key is {@code null}
	 */
	public void put(K key, V value) {
		if (key == null)
			throw new NullPointerException("Key must not be null.");

		checkTableSize();

		int address = getaddress(key);
		if (hashTable[address] == null) {
			hashTable[address] = new TableEntry<>(key, value, null);
			size++;
			modificationCount++;
			return;
		}

		TableEntry<K, V> entry = getTableEntryFromKey(key);
		if (entry == null) {
			// hashTable doesn't contain given key
			entry = new TableEntry<>(key, value, hashTable[address]);
			hashTable[address] = entry;
			size++;
			modificationCount++;
		} else {
			// hashTable already contains given key; just update value
			entry.setValue(value);
		}
	}

	/**
	 * Helper method that returns {@link TableEntry} that contains given key.
	 * 
	 * @param key key that table entry contains
	 * @return    {@link TableEntry} that contains given {@code key}, </br>
	 * 			  or {@code null} if there's no such table entry
	 */
	private TableEntry<K, V> getTableEntryFromKey(Object key) {
		int address = getaddress(key);
		for (TableEntry<K, V> entry = hashTable[address]; entry != null; entry = entry.next) {
			if (entry.key.equals(key)) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * Method that returns value that is associated to the given {@code key}.
	 * 
	 * @param key key whose value is returned
	 * @return    value that is associated to the given key, or </br>
	 * 			  {@code null} if there's no such key, or if the given key is {@code null}
	 */
	public V get(Object key) {
		if (key == null)
			return null;

		TableEntry<K, V> entry = getTableEntryFromKey(key);
		return entry == null ? null : entry.value;
	}

	/**
	 * Method returns number of stored key-value entries.
	 * 
	 * @return number of stored key-value entries.
	 */
	public int size() {
		return size;
	}

	/**
	 * Method that checks if the hash table contains given {@code key}.
	 * 
	 * @param key key that is looked for in hash table
	 * @return    {@code true} if hash table contains given key, </br>
	 * 			  {@code false} otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			return false;
		if (hashTable[getaddress(key)] == null)
			return false;

		return getTableEntryFromKey(key) != null;
	}

	/**
	 * Method that checks if the hash table contains given {@code value}.
	 * 
	 * @param value value that is looked for in hash table
	 * @return      {@code true} if hash table contains given value, </br>
	 * 			    {@code false} otherwise
	 */
	public boolean containsValue(Object value) {
		for (int address = 0; address < hashTable.length; address++) {
			TableEntry<K, V> entry = hashTable[address];
			while (entry != null) {
				if (entry.value.equals(value))
					return true;
				entry = entry.next;
			}
		}
		return false;
	}

	/**
	 * Method that removes stored entry in hash table that has given {@code key}.
	 * 
	 * @param key key that removed entry contains
	 */
	public void remove(Object key) {
		if (key == null)
			return;

		int address = getaddress(key);
		if (hashTable[address] == null)
			return;

		if (hashTable[address].key.equals(key)) {
			// just skip the first one
			hashTable[address] = hashTable[address].next;
			size--;
			modificationCount++;
			return;
		}

		for (TableEntry<K, V> entry = hashTable[address]; entry.next != null; entry = entry.next) {
			if (entry.next.key.equals(key)) {
				entry.next = entry.next.next;
				size--;
				modificationCount++;
				return;
			}
		}
	}

	/**
	 * Method that removes all hash table elements.
	 */
	public void clear() {
		for (int address = 0; address < hashTable.length; address++) {
			hashTable[address] = null;
		}
		size = 0;
	}

	/**
	 * Method checks if hash table contains any elements.
	 * 
	 * @return {@code true} if the hash table is contains no elements, </br>
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Method that returns content of hash table in {@code String} representation.
	 */
	public String toString() {
		if (size == 0)
			return "[]";

		StringBuilder sb = new StringBuilder("[");

		for (int address = 0; address < hashTable.length; address++) {
			TableEntry<K, V> entry = hashTable[address];
			while (entry != null) {
				String entryString = entry.key + "=" + entry.value + ", ";
				sb.append(entryString);
				entry = entry.next;
			}
		}
		return sb.substring(0, sb.length() - 2) + "]";
	}

	/**
	 * Method that checks if the hash table needs to be resized </br>
	 * and if so, it resizes the table.
	 */
	private void checkTableSize() {
		if (size / hashTable.length > maxPercentage) {
			reallocateHashTable();
		}
	}

	/**
	 * Method used for resizing the hash table and moving elements to the new one.
	 */
	private void reallocateHashTable() {
		int newCapacity = hashTable.length * capacityMultiplier;
		TableEntry<K, V>[] oldTable = hashTable;
		initSimpleHashTable(newCapacity);
		
		for (int i = 0; i < oldTable.length; i++) {
			TableEntry<K, V> entry = oldTable[i];
			while (entry != null) {
				this.put(entry.key, entry.value);
				entry = entry.next;
			}
		}
	}


	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Private class that implements {@link Iterator} interface.
	 * 
	 * @author Ante Gazibarić
	 * @version 1.0
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/** used for checking if any modification from outside has happened
		 * during the process of iteration
		 */
		private int modificationCheck;
		/** used for remembering the index to which iterator has come in hash table.
		 *  It always addresses the next index in hash table that has not yet been returned
		 */
		private int address;
		/** reference to the last element that is returned by iterator */
		private TableEntry<K, V> currentEntry;
		/** flag for checking if currentEntry is already removed */
		private boolean isRemoved = true;
		
		/**
		 * Constructor that creates new IteratorImpl object
		 */
		public IteratorImpl() {
			this.modificationCheck = modificationCount;
		}

		@Override
		public boolean hasNext() {
			checkForModifications();
			
			if (currentEntry != null && currentEntry.next != null)
				return true;
			
			while (address < hashTable.length) {
				if (hashTable[address] != null) {
					return true;
				}
				address++;
			}
			return false;
		}

		@Override
		public TableEntry<K, V> next() {
			checkForModifications();
			
			if (currentEntry == null) {
				currentEntry = getFirstEntry();
				isRemoved = false;
				return currentEntry;
			}

			// check if there's next entry in list in the same slot
			TableEntry<K, V> entry = currentEntry.next;
			if (entry != null) {
				currentEntry = entry;
				isRemoved = false;
				return entry;
			}

			// find next slot in hash table that has entry
			while (address < hashTable.length) {
				entry = hashTable[address++];
				if (entry != null) {
					currentEntry = entry;
					isRemoved = false;
					return entry;
				}
			}

			throw new NoSuchElementException();
		}

		/**
		 * Helper method used for getting the first entry element from hash table.
		 * 
		 * @return {@link TableEntry} that represents first entry that occurs in hash table
		 */
		private TableEntry<K, V> getFirstEntry() {
				while (address < hashTable.length) {
					TableEntry<K, V> entry = hashTable[address++];
					if (entry != null)
						return entry;
				}
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			checkForModifications();	
			if (currentEntry == null || isRemoved)
				throw new IllegalStateException("There is no element to remove");
			
			TableEntry<K, V> entryKeeper;
			if (hashTable[address - 1] == currentEntry) {
				// if it's in the beginning of the list, 
				// just set address to that index in table (address always indexes the next one to process)
				// and set currentEntry to null, so that next() can start from the address in hashTable
				address--;
				entryKeeper = null;
			} else {
				// if it's not in the beginning of the list
				// then find the one that points to it, so that currentEntry can be set to it
				// (currentEntry points to the last one processed)
				for (entryKeeper = hashTable[address - 1]; !(entryKeeper.next == currentEntry); entryKeeper = entryKeeper.next);
			}
			
			SimpleHashtable.this.remove(currentEntry.key);
			isRemoved = true;
			currentEntry = entryKeeper;
			modificationCheck++;
		}
		
		/**
		 * Method that checks if any modifications happened during process of iteration.
		 * 
		 * @throws ConcurrentModificationException if any modifications happened during process of iteration
		 */
		private void checkForModifications() {
			if (modificationCheck != modificationCount)
				throw new ConcurrentModificationException("SimpleHashtable has been illegaly modified.\n" +
													   	  " It should only be modified through this iterator.\n");
		}
	}

}
