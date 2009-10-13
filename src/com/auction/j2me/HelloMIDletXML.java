package com.auction.j2me;

import java.io.*;

import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.microedition.midlet.*;
import javax.microedition.io.*;
import javax.microedition.io.file.*;
import javax.microedition.lcdui.*;

/*
 * class based on example at forum nokia
 * http://wiki.forum.nokia.com/index.php/Parsing_XML_using_JSR_172
 * and example at java tips
 * http://www.java-tips.org/java-me-tips/midp/introducing-xml-parsing-in-j2me-devices-4.html 
 * 
 * This midlet only has an alert form, and no 'default' screen, so only shows you how to 
 * connect and retrieve the xml data which you can then use in your application.
 */
public class HelloMIDletXML extends MIDlet {

	private HttpConnection http;
	String URL = "http://www.csd.abdn.ac.uk/~bscharla/helloworld.xml";
	String result = "";

	public HelloMIDletXML() {
	}

	protected void startApp() {
		try {
			System.out.println("midlet parse");

			// start new thread to handle connection and not keep
			// device hanging
			Thread thread = new Thread() {
				public void run() {
					// invoke parser();
					loadXML(getInputStream());
					alert(result);
				}
			};
			thread.start();			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String loadXML(InputStream input) {

		SAXParser parser;
		try {
			//create the parser
			SAXParserFactory factory = SAXParserFactory.newInstance();
			parser = factory.newSAXParser();

			//set the input source and content handler
			InputSource fis = new InputSource(input);
			XmlHandler handler = new XmlHandler();
			//parse the file
			parser.parse(fis, handler);
			result = handler.parse();

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		return result;
	}

	private InputStream getInputStream() {
		InputStream input = null;
		try {
			http = (HttpConnection) Connector.open(URL);

			// Getting the response code will open the connection,
			int rc = http.getResponseCode();
			System.out.println("http rc: " + rc);
			if (rc == HttpConnection.HTTP_OK) {
				String type = http.getType();
				System.out.println("http content type: " + type);
				if (type.startsWith("text/xml")
						|| type.startsWith("application/xml")) {

					input = http.openInputStream();
				}
			}
		} catch (IOException ex) {
		}
		return input;
	}

	protected void alert(String msg) {
		System.out.println("midlet alert");
		Display display = Display.getDisplay(this);
		Form form = new Form("HelloWorld XML !");
		form.append(msg);
		display.setCurrent(form);
	}

	protected void pauseApp() {
	}

	protected void destroyApp(boolean bool) {
	}
}
