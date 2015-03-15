package com.nmea.core;

public class RealDataSource extends AbstractDataSource{
	
	public static void main(String[] args) throws Exception {
		CodecManager codecManager = CodecManager.getInstance();
		String vdm = "!AIVDM,1,1,,B,16:>>s5Oh08dLO8AsMAVqptj0@>p,0*67\r\n";
		String rmc = "$GPRMC,161229.487,A,3723.2475,N,12158.3416,W,0.13,309.62,120598,,*10\r\n";
		String gll = "$GPGLL,3723.2475,N,12158.3416,W,161229.487,A*2C\r\n";
		String gga = "$GPGGA,161229.487,3723.2475,N,12158.3416,W,1,07,1.0,9.0,M, , ,,0000*18\r\n";
		codecManager.decode(gga);
		codecManager.decode(gll);
		codecManager.decode(rmc);
		codecManager.decode(vdm);
	}
}
