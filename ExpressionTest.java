import junit.framework.TestCase;

public class ExpressionTest extends TestCase {
	
	public void testEmpty() {
		// tests cases where there is no operator or operand
		assertFalse(Expression.isValid(""));
		assertFalse(Expression.isValid(" "));
		assertFalse(Expression.isValid("   "));
		assertFalse(Expression.isValid("("));
		assertFalse(Expression.isValid(")"));
		assertFalse(Expression.isValid("()"));
		assertFalse(Expression.isValid("(   )"));
	}
	
	public void testInvalidAnd() {
		// should be false if and operator is used without parentheses, 
		//  or without letters on both sides of it
		assertFalse(Expression.isValid("&"));
		assertFalse(Expression.isValid("(&)"));
		assertFalse(Expression.isValid("( & )"));
		assertFalse(Expression.isValid("&&&"));
		assertFalse(Expression.isValid("(&&&)"));
		assertFalse(Expression.isValid("a(&)b"));
		assertFalse(Expression.isValid("(a(&)b)"));
		assertFalse(Expression.isValid("((&))"));
		assertFalse(Expression.isValid("((a)&(b))"));
		assertFalse(Expression.isValid("(( )&( ))"));
		assertFalse(Expression.isValid("a&b"));
		assertFalse(Expression.isValid("(&b)"));
		assertFalse(Expression.isValid("(a&)"));
		assertFalse(Expression.isValid("(a& )"));
		assertFalse(Expression.isValid("( &b)"));
		assertFalse(Expression.isValid("(a&~)"));
		assertFalse(Expression.isValid("(~&b)"));
		assertFalse(Expression.isValid("(a & b)"));
	}
	
	public void testInvalidOr() {
		// should be false if or operator is used without parentheses, 
		//  or without letters on both sides of it
		assertFalse(Expression.isValid("|"));
		assertFalse(Expression.isValid("(|)"));
		assertFalse(Expression.isValid("( | )"));
		assertFalse(Expression.isValid("|||"));
		assertFalse(Expression.isValid("(|||)"));
		assertFalse(Expression.isValid("a(|)b"));
		assertFalse(Expression.isValid("(a(|)b)"));
		assertFalse(Expression.isValid("((|))"));
		assertFalse(Expression.isValid("((a)|(b))"));
		assertFalse(Expression.isValid("(( )|( ))"));
		assertFalse(Expression.isValid("a|b"));
		assertFalse(Expression.isValid("a|"));
		assertFalse(Expression.isValid("a| "));
		assertFalse(Expression.isValid("|b"));
		assertFalse(Expression.isValid(" |b"));
		assertFalse(Expression.isValid("(a|~)"));
		assertFalse(Expression.isValid("~|b"));
		assertFalse(Expression.isValid("(a | b)"));
	}
	
	public void testInvalidImplies() {
		// should be false if implication operator is used without parentheses, 
		// without letters on both side of it, with multiple 
		// '=' or '>', or without either '=' or '>'
		assertFalse(Expression.isValid("=>"));
		assertFalse(Expression.isValid("(=>)"));
		assertFalse(Expression.isValid("( => )"));
		assertFalse(Expression.isValid("=>=>=>"));
		assertFalse(Expression.isValid("(=>=>=>)"));
		assertFalse(Expression.isValid("a(=>)b"));
		assertFalse(Expression.isValid("(a(=>)b)"));
		assertFalse(Expression.isValid("((=>))"));
		assertFalse(Expression.isValid("((a)=>(b))"));
		assertFalse(Expression.isValid("(( )=>( ))"));
		assertFalse(Expression.isValid("a=>b"));
		assertFalse(Expression.isValid("(a=b)"));
		assertFalse(Expression.isValid("(a=<b)"));
		assertFalse(Expression.isValid("(a==>b)"));
		assertFalse(Expression.isValid("(a>>b)"));
		assertFalse(Expression.isValid("(a=>>b)"));
		assertFalse(Expression.isValid("(a=>>)"));
		assertFalse(Expression.isValid("(a=>)"));
		assertFalse(Expression.isValid("(=>b)"));
		assertFalse(Expression.isValid("(a=> )"));
		assertFalse(Expression.isValid("( =>b)"));
		assertFalse(Expression.isValid("(a=>~)"));
		assertFalse(Expression.isValid("(~=>b)"));
		assertFalse(Expression.isValid("(a => b)"));
		assertFalse(Expression.isValid("(a->b)"));
	}
	
	public void testInvalidNot() {
		// should be false if the not operator is used with parentheses, 
		// in between letters, without a letter, or after a letter
		assertFalse(Expression.isValid("~"));
		assertFalse(Expression.isValid("~ "));
		assertFalse(Expression.isValid("~>"));
		assertFalse(Expression.isValid("(~)"));
		assertFalse(Expression.isValid("(~a)"));
		assertFalse(Expression.isValid("~(a)"));
		assertFalse(Expression.isValid("(~~a)"));
		assertFalse(Expression.isValid("~(~~a)"));
		assertFalse(Expression.isValid("(a~b)"));
		assertFalse(Expression.isValid("a~"));
		assertFalse(Expression.isValid("~a~"));
    	assertFalse(Expression.isValid("(~a~a)"));
	}
	
