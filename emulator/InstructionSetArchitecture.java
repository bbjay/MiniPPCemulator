package ch.zhaw.inf3.emulator;

import java.util.ArrayList;
import java.util.List;

public class InstructionSetArchitecture {
	public short start_offset;
	public int word_width;
	public int word_bits;
	private List<Instruction> instructions;

	public InstructionSetArchitecture() {
		start_offset = 100; // program memory start offset
		word_bits = 16;
		word_width = word_bits/8;
		instructions = new ArrayList<Instruction>();
		
		instructions.add(new Instruction("ADD" , "0000xx111<not u>", 1));
		instructions.add(new Instruction("LWDD", "010-xx<#addr>", 2));
		instructions.add(new Instruction("END" , "0", 0));
	}

	public Instruction decodeWord(short word) {
		for (Instruction i : instructions) {
			System.out.println(Integer.toBinaryString(i.opcode_bitmask));
			System.out.println(Integer.toBinaryString(word));
			if ( (short)(i.opcode_bitmask & word) == i.opcode_bitmask) {
				i.decodeOperandValues(word);
				return i;
			}
		}
		return null;
	}
	
	public Instruction getByMnemonic(String mnemonic){
		for (Instruction i : instructions) {
			if (i.mnemonic.equals(mnemonic)) {
				return i;
			}
		}
		return null;
	}
	
}
