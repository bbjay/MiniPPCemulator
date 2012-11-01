package ch.zhaw.inf3.emulator;

import java.util.List;

public class AssemblerCompiler2 extends AssemblerCompiler {
	InstructionSetArchitecture isa;

	public AssemblerCompiler2() {
		isa = new InstructionSetArchitecture();
	}

	public short[] compile(List<String> list) {
		short[] code = new short[list.size()];
		for (int i = 0; i < list.size(); i++) {
			String[] subString = list.get(i).split(" ");
			Instruction instruction = isa.getByMnemonic(subString[0]);
			
			short[] operands = new short[2];
			if (subString.length > 1 ) {
				operands[0] = parseRegister(subString[1]);
			}
			if (subString.length > 2 ) {
				operands[1] = parseAddress(subString[2]);				
			}
			//System.out.println(operands[0] +" "+ operands[1]);
			
			code[i] = instruction.encodeOperandValues(operands);
		}
		return code;
	}
	
	public short parseRegister(String reg){
		return (short)Integer.parseInt(reg.substring(1, 2));
	}
	
	public short parseAddress(String addr){
		return (short)Integer.parseInt(addr.substring(1, addr.length()));
	}
}
