package com.nmea.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nmea.codec.AbstractNmeaSentenceCodec;
import com.nmea.codec.GgaNmeaCodec;
import com.nmea.codec.GllNmeaCodec;
import com.nmea.codec.RmcNmeaCodec;
import com.nmea.codec.VdmNmeaCodec;
import com.nmea.obj.AbstractNmeaObject;


public class CodecManager {
	private Map<String,AbstractNmeaSentenceCodec> nmeaCodecs = new HashMap<String,AbstractNmeaSentenceCodec>();
	
	private static CodecManager codecManager;
	private CodecManager() {
		this.nmeaCodecs.put(AbstractNmeaObject.GGA_PROTOL, new GgaNmeaCodec());
		this.nmeaCodecs.put(AbstractNmeaObject.GLL_PROTOL, new GllNmeaCodec());
		this.nmeaCodecs.put(AbstractNmeaObject.RMC_PROTOL, new RmcNmeaCodec());
		this.nmeaCodecs.put(AbstractNmeaObject.VDM_PROTOL, new VdmNmeaCodec());
	}
	
	public static synchronized CodecManager getInstance(){
		if(codecManager == null){
			codecManager = new CodecManager();
		}
		return codecManager;
	}
	public Buffer buffer;

	public void decode(String content) throws Exception {
		String objType = AbstractNmeaSentenceCodec.getContentType(content);
		if (AbstractNmeaObject.GGA_PROTOL.equals(objType)) {
			this.getNmeaCodec(AbstractNmeaObject.GGA_PROTOL).decode(content);
		}
		if (AbstractNmeaObject.GLL_PROTOL.equals(objType)) {
			this.getNmeaCodec(AbstractNmeaObject.GLL_PROTOL).decode(content);
		}
		if (AbstractNmeaObject.RMC_PROTOL.equals(objType)) {
			this.getNmeaCodec(AbstractNmeaObject.RMC_PROTOL).decode(content);
		}
		if (AbstractNmeaObject.VDM_PROTOL.equals(objType)) {
			this.getNmeaCodec(AbstractNmeaObject.VDM_PROTOL).decode(content);
		}
	}

	public List<String> encode(AbstractNmeaObject obj) throws Exception {
		List<String> content = new ArrayList<String>();
		if (AbstractNmeaObject.GGA_PROTOL.equals(obj.getObjType())) {
			content = this.getNmeaCodec(AbstractNmeaObject.GGA_PROTOL).encode(obj);
		}
		if (AbstractNmeaObject.GLL_PROTOL.equals(obj.getObjType())) {
			content = this.getNmeaCodec(AbstractNmeaObject.GLL_PROTOL).encode(obj);
		}
		if (AbstractNmeaObject.RMC_PROTOL.equals(obj.getObjType())) {
			content =this.getNmeaCodec(AbstractNmeaObject.RMC_PROTOL).encode(obj);
		}
		if (AbstractNmeaObject.VDM_PROTOL.equals(obj.getObjType())) {
			content = this.getNmeaCodec(AbstractNmeaObject.VDM_PROTOL).encode(obj);
		}
		return content;
	}
	
	private AbstractNmeaSentenceCodec getNmeaCodec(String key){
		return nmeaCodecs.get(key);
	}
}
