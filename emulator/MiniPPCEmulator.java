package ch.zhaw.inf3.emulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MiniPPCEmulator {
	MiniPPC_CPU cpu;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MiniPPCEmulator emu = new MiniPPCEmulator();
		
		emu.loadAssemblerCodeFromFile("src/ch/zhaw/inf3/emulator/test/resources/program1.txt");
		emu.run(0);

	}
	
	public MiniPPCEmulator(){
		cpu = new MiniPPC_CPU(512);
	}
	
	public void run(int mode){
		cpu.runCycle();
		cpu.printRegisters();

		cpu.runCycle();
		cpu.printRegisters();
	};
	
	public void loadMachineCodeFromFile(){};
	
	public void loadAssemblerCodeFromFile(String fileName){
		FileParser fp = new FileParser();
		AssemblerCompiler asmc = new AssemblerCompiler2();
		try {
			cpu.loadCode(asmc.decodingAssemblerOrder(fp.load(new File(fileName))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	public void compileAssembler(){};
	
	public void disassemble(){};

}
