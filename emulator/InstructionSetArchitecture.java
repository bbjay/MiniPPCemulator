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
		
		instructions.add(new Instruction("CLR", "0000xx101<not u>", 1));
		instructions.add(new Instruction("ADD", "0000xx111<not u>", 1));
		instructions.add(new Instruction("ADDD", "1<----Zahl----->", 1));
	
		
		instructions.add(new Instruction("LWDD", "010-xx<#addr>", 2));
		instructions.add(new Instruction("SWDD", "011-xx<#addr>", 2));
		
		instructions.add(new Instruction("SRA", "00000101not used", 0));
		instructions.add(new Instruction("SLA", "00001000not used", 0));
		instructions.add(new Instruction("SRL", "00001001not used", 0));
		instructions.add(new Instruction("SLL", "00001100not used", 0));
		
		instructions.add(new Instruction("AND", "0000xx100<not u>", 1));
		instructions.add(new Instruction("OR", "0000xx110<not u>", 1));
		instructions.add(new Instruction("NOT", "0000xx001<not u>", 0));
		
		instructions.add(new Instruction("BZ", "0001xx10<not us>", 1));
		instructions.add(new Instruction("BNZ", "0001xx01<not us>", 1));
		instructions.add(new Instruction("BC", "0001xx11<not us>", 1));
		instructions.add(new Instruction("B", "0001xx00<not us>", 1));
		instructions.add(new Instruction("BZD", "00110-<Adresse->", 1));
		instructions.add(new Instruction("BNZD", "00101-<Adresse->", 1));
		instructions.add(new Instruction("BCD", "00111-<Adresse->", 1));
		instructions.add(new Instruction("BD", "00100-<Adresse->", 1));
		
		instructions.add(new Instruction("INC", "00000001not used", 0));
		instructions.add(new Instruction("DEC", "00000100not used", 0));
		
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
