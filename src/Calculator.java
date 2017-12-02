import java.util.ArrayList;

public class Calculator {
	public static String calculate(String s) {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		ArrayList<Character> symbols = new ArrayList<Character>();
		
		String number = new String();

		for(int i =0;i<s.length();i++) {
			while(Character.isDigit(s.charAt(i))) {
				number+=s.charAt(i++);
				
			}
			numbers.add(Integer.parseInt(number));
			if(s.charAt(i) ==  ' ')
				symbols.add(s.charAt(i));
			
	
		}
		int result = numbers.get(0);
		for(int i=1;i<numbers.size();i++) {
			if(symbols.get(i-1).equals('+'))
				result+=numbers.get(i);
			else if(symbols.get(i-1).equals('*'))
				result*=numbers.get(i);
			else if(symbols.get(i-1).equals('-'))
				result-=numbers.get(i);
			else if(symbols.get(i-1).equals('/'))
				result+=numbers.get(i);
				
		}
		
		return ""+result;
	}
}
