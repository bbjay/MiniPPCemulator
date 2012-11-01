package ch.zhaw.inf3.emulator;

import java.util.List;

public class AssemblerCompiler {
	
	public AssemblerCompiler(){
	
	}

	public short[] compile(List<String> list){
		short[] code=new short[list.size()];
		for(int i=0; i<list.size();i++){
			String[]subArray=list.get(i).split(" ");
			short sh = translateMnemonic(subArray[0]);
			if(subArray.length==3){
			sh += translateAdress(subArray[2]);
			}
			if(subArray.length>=2){
			sh += translateRegister(subArray[1]);
			}
			code[i]=sh;
		}
		return code;
	}
	
	public short translateMnemonic(String mnemonic){
		if (mnemonic.equals("CLR")){
			return 2^9+2^7;
		}
		else if (mnemonic.equals("ADD")){
			return 2^9+2^8+2^7;
		}
		else if (mnemonic.equals("INC")){
			return 2^8;
		}
		else if (mnemonic.equals("DEC")){
			return 2^10;
		}
		else if (mnemonic.equals("LWDD")){
			return 2^14;
		}
		else if (mnemonic.equals("SWDD")){
			return 2^14+2^13;
		}
		else if (mnemonic.equals("SRA")){
			return 2^10+2^8;
		}
		else if (mnemonic.equals("SLA")){
			return 2^11;
		}
		else if (mnemonic.equals("SRL")) {
			return 2^11+2^8;
		}
		else if (mnemonic.equals("SLL")){
			return 2^11+2^10;
		}
		else if (mnemonic.equals("AND")){
			return 2^9;
		}
		else if (mnemonic.equals("OR")){
			return 2^9+2^8;
		}
		else if (mnemonic.equals("NOT")){
			return 2^7;
		}
		else if (mnemonic.equals("BZ")){
			return 2^12+8^9;
		}
		else if (mnemonic.equals("BNZ")){
			return 2^12+2^8;
		}
		else if (mnemonic.equals("BC")){
			return 2^12+2^9+2^8;
		}
		else if (mnemonic.equals("B")){
			return 2^12;
		}
		else if (mnemonic.equals("BZD")){
			return 2^13+2^12;
		}
		else if (mnemonic.equals("BNZD")){
			return 2^13+2^11;
		}
		else if (mnemonic.equals("BCD")){
			return 2^13+2^12+2^11;
		}
		else if (mnemonic.equals("BD")){
			return 2^13;
		}
		else if (mnemonic.equals("END")){
			return 0;
		}
		else return 0;
	}
	
	public short translateRegister(String register){
		if (register.equals("R0")){
			return 0;
		}
		else if (register.equals("R1")){
			return 2^10;
		}
		else if (register.equals("R2")){
			return 2^11;
		}
		else if (register.equals("R3")){
			return 2^11+2^10;
		}
		else return 0;
	}
	public short translateAdress(String adress){
		short i = Short.valueOf(adress.substring(1));
		return i;
	}
}
