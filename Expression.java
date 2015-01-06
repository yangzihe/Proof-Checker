import java.util.*;

public class Expression {
	public TreeNode myRoot;
	private HashMap<String, String> subs = new HashMap<String, String>();
	
	public class TreeNode {
		public Object myItem;
		public TreeNode myLeft;
		public TreeNode myRight;
		public String leftString;
		public String rightString;
		
		public TreeNode (Object obj) {
			myItem = obj;
			myLeft = myRight = null;
			leftString = rightString = "";
		}
	
	public TreeNode (Object obj, TreeNode left, TreeNode right, String ls, String rs) {
		myItem = obj;
		myLeft = left;
		myRight = right;
		leftString = ls;
		rightString = rs;
	}
	
	}

	public Expression (String s) throws IllegalLineException {
		// throws an exception if the given string isn't in a valid expression format 
		
		s = s.trim(); //remove white spaces surrounding the expression
		
		if (s ==null || s.equals("")) {
			// throws an exception if the expression is null or empty
			throw new IllegalLineException("No expression was inputted");
		}
		// calls exprHelper to form expression tree for the given string
	    this.myRoot = this.exprHelper(s);
	}
	
	private TreeNode exprHelper (String expr) throws IllegalLineException {
		// returns the expression tree corresponding to the given string
		// and throws exceptions if the format is invalid
		if (expr.length()==1){
			if ( ! Character.isLetter(expr.charAt(0) ) ) {
				// throws an exception if the expression is length 1 and not a letter
				throw new IllegalLineException("The expression must include a letter");
			} else {
				// returns the root as the single letter
				return new TreeNode(expr);
			}
		} else {
			if (expr.charAt(0) == '~') {
				// if the operator is ~ make a node with root ~ and only a left node
				// the left node will call the helper method with the remaining string
				return new TreeNode("~", exprHelper(expr.substring(1)), null, expr.substring(1), "");
			}
			
			if (expr.charAt(0) != '(') {
				// if expression doesn't start with a ~ it must start with a (
				throw new IllegalLineException("The expression must include parentheses");
			} 
			
			if (expr.charAt(expr.length()-1)!= ')') {
				//if the expression starts with a ( it must end with a )
				throw new IllegalLineException("The expression must end with closing parentheses");
			}
			else {
				// expr is a parenthesized expression 
				// find end parentheses and strip off the beginning and ending parentheses
				// find the top-level operator which is either &, |, or =>, not nested
				// in parentheses, and construct the two subtrees 
				// while throwing errors as necessary
				int nesting = 0;
		        int opPos = 0;
		        String leftNode = "";
		        String rightNode = "";
		        String op = "";

		        for (int k=1; k<expr.length()-1; k++) {
		        	// go through the string to find the position of the top-level operand
		             if (k!=1 && nesting==0 && (expr.charAt(k-1)!='~')) {
		            	opPos=k;
		             	break;
		             }
		             if (expr.charAt(k)=='(') {
		            	 nesting+=1;
		             }
		             if(expr.charAt(k)==')') {
		            	 nesting-=1;
		             }
		        } //end of for loop
		        
		        if (nesting != 0) {
		        	// makes sure the parentheses are even 
		        	throw new IllegalLineException("The expression has uneven parentheses");
		        }
					
		        if (opPos==0){
		        	//throws an exception if there is no operator between parentheses 
		        	throw new IllegalLineException("The expression needs an operator between parentheses");
		        }
		        try {
		        	// define the operator and subtrees and remove parentheses
			        leftNode = expr.substring (1, opPos);
			        rightNode = expr.substring (opPos+1, expr.length()-1);
			        op = expr.substring (opPos, opPos+1);
		        } 
		        catch (Exception e) {
		        	System.out.println("The expression needs something after the operator");
		        }
		        
		        if (op.equals("=")) {
		        	// adjust for implication operator
		        	try {
			        	 leftNode = expr.substring (1, opPos);
					     rightNode = expr.substring (opPos+2, expr.length()-1);
					     op = expr.substring (opPos, opPos+2);
		        	}
		        	catch (Exception f) {
			        	System.out.println("The expression needs something after the operator");
		        	}
		        }
		        
		        /*		        
				// print out operation for testing
		        System.out.println ("expression : " + expr);
		        System.out.println ("leftNode   : " + leftNode);
		        System.out.println ("operator   : " + op);
		        System.out.println ("rightNode  : " + rightNode);
		        System.out.println ( );
		        */
		        
		        
		        if ( ! op.equals("&") &&  ! op.equals("|") && ! op.equals("=>") ) {
		        	//make sure the operator is legal
		        	throw new IllegalLineException ("This expression contains an illegal operator");
		        	
		        }
		        
		        if (rightNode.equals("") || leftNode.equals("")) {
		        	//make sure both nodes have been defined
		        	throw new IllegalLineException("The expression is incomplete");
		        }
		        
		        if (rightNode.charAt(0)=='&' || rightNode.charAt(0)=='|' || rightNode.charAt(0)=='=')  {
		        	// makes sure operator isn't followed by another operator
		        	throw new IllegalLineException("The  operator cannot be followed by another operator");
		        }
		        return new TreeNode(op, exprHelper(leftNode), exprHelper(rightNode), leftNode, rightNode);
			} // end char inside parentheses 
		} // end expression length>1
	} // end method
	
	public static boolean isValid(String s) {
		// returns true if the string is a valid expression
		// with correct use of parentheses, letters, and operators
		// else it will print out appropriate error message
		try {
			Expression expr = new Expression(s) ;
			return true;
		}
		catch (IllegalLineException e) {
			System.out.println(e);
			return false;
		}

	}
	public boolean matches(Expression thm) {
		// returns true if the expression matches the theorem expression
		// meaning the expressions are identical and all occurrences 
		// of a variable in the theorem match the same subexpression 
		// in the expression 
		if (thm.myRoot != null) {
			return matchesHelper(thm.myRoot, this.myRoot);
		} else {
			return false;
		}
	}
	private boolean matchesHelper (TreeNode thm, TreeNode expr) {
		// return false if the operators don't match
		if (! thm.myItem.equals(expr.myItem)) {
			return false;
		}
		// check if the left node is variable and if it is
		// make sure it matches the corresponding substring of expr
		if (thm.myLeft != null &&expr.myLeft != null) {
			if (thm.leftString.length()==1) {
				if (Character.isLetter(thm.leftString.charAt(0))) {
					if (! validSub(thm.leftString,expr.leftString)) {
						return false;
					}
				}
			}
			// if it isn't a variable call helper method again 
			matchesHelper(thm.myLeft, expr.myLeft);
		}
		// check if the right node is variable and if it is
		// make sure it matches the corresponding substring of expr
		if (thm.myRight != null &&expr.myRight != null) {
			if (thm.rightString.length()==1) {
				if (Character.isLetter(thm.rightString.charAt(0))) {
					if (! validSub(thm.rightString,expr.rightString)) {
						return false;
					}
				}
			}
			// if it isn't a variable call helper method again 
			matchesHelper(thm.myRight, expr.myRight);
		}
		return true;
	}
	private boolean validSub(String var, String expr) {
		// returns true if the variable is replaced by the same substring 
		// if the variable is new, adds the new pair to subs
		if (subs.get(var) == null) {
			subs.put(var, expr);
		} else {
			if (subs.get(var).equals(expr)) {
				return true;
			}
			return false;
		}
		return false;
	}
}