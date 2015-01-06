import java.util.*;

public class Proof {

	private LineNumber lineNum;
	private TheoremSet myTheoremSet;
	private Hashtable<LineNumber, String[]> history;

	public Proof(TheoremSet theorems) {
		myTheoremSet = theorems;
		history = new Hashtable<LineNumber, String[]>();
		lineNum = new LineNumber();
	}

	// Returns the number of the next line of the proof to be filled in
	public String nextLineNumber() {

		return lineNum.toString();

	}

	// Tries to extend the proof with the given input line
	public void extendProof(String x) throws IllegalLineException,
			IllegalInferenceException {
		x = x.trim();
		if (x.equalsIgnoreCase("print")) {
			System.out.println(this.toString());
		} else {
			// split the line between spaces
			String[] tokens = x.split(" +");
			// fist token will be the reason
			String reason = tokens[0];
			// last token will be the expression
			String exprAsString = tokens[tokens.length - 1];
			// make an Expression object to check if the expression is valid
			Expression currentExpr = new Expression(exprAsString);

			// check that the line is valid based on the reason given

			if (reason.equalsIgnoreCase("show")) {
				// make sure there are exactly two tokens (show followed by and
				// an expression)
				if (tokens.length != 2) {
					throw new IllegalLineException(
							"Lines beginning with show must be followed by a single expression");
				}

				// put valid show line into history
				LineNumber lineNumDuplicate = new LineNumber(lineNum.toString());
				history.put(lineNumDuplicate, tokens);

				// calculate next line number
				if (!(lineNum.size() == 1 && lineNum.get(0) == 1)) {
					lineNum.add();
				} else {
					lineNum.Next();
				}
			}

			else if (reason.equalsIgnoreCase("assume")) {
				// make sure assumption is valid
				validateAssume(tokens, currentExpr, exprAsString);

				// add valid line to history
				LineNumber lineNumDuplicate = new LineNumber(lineNum.toString());
				history.put(lineNumDuplicate, tokens);

				// Calculate next line number
				lineNum.Next();
			}

			else if (reason.equalsIgnoreCase("mp")) {
				// make sure the mp expression is valid
				validateMP(tokens, exprAsString);

				// add valid line to history
				LineNumber lineNumDuplicate = new LineNumber(lineNum.toString());
				history.put(lineNumDuplicate, tokens);

				// calculate the next line number
				if (lineNum.toString().length() > 1) {
					LineNumber parent = lineNum.parent();
					String[] showLine = history.get(parent);
					String showExpr = showLine[showLine.length - 1];
					if (exprAsString.equals(showExpr)) {
						lineNum = parent;
					}
				}
				lineNum.Next();
			}

			else if (reason.equalsIgnoreCase("mt")) {
				// make sure mt is valid
				validateMT(tokens, currentExpr);

				// add valid line to history
				LineNumber lineNumDuplicate = new LineNumber(lineNum.toString());
				history.put(lineNumDuplicate, tokens);

				// calculate the next line number
				if (lineNum.toString().length() > 1) {
					LineNumber parent = lineNum.parent();
					String[] showLine = history.get(parent);
					String showExpr = showLine[showLine.length - 1];
					if (exprAsString.equals(showExpr)) {
						lineNum = parent;
					}
				}
				lineNum.Next();
			}

			else if (reason.equalsIgnoreCase("co")) {
				// make sure co is valid
				validateCO(tokens);

				// add valid line to history
				LineNumber lineNumDuplicate = new LineNumber(lineNum.toString());
				history.put(lineNumDuplicate, tokens);

				// calculate the next line number
				if (lineNum.toString().length() > 1) {
					LineNumber parent = lineNum.parent();
					String[] showLine = history.get(parent);
					String showExpr = showLine[showLine.length - 1];
					if (exprAsString.equals(showExpr)) {
						lineNum = parent;
					}
				}
				lineNum.Next();

			} else if (reason.equalsIgnoreCase("ic")) {
				// make sure ic is valid
				validateIC(tokens, currentExpr);

				// add valid line to history
				LineNumber lineNumDuplicate = new LineNumber(lineNum.toString());
				history.put(lineNumDuplicate, tokens);

				// calculate the next line number
				if (lineNum.toString().length() > 1) {
					LineNumber parent = lineNum.parent();
					String[] showLine = history.get(parent);
					String showExpr = showLine[showLine.length - 1];
					if (exprAsString.equals(showExpr)) {
						lineNum = parent;
					}
				}
				lineNum.Next();
			}

			else if (reason.equalsIgnoreCase("repeat")) {
				if (tokens.length != 3) {
					throw new IllegalLineException(
							"Lines beginning with repeat must be followed by a line number and a single expression");
				}

				String[] tkns = history.get(new LineNumber(tokens[1]));
				String exprs = tkns[tkns.length - 1];

				if (exprs.equals(tokens[2])) {
					history.put(lineNum, tokens);
				}

				else {
					throw new IllegalInferenceException(
							"Line called in repeat must prove the given expression");
				}

				// add valid line to history
				LineNumber lineNumDuplicate = new LineNumber(lineNum.toString());
				history.put(lineNumDuplicate, tokens);

				// calculate next line number
				if (lineNum.toString().length() > 1) {
					LineNumber parent = lineNum.parent();
					String[] showLine = history.get(parent);
					String showExpr = showLine[showLine.length - 1];
					if (exprAsString.equals(showExpr)) {
						lineNum = parent;
					}
				}
				lineNum.Next();

			}

			else {
				// make sure thm is valid
				validateTHM(tokens, reason, currentExpr);

				// add valid line to history
				LineNumber lineNumDuplicate = new LineNumber(lineNum.toString());
				history.put(lineNumDuplicate, tokens);

				// calculate the next line number
				if (lineNum.toString().length() > 1) {
					LineNumber parent = lineNum.parent();
					String[] showLine = history.get(parent);
					String showExpr = showLine[showLine.length - 1];
					if (exprAsString.equals(showExpr)) {
						lineNum = parent;
					}
				}
				lineNum.Next();

			}
		} // end else statement
	}

