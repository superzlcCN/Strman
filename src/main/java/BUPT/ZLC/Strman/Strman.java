package BUPT.ZLC.Strman;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.function.Supplier;


public abstract class Strman {
	private static final Predicate<String> NULL_STRING_PREDICATE = str -> str== null;
	private static final Supplier<String> NULL_STRING_MSG_SUPPLIER = ()->"'value' should not be null";
	
	private Strman(){}
	
	/**
	 * Validate the value with predicate, if false then throw an IllegalArgumentException type Exception with supplier message
	 * @param value			Value to be validated
	 * @param predicate
	 * @param supplier
	 */
	private static void validate(String value, Predicate<String> predicate,final Supplier<String> supplier){
		if (predicate.test(value)) {
			throw new IllegalArgumentException(supplier.get());
		}
	}
	
	/**
	 * Appends Strings to value
	 * @param value		initial String
	 * @param appends	an array of Strings to append
	 * @return	full String
	 */
	public static String append(final String value, final String... appends){
		return appendArray(value, appends);
	}
	/**
	 * Append an array of String to value
	 * @param value		initial String
	 * @param appends	an array of strings to append
	 * @return	full string
	 */
	public static String appendArray(final String value, final String[] appends){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		if (appends == null || appends.length == 0) {
			return value;
		}
		
		StringJoiner sj = new StringJoiner("");
		for (String append: appends){
			sj.add(append);
		}
		
		return value + sj.toString();
	}
	
	/**
	 * return the character at index. This method will take negative indexes into consideration
	 * The validate index should between -(value.length-1) and (value.length-1)
	 * if index is out of range, Optional.empty will be returned
	 * @param value		input value
	 * @param index		index location
	 * @return	an Optional String if found else empty
	 */
	public static Optional<String> at(final String value, int index){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		
		int length = value.length();
		if (index < 0) {
			index = index + length;
		}
		
		return (index >= 0 && index < length)?Optional.of(String.valueOf(value.charAt(index))):Optional.empty();
	}

