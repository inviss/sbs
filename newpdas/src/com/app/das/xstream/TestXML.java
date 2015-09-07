package com.app.das.xstream;

import java.util.ArrayList;
import java.util.List;

import com.app.das.util.XmlUtil;

public class TestXML {

	public static void main(String args[]) {
		PersonRule person = null;
		List<Member> members = null;
		Member member = null;

		try {
			person = new PersonRule();
			
			members = new ArrayList<Member>(3);
			
			member = new Member();
			member.setAccountNo("mimul");
			member.setPassword("mimuladmim");
			members.add(member);

			member = new Member();
			member.setAccountNo("pepsi");
			member.setPassword("pepsiadmin");
			members.add(member);

			member = new Member();
			member.setAccountNo("scott");
			member.setPassword("tigerda");
			members.add(member);

			person.setMembers(members);

			// Object에서 XML 생성
			String xmlString = person.toXML();
			System.out.println("XML : \n " + XmlUtil.getXmlWrapper(xmlString));

			// XML파일 Object 매핑
			PersonRule personRule = (PersonRule) BaseRule.fromXML(xmlString,
					PersonRule.class);

			for (Member mem : personRule.getMembers()) {
				System.out.println(mem.getAccountNo());
				System.out.println(mem.getPassword());
			}
		} catch (Exception e) {
			System.out.println(e);
			person = null;
			members = null;
		}
	}

}
