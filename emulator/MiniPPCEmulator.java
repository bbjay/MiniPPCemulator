package ch.zhaw.inf3.emulator;

public class MiniPPCEmulator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MiniPPC_CPU cpu = new MiniPPC_CPU(512);

		cpu.loadDataAtOffset(new short[]{1337,42}, 500);

		short[] op_args = {2, 501};
		short out = new InstructionSetArchitecture().getByMnemonic("LWDD").encodeOperandValues(op_args);
		System.out.println(Integer.toBinaryString(out));
		
		// load program code
		short[] code = {0x41f4,out} ;
		cpu.loadCode(code);
		
		// run
		cpu.runCycle();
		cpu.printRegisters();

		cpu.runCycle();
		cpu.printRegisters();
	}
	
	public void run(int mode){};
	
	public void loadMachineCodeFromFile(){};
	
	public void loadAssemblerCodeFromFile(){};
	
	public void compileAssembler(){};
	
	public void disassemble(){};

}
