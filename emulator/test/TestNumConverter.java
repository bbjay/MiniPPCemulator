package ch.zhaw.inf3.emulator.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.zhaw.inf3.emulator.NumConverter;

public class TestNumConverter {

	@Test
	public void test() {
		assertEquals(17908, NumConverter.binStringToDec("0100010111110100")); //LWDD R1	#500
	}
	@Test
	public void test2() {
		System.out.println(NumConverter.decToBinString((short) 17908));
		
		assertEquals("0100010111110100", NumConverter.decToBinString((short) 17908));
	}
	
}
