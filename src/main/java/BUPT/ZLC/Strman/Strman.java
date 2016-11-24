package BUPT.ZLC.Strman;

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

	
}
