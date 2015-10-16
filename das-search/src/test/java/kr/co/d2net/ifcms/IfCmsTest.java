package kr.co.d2net.ifcms;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import kr.co.d2net.commons.utils.Utility;
import kr.co.d2net.model.Das;
import kr.co.d2net.model.SearchInfo;
import kr.co.d2net.service.XmlConvertorService;
import kr.co.d2net.service.XmlConvertorServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.sbs.ifcms.query.Query;
import com.sbs.ifcms.query.QueryResult;
import com.sbs.ifcms.query.SearchControls;
import com.sbs.ifcms.query.search.AndTerm;
import com.sbs.ifcms.query.search.OrTerm;
import com.sbs.ifcms.query.search.SearchTerm;
import com.sbs.ifcms.query.search.StringTerm;
import com.sbs.ifcms.spi.Session;

public class IfCmsTest {
	
	//@Ignore
	@Test
	public void loginTest() {
		try {
			/*D2NETCMS d2netcms = new D2NETCMS();
			d2netcms.setURL("http://10.30.23.48:8088/PDAS/services/PDASServices");
			
			Session session = d2netcms.login("S000000", "000000");
			*/
			Query query = buildQuery();
			SearchControls controls = buildSearchControls();
			//QueryResult result = session.search(query, controls);
			
			SearchTerm searchTerm = query.getConstraint();
			if(searchTerm instanceof AndTerm) {
				AndTerm andTerm = (AndTerm) searchTerm;
				
				Map<String, String> permsMap = new HashMap<String, String>();
				
				for (SearchTerm term : andTerm.getTerms()) {
					if (term instanceof StringTerm) {
						//
						System.out.println("and: "+term);
					} else if (term instanceof OrTerm) {
						OrTerm orTerm = (OrTerm) term;
						
						int i=0;
						for (SearchTerm term2 : orTerm.getTerms()) {
							if (term2 instanceof AndTerm) {
								AndTerm andTerm2 = (AndTerm) term2;
								
								String channelCd = "";
								for (SearchTerm term3 : andTerm2.getTerms()) {
									if (term3 instanceof StringTerm) {
										String propertyName = ((StringTerm) term3).getPropertyName();
										String value = ((StringTerm) term3).getPattern();
										System.out.println("propertyName: "+propertyName+", value: "+value);
										if("contents_channel".equals(propertyName)) {
											channelCd = value;
											if(!permsMap.containsKey(value))
												permsMap.put(value, null);
										} else if("material_type".equals(propertyName)) {
											if(permsMap.containsKey(channelCd)) {
												String channelValue = permsMap.get(channelCd);
												if(channelValue != null && channelValue.length() > 0) {
													channelValue += ",";
												}
												
												if(channelValue == null) channelValue = "";
												
												if(value.equals("PGM")){
													channelValue += "P";
												}else{
													channelValue += "M";
												}
												if(channelValue.indexOf("P") > -1 && channelValue.indexOf("M") > -1) {
													permsMap.put(channelCd, "A");
												} else {
													permsMap.put(channelCd, channelValue);
												}
											}
										}
									}
								}
							}
							i++;
						}
					}
				}
				
				StringBuffer channel = new StringBuffer();
				StringBuffer program = new StringBuffer();
				StringBuffer material = new StringBuffer();
				
				channel.setLength(0);
				program.setLength(0);
				material.setLength(0);
				
				System.out.println(permsMap);
				// 梨꾨꼸蹂??꾨줈洹몃옩, ?뚯옱 議고쉶沅뚰븳 ?щ?瑜?DAS CMS???ㅼ젙?쒕떎.
				Iterator<String> it = permsMap.keySet().iterator();
				while(it.hasNext()) {
					String channelCd = it.next();
					String channelValue = permsMap.get(channelCd);
					
					String p = "";
					String m = "";
					
					
					if(channelValue.equals("A")) {
						p = "Y";
						m = "Y";
					} else {
						if("P".equals(channelValue)) {
							p = "Y";
							m = "N";
						} else {
							p = "N";
							m = "Y";
						}
					}
					
					if(channel.toString().length() != 0) {
						channel.append(","+channelCd);
					} else {
						channel.append(channelCd);
					}
					if(program.toString().length() != 0) {
						program.append(","+p);
					} else {
						program.append(","+p);
					}
					if(material.toString().length() != 0) {
						material.append(","+m);
					} else {
						material.append(","+m);
					}
				}
				
				System.out.println(channel);
				System.out.println(program);
				System.out.println(material);
			}
			
/*
			// 梨꾨꼸蹂??꾨줈洹몃옩, ?뚯옱 議고쉶沅뚰븳 ?щ?瑜?DAS CMS???ㅼ젙?쒕떎.
			for(int i=0; i<channels.length; i++) {
				String programYn = StringUtils.defaultIfEmpty(perms[i][0], "N");
				String meterialYn = StringUtils.defaultIfEmpty(perms[i][1], "N");

				if(i == 0 || i < (channels.length-1)){
					channel.append(channels[i]+",");

				}else{
					channel.append(channels[i]);

				}
				if(i == 0 || i < (channels.length-1)){
					program.append(programYn+",");

				}else{
					program.append(programYn);

				}
				if(i == 0 || i < (channels.length-1)){
					material.append(meterialYn+",");

				}else{
					material.append(meterialYn);

				}
			}

			if(logger.isDebugEnabled()) {
				logger.debug("channel : "+channel.toString());
				logger.debug("program : "+program.toString());
				logger.debug("material : "+material.toString());
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void converterDur() {
		try {
			System.out.println(Utility.changeDuration(6602));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private Query buildQuery() {
		Query query = new Query();
		query.setStartDate(new Date());
		query.setEndDate(new Date());
		AndTerm andTerm = new AndTerm();
		andTerm.addTerm(new StringTerm("title", "foo"));
		
		OrTerm orTerm = new OrTerm();
		orTerm.addTerm(new AndTerm(new StringTerm("contents_channel", "A"), new StringTerm("material_type", "PGM")));
		orTerm.addTerm(new AndTerm(new StringTerm("contents_channel", "A"), new StringTerm("material_type", "Material")));
		orTerm.addTerm(new AndTerm(new StringTerm("contents_channel", "C"), new StringTerm("material_type", "PGM")));
		
		andTerm.addTerm(orTerm);
		query.setConstraint(andTerm);
		query.setMediaType("video");
		return query;
	}
	
	private SearchControls buildSearchControls() {
		SearchControls controls = new SearchControls();
		controls.setOffset(1);
		controls.setCountLimit(20);
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		return controls;
	}
	
	@Ignore
	@Test
	public void xmlCreate() {
		
		XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
		
		try {
			Das das = new Das();
			SearchInfo search = new SearchInfo();
			search.setKwd("?숉솕??怨쇳븰?먰뿕");
			search.setScn("");
			search.setDetailSearch("true");
			search.setUserId("");
			search.setReSrchFlag("false");
			search.setSort("D");
			search.setPageSize(2);
			search.setPageNum(1);
			search.setReSrchFlag("false");
			search.setSortcolumn("DAY");

			das.setSearchInfo(search);

			System.out.println(convertorService.createMarshaller(das));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
