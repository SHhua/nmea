package com.nmea.sentence;

import java.util.List;

/*
 * 封装语句，继承分组多语句
 */
public interface EncapsulationItem extends IGroupedSentence {

	boolean addEncapsulatedData(String content, int fillBits);
	
	void cleanup();
	
	List<Byte> getRawData();
	
	void setEncapsulatedData(String content);
	
	String getEncapsulatedData();
	
	int getFillBits();
	
	void setFillBits(int fillBits);
	
	void increaseSentenceNumber();
	
	void setTotalSentenceCount(int total);
	
	void setSentenceNumber(int number);
	
	void setSequenceId(Integer sequence);
	
	IEncapsulatedSentence getEncapsulatedSentence();
	
	void setEncapsulatedSentence(IEncapsulatedSentence sentence);
}
