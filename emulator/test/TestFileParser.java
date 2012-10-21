package ch.zhaw.inf3.emulator.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.zhaw.inf3.emulator.FileParser;

public class TestFileParser {

	/*	0100000111110110	LWDD R0 #502
		0100010111110100	LWDD R1	#500
		0000011110000000	ADD R1
		0110000111111010	SWDD R0 #506
		0000000000000000	END
	*/
	@Test
	public void testProgramInput() {
		FileParser fp = new FileParser();
		List<String> list = null;		
		
		try {
			list = fp.load(new File("src/ch/zhaw/inf3/emulator/test/resources/program1.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String s : list){
			System.out.println(s);
		}
			
		assertEquals("LWDD R0 #502", list.get(0));	
		assertEquals("LWDD R1 #500", list.get(1));
		assertEquals("ADD R1", list.get(2));
		assertEquals("SWDD R0 #506", list.get(3));
		assertEquals("END", list.get(4));
	
	}
}

