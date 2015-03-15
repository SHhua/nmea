package com.nmea.sentence.ais;

import com.nmea.sentence.IEncapsulatedSentence;

public interface IEncapsulatedAisMessage extends IEncapsulatedSentence {

	public int messageType();
}
