package com.xmpptask.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import com.xmpptask.commands.*;
import com.xmpptask.models.Tag;
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
	
	//automate some of the checking
	private void checkForProperCommands(CompositeCommand cmd, Class... classes ){
		assertNotNull(cmd);
		assertEquals(cmd.getCount(), classes.length);
		for(int i = 0; i < classes.length; i++){
			assertTrue(cmd.getCommands().get(i).getClass().equals(classes[i]));
		}
	}
	
	@Ignore
	@Test
	public void testObjectConstruction(){
		
	}
	
	@Test
	public void testEmpty() throws ParseException{
		parser.setParseString("");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
		checkForProperCommands(cmd, ListCommand.class);
	}
	
	@Test
	public void testList() throws ParseException{
		parser.setParseString("list");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
		checkForProperCommands(cmd, ListCommand.class);
		
		parser.setParseString("l");
		cmd = (CompositeCommand)parser.parse();
		checkForProperCommands(cmd, ListCommand.class);
	}
	
	//test another command that starts with a
	@Test(expected=ParseException.class)
	public void testUnknownCommand() throws ParseException{
		parser.setParseString("asp");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
	}
	
	@Test
	public void testHelpCommand() throws ParseException{
		parser.setParseString("help");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
		checkForProperCommands(cmd, HelpCommand.class);
	}
	
	@Test
	public void addCommand() throws ParseException{
		parser.setParseString("+ new task #tag1 #tag2");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
		checkForProperCommands(cmd, AddTask.class);
		
		AddTask ct = (AddTask) cmd.getCommands().get(0);
		assertEquals(ct.getTask().getTaskText(), "new task #tag1 #tag2");
		assertEquals(ct.getTask().getTags().size(), 2);
		
		Set<String> tset = new HashSet<String>();
		tset.add("tag1");
		tset.add("tag2");
		assertEquals(ct.getTask().getTags(), tset);
		
	}
	
	@Test
	public void addChildCommand() throws ParseException{
		parser.setParseString("@2.3 new task");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
		checkForProperCommands(cmd, AddChildTask.class);
		
		AddChildTask ct =  (AddChildTask) cmd.getCommands().get(0);
		assertEquals(ct.getId().toString(), "2.3");
		assertEquals(ct.getTask().getTaskText(), "new task");
	}
	
	@Test
	public void deleteCommand() throws ParseException{
		parser.setParseString("- @1");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
		checkForProperCommands(cmd, IdDelete.class, TagDelete.class);
		
		IdDelete dcmd = (IdDelete)cmd.getCommands().get(0);
		TagDelete tcmd = (TagDelete)cmd.getCommands().get(1);
		assertEquals(dcmd.getIds().size(), 1);
		assertEquals(dcmd.getIds().get(0).toString(), "1");
		assertEquals(tcmd.getTags().size(), 0);
	
		parser.setParseString("- @1 @3 @1.2.3.4.5 #asfd #oiu");
		cmd = (CompositeCommand)parser.parse();
		checkForProperCommands(cmd, IdDelete.class, TagDelete.class);
		dcmd = (IdDelete)cmd.getCommands().get(0);
		tcmd = (TagDelete)cmd.getCommands().get(1);
		assertEquals(dcmd.getIds().size(), 3);
		assertEquals(dcmd.getIds().get(0).toString(), "1");
		assertEquals(dcmd.getIds().get(1).toString(), "3");
		assertEquals(dcmd.getIds().get(2).toString(), "1.2.3.4.5");
		assertEquals(tcmd.getTags().size(), 2);
		assertEquals(tcmd.getTags().get(0).toString(), "asfd");
		assertEquals(tcmd.getTags().get(1).toString(), "oiu");
	}
	
	//parser doesn't yet validate ids are in proper format
	@Ignore
	@Test(expected=ParseException.class)
	public void testIdFormat() throws ParseException{
		parser.setParseString("@a.b.c.d");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
	}
	
	@Test(expected=ParseException.class)
	public void testImproperMultiplesFormat() throws ParseException{
		parser.setParseString("- 3");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
	}
	@Test(expected=ParseException.class)
	public void testLargeParseString() throws ParseException{
		parser.setParseString("help me");
		CompositeCommand cmd = (CompositeCommand)parser.parse();
	}
	
}
