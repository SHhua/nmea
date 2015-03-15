package com.nmea.codec;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import com.nmea.core.ObserverPrintMsg;
import com.nmea.obj.AbstractNmeaObject;
import com.nmea.obj.VdmNmeaObject;
import com.nmea.util.Pair;
import com.nmea.util.SentenceStore;



public class VdmNmeaCodec extends AbstractNmeaSentenceCodec {

	static public final long CHECK_INTERVAL = 500;
	static public final long INIT_DELAY = 200;
		
	private Timer checkTimer;
	
	public VdmNmeaCodec() {
		//增加观察者
		this.addObserver(new ObserverPrintMsg());
		
		//第二次作业新增
		this.checkTimer = new Timer(true);
		this.checkTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
					check();
			}
			
		}, INIT_DELAY, CHECK_INTERVAL);
	}
	
	//第二次作业新增
	private SentenceStore<Pair<Integer, String>, VdmNmeaObject> storedSentences =
			new SentenceStore<Pair<Integer, String>, VdmNmeaObject>();
	
	protected void check() {
		
		Date now = Calendar.getInstance().getTime();
		
		List<VdmNmeaObject> expiredSentences = this.storedSentences.getExpiredItems(now, 
				(int)CHECK_INTERVAL);
		
		for (VdmNmeaObject sentence : expiredSentences) {
			setChanged();
			notifyObservers(sentence);
			//sentence.decodeStringData(sentence.getEncapsulatedData());
			//this.decodeMessageFields(sentence);
		}
	}
	
	
	
	@Override
	//!AIVDM,1,1,,B,16:>>s5Oh08dLO8AsMAVqptj0@>p,0*67<CR><LF>   
	public void decode(String content) throws Exception {
		VdmNmeaObject nmeaObject = new VdmNmeaObject();
		if(!AbstractNmeaObject.VDM_PROTOL.equals(getContentType(content))){
			throw new Exception("不是VDM语句");
		}
		
		String msgChecksum = getStringChecksum(getChecksum(content));
		System.out.println(msgChecksum);
		
		//求出数据字符串长度
		int len = content.length();
		while (len > 0 && (content.charAt(len - 1) == '\r' || content.charAt(len - 1) == '\n')) {
			len--;
        }
		
		String checksum = null;
		//!AIVDM,1,1,,B,16:>>s5Oh08dLO8AsMAVqptj0@>p,0
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
		nmeaObject.setMsgId(fileds.get(0));//!AIVDM
		
		VdmNmeaObject vno = (VdmNmeaObject)nmeaObject;
		//语句总数
		String total = fileds.get(1);
		if(!StringUtils.isEmpty(total)){
			vno.setTotal(Integer.parseInt(total));
		}
		
		//语句编号
		String num = fileds.get(2);
		if(!StringUtils.isEmpty(num)){
			vno.setNum(Integer.parseInt(num));
		}
		
		//语句的序列号
		String sequence = fileds.get(3);
		if(!StringUtils.isEmpty(sequence)){
			vno.setSequence(Integer.parseInt(sequence));
		}
		
		//语句的发送信道
		String channel = fileds.get(4);
		if(!StringUtils.isEmpty(channel)){
			vno.setChannel(channel.charAt(0));
		}
		
		//语句的填充位数
		String fillNum = fileds.get(6);
		if(!StringUtils.isEmpty(fillNum)){
			vno.setFillNum(Integer.parseInt(fillNum));
		}
		
		//语句的内容，解析成比特字符串
		String msg = fileds.get(5);
		StringBuffer str = new StringBuffer();
		if(!StringUtils.isEmpty(msg)){
			for (int i = 0; i < msg.length(); i++) {
				char c = msg.charAt(i);
				int res = c + 40;
				if(res > 128){
					res = res + 32;
				}else{
					res = res + 40;
				}
				String binStr = Integer.toBinaryString(res);
				str.append(StringUtils.substring(binStr, binStr.length() - 6));
			}
		}
		vno.setByteStr(StringUtils.substring(str.toString(),vno.getFillNum(),str.length()));
		
		
		//第二次作业新增
		if (vno.getTotalNumberOfSentences() == 1) {
			setChanged();
			notifyObservers(vno);
		} else {
			Pair<Integer, String> key = new Pair<Integer, String>(vno.getSequentialMessageId(),
					vno.getMsgId());
			VdmNmeaObject sent = this.storedSentences.addItem(key, vno);
			if (sent != null ) {
				setChanged();
				notifyObservers(sent);
			}
		}
		
	}

	@Override
	public List<String> encode(AbstractNmeaObject obj) {
		
		return null;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		VdmNmeaCodec anc = new VdmNmeaCodec();
		String content = "!AIVDM,1,1,,B,16:>>s5Oh08dLO8AsMAVqptj0@>p,0*67";
		anc.decode(content);
	}

}
