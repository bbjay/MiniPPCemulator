package ch.zhaw.inf3.emulator;

public class MiniPPCEmulator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MiniPPC_CPU cpu = new MiniPPC_CPU(512);
		
		// load program code
		short[] code = {0x01,0x00} ;
		cpu.loadCode(code);
		
		// run
		cpu.runCycle();
	}
	
	public void run(int mode){};
	
	public void loadMachineCodeFromFile(){};
	
	public void loadAssemblerCodeFromFile(){};
	
	public void compileAssembler(){};
	
	public void disassemble(){};

}
