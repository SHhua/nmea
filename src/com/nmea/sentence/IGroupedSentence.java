package com.nmea.sentence;

/**
 * 继承Nmea协议语句
 * 
 * 分组语句：该类语句会分多条语句传输，需要通过合并后解析，
 *          同时对过期的不完整数据进行清除
 *          
 * 包含了分组语句特有的属性
 */
public interface IGroupedSentence extends INmeaSentence {

	int getSequentialMessageId();
	
	int getTotalNumberOfSentences();
	
	int getSentenceNumber();
	
	boolean isComplete();
	
	void concatenate(IGroupedSentence sentence);
}
