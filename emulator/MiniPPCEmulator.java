package ch.zhaw.inf3.emulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MiniPPCEmulator {
	private MiniPPC_CPU cpu;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MiniPPCEmulator emu = new MiniPPCEmulator();
		
		emu.loadAssemblerCodeFromFile("src/ch/zhaw/inf3/emulator/test/resources/program1.txt");
		emu.loadParameters(new short[]{ 1, 2, 3 });
		emu.runSlow();

	}
	
	public MiniPPCEmulator(){
		cpu = new MiniPPC_CPU(512);
	}
	
	public void runFast(){
		while (!cpu.isHalted) {
			cpu.runCycle();
		}
		cpu.printRegisters();
		cpu.printRam();
		cpu.reset();
	}
	
	public void runSlow(){
		while (!cpu.isHalted) {
			cpu.runCycle();
			cpu.printRegisters();
			cpu.printRam();
		}
		cpu.reset();
	}
	
	public void runSteped(){
		Scanner input = new Scanner(System.in);
		do {
			cpu.runCycle();
			cpu.printRegisters();
			cpu.printRam();
		} while (!cpu.isHalted && !input.nextLine().equals("q"));
		
		input.close();
		cpu.reset();
	}
	
	public void loadMachineCodeFromFile(){};
	
	public void loadAssemblerCodeFromFile(String fileName){
		FileParser fp = new FileParser();
		AssemblerCompiler asmc = new AssemblerCompiler2();
		try {
			cpu.loadCode(asmc.compile(fp.load(new File(fileName))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	public void loadParameters(short[] params){
		cpu.loadDataAtOffset(params, 500);
	}
	
	public void compileAssembler(){};
	
	public void disassemble(){};

}
