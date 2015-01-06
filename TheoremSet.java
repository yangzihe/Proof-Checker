import java.util.*;

public class TheoremSet {
	public HashMap<String, Expression> theorem;

	public TheoremSet ( ) {
		theorem = new HashMap<String, Expression>();
	}

	public Expression put (String s, Expression e) {
		if(theorem.get(s)!=null) {
			throw new IllegalArgumentException("This theorem "+s+" is already in this Theorem Set.");
		}
		theorem.put(s, e);
		return e;
	}
		
	public Expression get(String s){
		Expression expression= theorem.get(s);
		if (expression == null) {
			throw new IllegalArgumentException("The theorem " + s + " was not found in this Theorem Set");
		}
		return expression;
	}
}