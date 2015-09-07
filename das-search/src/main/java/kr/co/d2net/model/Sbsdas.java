package kr.co.d2net.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sbsdas")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sbsdas {

	@XmlElement(name="das")
	private Das das;

	public Das getDas() {
		return das;
	}
	public void setDas(Das das) {
		this.das = das;
	}
	
}
