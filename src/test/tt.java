package test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.opencv.core.Core;

import utils.Buzhuo;

public class tt {
	
public static void main(String[] args) throws AWTException, IOException {
	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

	Robot r = new Robot();
	r.delay(3000);
//	r.mouseMove(268, 498);
//	r.mouseMove(206, 248);
	r.keyPress(157);//按下mac的commanded键
//	r.delay(50);
	r.keyPress(KeyEvent.VK_E);
	r.keyRelease(KeyEvent.VK_E);
	r.keyRelease(157);
	
//	r.delay(3000);
//	Buzhuo.modifyMouse(206, 248);
//	r.delay(100);
//	r.mousePress(InputEvent.BUTTON1_MASK);
//	r.mouseRelease(InputEvent.BUTTON1_MASK);

//	r.delay(787);
//	r.mouseMove(164, 219);
//	
//	r.delay(100);
//	r.mousePress(InputEvent.BUTTON1_MASK);
//	r.mouseRelease(InputEvent.BUTTON1_MASK);

//	r.delay(787);
//	r.mouseMove(210, 197);
//	
//	r.delay(100);
//	r.mousePress(InputEvent.BUTTON1_MASK);
//	r.mouseRelease(InputEvent.BUTTON1_MASK);

//	r.delay(787);
//	r.mouseMove(210, 197);
//	
//	r.delay(100);
//	r.mousePress(InputEvent.BUTTON1_MASK);
//	r.mouseRelease(InputEvent.BUTTON1_MASK);

}

}
