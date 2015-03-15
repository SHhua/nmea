package com.nmea.sentence;

import java.util.Date;

/*
 * 基于Nmea协议的语句最基类
 */
public interface INmeaSentence {
	
	//语句类型
	String sentenceType();
	
	//接收时间
	Date getReceiveDate();
}
