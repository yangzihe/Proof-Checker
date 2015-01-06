import junit.framework.TestCase;

public class ProofTest extends TestCase {
	
	/*public void testGivenExamples(){
		for(int i=1; i<13; i++){
			try{
				
				ProofChecker.main(new String[] {"Theorems.txt", "Test" + i + ".txt"});
		
			}catch(Exception e){
				
				System.out.println("Test" + i + " failed");
				
			}
		}
	}*/
	public void testShow(){
		TheoremSet theorems=new TheoremSet();
		Proof thing=new Proof(theorems);
		
		// test most simple valid show
		try {
			thing.extendProof("show a");
		}
		catch(Exception e) {
			fail();
		}
		
		// test a valid show starting with ~
		try {
			thing.extendProof("show ~p");
		}
		catch(Exception e) {
			fail();
		}
		
		// test valid show using an operator
		try {
			thing.extendProof("show (a&b)");
		}
		catch(Exception e) {
			fail();
		}
		
		// test an invalid show with no expression
		try {
			thing.extendProof("show");
			fail();
		}
		catch(Exception e) {
		}
		
		// test an invalid show with too many tokens
		try {
			thing.extendProof("show 1 a");
			fail();
		}
		catch(Exception e) {
		}
		
		// test an invalid show with an illegal expression
		try {
			thing.extendProof("show ~");
			fail();
		}
		catch(Exception e) {
		}
		
	}

	public void testAssume() {
		TheoremSet theorems=new TheoremSet();
		Proof thing=new Proof(theorems);
		//see if it breaks when you have no spaces
		try {
			thing.extendProof("assumep");
			fail();
		}
		catch(Exception e) {
		}
		//just feed in assume and should say it needs a show before it
		try {
			thing.extendProof("assume p");
			fail();
		}
		catch(Exception e) {
		}
		//try to assume something completely irrelevant
		try {
			thing.extendProof("show ((a|b)=>b)");
			thing.extendProof("assume a");
			fail();
		}
		catch(Exception e) {
		}
		//shouldn't let you assume random other letters
		try {
			thing.extendProof("show ((a|b)=>b)");
			thing.extendProof("assume (a|b)");
		}
		catch(Exception e) {
			fail();
		}
		//to test spaces
		try {
			thing.extendProof("show (a=>b)");
			thing.extendProof("assume     a"); 
		}
		catch(Exception e) {
			fail();
		}
		//to test spaces within expression
		try {
			thing.extendProof("show ((a|c)=>(b=>c))");
			thing.extendProof("assume (a |c)"); 
			fail();
		}
		catch(Exception e) {
		}
		
	}
	
	public void testRepeat(){
		TheoremSet theorems=new TheoremSet();
		Proof thing=new Proof(theorems);
		//tests whether it throws an exception when there aren't enough arguments
		try {
			thing.extendProof("show ((a|b)=>b)");
			thing.extendProof("repeat 1");
			fail();
		}
		catch(Exception e) {
		}
		//tests whether it throws an exception when there's a space after
		try {
			thing.extendProof("show ((a|b)=>b)");
			thing.extendProof("repeat 1 ");
			fail();
		}
		catch(Exception e) {
		}
		//tests whether it can repeat a show statement
		try {
			thing.extendProof("show ((a|b)=>b)");
			thing.extendProof("repeat 1 ((a|b)=>b)");
		}
		catch(Exception e) {
			fail();
		}

		Proof thing2=new Proof(theorems);
		//tests whether it can repeat an assume
		try {
			thing.extendProof("show ((a|b)=>b)");
			thing.extendProof("assume (a|b)");
			thing.extendProof("repeat 2 (a|b)");
		}
		catch(Exception e) {
			//fail();
		}
		//tests whether it can repeat a line not directly before it
		try {
			thing.extendProof("show ((a|b)=>b)");
			thing.extendProof("assume (a|b)");
			thing.extendProof("repeat 1 ((a|b)=>b)");
		}
		catch(Exception e) {
			fail();
		}
		//tests whether it can repeat invalid expressions
		try {
			thing.extendProof("show ((a|b)=>b)");
			thing.extendProof("assume (a|b)");
			thing.extendProof("repeat 1 (a|b)=>b");
			fail();
		}
		catch(Exception e) {
		}
		//tests whether it can repeat diff things from line number
		try {
			thing.extendProof("show ((a|b)=>b)");
			thing.extendProof("assume (a|b)");
			thing.extendProof("repeat 1 (a|b)");
			fail();
		}
		catch(Exception e) {
		}
	}
	
