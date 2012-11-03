package ch.zhaw.inf3.emulator;

public class NumConverter{
	
	/**
	 * @param op_pattern, for example: "0100010111110100"
	 * @return short, the command in decimal form
	 */
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
	
	/**
	 * @param number, for example: 17908
	 * @return String representing a 16-digit binary, for example: "0100010111110100"
	 */
	public static String decToBinString(short number){
		String s = Integer.toBinaryString(number);
		// negative integers will have 32bit (11111111...1 cause of Two's-complement)
		// shorten it to 16 chars
		if(s.length() > 16) s = s.substring(s.length()-16, s.length());

		return String.format("%16s", s).replace(" ", "0");
		
	}
	
}