	public void testTooManyOperators() {
		// should be false if an operator is followed by another operator
		assertFalse(Expression.isValid("(a&&b)"));
		assertFalse(Expression.isValid("(a||b)"));
		assertFalse(Expression.isValid("(a=>=>b)"));
		assertFalse(Expression.isValid("(a~~b)"));
		assertFalse(Expression.isValid("(a&|b)"));
		assertFalse(Expression.isValid("(a|&b)"));
		assertFalse(Expression.isValid("(a&=b)"));
		assertFalse(Expression.isValid("(a=&b)"));
		assertFalse(Expression.isValid("(a=>&b)"));
		assertFalse(Expression.isValid("(a&=>b)"));
		assertFalse(Expression.isValid("(a|=b)"));
		assertFalse(Expression.isValid("(a=|b)"));
		assertFalse(Expression.isValid("(a=>|b)"));
		assertFalse(Expression.isValid("(a|=>b)"));
		assertFalse(Expression.isValid("(a=>=>b)"));
		assertFalse(Expression.isValid("(a~&b)"));
		assertFalse(Expression.isValid("(a~|b)"));
		assertFalse(Expression.isValid("(a~=>b)"));
	}
	
	public void testInvalidSymbols() {
		// should be false if symbols are used instead of letters or parentheses
		assertFalse(Expression.isValid("(-&-)"));
		assertFalse(Expression.isValid("(a&-)"));
		assertFalse(Expression.isValid("(-&a)"));
		assertFalse(Expression.isValid("(-|-)"));
		assertFalse(Expression.isValid("(a|-)"));
		assertFalse(Expression.isValid("(-|a)"));
		assertFalse(Expression.isValid("(-=>)"));
		assertFalse(Expression.isValid("(a=>-)"));
		assertFalse(Expression.isValid("(-=>-)"));
		assertFalse(Expression.isValid("{a&b}"));
		assertFalse(Expression.isValid("{a|b}"));
		assertFalse(Expression.isValid("{a=>b}"));
		assertFalse(Expression.isValid("{a&b)"));
		assertFalse(Expression.isValid("(a&b}"));
		assertFalse(Expression.isValid("(a|b}"));
		assertFalse(Expression.isValid("{a|b)"));
		assertFalse(Expression.isValid("(a=>b}"));
		assertFalse(Expression.isValid("{a=>b)"));	
		assertFalse(Expression.isValid("-"));	
    }
    public void testUnevenParentheses() {
    	// should be invalid if parentheses are uneven
    	assertFalse(Expression.isValid("(a&b"));	
    	assertFalse(Expression.isValid("a&b)"));	
    	assertFalse(Expression.isValid("(a&b("));
    	assertFalse(Expression.isValid(")a&b("));
    	assertFalse(Expression.isValid("()a&b"));
    	assertFalse(Expression.isValid("a&b()"));
    	assertFalse(Expression.isValid("(a&b))"));
    	assertFalse(Expression.isValid("(a(&b))"));	
    	assertFalse(Expression.isValid("(a&(b))"));	
    	assertFalse(Expression.isValid("((a&b))"));	
    	assertFalse(Expression.isValid("~a&b"));
    	assertFalse(Expression.isValid("a&~b"));
    	assertFalse(Expression.isValid("(a&b)=>(b|c)"));
    }
    
    public void testTooManyLetters() {
    	// should be false if there are too many letters per operator
    	assertFalse(Expression.isValid("(aa&b)"));	
    	assertFalse(Expression.isValid("(aa|b)"));
    	assertFalse(Expression.isValid("(aa=>b)"));
    	assertFalse(Expression.isValid("(~bb)"));
      	assertFalse(Expression.isValid("(~~aa)"));
    	assertFalse(Expression.isValid("(a&bb)"));	
    	assertFalse(Expression.isValid("(a|bb)"));
    	assertFalse(Expression.isValid("(a=>bb)"));
    	assertFalse(Expression.isValid("(aa&bb)"));
    	assertFalse(Expression.isValid("(aa|bb)"));
    	assertFalse(Expression.isValid("(aa=>bb)"));
    	assertFalse(Expression.isValid("(aa)"));
    	assertFalse(Expression.isValid("(aaa)"));
    }
    
    public void testValidAnd() {
    	// should be valid if and is fully parenthesized and used properly
		assertTrue(Expression.isValid("(a&b)"));
		assertTrue(Expression.isValid("((a&b)&(a&b))"));	
    }
    
    public void testValidOr() {
    	// should be valid if or is fully parenthesized and used properly
		assertTrue(Expression.isValid("(a|b)"));
		assertTrue(Expression.isValid("((a|b)|(a|b))"));
    }
    