	private void validateAssume(String[] tokens, Expression currentExpr,
			String exprAsString) throws IllegalLineException,
			IllegalInferenceException {
		// make sure there are exactly two tokens (assume followed by an
		// expression)
		if (tokens.length != 2) {
			throw new IllegalLineException(
					"Lines beginning with assume must be followed by a single expression");
		}
		// make sure the previous line's reason was "show"
		String[] previousLine = history.get(lineNum.previous());
		if (previousLine == null) {
			throw new IllegalInferenceException("Proof must start with show");
		}
		Expression previousLineExpr = new Expression(
				previousLine[previousLine.length - 1]);
		if (!(previousLine[0].equalsIgnoreCase("show"))) {
			throw new IllegalInferenceException(
					"Assume may only come directly after a \"show\"");
		}
		// check the validity of the assumption
		else {
			// if the operator of the current line is ~ then the leftString must
			// be the same as the previous line's expression
			if (currentExpr.myRoot.myItem.equals("~")) {
				if (!currentExpr.myRoot.leftString
						.equals(previousLine[previousLine.length - 1])) {
					throw new IllegalInferenceException(
							"Can only assume ~E after E");
				}
			}
			// if the operator isn't ~, then the previous operator must be an
			// implication
			// and the current line must equal the leftString of the the
			// previous line
			else if (!(previousLineExpr.myRoot.myItem.equals("=>"))
					|| (!exprAsString
							.equals(previousLineExpr.myRoot.leftString))) {
				throw new IllegalInferenceException(
						"Can only assume E1 after E1=>E2");
			}
		}
	}

