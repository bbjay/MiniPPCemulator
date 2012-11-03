package ch.zhaw.inf3.emulator;

import java.util.HashMap;
import java.util.List;

public class AssemblerCompiler2 extends AssemblerCompiler {
	InstructionSetArchitecture isa;

	public AssemblerCompiler2() {
		isa = new InstructionSetArchitecture();
	}

	public short[] compile(List<String> list) {
		short[] code = new short[list.size()];
		preprocess(list);

		for (int i = 0; i < list.size(); i++) {
			String[] subString = list.get(i).split(" ");
			Instruction instruction = isa.getByMnemonic(subString[0]);
			
			short[] operands = new short[2];
			if (subString.length > 1 ) {
				operands[0] = parseInt(subString[1]);
			}
			if (subString.length > 2 ) {
				operands[1] = parseInt(subString[2]);
			}
			//System.out.println(operands[0] +" "+ operands[1]);
			
			if(instruction != null) {
				code[i] = instruction.encodeOperandValues(operands);
			}
			else{ // also allow raw numeric values
				code[i] = (short)Integer.parseInt(list.get(i));
			}
		}
		return code;
	}
	
	public short parseInt(String op){
		String num = op.replaceAll("[R#,]", "");
		return (short)Integer.parseInt(num);
	}
	
	/**
	 * @param list
	 * @return list
	 * Preprocesses the code to handle it over to the compiler.
	 * Resolves labels to absolute addresses
	 */
	private List<String> preprocess(List<String> list){
		HashMap<String,Integer> labelLookUpTable = new HashMap<String, Integer>();
		// search for label declarations, store its address in the LUT and remove it from our code
		for (int i = 0; i < list.size(); i++) {
			String line = list.get(i);
			if (line.endsWith(":")) {
				String label = list.get(i).replaceFirst(":", "");
				labelLookUpTable.put(label, i);
				list.remove(i);
			}
		}
		// search for label usage and replace it with corresponding address
		for (String line : list) {
			String[] part = line.split(" ");
			boolean found = false;
			for (int i = 1; i < part.length; i++) {
				Integer relative_addr = labelLookUpTable.get(part[i]);
				if(relative_addr != null){
					part[i] = "#"+ Integer.valueOf(relative_addr + isa.start_offset).toString(); // replace label with resolved address
					found = true;
				}
			}
			if (found) {
				String comp = "";
				for(String s: part) comp += s +" " ;
				int index = list.indexOf(line);
				list.set(index, comp); // replace line
			}
		}
		return list;
	}
}
