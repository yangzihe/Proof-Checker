import junit.framework.TestCase;


public class LineNumberTest extends TestCase {

	public void testLineNumbersConstructors() {

		// Test constructor with no arguments
		LineNumber myLineNumber = new LineNumber();
		assertFalse(myLineNumber.size() == 0);
		assertEquals("1", myLineNumber.toString());

		// Test constructor with multiple arguments
		LineNumber tester = new LineNumber("1");
		assertFalse(tester.size() == 0);
		assertEquals("1", tester.toString());
		LineNumber test = new LineNumber("2.1.3");
		assertEquals(3, test.size());
	}
	
	public void testAdd(){
		// Test that indenting works for line numbers
		LineNumber test = new LineNumber();
		test.add();
		assertEquals(2, test.size());
		assertEquals("1.1", test.toString());
		test.add();
		assertEquals(3, test.size());
		assertEquals("1.1.1", test.toString());
	}
	
	public void testNext(){
		
		// Test that if the line isn't supposed to be indented
		// it will correctly be increased by 1
		LineNumber test = new LineNumber();
		test.Next();
		assertEquals(1, test.size());
		assertEquals("2", test.toString());
		test.add();
		assertEquals(2, test.size());
		assertEquals("2.1", test.toString());
		test.Next();
		assertEquals("2.2", test.toString());
		
		// Make sure Next() only increments the last digit
		test.add();
		test.add();
		assertEquals("2.2.1.1", test.toString());
	}
	
	public void testPrevious(){
		
		// Test that calling previous doesn't actually change the
		// original LineNumber, but returns a duplicate LineNumber
		// instead
		LineNumber test = new LineNumber();
		test.Next();
		assertEquals("2", test.toString());
		LineNumber test2 = test.previous();
		assertEquals(1, test2.size());
		assertEquals("1", test2.toString());
		test2.add();
		assertEquals(2, test2.size());
		assertEquals("1.1", test2.toString());
		test2.Next();
		test2.Next();
		LineNumber test3 = test2.previous();
		assertEquals("1.2", test3.toString());
		
		// Test that if the last digit is 1, then calling previous
		// will change just take off the 1, which shrinks the arraylist
		LineNumber test4 = new LineNumber();
		test4.add();
		assertEquals("1.1", test4.toString());
		assertEquals(2, test4.size());
		LineNumber test5 = test4.previous();
		assertEquals(1, test5.size());
		assertEquals("1", test5.toString());
		
	}
	
	public void testParent(){
		// Test to make sure parent takes off the last
		// place of the LineNumber on which it is called by
		// returning a new object
		LineNumber test = new LineNumber();
		test.add();
		test.Next();
		assertEquals("1.2", test.toString());
		LineNumber test2 = test.parent();
		assertEquals("1", test2.toString());
	}
	
	public void testToString(){
		
		// Test that toString correctly represents the arraylist
		// as specific in the project specs.
		// Didn't test as thoroughly because previous tests
		// all used the toString method, which indicates that it
		// works
		LineNumber test = new LineNumber();
		test.add();
		test.add();
		test.add();
		test.add();
		test.add();
		test.Next();
		assertEquals(6, test.size());
		assertEquals("1.1.1.1.1.2", test.toString());
	}
	
	public void testEquals(){
		
		// Test that equals method works
		LineNumber test = new LineNumber();
		LineNumber test2 = new LineNumber("1");
		assertTrue(test.equals(test2));
		
		// Test that equals method can fail
		LineNumber test3 = new LineNumber();
		LineNumber test4 = new LineNumber("2");
		assertFalse(test3.equals(test4));
		
		// Test that equals method works for longer line numbers
		test3 = new LineNumber("2.1.3.1");
		test4 = new LineNumber("2.1.3.2");
		assertFalse(test3.equals(test4));
		
		
	}
	
	public void testSize(){
		
		// Test size works for line numbers with more than
		// one element
		LineNumber test = new LineNumber();
		test.add();
		test.add();
		assertEquals(3, test.size());
		assertEquals("1.1.1", test.toString());
		
		// Tests that parent() and previous() work well with
		// size() method. Also tests that parent() and previous()
		// work as intended
		LineNumber test2 = test.parent();
		assertEquals("1.1", test2.toString());
		assertEquals(2, test2.size());
		LineNumber test3 = test2.previous();
		assertEquals(1, test3.size());
		assertEquals("1", test3.toString());
	}

}
