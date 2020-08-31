/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    Copyright (C) 2015 George Antony Papadakis (gpapadis@yahoo.gr)
 */

package DataStructures;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tokens.KeywordGenerator;
import tokens.KeywordGeneratorImpl;

/**
 *
 * @author gap2
 */

public class EntityProfile implements Serializable {

	private static final long serialVersionUID = 122354534453243447L;

	private final Set<Attribute> attributes;
	private final String entityUrl;
	private boolean isSource;
	private int key;
	
	private final String split1 = "<<>>";
	private final String split2 = "#=#";

//	public EntityProfile(String url) {
//		entityUrl = url;
//		attributes = new HashSet();
//	}

	/**
	 * Parse out of the format found in generated CSV files
	 * 
	 * @param csvLine
	 *            A comma separated key and values
	 * @throws IllegalArgumentException
	 */
	public EntityProfile(String csvLine, String separator) {
		String[] parts = csvLine.split(separator);
		key = Integer.valueOf(parts[0]);
		entityUrl = parts[1];
		attributes = new HashSet();
		for (int i = 1; i < parts.length; i++) {//the first element is the key (avoid!)
			attributes.add(new Attribute("", parts[i]));
		}
	}
	
	
	public EntityProfile(String standardFormat) {
		String[] parts = standardFormat.split(split1);
		isSource = Boolean.parseBoolean(parts[0]);
		entityUrl = parts[1];
		key = Integer.valueOf(parts[2]);
		attributes = new HashSet();
		for (int i = 3; i < parts.length; i++) {//the first element is the key (avoid!)
			String[] nameValue = parts[i].split(split2);
			if (nameValue.length == 1) {
				attributes.add(new Attribute(nameValue[0], ""));
			} else {
				attributes.add(new Attribute(nameValue[0], nameValue[1]));
			}
		}
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public void addAttribute(String propertyName, String propertyValue) {
		attributes.add(new Attribute(propertyName, propertyValue));
	}


	public String getEntityUrl() {
		return entityUrl;
	}

	public int getProfileSize() {
		return attributes.size();
	}

	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public String getStandardFormat() {
		String output = "";
		output += isSource + split1;
		output += entityUrl + split1;//separate the attributes
		output += key + split1;//separate the attributes
		
		for (Attribute attribute : attributes) {
			output += attribute.getName() + split2 + attribute.getValue() + split1;
		}
				
		return output;
	}


	public boolean isSource() {
		return isSource;
	}


	public void setSource(boolean isSource) {
		this.isSource = isSource;
	}
	
	public Set<String> generateTokens() {
		HashSet<String> tokens = new HashSet<String>();
		KeywordGenerator kw = new KeywordGeneratorImpl();
		Pattern p = Pattern.compile("[^\"a-zA-Z\\s0-9]");
		 
		
		for (Attribute attribute : attributes) {
			//To improve quality, use the following code
			Matcher m = p.matcher("");
			m.reset(attribute.getValue());
			String standardString = m.replaceAll("");
			
			tokens.addAll(kw.generateKeyWords(standardString));
		}
		
		return tokens;
		
	}
	
	@Override
	public String toString() {
		String attributeList = "";
		for (Attribute a : attributes) {
			attributeList += a.getValue() + ", ";
		}
		return entityUrl + ": " + (attributeList.isEmpty() ? "" : attributeList.substring(0, attributeList.length()-2));
	}

}