	private void validateMP(String[] tokens, String exprAsString)
			throws IllegalLineException, IllegalInferenceException {
		// Check valid line reference
		if (!validateLN(lineNum)) {
			throw new IllegalLineException("Line number is invalid.");
		}

		// make sure there are exactly 4 tokens (mp followed by 2 line numbers
		// and an expression)
		if (tokens.length != 4) {
			throw new IllegalLineException(
					"Lines beginning with mp must be followed by exactly 2 line numbers and an expression");
		}

		String[] previousLine = history.get(lineNum.previous());
		if (previousLine == null) {
			throw new IllegalInferenceException("Proof must start with show");
		}
		// get the lines corresponding the the indicated line numbers
		String[] input1 = history.get(new LineNumber(tokens[1]));
		String[] input2 = history.get(new LineNumber(tokens[2]));

		// expr1 and expr2 as strings
		String expr1String = input1[input1.length - 1];
		String expr2String = input2[input2.length - 1];

		// make the longer expression expr1
		if (expr1String.length() < expr2String.length()) {
			String tempString = expr1String;
			expr1String = expr2String;
			expr2String = tempString;
		}

		// get the expressions indicated by the line numbers
		Expression expr1 = new Expression(expr1String);
		Expression expr2 = new Expression(expr2String);

		// make sure expr1 is an implication
		if (!expr1.myRoot.myItem.equals("=>")) {
			throw new IllegalInferenceException(
					"mp must use the expressions E1=>E2 and E1");
		}

		// make sure expr2 equals the leftString of expr1
		if (!expr2String.equals(expr1.myRoot.leftString)) {
			throw new IllegalInferenceException(
					"mp must use the expressions E1=>E2 and E1");
		}

		// make sure current expression equals the righString of expr1
		if (!exprAsString.equals(expr1.myRoot.rightString)) {
			throw new IllegalInferenceException(
					"mp must infer E2 from E1=>E2 and E1");
		}
	}

	private void validateMT(String[] tokens, Expression currentExpr)
			throws IllegalLineException, IllegalInferenceException {
		// Check valid line reference
		if (!validateLN(lineNum)) {
			throw new IllegalLineException("Line number is invalid.");
		}
		// make sure there are exactly 4 tokens (mt followed by 2 line numbers
		// and an expression)
		if (tokens.length != 4) {
			throw new IllegalLineException(
					"Lines beginning with mt must be followed by exactly 2 line numbers and an expression");
		}
		String[] previousLine = history.get(lineNum.previous());
		if (previousLine == null) {
			throw new IllegalInferenceException("Proof must start with show");
		}
		// get the lines corresponding the the indicated line numbers
		String[] input1 = history.get(new LineNumber(tokens[1]));
		String[] input2 = history.get(new LineNumber(tokens[2]));

		// expr1 and expr2 as strings
		String expr1String = input1[input1.length - 1];
		String expr2String = input2[input2.length - 1];

		// make the longer expression expr1
		if (expr1String.length() < expr2String.length()) {
			String tempString = expr1String;
			expr1String = expr2String;
			expr2String = tempString;
		}

		// get the expressions indicated by the line numbers
		Expression expr1 = new Expression(expr1String);
		Expression expr2 = new Expression(expr2String);

		// make sure expr1 is an implication
		if (!expr1.myRoot.myItem.equals("=>")) {
			throw new IllegalInferenceException(
					"mt must use the expressions E1=>E2 and ~E2");
		}

		// make sure expr2 is ~E2
		if (!expr2.myRoot.myItem.equals("~")
				|| !(expr2.myRoot.leftString.equals(expr1.myRoot.rightString))) {
			throw new IllegalInferenceException(
					"mt must use the expressions E1=>E2 and ~E2");
		}

		// make sure current expression equals ~E1
		if ((!currentExpr.myRoot.myItem.equals("~"))
				|| (!currentExpr.myRoot.leftString
						.equals(expr1.myRoot.leftString))) {
			throw new IllegalInferenceException(
					"mt must infer ~E1 from E1=>E2 and ~E2");
		}
	}

	private void validateCO(String[] tokens) throws IllegalLineException,
			IllegalInferenceException {
		// Check valid line reference
		if (!validateLN(lineNum)) {
			throw new IllegalLineException("Line number is invalid.");
		}
		// make sure there are exactly 4 tokens (co followed by 2 line numbers
		// and an expression)
		if (tokens.length != 4)
			throw new IllegalLineException(
					"Lines beginning with co must be followed by exactly 2 line numbers and an expression");

		// get the lines corresponding the the indicated line numbers
		String[] input1 = history.get(new LineNumber(tokens[1]));
		String[] input2 = history.get(new LineNumber(tokens[2]));

		// expr1 and expr2 as strings
		String expr1String = input1[input1.length - 1];
		String expr2String = input2[input2.length - 1];

		// make the longer expression expr1
		if (expr1String.length() < expr2String.length()) {
			String tempString = expr1String;
			expr1String = expr2String;
			expr2String = tempString;
		}

		// get the expressions indicated by the line numbers
		Expression expr1 = new Expression(expr1String);
		Expression expr2 = new Expression(expr2String);

		// makes sure expr1 is starts with a ~ and it's leftString is equal to
		// expr2
		if ((!expr1.myRoot.myItem.equals("~"))
				|| !(expr1.myRoot.leftString.equals(expr2String))) {
			throw new IllegalInferenceException(
					"co must use the expressions E1 and ~E1");
		}

	}

