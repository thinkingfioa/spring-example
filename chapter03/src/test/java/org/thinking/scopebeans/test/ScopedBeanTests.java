package org.thinking.scopebeans.test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thinking.scopebeans.Notepad;
import org.thinking.scopebeans.UniqueThing;

/** 
 * @author thinkingfioa
 * @Email thinking_fioa@163.com
 * @version May 21, 2017 11:50:30 AM
 */
public class ScopedBeanTests {

	  @RunWith(SpringJUnit4ClassRunner.class)
	  @ContextConfiguration(classes=ComponentScannedConfig.class)
	  public static class ComponentScannedScopedBeanTest {
	    
	    @Autowired
	    private ApplicationContext context;
	    
	    @Test
	    public void testProxyScope() {
	      Notepad notepad1 = context.getBean(Notepad.class);
	      Notepad notepad2 = context.getBean(Notepad.class);
	      assertNotSame(notepad1, notepad2);
	    }
	    
	    @Test
	    public void testSingletonScope() {
	      UniqueThing thing1 = context.getBean(UniqueThing.class);
	      UniqueThing thing2 = context.getBean(UniqueThing.class);
	      assertSame(thing1, thing2);
	    }
	    
	  }
	  
	  @RunWith(SpringJUnit4ClassRunner.class)
	  @ContextConfiguration(classes=ExplicitConfig.class)
	  public static class ExplicitConfigScopedBeanTest {
	    
	    @Autowired
	    private ApplicationContext context;
	    
	    @Test
	    public void testProxyScope() {
	      Notepad notepad1 = context.getBean(Notepad.class);
	      Notepad notepad2 = context.getBean(Notepad.class);
	      assertNotSame(notepad1, notepad2);
	    }
	    
	    @Test
	    public void testSingletonScope() {
	      UniqueThing thing1 = context.getBean(UniqueThing.class);
	      UniqueThing thing2 = context.getBean(UniqueThing.class);
	      assertSame(thing1, thing2);
	    }
	    
	  }

	  @RunWith(SpringJUnit4ClassRunner.class)
	  @ContextConfiguration("classpath:scoped-beans.xml")
	  public static class XMLConfigScopedBeanTest {
	    
	    @Autowired
	    private ApplicationContext context;
	    
	    @Test
	    public void testProxyScope() {
	      Notepad notepad1 = context.getBean(Notepad.class);
	      Notepad notepad2 = context.getBean(Notepad.class);
	      assertNotSame(notepad1, notepad2);
	    }
	    
	    @Test
	    public void testSingletonScope() {
	      UniqueThing thing1 = context.getBean(UniqueThing.class);
	      UniqueThing thing2 = context.getBean(UniqueThing.class);
	      assertSame(thing1, thing2);
	    }
	    
	  }


	}