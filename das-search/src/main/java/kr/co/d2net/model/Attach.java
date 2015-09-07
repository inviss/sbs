package kr.co.d2net.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 첨부파일
 * @author Administrator
 *
 */
@XmlRootElement(name="Attach")
@XmlAccessorType(XmlAccessType.FIELD)
public class Attach {

	@XmlElement(name="attach_item")
	List<AttachItem> items = new ArrayList<AttachItem>();
	public List<AttachItem> getItems() {
		return items;
	}
	public void setItems(List<AttachItem> items) {
		this.items = items;
	}
	public void addItem(AttachItem attachItem) {
		this.items.add(attachItem);
	}
	
}
