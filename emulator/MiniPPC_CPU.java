package ch.zhaw.inf3.emulator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("unused")
public class MiniPPC_CPU {
	private int cycle_count = 0;
	private short register[] = new short[4];
	private short RAM[];
	private short IP; // Next Instruction Pointer
	private short IR; // Instruction Register
	private int carry;
	private InstructionSetArchitecture isa;
	private InsImplAnnotationParser annotaion_parser;
	private int lwdd_calls = 0;
	private int swdd_calls = 0;

	public boolean isHalted = false;
	public Instruction current_instruction;

	public MiniPPC_CPU(int memory){
		this.RAM = new short[memory];
		this.isa = new InstructionSetArchitecture();
		this.IP = isa.start_offset;
		
		annotaion_parser = new InsImplAnnotationParser();
		annotaion_parser.parse(MiniPPC_CPU.class);
	}
	
	public void runCycle(){
		// Instruction Fetch
		IR = RAM[IP];
		
		// Instruction Decode
		Instruction op = isa.decodeWord(IR);
		
		// Increment IP
		IP = (short) (IP + 1);
		
		// Execute
		executeInstruction(op);
		
		cycle_count = cycle_count + 1;
	}
	
	public void reset(){
		IP = isa.start_offset;
		isHalted = false;
		carry = 0;
	}

	public void loadCode(short[] words){
		for (int i = 0; i < words.length; i++) {
			RAM[100+i] = words[i];
		}
	}
	
	public short[] getCodeAt(int start, int stop){
		short[] code = new short[stop-start +1];
		for (int i = 0; i < code.length; i++) code[i] = RAM[start+i];
		return code;
	}
	
	public void loadDataAtOffset(short[] words, int offset){
		for (int i = 0; i < words.length; i++) {
			RAM[offset+i] = words[i];
		}
	}
	
	public void printRegisters(StringBuilder buffer){
		for (int i = 0; i < register.length; i++) {
			//System.out.println("R"+i+":"+ + " "+ register[i]);
			buffer.append(String.format("R%d: %s %6d\n",i,NumConverter.decToBinString(register[i]), register[i]));
		}
		buffer.append(String.format("IR: %s %s\n", NumConverter.decToBinString(IR), current_instruction));
		buffer.append(String.format("IP: %3s  carry: %s  cycles:%5s  #LWDD:%4s  #SWDD:%4s\n", IP, carry, cycle_count, lwdd_calls, swdd_calls) );
	}
	
	public void printRam(StringBuilder buffer){
		for (int i = 500; i < RAM.length && i < 530; i++) {
			if(i%10==0) buffer.append("\nRAM["+i+":]:");
			buffer.append(String.format(" 0x%04x",RAM[i]));
			//buffer.append(String.format(" %s", NumConverter.decToBinString(RAM[i])));
		}
		buffer.append("\n");
	}

	private void executeInstruction(Instruction ins) {
		if (ins != null){
			current_instruction = ins;
			
			String method_name = annotaion_parser.instruction_map.get(ins.mnemonic);
			
			if (method_name != null)
				try {
					Method m = this.getClass().getDeclaredMethod(method_name, Instruction.class);
					m.invoke(this, ins);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			else {
				System.err.println("Instruction not implemented: " + ins.mnemonic);
			}
		}
	}
	
	
	// Instruction Implementations
	
	@InstructionImpl("CLR")
	private void clr(Instruction ins){
		register[ins.operands[0]] = 0;
		carry = 0;
	}
	
	@InstructionImpl("ADDD")
	private void addd(Instruction ins){
		register[0] += ins.operands[0];
		// check for overflow, set carry
	}
	
	@InstructionImpl("ADD")
	private void add(Instruction ins){
		short sh = register[ins.operands[0]];
		int result = register[0] + sh; // cheap&dirty solution ;-)
		register[0] += register[ins.operands[0]];
		if (result != register[0]) carry = 1;
		else carry = 0;
	}

	@InstructionImpl("INC")
	private void inc(Instruction ins) {
		short sh = register[0];
		register[0] += 1;
		if (sh > register[0]) carry = 1;
		else carry = 0;
	}

	@InstructionImpl("DEC")
	private void dec(Instruction ins) {
		short sh = register[0];
		register[0] -= 1;
		if (sh < register[0]) carry = 1;
		else carry = 0;
	}

	@InstructionImpl("LWDD")
	private void loadWordDirect(Instruction ins){
		register[ins.operands[0] ] = RAM[ins.operands[1]];
		lwdd_calls++;
	}

	@InstructionImpl("SWDD")
	private void storeWordDirect(Instruction ins){
		RAM[ins.operands[1]] = register[ins.operands[0]];
		swdd_calls++;
	}
	
	@InstructionImpl("SRA")
	private void sra(Instruction ins){
		carry = (register[0] & 1);
		register[0] >>= 1;
	}
	
	@InstructionImpl("SLA")
	private void shiftLeftArithmetic(Instruction ins){
		carry = (register[0] >> 15);
		int sign = register[0] & 2^15;
		register[0] = (short)(register[0] << 1);
		register[0] |= sign;
	}

	@InstructionImpl("SRL")
	private void srl(Instruction ins){
		carry = (register[0] & 1);
		register[0] >>>= 1; // doesn't insert 0s
		register[0] = (short)(register[0] & (short)0x7fff);
	}
	
	@InstructionImpl("SLL")
	private void shiftLeftLogical(Instruction ins){
		carry = (register[0] >> 15);
		register[0] = (short)(register[0] << 1);
	}

	@InstructionImpl("AND")
	private void and(Instruction ins){
		register[0] &= register[ins.operands[0]];
	}
	@InstructionImpl("OR")
	private void or(Instruction ins){
		register[0] |= register[ins.operands[0]];
	}
	
	@InstructionImpl("NOT")
	private void not(Instruction ins){
		//register[0] = (short)~register[0];
		register[0] = (short)(register[0] ^ 0xffff);
	}
	
	@InstructionImpl("BZ")
	private void branchZero(Instruction ins){
		if(register[0] == 0){
			IP = register[ins.operands[0]];
		}
	}
	
	@InstructionImpl("BNZ")
	private void branchNotZero(Instruction ins){
		if(register[0] != 0){
			IP = register[ins.operands[0]];
		}
	}

	@InstructionImpl("BC")
	private void branchCarry(Instruction ins){
		if(carry == 1){
			IP = register[ins.operands[0]];
		}
	}

	@InstructionImpl("B")
	private void branch(Instruction ins){
		IP = register[ins.operands[0]];
	}

	@InstructionImpl("BZD")
	private void branchZeroDirect(Instruction ins){
		if(register[0] == 0){
			IP = ins.operands[0];
		}
	}	
	
	@InstructionImpl("BNZD")
	private void branchNotZeroDirect(Instruction ins){
		if(register[0] != 0){
			IP = ins.operands[0];
		}
	}

	@InstructionImpl("BCD")
	private void branchCarryDirect(Instruction ins){
		if(carry == 1){
			IP = ins.operands[0];		}
	}

	@InstructionImpl("BD")
	private void branchDirect(Instruction ins){
		IP = ins.operands[0];
	}
	
	@InstructionImpl("END")
	private void end(Instruction ins) {
		isHalted = true;
	}

}
