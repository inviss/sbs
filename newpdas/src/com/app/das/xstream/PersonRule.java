package com.app.das.xstream;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("das")
public class PersonRule extends BaseRule implements IXmlRule {
	private static final long serialVersionUID = 1L;

	public String toXML() {
		return super.toXML();
	}

	private String name;
	private int age;
	private String occupation;

	@XStreamImplicit(itemFieldName = "memberss")
	private List<Member> members;

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
//
}
