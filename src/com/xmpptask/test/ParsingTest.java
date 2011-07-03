package com.xmpptask.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.xmpptask.commands.Command;
import com.xmpptask.commands.CompositeCommand;
import com.xmpptask.parser.ParseException;
import com.xmpptask.parser.XMPPParser;

public class ParsingTest {

	private XMPPParser parser;
	
	@Before
	public void setupParser(){
		parser = new XMPPParser();
	}
	
	@After
	public void clearState(){
		
	}
	
	@Test
	public void testEmpty() throws ParseException{
		parser.setTask("");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
		assertEquals(cmd.getCount(), 1);
	}
	
	@Test
	public void testList() throws ParseException{
		parser.setTask("list");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
		assertEquals(cmd.getCount(), 1);
	}
	
	@Test(expected=ParseException.class)
	public void testUnknownCommand() throws ParseException{
		parser.setTask("asfd");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
	}
	
}
