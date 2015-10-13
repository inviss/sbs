package com.app.das.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AdaptorCDATA extends XmlAdapter<String, String> {
	@Override
    public String marshal(String arg0) throws Exception {
		return CommonUtl.removeCharacter(arg0);
        //return "<![CDATA[" + arg0 + "]]>";
    }
    @Override
    public String unmarshal(String arg0) throws Exception {
    	return arg0;
    }
}