	public void testmp(){
		TheoremSet theorems=new TheoremSet();
		Proof thing=new Proof(theorems);
		//try to start a proof with mp
		try {
			thing.extendProof("mp 1 2 e");
			fail();
		}
		catch(Exception e) {
		}
		//try a basic mp
		try {
			thing.extendProof("show (m=>p)");
			thing.extendProof("assume m");
			thing.extendProof("mp 1 2 p");
		}
		catch(Exception e) {
			fail();
		}
		//try to do mp without spaces between line numbers
		try {
			thing.extendProof("show (m=>p)");
			thing.extendProof("assume m");
			thing.extendProof("mp 12 (m=>p)");
			fail();
		}
		catch(Exception e) {
		}
		//try to see if you need spaces between numbers and expression
		try {
			thing.extendProof("show (m=>p)");
			thing.extendProof("assume m");
			thing.extendProof("mp 1 2(m=>p)");
			fail();
		}
		catch(Exception e) {
		}
		//try to see if complicated expressions work
		Proof thing2=new Proof(theorems);
		try {
			thing2.extendProof("show ((m|k)=>p)");
			thing2.extendProof("assume (m|k)");
			thing2.extendProof("mp 1 2 p");
		}
		catch(Exception e) {
			fail();
		}
	}
	
	public void testmt(){
		TheoremSet theorems=new TheoremSet();
		Proof thing=new Proof(theorems);
		//try to start a proof with mt
		try {
			thing.extendProof("mt 1 2 e");
			fail();
		}
		catch(Exception e) {
		}
		//try to do mt without spaces between the line numbers
		thing=new Proof(theorems);
		try {
			thing.extendProof("show (m=>p)");
			thing.extendProof("assume m");
			thing.extendProof("mt 12 (m=>p)");
			fail();
		}
		catch(Exception e) {
		}
		//try to see if a basic mt works
		thing=new Proof(theorems);
		try {
			thing.extendProof("show (m=>p)");
			thing.extendProof("assume m");
			thing.extendProof("show p");
			thing.extendProof("assume ~p");
			thing.extendProof("mt 1 3.1 ~m");
		}
		catch(Exception e) {
			fail();
		}
		//try to see if complicated expressions work
		Proof thing2=new Proof(theorems);
		try {
			thing2.extendProof("show (((p=>q)=>q)=>((q=>p)=>p))");
			thing2.extendProof("assume ((p=>q)=>q)");
			thing2.extendProof("show ((q=>p)=>p)");
			thing2.extendProof("assume (q=>p)");
			thing2.extendProof("show p");
			thing2.extendProof("assume ~p");
			thing2.extendProof("mt 3.2.1 3.1 ~q");
		}
		catch(Exception e) {
			fail();
		}
	}
	
	public void testic(){
		TheoremSet theorems=new TheoremSet();
		Proof thing=new Proof(theorems);
		//try to do a basic ic
		try {
			thing.extendProof("show (a=>c)");
			thing.extendProof("assume a");
			thing.extendProof("ic 2 (k=>a)");
		}
		catch(Exception e) {
			fail();
		}
		//see if ic will take 2 line numbers
		try {
			thing.extendProof("show (a=>c)");
			thing.extendProof("assume a");
			thing.extendProof("ic 1 2 (k=>a)");
			fail();
		}
		catch(Exception e) {
		}
		//test a more complex ic
		try {
			thing.extendProof("show (a=>(c|b))");
			thing.extendProof("assume a");
			thing.extendProof("ic 2 (((k|b)&l)=>a)");
		}
		catch(Exception e) {
			fail();
		}
		//see if you can use variables you already have in ic(like c in the expression pointing toa)
		try {
			thing.extendProof("show (a=>(c|b))");
			thing.extendProof("assume a");
			thing.extendProof("ic 2 (((c|b)&l)=>a)");
		}
		catch(Exception e) {
			fail();
		}
	}
	
	public void testco(){
		TheoremSet theorems=new TheoremSet();
		Proof thing=new Proof(theorems);
		//try to do a basic co
		try {
			thing.extendProof("show a");
			thing.extendProof("assume ~a");
			thing.extendProof("co 1 2 ((k|b)=>l)");
		}
		catch(Exception e) {
			fail();
		}
		//see if co lets you infer things with variables you're already using
		try {
			thing.extendProof("show a");
			thing.extendProof("assume ~a");
			thing.extendProof("co 1 2 a");
		}
		catch(Exception e) {
			fail();
		}
		//see if co lets you infer not a
		try {
			thing.extendProof("show a");
			thing.extendProof("assume ~a");
			thing.extendProof("co 1 2 ~a");
		}
		catch(Exception e) {
			fail();
		}
	}
}