    public void testValidImplies() {
    	// should be valid if the implication is fully parenthesized and used properly
		assertTrue(Expression.isValid("(a=>b)"));
		assertTrue(Expression.isValid("((a=>b)=>(a=>b))"));
    }
    
    public void testValidNot() {
    	// should be valid if not is not is used properly and without parentheses
		assertTrue(Expression.isValid("~a"));
		assertTrue(Expression.isValid("~~a"));
		assertTrue(Expression.isValid("~~~~~~~~~~~a"));
		assertTrue(Expression.isValid("(~a&b)"));
		assertTrue(Expression.isValid("(~a|b)"));
		assertTrue(Expression.isValid("(~a=>b)"));
		assertTrue(Expression.isValid("(~a&~b)"));
		assertTrue(Expression.isValid("(~a|~b)"));
		assertTrue(Expression.isValid("(~a=>~b)"));
		assertTrue(Expression.isValid("~(~a&b)"));
		assertTrue(Expression.isValid("~(~a|b)"));
		assertTrue(Expression.isValid("~(~a=>b)"));
		assertTrue(Expression.isValid("(~~~~a&b)"));
		assertTrue(Expression.isValid("(~~~~a|b)"));
		assertTrue(Expression.isValid("(~~~a=>b)"));
		assertTrue(Expression.isValid("(~~~a&~~~~b)"));
		assertTrue(Expression.isValid("(~~~~a|~~~b)"));
		assertTrue(Expression.isValid("(~~~~a=>~~~b)"));
		assertTrue(Expression.isValid("~~~~~(~a&b)"));
		assertTrue(Expression.isValid("~~~~~(~a|~b)"));
		assertTrue(Expression.isValid("~~~~(~a=>b)"));
		assertTrue(Expression.isValid("~~~~~(~~~~~a&~~~~~~b)"));
		assertTrue(Expression.isValid("~~~~~(~a|~~~b)"));
		assertTrue(Expression.isValid("~~~~(a=>~~~~b)"));    	
    }
    
    public void testValidComplex() {
    	// complex expression should be valid
		assertTrue(Expression.isValid("((a&b)&(a|b))"));
		assertTrue(Expression.isValid("((a&b)&(a=>b))"));
		assertTrue(Expression.isValid("((a&b)|(a&b))"));
		assertTrue(Expression.isValid("((a&b)|(a|b))"));
		assertTrue(Expression.isValid("((a&b)|(a=>b))"));
		assertTrue(Expression.isValid("((a&b)=>(a&b))"));
		assertTrue(Expression.isValid("((a&b)=>(a|b))"));
		assertTrue(Expression.isValid("((a&b)=>(a=>b))"));
		assertTrue(Expression.isValid("((a|b)&(a&b))"));
		assertTrue(Expression.isValid("((a|b)&(a=>b))"));
		assertTrue(Expression.isValid("((a|b)&(a|b))"));
		assertTrue(Expression.isValid("((a|b)|(a&b))"));
		assertTrue(Expression.isValid("((a|b)|(a=>b))"));
		assertTrue(Expression.isValid("((a|b)=>(a&b))"));
		assertTrue(Expression.isValid("((a|b)=>(a|b))"));
		assertTrue(Expression.isValid("((a|b)=>(a=>b))"));
		assertTrue(Expression.isValid("((a=>b)&(a&b))"));
		assertTrue(Expression.isValid("((a=>b)&(a=>b))"));
		assertTrue(Expression.isValid("((a=>b)&(a|b))"));
		assertTrue(Expression.isValid("((a=>b)|(a&b))"));
		assertTrue(Expression.isValid("((a=>b)|(a|b))"));
		assertTrue(Expression.isValid("((a=>b)|(a=>b))"));
		assertTrue(Expression.isValid("((a=>b)=>(a&b))"));
		assertTrue(Expression.isValid("((a=>b)=>(a|b))"));
		assertTrue(Expression.isValid("~~~((a|b)&(a&b))"));
		assertTrue(Expression.isValid("~~((~a|~~~b)&~(~~a=>~b))"));
		assertTrue(Expression.isValid("~~~~~~(~~~(a|~b)&~~(~a|~b))"));
    }
    
    public void testInvalidMatches() {
    	// expressions do not match if operators are not the same
    	try {
    		Expression thm = new Expression("(~~x=>x)");
    		Expression expr = new Expression("(~~(a|b)&(a|b))");
    		assertFalse(expr.matches(thm));
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	// expressions do not match if variable is not replaced with the same substring
    	// throughout the expression
     	try {
    		Expression thm = new Expression("(~~x=>x)");
    		Expression expr = new Expression("(~~(a|b)=>a)");
    		assertFalse(expr.matches(thm));
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	
    }
    
    public void testValidMatches() {
    	 // expressions are valid if operators match and variables are replaced
    	// with the same substring throughout the expression
     	try {
    		Expression thm = new Expression("(~~x=>x)");
    		Expression expr = new Expression("(~~(a|b)=>(a|b)");
    		assertTrue(expr.matches(thm));
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	
    }
}
