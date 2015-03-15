package com.nmea.obj;


public class GllNmeaObject extends AbstractNmeaObject{
	public GllNmeaObject(){
		this.objType = GLL_PROTOL;
		
	}
	
	@Override
	public String toString() {
		if(this.msgFields == null || this.msgFields.size()< 7){
			return "数据格式有误";
		}
		StringBuffer str = new StringBuffer();
		str.append("GLL消息:");
		
		str.append("定位点的UTC时间：");
		String time = this.msgFields.get(5);
		str.append(time.substring(0, 2)+"时");
		str.append(time.substring(2, 4)+"分");
		str.append(time.substring(4)+"秒");
		
		if("N".equals(this.msgFields.get(2))){
			str.append("，北纬");
		}
		if("S".equals(this.msgFields.get(2))){
			str.append("，南纬");
		}
		
		String latitude = this.msgFields.get(1);
		str.append(latitude.substring(0, 2)+"度");
		str.append(latitude.substring(2)+"分");
		
		if("W".equals(this.msgFields.get(4))){
			str.append("，西经");
		}
		if("E".equals(this.msgFields.get(4))){
			str.append("，东经");
		}
		
		String longitude = this.msgFields.get(3);
		str.append(longitude.substring(0, 3)+"度");
		str.append(longitude.substring(3)+"分");
		
		str.append(",数据状态:");
		if("A".equals(this.msgFields.get(6))){
			str.append("定位数据有效");
		}
		if("V".equals(this.msgFields.get(6))){
			str.append("定位数据无效");
		}
				
		return str.toString();
	}
}
