package ch.zhaw.inf3.emulator;

import java.util.ArrayList;
import java.util.List;

public class InstructionSetArchitecture {
	public short start_offset;
	public int word_width;
	public int word_bits;
	private List<Instruction> instructions;

	public InstructionSetArchitecture() {
		start_offset = 100;
		word_bits = 16;
		word_width = word_bits/8;
		instructions = new ArrayList<Instruction>();
		
		Instruction add = new Instruction("0000xx101<not u>", 1);
		add.mnemonic = "ADD";
		
		Instruction lwdd = new Instruction("0100xx<#addr>", 2);
		lwdd.mnemonic = "LWDD";
		lwdd.full_mnemonic ="LWDD Rnr, #Adr";
		
		instructions.add(add);
	}

	public Instruction decodeWord(short word) {
		if ((word & Short.decode("0xf000")) == 1 ) {

		}
		// search for a matching instruction
		// else
		return null;
	}

}
