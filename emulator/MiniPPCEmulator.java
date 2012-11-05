package ch.zhaw.inf3.emulator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MiniPPCEmulator {
	private MiniPPC_CPU cpu;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MiniPPCEmulator emu = new MiniPPCEmulator();
		
		emu.loadAssemblerCodeFromFile("src/ch/zhaw/inf3/emulator/booth.asm");
		//emu.loadMachineCodeFromFile("src/ch/zhaw/inf3/emulator/out.bin");
		emu.loadParameters(new short[]{ -15, -32768 });
		//emu.runFast();
		emu.runSlow(33);
		//emu.runSteped();
		
		//emu.dumpCodeToFile("src/ch/zhaw/inf3/emulator/out.bin");
	}
	
	public MiniPPCEmulator(){
		cpu = new MiniPPC_CPU(512);
	}
	
	public void runFast(){
		while (!cpu.isHalted) {
			cpu.runCycle();
		}
		StringBuilder buffer = new StringBuilder();
		cpu.printRegisters(buffer);
		cpu.printRam(buffer);
		System.out.println(buffer);;
		cpu.reset();
	}
	
	public void runSlow(long delay){
		while (!cpu.isHalted) {
			StringBuilder buffer = new StringBuilder();
			buffer.append("---------------------------------\n");
			cpu.runCycle();
			cpu.printRegisters(buffer);
			cpu.printRam(buffer);
			System.out.println(buffer);
			try { Thread.sleep(delay); } catch (InterruptedException e) { e.printStackTrace(); }
		}
		cpu.reset();
	}
	
	public void runSteped(){
		Scanner input = new Scanner(System.in);
		do {
			cpu.runCycle();
			StringBuilder buffer = new StringBuilder();
			buffer.append("---------------------------------\n");
			cpu.printRegisters(buffer);
			cpu.printRam(buffer);
			System.out.println(buffer);
		} while (!cpu.isHalted && !input.nextLine().equals("q"));
		
		input.close();
		cpu.reset();
	}
	
	public void loadMachineCodeFromFile(String fileName){
		DataInputStream in = null;
		File file;
		short[] code = null;
		try {
			file = new File(fileName);
			code = new short[(int)(file.length()/2)];
			in = new DataInputStream(new FileInputStream(file));
			for (int i = 0; i < code.length; i++) {
				code[i] = in.readShort();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		cpu.loadCode(code);
	};
	
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
	
	public void dumpCodeToFile(String fileName){
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream(fileName));
			short[] code = cpu.getCodeAt(100, 512);
			for (int i = 0; i < code.length; i++) out.writeShort(code[i]);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
}
