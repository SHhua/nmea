package com.nmea.obj;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.nmea.sentence.IGroupedSentence;

public class VdmNmeaObject extends AbstractNmeaObject implements
		IGroupedSentence {
	public VdmNmeaObject() {
		this.objType = VDM_PROTOL;
	}

	private int total;
	private int num;
	private int sequence;
	private char channel;
	private int fillNum;

	private String byteStr;

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the channel
	 */
	public char getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(char channel) {
		this.channel = channel;
	}

	/**
	 * @return the fillNum
	 */
	public int getFillNum() {
		return fillNum;
	}

	/**
	 * @param fillNum
	 *            the fillNum to set
	 */
	public void setFillNum(int fillNum) {
		this.fillNum = fillNum;
	}

	/**
	 * @return the byteStr
	 */
	public String getByteStr() {
		return byteStr;
	}

	/**
	 * @param byteStr
	 *            the byteStr to set
	 */
	public void setByteStr(String byteStr) {
		this.byteStr = byteStr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("VDM消息:");

		String messageId = StringUtils.substring(byteStr, 0, 6);
		str.append("，报文ID message ID：");
		str.append(Integer.parseInt(messageId, 2));

		String repeatIndicator = StringUtils.substring(byteStr, 6, 8);
		str.append("，重复指示器repeate times：");
		str.append(Integer.parseInt(repeatIndicator, 2));

		String userID = StringUtils.substring(byteStr, 8, 38);
		str.append("，用户ID User ID：");
		str.append(Integer.parseInt(userID, 2));

		String navigationalStatus = StringUtils.substring(byteStr, 38, 42);
		str.append("，导航状态Navigational status：");
		int navstatus = Integer.parseInt(navigationalStatus, 2);
		String navval = null;
		switch (navstatus) {
		case 0:
			navval = "under way using engine";
			break;
		case 1:
			navval = "at anchor";
			break;
		case 2:
			navval = "not under command";
			break;
		case 3:
			navval = "restricted manoeuvrability";
			break;
		case 4:
			navval = "constrained by her draught";
			break;
		case 5:
			navval = "moored";
			break;
		case 6:
			navval = "aground";
			break;
		case 7:
			navval = "engaged in fishing";
			break;
		case 8:
			navval = "under way sailing";
			break;
		case 9:
			navval = "reserved for future amendment of navigational status for ships carrying DG, HS, or MP, or IMO hazard or pollutant category C, high speed craft (HSC)";
			break;
		case 10:
			navval = "reserved for future amendment of navigational status for ships carrying dangerous goods (DG), harmful substances (HS) or marine pollutants (MP), or IMO hazard or pollutant category A, wing in grand (WIG)";
			break;
		default:
			break;
		}

		str.append(navval);

		String rateOfTurn = StringUtils.substring(byteStr, 42, 50);
		str.append("，转弯速率Rate of turn：");
		str.append(Integer.parseInt(rateOfTurn, 2));

		String sog = StringUtils.substring(byteStr, 50, 60);
		str.append("，地速Speed over groun：");
		str.append(Integer.parseInt(sog, 2));

		String positionAaccuracy = StringUtils.substring(byteStr, 60, 61);
		str.append("，位置精度Position accuracy：");
		str.append(Integer.parseInt(positionAaccuracy, 2));

		String longitude = StringUtils.substring(byteStr, 61, 89);
		str.append("，经度Longitude：");
		double lonVal = (double) toIntByComplement(longitude) / 600000;
		if (lonVal > 0) {
			str.append("东经" + lonVal + "°");
		} else {
			str.append("西经" + Math.abs(lonVal) + "°");
		}

		String latitude = StringUtils.substring(byteStr, 89, 116);
		str.append("，纬度Latitude：");
		double latVal = (double) toIntByComplement(latitude) / 600000;
		if (lonVal > 0) {
			str.append("北纬" + latVal + "°");
		} else {
			str.append("南纬" + Math.abs(latVal) + "°");
		}

		String cog = StringUtils.substring(byteStr, 116, 128);
		str.append("，对地航向Course over ground：");
		str.append((float) Integer.parseInt(cog, 2) / 10 + "°");

		String trueHeading = StringUtils.substring(byteStr, 128, 137);
		str.append("，直航向True heading：");
		str.append(Integer.parseInt(trueHeading, 2) + "°");

		String timeStamp = StringUtils.substring(byteStr, 137, 143);
		str.append("，时间戳Time stamp：");
		str.append(Integer.parseInt(timeStamp, 2) + "秒");

		String smi = StringUtils.substring(byteStr, 143, 145);
		str.append("，地区应用标识special manoeuvre indicator：");
		str.append(Integer.parseInt(smi, 2));

		String spare = StringUtils.substring(byteStr, 145, 148);
		str.append("，备用Spare：");
		str.append(Integer.parseInt(spare, 2));

		String raimFlag = StringUtils.substring(byteStr, 148, 149);
		str.append("，RAIM标志RAIM-flag：");
		str.append(Integer.parseInt(raimFlag, 2));

		String communicationState = StringUtils.substring(byteStr, 149, 168);
		str.append("，通信状态Communication state：");
		str.append(Integer.parseInt(communicationState, 2));

		String syncState = StringUtils.substring(communicationState, 0, 2);
		str.append("，同步状态Sync State：");
		str.append(Integer.parseInt(syncState, 2));

		String slotTimeout = StringUtils.substring(communicationState, 2, 5);
		str.append("，时隙超时Slot Timeout：");
		int st = Integer.parseInt(slotTimeout, 2);
		str.append(st);

		String subMessage = StringUtils.substring(communicationState, 5, 19);
		str.append("，子消息Sub Message：");
		str.append(Integer.parseInt(subMessage, 2));

		str.append("，收到的电台数Received stations：");
		if (st == 3 || st == 5 | st == 7) {
			str.append(Integer.parseInt(subMessage, 2));
		}

		str.append("，时隙号Slot number：");
		if (st == 2 || st == 4 | st == 6) {
			str.append(Integer.parseInt(subMessage, 2));
		}

		str.append("，UTC小时和分钟UTC hour and minute：");
		if (st == 1) {
			String hour = StringUtils.substring(subMessage, 9, 14);
			str.append(Integer.parseInt(hour, 2) + "时");
			String min = StringUtils.substring(subMessage, 2, 9);
			str.append(Integer.parseInt(min, 2) + "分");
		}

		str.append("，时隙偏移量Slot offset：");
		if (st == 0) {
			str.append(Integer.parseInt(subMessage, 2));
		}

		return str.toString();
	}

	// 按照2的补码求int值
	public static int toIntByComplement(String str) {
		int result = 0;
		if (StringUtils.isEmpty(str)) {
			result = -1;
		}
		if (StringUtils.startsWith(str, "0")) {
			result = Integer.parseInt(str, 2);
		}
		if (StringUtils.startsWith(str, "1")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < str.length(); i++) {
				char c = str.charAt(i);
				if (c == '1') {
					sb.append("0");
				}
				if (c == '0') {
					sb.append("1");
				}

			}
			result = Integer.parseInt(sb.toString(), 2) + 1;
			result = 0 - result;
		}
		return result;
	}

	//以下第二次作业新增
	@Override
	public String sentenceType() {
		return "VDM";
	}

	@Override
	public Date getReceiveDate() {
		return null;
	}

	@Override
	public int getSequentialMessageId() {
		return this.sequence;
	}

	@Override
	public int getTotalNumberOfSentences() {
		return this.total;
	}

	@Override
	public int getSentenceNumber() {
		return this.num;
	}

	@Override
	public boolean isComplete() {
		return this.getTotalNumberOfSentences() == this.getSentenceNumber();
	}

	@Override
	public void concatenate(IGroupedSentence sentence) {
		VdmNmeaObject vno = (VdmNmeaObject) sentence;
		this.num += 1;
		this.byteStr = new StringBuffer(byteStr).append(vno.getByteStr())
				.toString();
	}

}
