package BUPT.ZLC.Strman;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;

import org.junit.Test;
import static BUPT.ZLC.Strman.Strman.*;

public class StrmanTest {
	@Test
	public void append_shouldAppendStringsToEndOfValue() throws Exception {
		assertThat(append("s", "u","p","e","r","z","l","c"),equalTo("superzlc"));
		assertThat(append("superzlc"),equalTo("superzlc"));
		assertThat(append("", "superzlc"), equalTo("superzlc"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void append_shouldThrowIllegalArgumentExceptionWhenValueIsNull() throws Exception{
		append(null);
	}
	
	@Test
	public void appendArray_shouldAppendStringArrayToEndOfValue() throws Exception{
		assertThat(appendArray("s", new String[]{"u","p","e","r","z","l","c"}),equalTo("superzlc"));
		assertThat(appendArray("superzlc", new String[]{}), equalTo("superzlc"));
		assertThat(appendArray("", new String[]{"superzlc"}), equalTo("superzlc"));
	}
	
	@Test
	public void at_shouldFindCharacterAtIndex() throws Exception {
		assertThat(at("superzlc", 0), equalTo(Optional.of("s")));
		assertThat(at("superzlc", 7), equalTo(Optional.of("c")));
		assertThat(at("superzlc", -1), equalTo(Optional.of("c")));
		assertThat(at("superzlc", -2), equalTo(Optional.of("l")));
		assertThat(at("superzlc", 10), equalTo(Optional.empty()));
		assertThat(at("superzlc", -10), equalTo(Optional.empty()));
	}
	
	@Test
	public void collapseWhitespace_shouldReplaceConsecutiveWhitespaceWithSingleSpace() throws Exception {
		String[] fixture = {
				"foo        bar",
				"        foo      bar		",
				" foo    bar		",
				"    foo    bar "	
		};
		Arrays.stream(fixture).forEach(el->assertThat(collapseWhitespace(el),equalTo("foo bar")));
	}
	
	@Test
	public void between_shouldReturnArrayWIthStringsBetweenStartAndEnd() throws Exception{
		assertThat(between("[Thanks][God]", "[", "]"), arrayContaining("Thanks","God"));
		assertThat(between("<span>foolish</span>", "<span>", "</span>"), arrayContaining("foolish"));
		assertThat(between("<span>foolish</span><span>man</span>", "<span>", "</span>"), arrayContaining("foolish","man"));
	}
	
	@Test
	public void between_shouldReturnFullStringWhenStartAndEndDoNotExist() throws Exception{
		assertThat(between("[abc][def]", "<", ">"), arrayContaining("[abc][def]"));
		assertThat(between("", "{", "}"), arrayContaining(""));
	}
	@Test (expected = IllegalArgumentException.class)
	public void appendArray_shouldThrowIllegalArgumentExceptionWHenValueIsNull() throws Exception {
		appendArray(null, new String[]{});
	}
	
	@Test
	public void chars_shouldReturnAllCharactersInString() throws Exception{
		final String title = "title";
		assertThat(chars(title), equalTo(new String[]{"t","i","t","l","e"}));
	}
	
	@Test
	public void collapseWhitespace_shouldReplaceConsecutiveWhitespaceBetweenMultipleStrings() throws Exception {
		String input = " foo    bar     bazz   hello    world   ";
		assertThat(collapseWhitespace(input), equalTo("foo bar bazz hello world"));
	}
	
	@Test
	public void contains_shouldReturnTrueWhenStringContainsNeedle() throws Exception {
		String[] fixture = {
				"foo bar",
				"bar foo",
				"foobar",
				"foo"
		};
		Arrays.stream(fixture).forEach(el -> assertTrue(contains(el, "FOO")));
	}
	
	@Test
	public void containsWithCaseSensitiveTrue_shouldReturnTrueWhenStringContainsNeedle() throws Exception {
		String[] fixture = {
				"foo bar",
				"bar foo",
				"foo",
				"foobar"
		};
		Arrays.stream(fixture).forEach(el -> assertFalse(contains(el, "Foo",true)));
	}
	
	@Test
	public void containsAll_shouldReturnTrueOnlyWhenAllNeedlesAreContainedInValue() throws Exception {
		String[] fixture = {
				"foo bar",
				"bar foo",
				"foobar"
		};
		Arrays.stream(fixture).forEach(el->assertTrue(containsAll(el,new String[]{"foo","bar"})));
	}
	
	@Test
	public void containsAny_shouldReturnTrueWhenAnyOfSearchNeedleExistInInputValue() throws Exception {
		String[] fixture = {
				"foo bar",
				"bar foo",
				"foobar"
		};
		Arrays.stream(fixture).forEach(el -> assertTrue(containsAny(el, new String[]{"foo", "bar", "test"})));
	}
	
	public void containsAny_shouldReturnFalseWhenNoneOfSearchNeedleExistInInputValue() throws Exception{
		String[] fixture = {
				"foo bar",
				"bar foo",
				"foobar"
		};
		Arrays.stream(fixture).forEach(el->assertFalse(containsAny(el, new String[]{"FOO","BAR","Test"},true)));
	}
	
	@Test
	public void countSubstr_shouldCountCaseInsensitiveWithoutOverlapingInValue(){
		assertThat(countSubstr("aaaAAAaaa", "aaa",false,false), equalTo(3L));
	}
	
	@Test
	public void countSubstr_shouldCountCaseSensitiveWithoutOverlappingInValue() {
		assertThat(countSubstr("aaaAAAaaa", "aaa",true,false), equalTo(2L));
	}
	
	@Test
	public void countSubstr_shouldCountCaseInsensitiveWithOverlappingInValue() throws Exception {
		assertThat(countSubstr("aaaAAAaaa", "aaa",false,true), equalTo(7L));
	}
	
	@Test
	public void countSubstr_shouldÇountSubStrCountCaseSensitiveWithOverlapInValue() {
		assertThat(countSubstr("aaaAAAaaa", "AAA",true,true), equalTo(1L));
	}
	
	@Test
	public void endsWith_caseSensitive_ShouldBeTrueWhenStringEndsWithSearchStr() throws Exception{
		String[] fixture = {
				"foo bar",
				"bar"
		};
		Arrays.stream(fixture).forEach(el->assertTrue(endsWith(el,"bar")));
	}
	
	@Test
	public void endsWith_notCaseSensitive_ShouldBeTrueWhenStringEndsWithSearchStr() throws Exception{
		String[] fixture = {
				"foo bar",
				"bar"
		};
		
		Arrays.stream(fixture).forEach(el->assertTrue(endsWith(el,"BAR",false)));
	}
	
	@Test
	public void endsWith_caseSensitiveAtPosition_ShouldBeTrueWhenSTringEndsWithSearchStr() throws Exception{
		String[] fixtrue = {
				"foo barr",
				"barr"
		};
		Arrays.stream(fixtrue).forEach(
				el->
				assertTrue(
						endsWith(el,"bar",el.length()-1,true)
						
						));
	}
	
	@Test
	public void endsWith_notCaseSensitiveAtPostion_ShouldBeTrueWhenStringEndsWithSearchStr() throws Exception{
		String[] fixture={
				"foo barr",
				"barr"
		};
		Arrays.stream(fixture)
		.forEach(
				el->assertTrue(
						endsWith(el,"BAR",el.length()-1,false)));
	}
	
	@Test
	public void prefixWithNotCaseSensitive_shouldReturnTrue(){
		String[] fixture = {
				"foobar",
				"foo"
		};
		Arrays.stream(fixture).forEach(el->
		assertTrue(prefixWith(el, "FOO")));
	}
	
	@Test
	public void prefixWithCaseSensitive_shouldReturnTrue(){
		String[] fixture = {
				"foobar",
				"foo"
		};
		Arrays.stream(fixture).forEach(el->
		assertTrue(prefixWith(el, "foo",true)));
	}
}