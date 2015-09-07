package com.sbs.das.soap;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:/com/sbs/das/soap/applicationContext.xml",
		"classpath:/com/sbs/das/soap/applicationContext-cxf.xml"
})
public class BaseConfig {
	@Autowired
	private ApplicationContext context;

	public ApplicationContext getContext() {
		return context;
	}
}