	private void validateIC(String[] tokens, Expression currentExpr)
			throws IllegalLineException, IllegalInferenceException {
		// Check valid line reference
		if (!validateLN(lineNum)) {
			throw new IllegalLineException("Line number is invalid.");
		}
		// make sure there are exactly 3 tokens (ic followed by a line number
		// and an expression)
		if (tokens.length != 3) {
			throw new IllegalLineException(
					"Lines beginning with ic must be followed by a single line number and an expression");
		}

		// get the line corresponding the the indicated line number
		String[] input1 = history.get(new LineNumber(tokens[1]));

		// expr1 as a String
		String expr1String = input1[input1.length - 1];

		// make sure current expression's operator is implication
		if (!currentExpr.myRoot.myItem.equals("=>")) {
			throw new IllegalInferenceException("ic must infer an implication");
		}

		// make sure the current expression's rightString equals the indicated
		// line number's expression
		if (!currentExpr.myRoot.rightString.equals(expr1String)) {
			throw new IllegalInferenceException("ic must E1=>E2 from E2");
		}
	}

	private void validateTHM(String[] tokens, String reason,
			Expression currentExpr) throws IllegalLineException,
			IllegalInferenceException {
		// get the expression corresponding to the indicated thm
		try {
			Expression thm = myTheoremSet.get(reason);

			// see if current expression matches indicated thm's expression
			if (!currentExpr.matches(thm)) {
				throw new IllegalInferenceException(
						"Indicated theorem does not apply to current expression");
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalInferenceException(
					"Theorem is not defined in TheoremSet");
		}
	}

	// Returns a printable version of the legal proof steps so far
	public String toString() {
		Enumeration<LineNumber> keys = history.keys();
		ArrayList<LineNumber> key = new ArrayList<LineNumber>();
		while (keys.hasMoreElements()) {
			key.add(keys.nextElement());
		}
		Collections.sort(key);
		Iterator iter = key.iterator();
		// Enumeration<String[]> values = history.elements();
		String summary = "";
		while (iter.hasNext()) {
			LineNumber ln = (LineNumber) iter.next();
			String[] current = history.get(ln);
			String value = "";
			for (int i = 0; i < current.length; i++) {
				value += current[i] + " ";
			}
			value.trim();
			summary += ln.toString() + "\t" + value + "\n";
		}
		return summary;
	}

	// Returns true if the most recent line matches the expression given in the
	// outermost
	// show step and is not part of a subproof and returns false otherwise
	public boolean isComplete() {

		String [] tkns = history.get(lineNum.previous());
		String exprs = tkns[tkns.length-1];
		LineNumber first = new LineNumber();
		String [] tkns2 = history.get(first);
		String original = tkns2[tkns2.length-1];
	
		if(exprs.equals(original) && !(lineNum.previous().equals(1))) {
			return true;
		}
		return false;
	}

	private boolean validateLN(LineNumber requested) {

		if (requested.size() == 1) {
			return true;
		}

		else if (requested.size() > lineNum.size()) {
			return false;
		}

		else {
			String ln = lineNum.toString();
			String req = requested.toString();
			if (ln.charAt(0) != ln.charAt(0)) {
				return false;
			}
			for (int i = 1; i < req.length(); i++) {
				if (ln.charAt(i) < (req.charAt(i))) {
					return false;
				}
			}
			return true;
		}
	}

	public LineNumber getLineNum() {
		return lineNum;
	}

	public Hashtable<LineNumber, String[]> getHistory() {
		return history;
	}
}