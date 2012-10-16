package ch.zhaw.inf3.emulator;

public class TransformNumbersAndFormats{

	public static short binStringToDec(String op_pattern){
		short bm = 0;

		for (int i = 0; i < op_pattern.length(); i++) {
			Character c = op_pattern.charAt(i);
			int n = Character.getNumericValue(c);
			if (n == 1) {
				bm += (short)( 1 << (15-i) );				
			}
			//System.out.printf("%s:%s %s %s\n",i, c, n, bm);
		}
		return bm;		
	}
	
	public static String decToBinString(int number){
		
		//String s = Integer.toString(number, 2); 
		return String.format("%16s", Integer.toBinaryString(number)).replace(" ", "0");
		//	return Integer.toString(number, 2); 
		
	}
}

