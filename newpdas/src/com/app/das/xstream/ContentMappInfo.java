package com.app.das.xstream;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("das")
public class ContentMappInfo extends BaseRule implements IXmlRule {

	private static final long serialVersionUID = 1L;
	
	public String toXML(){
		return super.toXML();
	}

	@XStreamImplicit(itemFieldName="contentMappInfo")
	List<ContentMapp> contentMapps;

	@XStreamImplicit(itemFieldName="contentMappInfotest")
	List<ContentMapp> contentMapptest;
	
	public List<ContentMapp> getContentMapps() {
		return contentMapps;
	}

	public void setContentMapps(List<ContentMapp> contentMapps) {
		this.contentMapps = contentMapps;
	}

	public List<ContentMapp> getContentMapptest() {
		return contentMapptest;
	}

	public void setContentMapptest(List<ContentMapp> contentMapptest) {
		this.contentMapptest = contentMapptest;
	}
	

}