	public static String[] between(final String value,final String start, final String end){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		validate(start, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		validate(end, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);

		String[] parts = value.split(end);
		
		return Arrays.stream(parts)
				.map(str -> str.substring( value.indexOf(start) + start.length())).toArray(String[]::new);
	}
	/**
	 * Return a String array consisting of the characters in the String.
	 * @param value		input
	 * @return	character array
	 */
	public static String[] chars(final String value){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		return value.split("");
	}
	
	/**
	 * Replace consecutive whitespace characters with a single space
	 * @param value
	 * @return
	 */
	public static String collapseWhitespace(final String value){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		return value.trim().replaceAll("\\s\\s+", " ");
	}
	
	public static boolean contains(final String value,final String needle){
		return contains(value, needle,false);
	}
	
	public static boolean contains(final String value, final String needle, final boolean isCaseSensitive){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		if (isCaseSensitive) {
			return value.contains(needle);
		}
		
		return value.toUpperCase().contains(needle.toUpperCase());
	}
	/**
	 * Verify that all needles are contained in value.The search is case insentive
	 * @param value			input String to search
	 * @param needles		needles to find
	 * @return true			if all needles are found else false.
	 */
	public static boolean containsAll(final String value,final String[] needles) {
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		return Arrays.stream(needles).allMatch(needle -> contains(value, needle,true));
	}
	/**
	 * Verifies that all needles are contained in value
	 * @param value				input String to search
	 * @param needles			needles to find
	 * @param caseSensivie		true or false
	 * @return	if all needles are found else false.
	 */
	public static boolean containsAll(final String value, final String[] needles,final boolean caseSensivie){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		return Arrays.stream(needles).allMatch(needle -> contains(value, needle, caseSensivie));
	}
	
	/**
	 * Verifies that one or more of needles are contained in value.
	 * @param value					input
	 * @param needle				needles to search
	 * @return	boolean true if any needle is found else false.
	 */
	public static boolean containsAny(final String value, final String[] needle){
		return containsAny(value, needle,false);
	}
	
	/**
	 * Verifies that one or more of needles are contained in value.
	 * @param value				input
	 * @param needles			needles to search
	 * @param caseSensitive		true or false
	 * @return	boolean true if any needle is found else false
	 */
	public static boolean containsAny(final String value, final String[] needles,final boolean caseSensitive) {
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		return Arrays.stream(needles).anyMatch(needle -> contains(value, needle, caseSensitive));
	}
	/**
	 * Count the number of time  substr appears in value
	 * @param value				input
	 * @param substr			search string
	 * @return number of times substring appears in value
	 */
	public static long countSubstr(final String value,final String substr){
		return countSubstr(value, substr,true,false);
	}
	/**
	 * Count the number of time substr appears in value
	 * @param value				input
	 * @param subStr			search string
	 * @param caseSensitive		whether search should be case sensitive
	 * @param allowOverlapping	whether to take overlapping into account
	 * @return	count of times substring exist
	 */
	public static long countSubstr(final String value,final String subStr,final boolean caseSensitive,boolean allowOverlapping){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		return countSubstr(
				caseSensitive?value:value.toUpperCase(), 
				caseSensitive?subStr:subStr.toUpperCase(),
				allowOverlapping, 
				0
		);
	}
	
	/**
	 * Count of times of substr apppears in value
	 * @param value			input
	 * @param substr		substr
	 * @param allowOverlapping		whether to take overlapping into consideration
	 * @param count	
	 * @return
	 */
	private static long countSubstr(String value, String substr,boolean allowOverlapping,long count){
		int position = value.indexOf(substr);
		if (position == -1) {
			return count;
		}
		int offset;
		if (!allowOverlapping) {
			offset = position + substr.length();
		}else
			offset = position +1;
		return countSubstr(value.substring(offset), substr, allowOverlapping, ++count);
		
	}
	/**
	 * Validate input string whether is ended with search and case is sensitive
	 * @param value 	the input string
	 * @param search	the string to search
	 * @return true of false
	 */
	public static boolean endsWith(final String value,final String search){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		return endsWith(value, search,value.length(),true);
	}
	/**
	 * Validate whether the input string is ended with search String
	 * @param value		 the input string
	 * @param search	the search value
	 * @param isCaseSensitive	true of false
	 * @return true or false
	 */
	public  static boolean endsWith(final String value,final String search,boolean isCaseSensitive){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		return endsWith(value, search,value.length(),isCaseSensitive);
	}
	/**
	 * Validate whether input value ends up with search
	 * @param value		input string
	 * @param search	string to search
	 * @param position  position till which you want to search
	 * @param caseSensitive	true or false
	 * @return true or false
	 */
	public static boolean endsWith(final String value, final String search,final int position,final boolean caseSensitive){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		int remainningLength = position - search.length();
		if (caseSensitive) {
			return value.indexOf(search,remainningLength) >-1;
		}
		return value.toUpperCase().indexOf(search.toUpperCase(),remainningLength) > -1;
	}
	
	/**
	 * Validate value is started with prefix value and case is not sensitive
	 * @param value		the input string
	 * @param prefix	the prefix value
	 * @return	true of false
	 */
	public static boolean prefixWith(final String value,final String prefix){
		return prefixWith(value, prefix,false);
	}
	/**
	 * Validate the string value is started with prefix 
	 * @param value			the input String
	 * @param prefix		the prefix
	 * @param caseSensitive	true or false
	 * @return	true or false
	 */
	public static boolean prefixWith(final String value,final String prefix,final boolean caseSensitive){
		validate(value, NULL_STRING_PREDICATE, NULL_STRING_MSG_SUPPLIER);
		if (caseSensitive) {
			return value.startsWith(prefix);
		}
		String _value = value.toLowerCase();
		String _prefix = prefix.toLowerCase();
		return _value.startsWith(_prefix);
	}
}
