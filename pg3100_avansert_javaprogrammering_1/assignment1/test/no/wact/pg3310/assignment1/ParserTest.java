package no.wact.pg3310.assignment1;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test cases for Parser class in Familiar project.
 * 
 * @author Charlie Greenbacker
 * @author <a href="mailto:charlieg@cis.udel.edu">charlieg@cis.udel.edu</a>
 * @author <a
 *         href="http://www.cis.udel.edu/~charlieg">www.cis.udel.edu/~charlieg
 *         </a>
 * @version 1.0, &nbsp; 08 Sept 2009
 * 
 */
public class ParserTest {

	private String output; // common variable used across individual tests

	@Test
	public void testConstructor() throws Exception {
		Parser p = new Parser();
		assertNotNull(p);
	}

	@Test
	public void testParseEvent() throws Exception {
		output = Parser.parseEvent("born JOHN_A_SMITH 18851105");
		assertEquals("complete single-participant event",
				"EVENT: born; PARTICIPANTS: John A Smith; DATE: Nov 5 1885",
				output);
		output = Parser
				.parseEvent("married JOHN_A_SMITH & MARY_E_JONES 19090623");
		assertEquals(
				"complete multi-participant event",
				"EVENT: married; PARTICIPANTS: John A Smith, Mary E Jones; DATE: Jun 23 1909",
				output);

		// handling bad input
		output = Parser.parseEvent(" ");
		assertEquals("empty input string",
				"EVENT: unknown; PARTICIPANTS: none; DATE: unknown", output);
		output = Parser.parseEvent(null);
		assertEquals("null input",
				"EVENT: unknown; PARTICIPANTS: none; DATE: unknown", output);
		output = Parser.parseEvent("Born john_smith 8/10/1965");
		assertEquals("incorrect input format",
				"EVENT: unknown; PARTICIPANTS: none; DATE: unknown", output);
	}

	@Test
	public void testParseEventType() throws Exception {
		output = Parser.parseEventType("born JOHN_A_SMITH 18851105");
		assertEquals("basic event type", "born", output);

		// handling bad input
		output = Parser
				.parseEventType("born married JOHN_A_SMITH & MARY_E_JONES 19090623");
		assertEquals("multiple event types", "born", output);
		output = Parser
				.parseEventType("born-married JOHN_A_SMITH & MARY_E_JONES 19090623");
		assertEquals("mal-formed event type", "unknown", output);
		output = Parser.parseEventType("JOHN_A_SMITH & MARY_E_JONES 19090623");
		assertEquals("no event type", "unknown", output);
		output = Parser.parseEventType(null);
		assertEquals("null input", "unknown", output);
	}

	@Test
	public void testParseNames() throws Exception {
		output = Parser.parseNames("born JOHN_A_SMITH 18851105");
		assertEquals("complete single-participant event", "John A Smith",
				output);
		output = Parser
				.parseNames("married JOHN_A_SMITH & MARY_E_JONES 19090623");
		assertEquals("complete multi-participant event",
				"John A Smith, Mary E Jones", output);

		// handling bad input
		output = Parser
				.parseNames("born JOHN_A_SMITH & JACK_A_SMITH & JOE_A_SMITH 19090623");
		assertEquals("triple-participant event", // doesn't make much sense, but
													// it's handled
				"John A Smith, Jack A Smith, Joe A Smith", output);
		output = Parser.parseNames("born 19090623");
		assertEquals("zero-participant event", "none", output);
		output = Parser.parseNames("born john_smith 19090623");
		assertEquals("mal-formed name", "none", output);
		output = Parser.parseNames(null);
		assertEquals("null input", "none", output);
	}

	@Test
	public void testParseDate() throws Exception {
		output = Parser.parseDate("born JOHN_A_SMITH 18851105");
		assertEquals("basic date", "Nov 5 1885", output);
		output = Parser.parseDate("born JOHN_A_SMITH 98761231");
		assertEquals("super future date", "Dec 31 9876", output);

		// handling bad input
		output = Parser.parseDate("born JOHN_A_SMITH");
		assertEquals("no date", "unknown", output);
		output = Parser.parseDate("born JOHN_A_SMITH 1909AB05");
		assertEquals("mal-formed date", "unknown", output);
		output = Parser.parseDate("born JOHN_A_SMITH 19091105 19110401");
		assertEquals("multiple dates", "Nov 5 1909", output);
		output = Parser.parseDate("born JOHN_A_SMITH 1911 19091105");
		assertEquals("multiple dates, incomplete one first", "unknown", output);
		output = Parser.parseDate(null);
		assertEquals("null input", "unknown", output);
	}

	@Test
	public void testCapitalizeFirst() throws Exception {
		output = Parser.capitalizeFirst(null);
		assertEquals("capitalize null", "", output);
		output = Parser.capitalizeFirst("");
		assertEquals("string with length 0", "", output);
		output = Parser.capitalizeFirst(" ");
		assertEquals("empty string", "", output);
		output = Parser.capitalizeFirst("test");
		assertEquals("tiny test", "Test", output);
		output = Parser.capitalizeFirst("test This");
		assertEquals("two words, one to fix", "Test This", output);
		output = Parser.capitalizeFirst("test this");
		assertEquals("two words, fix both", "Test This", output);
		output = Parser.capitalizeFirst("this is a string containing several letters");
		assertEquals("a long string", "This Is A String Containing Several Letters", output);
	}

	@Test
	public void testParseEvents() throws Exception {
		List<String> list = new ArrayList<>();
		list.add("born JOHN_A_SMITH 18851105");
		list.add("married JOHN_A_SMITH & MARY_E_JONES 19090623");
		list.add("");
		list.add(" ");
		list.add(null);
		list.add("Born john_smith 8/10/1965");
		List<String> output = Parser.parseEvents(list);
		assertEquals(6, output.size());
		assertEquals("EVENT: born; PARTICIPANTS: John A Smith; DATE: Nov 5 1885", list.get(0));
		assertEquals("EVENT: married; PARTICIPANTS: John A Smith, Mary E Jones; DATE: Jun 23 1909", list.get(1));
		assertEquals("EVENT: unknown; PARTICIPANTS: none; DATE: unknown", list.get(2));
		assertEquals("EVENT: unknown; PARTICIPANTS: none; DATE: unknown", list.get(3));
		assertEquals("EVENT: unknown; PARTICIPANTS: none; DATE: unknown", list.get(4));
		assertEquals("EVENT: unknown; PARTICIPANTS: none; DATE: unknown", list.get(5));
	}

	@Test
	public void testParseEventsNullOrEmptyList() throws Exception {
		List<String> output = Parser.parseEvents(null);
		assertNull(output);
		List<String> input = new ArrayList<>();
		output = Parser.parseEvents(input);
		assertEquals(0, output.size());
	}
}