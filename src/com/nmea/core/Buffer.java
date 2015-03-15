package com.nmea.core;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
	private List<String> contents;

	public Buffer() {
		contents = new ArrayList<String>();
	}

	public List<String> appendContent(String content) {
		this.contents.add(content);
		return contents;
	}

	/**
	 * @return the contents
	 */
	public List<String> getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(List<String> contents) {
		this.contents = contents;
	}
	
	
}
