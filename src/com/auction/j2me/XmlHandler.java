package com.auction.j2me;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.util.*;
/*
 * class based on example at java tips
 * http://www.java-tips.org/java-me-tips/midp/introducing-xml-parsing-in-j2me-devices-4.html 
 * 
 */
class XmlHandler extends DefaultHandler {
	private HelloMIDletXML midlet;
	private StringBuffer sb = new StringBuffer(1024);
	private Vector nodes = new Vector();
	private Stack tagStack = new Stack();

	public XmlHandler() {
		System.out.println("midlet handler");
	}

	public void startDocument() throws SAXException {
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("phone")) {
			System.out.println("qname is: phone");
			Noden node = new Noden();
			sb.append("item is a " + qName + " ");
			nodes.addElement(node);
		}
		tagStack.push(qName);
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String chars = new String(ch, start, length).trim();

		if (chars.length() > 0) {
			String qName = (String) tagStack.peek();
			Noden currentnode = (Noden) nodes.lastElement();

			if (qName.equals("name")) {
				currentnode.setName(chars);
				System.out.println("currentnode name: " + chars);
			
			} else if (qName.equals("type")) {
				currentnode.setType(chars);
				System.out.println("currentnode type: " + chars);
			}
		}
	}

	public void endElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		tagStack.pop();
	}

	public void endDocument() throws SAXException {
	//	StringBuffer result = new StringBuffer();
		for (int i = 0; i < nodes.size(); i++) {
			Noden currentnode = (Noden) nodes.elementAt(i);
			sb.append("Name : " + currentnode.getName() + " Type : "
					+ currentnode.getType() + "\n");
		}
		System.out.println(sb.toString());
	}
	
	//	midlet.alert(result.toString());

		
		public String parse(){
			return sb.toString();
		}
	

	class Noden {
		private String name;
		private String type;

		public Noden() {
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public String getType() {
			return type;
		}
	};
}
