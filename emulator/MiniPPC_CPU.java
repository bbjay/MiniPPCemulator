package ch.zhaw.inf3.emulator;

public class MiniPPC_CPU {
	private int cycle_count = 0;
	private short register[] = new short[4];
	private short RAM[];
	private short IP; // Next Instruction Pointer
	private short IR; // Instruction Register
	private boolean carry;
	private InstructionSetArchitecture isa;
	
	public enum Reg{
		AKKU,
		R1,
		R2,
		R3
	}

	public MiniPPC_CPU(int memory){
		this.RAM = new short[memory];
		this.isa = new InstructionSetArchitecture();
		this.IP = isa.start_offset;
	}
	
	public void runCycle(){
		// Instruction Fetch
		IR = RAM[IP];
		
		// Instruction Decode
		Instruction op = isa.decodeWord(IR);
		
		// Increment IP
		IP = (short) (IP + isa.word_width);
		
		// Execute
		executeInstruction(op);
		
		cycle_count = cycle_count + 1;
	}

	public void loadCode(short[] words){
		for (int i = 0; i < words.length; i++) {
			RAM[100+i] = words[i];
		}
	}

	private void executeInstruction(Instruction op) {
		// TODO Auto-generated method stub
		
	}
	
	
	// Instruction Implementations
	
	private void add(short Rnr){
		register[Reg.AKKU.ordinal()] += register[Rnr];
		// check for overflow, set carry
	}
	
	private void end(){}
	
	private void shiftLeftLogical(){}
	
	private void loadWordDirect(){}
	
	private void storeWordDirect(){}
}
