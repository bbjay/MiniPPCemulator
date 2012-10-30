package ch.zhaw.inf3.emulator.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ch.zhaw.inf3.emulator.Instruction;
import ch.zhaw.inf3.emulator.InstructionSetArchitecture;

@RunWith(Parameterized.class)
public class TestISA {
	private InstructionSetArchitecture isa;
	private Instruction testInstruction;
	private String testMnemonic;

	public TestISA(Instruction ins) {
		testInstruction = ins;
		testMnemonic = ins.mnemonic;
	}

	@Before
	public void setUp() {
		isa = new InstructionSetArchitecture();
	}

	@Parameterized.Parameters
	public static Collection<?> instructionsUnderTest() {
		InstructionSetArchitecture isa = new InstructionSetArchitecture();
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		for (Instruction instruction : isa.instructions) {
			list.add(new Object[] { instruction });
		}
		return list;
	}

	@Test
	public void testEncodeDecode() {
		short[] ops = { 1, 500 };
		short word = testInstruction.encodeOperandValues(ops);
		Instruction decodedIns = isa.decodeWord(word);
		assertEquals(testMnemonic, decodedIns.mnemonic);
	}

}
