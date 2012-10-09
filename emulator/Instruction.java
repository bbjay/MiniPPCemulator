package ch.zhaw.inf3.emulator;

public class Instruction {
	public String mnemonic;
	public String full_mnemonic;
	public int num_operands;
	public String opcode_pattern;
	public short[] operands;

	public Instruction(String opcode_pattern, int num_operands){
		this.opcode_pattern = opcode_pattern;
		this.num_operands = num_operands;
		operands = new short[num_operands];
	}
	
	public String assemble(short[] ops){
		operands = ops;
		return "xxxxxxxxx";
	}
	
	public boolean decode(short word){
		return true;
	}


}
