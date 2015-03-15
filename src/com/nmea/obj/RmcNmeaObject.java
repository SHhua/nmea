package com.nmea.obj;


public class RmcNmeaObject extends AbstractNmeaObject{
	public RmcNmeaObject (){
		this.objType = RMC_PROTOL;
	}
	
	@Override
	public String toString() {
		if(this.msgFields == null || this.msgFields.size()< 12){
			return "数据格式有误";
		}
		StringBuffer str = new StringBuffer();
		str.append("RMC消息:");
		
		str.append("定位点的UTC时间：");
		String time = this.msgFields.get(1);
		str.append(time.substring(0, 2)+"时");
		str.append(time.substring(2, 4)+"分");
		str.append(time.substring(4)+"秒");
		
		str.append(",定位状态:");
		if("A".equals(this.msgFields.get(2))){
			str.append("定位");
		}
		if("V".equals(this.msgFields.get(2))){
			str.append("导航");
		}
		
		if("N".equals(this.msgFields.get(4))){
			str.append("，北纬");
		}
		if("S".equals(this.msgFields.get(4))){
			str.append("，南纬");
		}
		
		String latitude = this.msgFields.get(3);
		str.append(latitude.substring(0, 2)+"度");
		str.append(latitude.substring(2)+"分");
		
		if("W".equals(this.msgFields.get(6))){
			str.append("，西经");
		}
		if("E".equals(this.msgFields.get(6))){
			str.append("，东经");
		}
		
		String longitude = this.msgFields.get(5);
		str.append(longitude.substring(0, 3)+"度");
		str.append(longitude.substring(3)+"分");
				
		str.append(",对地速度:");
		str.append(this.msgFields.get(7));
		str.append("Knots");
		
		str.append(",对地航向:");
		str.append(this.msgFields.get(8));
		str.append("度");
		
		String dt = this.msgFields.get(9);
		str.append(",定位点的UTC日期:");
		str.append(dt.substring(4)+"年");
		str.append(dt.substring(2,4)+"月");
		str.append(dt.substring(0,2)+"日");
		
		str.append(",磁偏角:");
		str.append(this.msgFields.get(10));
		str.append("度");
		
		str.append(",磁偏角方向:");
		if("W".equals(this.msgFields.get(11))){
			str.append("西");
		}
		if("E".equals(this.msgFields.get(11))){
			str.append("东");
		}
		
		return str.toString();
	}
}
