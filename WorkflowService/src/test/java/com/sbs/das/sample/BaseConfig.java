package com.sbs.das.sample;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:/spring/applicationContext-bean.xml",
		//"classpath:/spring/applicationContext-dao.xml",
		//"classpath:/spring/applicationContext-tx.xml",
		//"classpath:/com/sbs/das/soap/applicationContext-cxf.xml"
})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class BaseConfig {
	@Autowired
	private ApplicationContext context;

	public ApplicationContext getContext() {
		return context;
	}
}
