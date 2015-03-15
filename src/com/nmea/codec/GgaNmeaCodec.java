package com.nmea.codec;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;

import com.nmea.core.ObserverPrintMsg;
import com.nmea.obj.AbstractNmeaObject;
import com.nmea.obj.GgaNmeaObject;


public class GgaNmeaCodec extends AbstractNmeaSentenceCodec{

	public GgaNmeaCodec() {
		//增加观察者
		this.addObserver(new ObserverPrintMsg());
	}
	@Override
	//$GPGGA,<1>,<2>,<3>,<4>,<5>,<6>,<7>,<8>,<9>,<10>,<11>,<12>,<13>,<14>*hh<CR><LF>  
	public void decode(String content) throws Exception {
		nmeaObject = new GgaNmeaObject();
		if(!AbstractNmeaObject.GGA_PROTOL.equals(getContentType(content))){
			throw new Exception("不是GGA语句");
		}
		
		String msgChecksum = getStringChecksum(getChecksum(content));
		System.out.println(msgChecksum);
		
		//求出数据字符串长度
		int len = content.length();
		while (len > 0 && (content.charAt(len - 1) == '\r' || content.charAt(len - 1) == '\n')) {
			len--;
        }
		
		String checksum = null;
		//$GPGGA,161229.487,3723.2475,N,12158.3416,W,1,07,1.0,9.0,M, , ,,0000
		List<String> fileds = new ArrayList<String>();
		int pos = 0;
		for (int i = 0; i < len; i++) {
			char ch = content.charAt(i);
			
			if(ch ==','){
				fileds.add(StringUtils.substring(content, pos, i));
				pos = i + 1;
			}
			if(ch =='*'){
				fileds.add(StringUtils.substring(content, pos, i));
				pos = i + 1;
				checksum = StringUtils.substring(content, pos, len);
				break;
			}
		}
		
		if(!StringUtils.isEmpty(checksum)&&!checksum.equals(msgChecksum)){
			throw new Exception("数据校验和有误");
		}
		nmeaObject.setMsgChecksum(msgChecksum);
		nmeaObject.setMsgFields(fileds);
		nmeaObject.setMsgId(fileds.get(0));//$GPGGA
		
		setChanged();
		notifyObservers(nmeaObject);
		
		encode(nmeaObject);
	}

	@Override
	public List<String> encode(AbstractNmeaObject obj) {
		List<String> msg = new ArrayList<String>();
		List<String> fields = obj.getMsgFields();
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < fields.size(); i++) {
			content.append(fields.get(i));
			if(i == fields.size()-1){
				break;
			}
			content.append(',');
		}
		content.append('*');
		content.append(obj.getMsgChecksum());
		content.append("\r");
		content.append("\n");
		msg.add(content.toString());
		return msg;
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		GgaNmeaCodec anc = new GgaNmeaCodec();
		String content = "$GPGGA,161229.487,3723.2475,N,12158.3416,W,1,07,1.0,9.0,M, , ,,0000*18";
		anc.decode(content);
	}
}
