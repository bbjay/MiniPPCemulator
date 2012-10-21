package ch.zhaw.inf3.emulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MiniPPCEmulator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MiniPPC_CPU cpu = new MiniPPC_CPU(512);
		
		FileParser fp = new FileParser();
		Translator tr = new Translator();
		
		
		// load program code
		//short[] code = {0x41f4,0x4003,0x4000,0x380} ;
		short[] code;
		try {
			cpu.loadCode(tr.decodingAssemblerOrder(fp.load(new File("src/ch/zhaw/inf3/emulator/test/resources/program1.txt"))));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//cpu.loadCode(code);
		
		// run
		cpu.runCycle();
		
		short[] op_args = {2, 500};
		short out = new InstructionSetArchitecture().getByMnemonic("LWDD").encodeOperandValues(op_args);
		System.out.println(Integer.toBinaryString(out));
	}
	
	public void run(int mode){};
	
	public void loadMachineCodeFromFile(){};
	
	public void loadAssemblerCodeFromFile(){};
	
	public void compileAssembler(){};
	
	public void disassemble(){};

}
