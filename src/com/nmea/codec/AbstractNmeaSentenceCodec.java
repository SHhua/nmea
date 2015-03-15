package com.nmea.codec;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang.StringUtils;

import com.nmea.obj.AbstractNmeaObject;
import com.nmea.sentence.EncapsulationSentence;
import com.nmea.sentence.IEncapsulatedSentence;
import com.nmea.sentence.NmeaSentence;



public abstract class AbstractNmeaSentenceCodec extends Observable{
	
	protected  AbstractNmeaObject nmeaObject;
	protected List<Observer> observers = new ArrayList<Observer>();
	
	public abstract void decode(String content) throws Exception;
	
	public abstract List<String> encode(AbstractNmeaObject obj) throws Exception;
	
	public static int getChecksum(String content) throws Exception {
        int checksum = 0;
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '!' || c == '$') {
                continue;
            }
            if (c == '*') {
                break;
            }
            checksum ^= c;
        }
        return checksum;
    }
	
	public static String getStringChecksum(int checksum) {
        String strChecksum = Integer.toString(checksum, 16).toUpperCase();
        if (strChecksum.length() < 2) {
            strChecksum = "0" + strChecksum;
        }
        return strChecksum;
    }
	
	public static String getContentType(String content){
		return StringUtils.substring(content,3,6);
	}

	public static List<Field> getSentenceFields(NmeaSentence nmeaSentence) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Field> getMessageFields(NmeaSentence nmeaSentence) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Field> getMessageFields(
			IEncapsulatedSentence encapsulated) {
		// TODO Auto-generated method stub
		return null;
	}

	public void decode(EncapsulationSentence sentence) throws Exception{
		
	};
}
