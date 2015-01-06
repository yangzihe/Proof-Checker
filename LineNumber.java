import java.util.ArrayList;

import javax.sound.sampled.Line;

public class LineNumber implements Comparable{

	private ArrayList<Integer> number;
	
	public LineNumber() {
		number = new ArrayList<Integer>();
		number.add(new Integer(1));
	}
	
	public LineNumber(String s) {
		number = new ArrayList<Integer>();
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != '.') {
				number.add(Integer.parseInt(s.substring(i, i + 1)));
			}
		}
	}
	
	
	public Integer get(int ind) {
		return number.get(ind);
	}
	
	public void add() {
		number.add(new Integer(1));
	}
	
	public void Next() {
		Integer change = number.get(number.size() - 1);
		change++;
		int index = number.size();
		number.remove(index - 1);
		number.add(change);
	}
	
	public LineNumber previous() {
		LineNumber previous = new LineNumber();
		previous.number.remove(0);
		
		
		for (int i = 0; i < this.number.size(); i++) {
			previous.number.add(this.number.get(i));
		}
		Integer change = previous.number.get(previous.number.size() - 1);
		change--;
		int index = previous.size();
		previous.number.remove(index - 1);
		if (change != 0) {
			previous.number.add(change);
		}
		return previous;
	}
	
	public LineNumber parent () {
		LineNumber parent = new LineNumber();
		parent.number.clear();
		for (int i = 0; i < this.size(); i++) {
			parent.number.add(this.number.get(i));
		}
		parent.number.remove(parent.size() - 1);
		return parent;
	}
	
	public String toString(){
		String rtn = "";
		for (int i = 0; i < number.size(); i++) {
			if (i == number.size() - 1) {
				rtn += number.get(i).toString();
			} else { rtn += number.get(i).toString() + ".";
			}
		}
		return rtn;
	}
	
	
	public boolean equals (Object obj) {
		if (this.toString().equals(obj.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public int hashCode(){
		return number.hashCode();
	}
	
	public int size(){
		return number.size();
	}
	
	public int compareTo(Object obj){
		LineNumber other = (LineNumber) obj;
		int extra = 0;
		
		if(other.size()<number.size()){
			extra = 1;
		}
		
		if(number.size()<other.size()){
			extra = -1;
		}
		
		for(int i=0; i<Math.min(number.size(), other.size()); i++){
			if(number.get(i)<other.get(i)){
				return -1;
			}
			if(other.get(i)<number.get(i)){
				return 1;
			}
		}
		return extra;
	}
}
