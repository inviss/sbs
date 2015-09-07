package kr.co.d2net.ifcms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kr.co.d2net.commons.system.DasCmsConnector;
import kr.co.d2net.commons.system.DasCmsConnectorImpl;
import kr.co.d2net.commons.utils.PropertyLoader;
import kr.co.d2net.commons.utils.Utility;
import kr.co.d2net.model.Annot;
import kr.co.d2net.model.AnnotInfo;
import kr.co.d2net.model.AttachItem;
import kr.co.d2net.model.Corner;
import kr.co.d2net.model.CornerItem;
import kr.co.d2net.model.Das;
import kr.co.d2net.model.DasSearch;
import kr.co.d2net.model.IngestInfo;
import kr.co.d2net.model.MetaDataInfo;
import kr.co.d2net.model.SearchInfo;
import kr.co.d2net.model.TokenInfo;
import kr.co.d2net.service.XmlConvertorService;
import kr.co.d2net.service.XmlConvertorServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sbs.ifcms.Asset;
import com.sbs.ifcms.CMSRuntimeException;
import com.sbs.ifcms.Folder;
import com.sbs.ifcms.InvalidSessionException;
import com.sbs.ifcms.ObjectFactory;
import com.sbs.ifcms.OperationNotSupportedException;
import com.sbs.ifcms.Property;
import com.sbs.ifcms.Segment;
import com.sbs.ifcms.Shot;
import com.sbs.ifcms.Storyboard;
import com.sbs.ifcms.query.Query;
import com.sbs.ifcms.query.QueryResult;
import com.sbs.ifcms.query.SearchControls;
import com.sbs.ifcms.query.search.AndTerm;
import com.sbs.ifcms.query.search.ComparisonTerm;
import com.sbs.ifcms.query.search.DateTerm;
import com.sbs.ifcms.query.search.OrTerm;
import com.sbs.ifcms.query.search.SearchTerm;
import com.sbs.ifcms.query.search.StringTerm;
import com.sbs.ifcms.spi.Session;
import com.sbs.ifcms.spi.WorkflowManager;

/**
 * Sub CMS에 검색 질의, 로그아웃
 * 세션정보가 필요할 경우 생성자 부분에 세션정보를 추가로 받음
 * @author Administrator
 *
 */
public class D2NETCMSSession implements Session {

	private static Logger logger = Logger.getLogger(D2NETCMSSession.class);

	private String token = null;
	private String url = null;
	private String id = null;
	private String name = null;
	private boolean isOpen = false;

	public D2NETCMSSession(String url, String token, String id, String name, boolean isOpen) {
		this.url = url;
		this.token = token;
		this.id = id;
		this.name = name;
		this.isOpen = isOpen;
		logger.debug("session login id: "+this.id);
	}

	public String getURL() {
		return url;
	}

	public String getUserId() {
		return id;
	}

	public String getUserNm() {
		return name;
	}

	public List<Folder> getFolders(String parentId, int searchScope, String mode) {

		if(mode.equals("archive")){
			List<Folder> result = new ArrayList<Folder>(0);

			Folder folder = ObjectFactory.createFolder(
					"DAS",
					"아카이브"
					);
			folder.setProperty("size",0);
			folder.setProperty("selectable",true );
			result.add(folder);;
			return result;

		}else{
			return null;
		}       

	}

	public QueryResult search(Query query, SearchControls controls) {
		QueryResult result = new QueryResult();
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl(url);
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			Das das = new Das();

			TokenInfo tokenDO = new TokenInfo();
			tokenDO.setHex("DAS");
			tokenDO.setToken(token);
			das.setTokenInfo(tokenDO);

			if(isOpen) {
				das = convertorService.unMarshaller(cmsConnector.loginValid(convertorService.createMarshaller(das)));

				if(logger.isDebugEnabled()) {
					//logger.debug("login result: "+das.getTokenInfo().getResult());
				}
			}else{
				das.getTokenInfo().setResult("0:TEST");
			}

			if(das.getTokenInfo().getResult().startsWith("0:")) {
				das.setTokenInfo(null);

				cmsConnector = new DasCmsConnectorImpl(url);

				SearchInfo search = new SearchInfo();

				if(StringUtils.isNotBlank(query.getQuery())) {
					search.setKwd(query.getQuery());
				}

				StringBuffer grpKwd = new StringBuffer();
				StringBuffer grpFd = new StringBuffer();
				StringBuffer grpAnd = new StringBuffer();
				StringBuffer grpStartDd = new StringBuffer();
				StringBuffer grpEndDd = new StringBuffer();

				StringBuffer channel = new StringBuffer();
				StringBuffer program = new StringBuffer();
				StringBuffer material = new StringBuffer();

				SearchTerm searchTerm = query.getConstraint();
				if(searchTerm != null) {
					if (searchTerm instanceof AndTerm) {
						AndTerm andTerm = (AndTerm) searchTerm;
						for (SearchTerm term : andTerm.getTerms()) {
							if (term instanceof StringTerm) {
								String propertyName = ((StringTerm) term).getPropertyName();
								String value = ((StringTerm) term).getPattern();

								if(!"keyword".equals(propertyName)) {
									search.setDetailSearch("true");
									if(grpKwd.length() != 0) grpKwd.append(",");
									if(grpFd.length() != 0) grpFd.append(",");
									if(grpAnd.length() != 0) grpAnd.append(",");

								}

								if(logger.isDebugEnabled()) {
									logger.debug("String term -> propertyName: "+propertyName+", value: "+value);
								}

								if("title".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("title");
									grpAnd.append("and");
								} else if("title_sub".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("sub_ttl");
									grpAnd.append("and");
								} else if("program_name".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("pgm_nm");
									grpAnd.append("and");
								} else if("corner_title".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("cn_nm");
									grpAnd.append("and");
								} else if("corner_contents".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("cn_info");
									grpAnd.append("and");
								} else if("program_sequence".equals(propertyName)) {
									grpKwd.append(Integer.valueOf(StringUtils.defaultIfEmpty(value, "0")));
									grpFd.append("epis_no");
									grpAnd.append("and");
								} else if("creator".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("producer_nm");
									grpAnd.append("and");
								} else if("creator_sub".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("drt_nm");
									grpAnd.append("and");
								} else if("publisher_external".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("org_prdr_nm");
									grpAnd.append("and");
								} else if("genre_l".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("ctgr_l_cd");
									grpAnd.append("and");
								} else if("genre_m".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("ctgr_m_cd");
									grpAnd.append("and");
								} else if("genre_s".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("ctgr_s_cd");
									grpAnd.append("and");
								} else if("location_shooting".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("cmr_place");
								} else if("keyword".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("key_words");
									grpAnd.append("and");
								} else if("description".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("spc_info");
									grpAnd.append("and");
								} else if("copyright_type".equals(propertyName)) {

									if(value.equals("001")){
										grpKwd.append("일체소유");

									}else if(value.equals("002")){
										grpKwd.append("일부소유");	
									}else if(value.equals("003")){
										grpKwd.append("저작권없음");
									}else{
										grpKwd.append(value);
									}
									grpFd.append("cprt_type");
									grpAnd.append("and");
								} else if("copyright_owner".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("cprtr_nm");
									grpAnd.append("and");
								} else if("production_type".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("prdt_in_outs_cd");
									grpAnd.append("and");

								} else if("usegrade".equals(propertyName)) {
									if(value.equals("002")){
										grpKwd.append("방송심의제재");										
									} else 	if(value.equals("003")){
										grpKwd.append("사용금지");	
									} else if(value.equals("004")){
										grpKwd.append("확인후 사용");
									} else if(value.equals("005")){
										grpKwd.append("사내심의사항");
									} else if(value.equals("006")){
										grpKwd.append("심의제한");
									} else if(value.equals("007")){
										grpKwd.append(" ");
									} else{
										grpKwd.append(value);
									}
									grpFd.append("annot_clf_nm");
									grpAnd.append("and");
									search.setAnnotClfNm(value);
								} else if("usegrade_desc".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("annot_clf_cont");
									grpAnd.append("and");

								} else if("parents_guidence".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("view_gr_cd");
									grpAnd.append("and");

								} else if("name_host".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("mc_nm");
									grpAnd.append("and");

								} else if("name_guest".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("cast_nm");
									grpAnd.append("and");

								} else if("artist".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("artist");
									grpAnd.append("and");

								} else if("country".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("country_cd");
									grpAnd.append("and");

								} else if("music_info".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("music_info");
									grpAnd.append("and");

								} else if("media_id".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("media_id");
									grpAnd.append("and");

								} else if("media_format".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("ct_typ");
									grpAnd.append("and");

								} else if("resolution".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("vd_qlty");
									grpAnd.append("and");

								} else if("duration".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("brd_leng");
									grpAnd.append("and");

								}else if("contents_channel".equals(propertyName)) {
									grpKwd.append(value);
									grpFd.append("chennel_cd");
									grpAnd.append("and");

								}
							} else if (term instanceof DateTerm) {

								String propertyName = ((DateTerm) term).getPropertyName();
								Date value = ((DateTerm) term).getDate();
								int comparison = ((DateTerm) term).getComparison();
								if(logger.isDebugEnabled()) {
									logger.debug("Date term -> propertyName: "+propertyName+", value: "+Utility.getDate(value, "yyyyMMdd"));
								}

								if("datetime_onair".equals(propertyName)) {
									if(ComparisonTerm.GE == comparison) {
										search.setStartDate(Utility.getDate(value, "yyyyMMdd"));
									} else if(ComparisonTerm.LE == comparison) {
										search.setEndDate(Utility.getDate(value, "yyyyMMdd"));
									}
								} else if("datetime_shooting".equals(propertyName)) {
									if(ComparisonTerm.GE == comparison) {
										search.setStartDate(Utility.getDate(value, "yyyyMMdd"));
									} else if(ComparisonTerm.LE == comparison) {
										search.setEndDate(Utility.getDate(value, "yyyyMMdd"));
									}
								} else if("datetime_regist".equals(propertyName)) {
									if(ComparisonTerm.GE == comparison) {
										search.setStartDate(Utility.getDate(value, "yyyyMMdd"));
									} else if(ComparisonTerm.LE == comparison) {
										search.setEndDate(Utility.getDate(value, "yyyyMMdd"));
									}
								}


							} else if(term instanceof OrTerm) {


								Map<String, String> permsMap = new HashMap<String, String>();

								// 사용자별 채널별 조회 권한 및 컨텐츠별 조회 권한이 있는 타입만 검색 조건에 포함하면 됨
								OrTerm orTerm = (OrTerm) term;
								int count=0;

								for (SearchTerm term2 : orTerm.getTerms()) {
									if (term2 instanceof AndTerm) {
										AndTerm andTerm2 = (AndTerm) term2;

										String channelCd = "";
										for (SearchTerm term3 : andTerm2.getTerms()) {
											//logger.debug("#############term3 : "+term3);

											if (term3 instanceof StringTerm) {
												String propertyName = ((StringTerm) term3).getPropertyName();
												String value = ((StringTerm) term3).getPattern();
												//		logger.debug("############# propertyName : "+propertyName);
												//		logger.debug("############# value : "+value);

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
								}

								channel.setLength(0);
								program.setLength(0);
								material.setLength(0);

								// 채널별 프로그램, 소재 조회권한 여부를 DAS CMS에 설정한다.
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
										program.append(p);
									}
									if(material.toString().length() != 0) {
										material.append(","+m);
									} else {
										material.append(m);
									}
								}

								if(logger.isDebugEnabled()) {
									//	logger.debug("channel : "+channel.toString());
									//	logger.debug("program : "+program.toString());
									//	logger.debug("material : "+material.toString());
								}		

								search.setChannelCd(channel.toString());
								search.setProgramYN(program.toString());
								search.setMaterialYn(material.toString());

							}else if (term instanceof AndTerm) {
								AndTerm andTerm2 = (AndTerm) term;
								int count=0;

								for (SearchTerm term4 : andTerm2.getTerms()) {
									if(term4 instanceof DateTerm){
										String propertyName = ((DateTerm) term4).getPropertyName();
										Date value = ((DateTerm) term4).getDate();
										int comparison = ((DateTerm) term4).getComparison();

										if(logger.isDebugEnabled()) {
											logger.debug("Date term4 -> propertyName: "+propertyName+", value: "+Utility.getDate(value, "yyyyMMdd"));
										}

										search.setDetailSearch("true");

										if("datetime_onair".equals(propertyName)) {
											if(ComparisonTerm.GE == comparison) {
												if(grpFd.length() != 0) grpFd.append(",");
												grpFd.append("BRD_DD");
												if(grpStartDd.length() != 0) grpStartDd.append(",");
												grpStartDd.append((Utility.getDate(value, "yyyyMMdd")));
											} else if(ComparisonTerm.LE == comparison) {
												if(grpEndDd.length() != 0) grpEndDd.append(",");
												grpEndDd.append((Utility.getDate(value, "yyyyMMdd")));
											}
										} else if("datetime_shooting".equals(propertyName)) {
											if(ComparisonTerm.GE == comparison) {
												if(grpFd.length() != 0) grpFd.append(",");
												grpFd.append("FM_DT");
												if(grpStartDd.length() != 0) grpStartDd.append(",");
												grpStartDd.append((Utility.getDate(value, "yyyyMMdd")));
											} else if(ComparisonTerm.LE == comparison) {
												if(grpEndDd.length() != 0) grpEndDd.append(",");
												grpEndDd.append((Utility.getDate(value, "yyyyMMdd")));
											}
										} else if("datetime_regist".equals(propertyName)) {
											if(ComparisonTerm.GE == comparison) {
												if(grpFd.length() != 0) grpFd.append(",");
												grpFd.append("REG_DT");
												if(grpStartDd.length() != 0) grpStartDd.append(",");
												grpStartDd.append((Utility.getDate(value, "yyyyMMdd")));
											} else if(ComparisonTerm.LE == comparison) {
												if(grpEndDd.length() != 0) grpEndDd.append(",");
												grpEndDd.append((Utility.getDate(value, "yyyyMMdd")));
											}
										}


									}
								}
							}

							// 방송일/촬영일 검색
							if (query.getStartDate() != null) {
								search.setStartDate(Utility.getDate(query.getStartDate(), "yyyyMMdd"));
							}
							if (query.getEndDate() != null) {
								search.setEndDate(Utility.getDate(query.getEndDate(), "yyyyMMdd"));
							}
						}
					}
					//	logger.debug("query.isAscending : "+query.isAscending());
					//logger.debug("query.getSortingOrder() : "+program.toString());

					// 정렬조건 추가 (20120816)

					if(query.isAscending()){
						search.setSort("A");
					}else{
						search.setSort("D");
					}
					String sortcolumn = query.getSortingOrder();	
					if(sortcolumn.equals("datetime_regist")){
						search.setSortcolumn("REG_DT");
					}else if(sortcolumn.equals("title")){
						search.setSortcolumn("PGMNM_TITLE");	
					}

					search.setPageSize(controls.getCountLimit());

					//search.setPageNum(controls.getOffset()+1);offset은 전체페이지.
					int total_page = controls.getOffset();
					int pageNum = total_page/controls.getCountLimit();
					//각페이지는 +1을 해줘야 정상적으로 인식 첫페이지가 0값
					if(total_page==0){

						pageNum=1;
						search.setPageNum(pageNum);
					}else{
						search.setPageNum(pageNum+1);
					}


					search.setBrowser("ifcms");
					controls.getCountLimit();//페이지
					//logger.debug("###################pageSize     "+controls.getCountLimit());
					//logger.debug("###################controls.getOffset()     "+controls.getOffset());
					if(StringUtils.isNotBlank(search.getDetailSearch()) && search.getDetailSearch().equals("true")) {
						search.setGrpKwd(grpKwd.toString());
						search.setGrpSrchFd(grpFd.toString());
						search.setGrpAndOr(grpAnd.toString());
						search.setGrpstartdd(grpStartDd.toString());
						search.setGrpenddd(grpEndDd.toString());
						search.setStartDate("");
						search.setEndDate("");
						search.setKwd("");
					}else{
						search.setGrpKwd("");
						search.setGrpSrchFd("");
						search.setGrpAndOr("");
						search.setGrpstartdd("");
						search.setGrpenddd("");
					}

					das.setSearchInfo(search);
					//logger.debug("query.getMediaType(): "+query.getMediaType());
					//상세검색조건이 비디오 타입이거나 아닐경우만 조회가능
					if(query.getMediaType().equals("video")||query.getMediaType().equals("")){

						String searchXML = convertorService.createMarshaller(das);

						if(logger.isDebugEnabled()) {
							logger.debug("request: "+searchXML);
						}
						long time1;long time2;
						time1 = System.currentTimeMillis();
						String responseXML = cmsConnector.findContents(searchXML);
						time2 = System.currentTimeMillis();
						logger.debug("responseXML end time : "+ ((time2 - time1)/1000.0f) +"초");
						if(StringUtils.isNotBlank(responseXML)) {
							das = convertorService.unMarshaller(responseXML);

							java.util.Map<String, Object> configMap = PropertyLoader.getPropertyHash("config");
							String mp4StreamUrl = (String)configMap.get("mp4.streaming.url");
							String wmvStreamUrl = (String)configMap.get("wmv.streaming.url");
							String noImgUrl = (String)configMap.get("no.rp.image.url");
							result.setTotalCount(das.getTotal());
							result.setOffset(das.getStartNum());
							for(DasSearch dasSearch : das.getSearchList()) {
								logger.debug("master_id xml : "+dasSearch.getMasterId());
								Asset asset = ObjectFactory.createAsset("M"+dasSearch.getMasterId());

								asset.setProperty("title", Utility.unescapeXml(StringUtils.defaultString(dasSearch.getPgmnmTitle(), "")));
								asset.setProperty("title_sub", Utility.unescapeXml(StringUtils.defaultString(dasSearch.getSubTtl(), "")));
								asset.setProperty("program_name", "");
								asset.setProperty("corner_contents", Utility.unescapeXml(StringUtils.defaultString(dasSearch.getCnNm(), "")));
								asset.setProperty("corner_title", Utility.unescapeXml(StringUtils.defaultString(dasSearch.getCnInfo(), "")));
								asset.setProperty("program_sequence", StringUtils.defaultString(dasSearch.getEpisNo(), ""));
								asset.setProperty("program_sequence_total", "");
								asset.setProperty("creator", StringUtils.defaultString(dasSearch.getProducerNm(), ""));
								asset.setProperty("creator_sub", StringUtils.defaultString(dasSearch.getDrtNm(), ""));
								asset.setProperty("publisher", "");
								asset.setProperty("publisher_external", StringUtils.defaultString(dasSearch.getOrgPrdrNm(), ""));
								asset.setProperty("media_id", StringUtils.defaultString(dasSearch.getMediaId(), ""));
								String annot_nm=StringUtils.defaultString(dasSearch.getAnnotClfNm(), "");
								if(annot_nm.equals("방송심의제재")){
									annot_nm = ("002");										
								} else 	if(annot_nm.equals("사용금지")){
									annot_nm = ("003");	
								} else if(annot_nm.equals("확인후 사용")){
									annot_nm = ("004");
								} else if(annot_nm.equals("사내심의사항")){
									annot_nm = ("005");
								} else if(annot_nm.equals("심의제한")){
									annot_nm = ("006");
								} else if(annot_nm.equals("무제한")){
									annot_nm = ("007");
								} else{
									annot_nm = "007";
								}
								asset.setProperty("usegrade", annot_nm);

								//20120802 추가

								String brd_dd = StringUtils.defaultString(dasSearch.getBrdDd(), "");
								String fm_dt = StringUtils.defaultString(dasSearch.getFmDt(), "");
								String ctgr_l_Cd = StringUtils.defaultString(dasSearch.getCtgrLCd(), "");

								String onAirDt = "";
								if(StringUtils.isNotBlank(ctgr_l_Cd)){
									if(ctgr_l_Cd.equals("100")){
										onAirDt=fm_dt;
									}else{
										onAirDt=brd_dd;									
									}
								}else{
									onAirDt=brd_dd;
								}

								if(StringUtils.isBlank(onAirDt))
									onAirDt = "19000101";

								logger.debug("master_id: "+"M"+dasSearch.getMasterId()+", onAirDt:   "+onAirDt);

								String yyyy = onAirDt.substring(0, 4);
								String mm = onAirDt.substring(4, 6);
								String dd = onAirDt.substring(6,8);
								String bd = yyyy+"-"+mm+"-"+dd;

								if(ctgr_l_Cd.equals("100")){
									asset.setProperty("datetime_onair", bd+" 00:00:00");
									asset.setProperty("datetime_shooting", bd+" 00:00:00");
								} else {
									asset.setProperty("datetime_onair", bd+" 00:00:00");
								}

								asset.setProperty("location_shooting", StringUtils.defaultString(dasSearch.getCmrPlace(), ""));
								asset.setProperty("keyword", StringUtils.defaultString(dasSearch.getKeyWords(), ""));
								asset.setProperty("synopsis_dir", "");
								asset.setProperty("synopsis_kr", "");
								asset.setProperty("synopsis_download_url", "");
								asset.setProperty("description", Utility.unescapeXml(StringUtils.defaultString(dasSearch.getSpcInfo(), "")));
								asset.setProperty("copyright_type", StringUtils.defaultString(dasSearch.getCprtType(), ""));
								asset.setProperty("copyright_owner", StringUtils.defaultString(dasSearch.getCprtrNm(), ""));
								asset.setProperty("copyright_desc", "");
								asset.setProperty("production_type", StringUtils.defaultString(dasSearch.getPrdtInOutsCd(), ""));
								asset.setProperty("usegrade_desc", StringUtils.defaultString(dasSearch.getAnnotClfCont(), ""));
								asset.setProperty("parents_guidence", StringUtils.defaultString(dasSearch.getViewGrCd(), ""));
								asset.setProperty("name_host", StringUtils.defaultString(dasSearch.getMcNm(), ""));
								asset.setProperty("name_guest", StringUtils.defaultString(dasSearch.getCastNm(), ""));
								asset.setProperty("artist", StringUtils.defaultString(dasSearch.getArtist(), ""));
								asset.setProperty("country", StringUtils.defaultString(dasSearch.getCountryCd(), ""));
								asset.setProperty("music_info", Utility.unescapeXml(StringUtils.defaultString(dasSearch.getMusicInfo(), "")));
								asset.setProperty("media_id", "");
								asset.setProperty("media_format","XDCAM MXF");
								logger.debug("#################media_format + "+ "XDCAM MXF");

								asset.setProperty("resolution", StringUtils.defaultString(dasSearch.getVdQltyNm(), ""));
								asset.setProperty("aspectratio", "");
								asset.setProperty("duration", StringUtils.defaultString(dasSearch.getBrdLeng(), ""));
								asset.setProperty("audio_type", "");
								asset.setProperty("caption_dir", "");
								asset.setProperty("caption_kr", "");
								asset.setProperty("caption_kr_download_url", "");
								asset.setProperty("caption_jp", "");
								asset.setProperty("caption_jp_download_url", "");
								asset.setProperty("caption_en", "");
								asset.setProperty("caption_en_download_url", "");
								asset.setProperty("caption_cn", "");
								asset.setProperty("caption_cn_download_url", "");
								asset.setProperty("preview_dir", "");
								asset.setProperty("preview_kr", "");
								asset.setProperty("preview_download_url", "");
								asset.setProperty("preview_url", "");
								asset.setProperty("file_name", "");
								asset.setProperty("file_size",  "");
								asset.setProperty("asset_type",  "");
								asset.setProperty("thumbnail_url", "");
								asset.setProperty("asset_id", "");
								asset.setProperty("datetime_regist", "");
								asset.setProperty("contents_channel",  StringUtils.defaultString(dasSearch.getChennel_cd(), ""));
								//logger.debug("#############################contents_channel   "  +  StringUtils.defaultString(dasSearch.getChennel_cd(), ""));
								//	asset.setProperty("contents_type", "");

								String kfrmPath = StringUtils.defaultString(dasSearch.getKfrmPath(), "");
								if(kfrmPath.indexOf("\\") > -1) {
									kfrmPath = kfrmPath.replace("\\", "/");
								}
								if(!kfrmPath.startsWith("/")) {
									kfrmPath = "/"+kfrmPath;
								}
								if(!kfrmPath.endsWith("/")) {
									kfrmPath =kfrmPath+ "/";
								}
								String kfrmSeq = StringUtils.defaultString(dasSearch.getRpimgKfrmSeq(), "0");
								logger.debug("############kfrmSeq    :  "+kfrmSeq);
								String rpUrl = (String)configMap.get("rp.image.url");
								String rpKfrmPath = "";
								logger.debug("############kfrmPath    :  "+kfrmPath);
								
								/* 2012.12.26 스트리밍 서버에서는 컨텍스트 정보가 빠짐
								if(kfrmPath.indexOf("net_mp4") > -1) {
									rpKfrmPath = kfrmPath.replace("net_mp4", "MDE_SB").replace("kfrm", "KFRM");
								}else{
									rpKfrmPath = kfrmPath.replace("mp4", "SBS_SB").replace("kfrm", "KFRM");
								}
								*/
								rpKfrmPath = kfrmPath.replace("kfrm", "KFRM");
								
								if(!kfrmPath.trim().equals("/")){
									asset.setProperty("thumbnail_url", rpUrl+rpKfrmPath+kfrmSeq+".jpg");
									logger.debug("############rpKfrmPath    :  "+rpUrl+rpKfrmPath+kfrmSeq+".jpg");

								}else{
									rpKfrmPath="./images/thumb_repre_wait.gif";
									asset.setProperty("thumbnail_url", "./images/thumb_repre_wait.gif");
									logger.debug("############rpKfrmPath    :  ./images/thumb_repre_wait.gif");

								}

								asset.setProperty("multi_asset_YN", "Y");


								/*
								 * 스트리밍 URL 추가
								 * config.properties에 스트리밍 url이 설정되어 있고, wmv 스트리밍 url의 ip 정보는 변경이 필요.
								 * DAS CMS에서 검색시 fl_path, fl_nm, cti_fmt 를 포함해서 넘겨줘야하고 검색엔진에 추가 색인 필요
								 */
								String flPath = StringUtils.defaultString(dasSearch.getFlPath(), "");
								String flNm = StringUtils.defaultString(dasSearch.getFlNm(), "");
								String ctiFmt = StringUtils.defaultString(dasSearch.getCtiFmt(), "301");
								/*if(logger.isDebugEnabled()) {
								logger.debug("fl_path : "+flPath);
								logger.debug("flNm : "+flNm);
								logger.debug("ctiFmt : "+ctiFmt);
							}*/

								if(StringUtils.isNotBlank(flPath) && StringUtils.isNotBlank(flNm)) {
									if(flPath.indexOf("\\") > -1) {
										flPath = flPath.replace("\\", "/");
									}
									if(!flPath.startsWith("/")) {
										flPath = "/"+flPath;
									}
									if(!flPath.endsWith("/")) {
										flPath =flPath+ "/";
									}
									if(ctiFmt.equals("301")) {
										asset.setProperty("preview_url", mp4StreamUrl+flPath.trim()+flNm.trim());
									} else {
										asset.setProperty("preview_url", wmvStreamUrl+flPath.trim()+flNm.trim());
									}
								}

								String ctgrLCd = StringUtils.defaultString(dasSearch.getCtgrLCd(), "100");
								if(ctgrLCd.equals("100")) {
									asset.setProperty("genre_l", "100");
									asset.setProperty("material_type", "Material");
								} else if(ctgrLCd.equals("200")) {
									asset.setProperty("genre_l", "200");
									asset.setProperty("material_type", "PGM");
								}

								//System.out.println("genre_l: "+asset.getProperty("genre_l"));
								if(logger.isDebugEnabled()) {
									//	logger.debug("genre_l: "+asset.getProperty("genre_l"));
								}
								asset.setProperty("genre_m", StringUtils.defaultString(dasSearch.getCtgrMCd(), ""));
								asset.setProperty("genre_s", StringUtils.defaultString(dasSearch.getCtgrSCd(), ""));

								// 이하 조회메타를 추가로 정의

								result.addAsset(asset);
							}
						} else {
							// 검색 결과가 없을때는 null 을 반환하도록 변경(요구사항) - 2012.07.29
							// 검색 결과가 없을 때에는 검색결과 0으로 셋팅후 반환토록 변경 - 2012.10.22
							result.setTotalCount(0);
							return result;
						}
					}else{
						// 검색 결과가 없을때는 null 을 반환하도록 변경(요구사항) - 2012.07.29
						// 검색 결과가 없을 때에는 검색결과 0으로 셋팅후 반환토록 변경 - 2012.10.22
						result.setTotalCount(0);
						return result;
					}
				} else {
					throw new InvalidSessionException("session expired!");
				}
			}
		} catch (RemoteException e) {
			throw new CMSRuntimeException(e);
		} catch (Exception e) {
			logger.error("e.getMessage()" +e.getMessage() ,e );
			throw new CMSRuntimeException(e);
		}
		return result;
	}

	public Asset getAsset(String id) {
		Asset asset = null;
		try {
			logger.debug("getAsset id: "+id);
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl(url);
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			java.util.Map<String, Object> configMap = PropertyLoader.getPropertyHash("config");
			if(StringUtils.isNotBlank(id)) {

				asset = ObjectFactory.createAsset(id);

				Das das = null;    
				if(id.startsWith("M")) { // master_Id
					das = convertorService.unMarshaller(cmsConnector.getBaseInfo(Long.valueOf(id.substring(1))));

					if(das != null && das.getMetaDataInfo() != null) {
						MetaDataInfo metaDataInfo = das.getMetaDataInfo();

						asset.setProperty("title", Utility.unescapeXml(StringUtils.defaultString(metaDataInfo.getPgmNm(), "")));
						asset.setProperty("title_sub", Utility.unescapeXml(StringUtils.defaultString(metaDataInfo.getSubTtl(), "")));
						asset.setProperty("program_name", "");
						//asset.setProperty("program_sequence", metaDataInfo.getEpisNo() == null ? "": String.valueOf(metaDataInfo.getEpisNo()));
						String epis_no = StringUtils.defaultString(String.valueOf(metaDataInfo.getEpisNo()),"");
						if(epis_no.equals("")||epis_no.equals("0")){
							asset.setProperty("program_sequence", "");
							
						}else{
							asset.setProperty("program_sequence", epis_no);
							
						}logger.debug("#################epis_no + "+ epis_no);
						
						asset.setProperty("program_sequence_total", "");
						asset.setProperty("creator", StringUtils.defaultString(metaDataInfo.getProducerNm(), ""));
						asset.setProperty("creator_sub", StringUtils.defaultString(metaDataInfo.getDrtNm(), ""));
						asset.setProperty("publisher", StringUtils.defaultString(metaDataInfo.getCocd(), ""));
						asset.setProperty("publisher_external", StringUtils.defaultString(metaDataInfo.getOrgPrdrNm(), ""));
						asset.setProperty("genre_l", StringUtils.defaultString(metaDataInfo.getCtgrLCd(), ""));
						asset.setProperty("genre_m", StringUtils.defaultString(metaDataInfo.getCtgrMCd(), ""));
						asset.setProperty("genre_s", StringUtils.defaultString(metaDataInfo.getCtgrSCd(), ""));

						String usegrade=StringUtils.defaultString(metaDataInfo.getAnnotClfNm(), "");
						if(usegrade.equals("방송심의제재")){
							usegrade = ("002");										
						} else 	if(usegrade.equals("사용금지")){
							usegrade = ("003");	
						} else if(usegrade.equals("확인후 사용")){
							usegrade = ("004");
						} else if(usegrade.equals("사내심의사항")){
							usegrade = ("005");
						} else if(usegrade.equals("심의제한")){
							usegrade = ("006");
						} else if(usegrade.equals("무제한")){
							usegrade = ("007");
						} else if(!usegrade.equals("")){

						} else {
							usegrade="007";
						}
						asset.setProperty("usegrade", usegrade);

						//20120802 추가

						String brd_dd = StringUtils.defaultString(metaDataInfo.getBrdDd(), "");
						String fm_dt = StringUtils.defaultString(metaDataInfo.getFmDt(), "");
						String ctgr_l_Cd = StringUtils.defaultString(metaDataInfo.getCtgrLCd(), "");
						String reg_dt = StringUtils.defaultString(metaDataInfo.getRegDt(), "");
						String onAirDt = "";
						if(StringUtils.isNotBlank(ctgr_l_Cd)){
							if(ctgr_l_Cd.equals("100")){
								onAirDt=fm_dt;
							}else{
								onAirDt=brd_dd;									
							}
						}else{
							onAirDt=brd_dd;
						}
						logger.debug("master_id: "+metaDataInfo.getMasterId()+", brd_dd:   "+brd_dd);

						String yyyy = onAirDt.substring(0, 4);
						String mm = onAirDt.substring(4, 6);
						String dd = onAirDt.substring(6,8);
						String bd = yyyy+"-"+mm+"-"+dd;


						if(ctgr_l_Cd.equals("100")){
							asset.setProperty("datetime_onair", bd+" 00:00:00");
							asset.setProperty("datetime_shooting", bd+" 00:00:00");
						} else {
							asset.setProperty("datetime_onair", bd+" 00:00:00");
						}

						asset.setProperty("location_shooting", StringUtils.defaultString(metaDataInfo.getCmrPlace(), ""));
						asset.setProperty("keyword", StringUtils.defaultString(metaDataInfo.getKeyWords(), ""));

						asset.setProperty("description", Utility.unescapeXml(StringUtils.defaultString(metaDataInfo.getSpcInfo(), "")));
						asset.setProperty("copyright_type", StringUtils.defaultString(metaDataInfo.getCprtType(), ""));
						asset.setProperty("copyright_owner", StringUtils.defaultString(metaDataInfo.getCprtrNm(), ""));
						asset.setProperty("copyright_desc", StringUtils.defaultString(metaDataInfo.getCprtTypeDsc(), ""));
						asset.setProperty("production_type", StringUtils.defaultString(metaDataInfo.getPrdtInOutsCd(), ""));
						asset.setProperty("usegrade_desc", StringUtils.defaultString(metaDataInfo.getAnnotClfCont(), ""));

						String value = StringUtils.defaultString(metaDataInfo.getViewGrCd());
						
						if(value.equals("001")){
							value="0";
						}else if(value.equals("002")){
							value="7";
						}else if(value.equals("003")){
							value="12";
						}else if(value.equals("004")){
							value="15";
						}else if(value.equals("005")){
							value="19";
						}else if(value.equals("006")){
							value="IMP";
						}else if(value.equals("007")){
							value="EXP";
						}else if(value.equals("008")){
							value="NONE";
						}else{  
							value="NONE";
						}
						logger.debug("#################parents_guidence + "+ value);
						asset.setProperty("parents_guidence", value);
						asset.setProperty("name_host", StringUtils.defaultString(metaDataInfo.getMcNm(), ""));
						asset.setProperty("name_guest", StringUtils.defaultString(metaDataInfo.getCastNm(), ""));
						asset.setProperty("artist", StringUtils.defaultString(metaDataInfo.getArtist(), ""));
						asset.setProperty("country", StringUtils.defaultString(metaDataInfo.getCountryCd(), ""));
						asset.setProperty("music_info", Utility.unescapeXml(StringUtils.defaultString(metaDataInfo.getMusicInfo(), "")));

						asset.setProperty("media_format","XDCAM MXF");
						logger.debug("#################media_format + "+ "XDCAM MXF");

						String value2 =StringUtils.defaultString(metaDataInfo.getMetaVdQlty(), "");
						if(value2.equals("001")){
							value2="1";
						}else if(value2.equals("002")){
							value2="0";
						}else if(value2.equals("003")){
							value2="2";
						}else if(value2.equals("004")){
							value2="";
						}else{
							value2="";
						}
						//logger.debug("#############value2" + value2);
						logger.debug("#################resolution + "+ value2);
						asset.setProperty("resolution", value2);

						asset.setProperty("aspectratio", metaDataInfo.getMetaAspRtoCd());
						asset.setProperty("duration", StringUtils.defaultString(metaDataInfo.getBrdLeng(), ""));
						 String val = StringUtils.defaultString(metaDataInfo.getMetaRecordTypeCd());
						if(val.equals("001")){
							val="M";
						}else if(val.equals("002")){
							val="S";
						}else if(val.equals("003")){
							val="B";
						}else if(val.equals("004")){
							val="F";
						}else if(val.equals("005")){
							val="Y";
						}else if(val.equals("006")){
							val="O";
						}else if(val.equals("007")){
							val="A";
						}
						else {
							val="";
						}
						logger.debug("#################audio_type + "+ val);
						asset.setProperty("audio_type", val);

						//다중 파일경우 그 크기의 합을 합해서 보여준다. 대표영상의 미디어id를 보여준다.//20121119 asura207
						long m2sz =0;
						if(das.getMetaDataInfo().getIngest() != null && !das.getMetaDataInfo().getIngest().getItems().isEmpty()) {
							for(IngestInfo ingest : das.getMetaDataInfo().getIngest().getItems()) {
								logger.debug("#############file_size   =   " + ingest.getM2Sz());
								m2sz=m2sz+ingest.getM2Sz(); 
							}
							asset.setProperty("file_size",  m2sz/8);
						}
						asset.setProperty("media_id", metaDataInfo.getMediaId());
						logger.debug("#############file_size   =   " + m2sz/8);
						logger.debug("#############media_id   =   " + metaDataInfo.getMediaId());

						/*asset.setProperty("file_size",  "");
						asset.setProperty("media_id", "");*/
						asset.setProperty("file_name", "");
						asset.setProperty("asset_type",  "");
						asset.setProperty("thumbnail_url", "");
						asset.setProperty("asset_id", id);
						String reg_yyyy = reg_dt.substring(0, 4);
						String reg_mm = reg_dt.substring(4, 6);
						String reg_dd = reg_dt.substring(6,8);
						String regdt = reg_yyyy+"-"+reg_mm+"-"+reg_dd;
						asset.setProperty("datetime_regist", regdt+" 00:00:00:00");
						asset.setProperty("contents_owner",  StringUtils.defaultString(metaDataInfo.getChennelCd(), ""));
						asset.setProperty("contents_channel",  StringUtils.defaultString(metaDataInfo.getChennelCd(), ""));
						//asset.setProperty("contents_type", "");
						if(StringUtils.defaultString(metaDataInfo.getCtgrLCd(), "").equals("100")) {
							asset.setProperty("genre_l", "100");
							asset.setProperty("material_type", "Material");
						} else if(StringUtils.defaultString(metaDataInfo.getCtgrLCd(), "").equals("200")) {
							asset.setProperty("genre_l", "200");
							asset.setProperty("material_type", "PGM");
						}

						//첨부파일 조회기능 추가
						if(das.getMetaDataInfo().getAttach() != null && !das.getMetaDataInfo().getAttach().getItems().isEmpty()) {
							String fileDownloadUrl = (String)configMap.get("file.download.url");
							for(AttachItem att : das.getMetaDataInfo().getAttach().getItems()) {
								if(logger.isDebugEnabled()) {
									logger.debug("#############fl_path   =   " + att.getFlPath());
									logger.debug("#############filenm   =   " + att.getOrgFileNm());
								}
								if(att.getAttcFileType().equals("010")){//한국어
									asset.setProperty("caption_dir", att.getFlPath());
									asset.setProperty("caption_kr", att.getOrgFileNm());
									asset.setProperty("caption_kr_download_url", fileDownloadUrl+"/"+att.getFlPath()+att.getOrgFileNm());
									logger.debug("#############caption_kr_download_url" + fileDownloadUrl+"/"+att.getFlPath()+att.getOrgFileNm());
								}else if(att.getAttcFileType().equals("011")){//일본어
									asset.setProperty("caption_dir", att.getFlPath());
									asset.setProperty("caption_jp", att.getOrgFileNm());
									asset.setProperty("caption_jp_download_url", fileDownloadUrl+"/"+att.getFlPath()+att.getOrgFileNm());
									logger.debug("#############caption_jp_download_url" + fileDownloadUrl+"/"+att.getFlPath()+att.getOrgFileNm());
								}else if(att.getAttcFileType().equals("012")){//영어
									asset.setProperty("caption_dir", att.getFlPath());
									asset.setProperty("caption_en", att.getOrgFileNm());
									asset.setProperty("caption_en_download_url", fileDownloadUrl+"/"+att.getFlPath()+att.getOrgFileNm());
									logger.debug("#############caption_en_download_url" + fileDownloadUrl+"/"+att.getFlPath()+att.getOrgFileNm());
								}else if(att.getAttcFileType().equals("013")){//중국어
									asset.setProperty("caption_dir", att.getFlPath());
									asset.setProperty("caption_cn", att.getOrgFileNm());
									asset.setProperty("caption_cn_download_url", fileDownloadUrl+"/"+att.getFlPath()+att.getOrgFileNm());
									logger.debug("#############caption_cn_download_url" + fileDownloadUrl+"/"+att.getFlPath()+att.getOrgFileNm());
								}else if(att.getAttcFileType().equals("001")){//대본

									asset.setProperty("synopsis_dir", att.getFlPath());
									asset.setProperty("synopsis_kr", att.getOrgFileNm());
									asset.setProperty("synopsis_download_url", fileDownloadUrl+"/"+att.getFlPath()+att.getOrgFileNm());
									logger.debug("#############synopsis_download_url" + fileDownloadUrl+"/"+att.getFlPath()+att.getOrgFileNm());
								}else if(att.getAttcFileType().equals("014")){//프리뷰

									asset.setProperty("preview_url", "");
									asset.setProperty("preview_dir", att.getFlPath());
									asset.setProperty("preview_kr", att.getOrgFileNm());
									asset.setProperty("preview_download_url", fileDownloadUrl+"/"+att.getFlPath()+"/"+att.getOrgFileNm());
									logger.debug("#############preview_download_url" + fileDownloadUrl+"/"+att.getFlPath()+"/"+att.getOrgFileNm());
								}
							}
						}

						asset.setName(StringUtils.defaultString(metaDataInfo.getTitle(), ""));

						logger.debug("######################3asset.getName     " + asset.getName());

					}
				} else if(id.startsWith("C")) { // ct_id
					das = convertorService.unMarshaller(cmsConnector.getSceanInfoForIfCms(Long.valueOf(id.substring(1))));
					Corner corner = das.getCorner();
					if(logger.isDebugEnabled()) {
						logger.debug("media_id   " + corner.getMediaId().toUpperCase());
					}
					asset.setName(corner.getTitle());
					logger.debug("######################4asset.getName     " + corner.getTitle());
					asset.setProperty("media_id", corner.getMediaId().toUpperCase());
					asset.setProperty("duration", corner.getCtLeng());
					asset.setProperty("media_format","XDCAM MXF");
					logger.debug("#################media_format + "+ "XDCAM MXF");

				}


			}
		} catch (RemoteException e) {
			throw new CMSRuntimeException(e);
		} catch (Exception e) {
			throw new CMSRuntimeException(e);
		}
		return asset;
	}

	/**
	 * master_id를 인자로 받아서 영상ID(CTI_ID)를 얻어온다.
	 */
	public List<Asset> getSubAssets(String id) {

		logger.debug("getSubAssets master_id: "+id);

		List<Asset> list = new ArrayList<Asset>();
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl(url);
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			Das das = convertorService.unMarshaller(cmsConnector.getGroupForMaster(Long.valueOf(id.substring(1))));
			logger.debug("RESPONS XML " +cmsConnector.getGroupForMaster(Long.valueOf(id.substring(1))) );


			/*
			 * 스트리밍 URL 추가
			 * config.properties에 스트리밍 url이 설정되어 있고, wmv 스트리밍 url의 ip 정보는 변경이 필요.
			 * 
			 */
			java.util.Map<String, Object> configMap = PropertyLoader.getPropertyHash("config");
			String mp4StreamUrl = (String)configMap.get("mp4.streaming.url");
			String netmp4StreamUrl = (String)configMap.get("netmp4.streaming.url");
			String wmvStreamUrl = (String)configMap.get("wmv.streaming.url");
			if(das.getCorner() != null) {
				if(StringUtils.isNotBlank(das.getCorner().getGroup())) {

					// master_id로 관련영상 조회 후 영상ID를 append
					logger.debug("START REL " +das.getRelation() );
					if(das.getRelation() != null && StringUtils.isNotBlank(das.getRelation().getCtId())) {
						das.getCorner().setGroup(das.getCorner().getGroup()+","+das.getRelation().getCtId());
						das.getCorner().setCtNm(das.getCorner().getCtNm()+","+das.getRelation().getCtNm());
					}
					logger.debug("END REL ");
					String[] ctIds = null;
					String[] ctNms = null;
					if(das.getCorner().getGroup().indexOf(",") > -1) {
						ctIds = das.getCorner().getGroup().split("\\,");
					} else {
						ctIds = new String[1];
						ctIds[0] = das.getCorner().getGroup();
					}

					if(StringUtils.isNotBlank(das.getCorner().getCtNm())) {
						if(das.getCorner().getCtNm().indexOf(",") > -1) {
							logger.debug("ctnms : "+das.getCorner().getCtNm());
							ctNms = das.getCorner().getCtNm().split("\\,");
						} else {
							ctNms = new String[1];
							ctNms[0] = das.getCorner().getCtNm();
						}
					}

					if(ctIds != null && ctNms != null) {
						for(int i =0; i<ctIds.length;i++) {
							String ctId = ctIds[i];
							String ctNm = ctNms[i];
							Asset asset = ObjectFactory.createAsset(ctId);
							asset.setName(ctNm);
							asset.setIdentifier("C"+ctId);
							//파일패스를 가지고 오기위한 조회
							if(logger.isDebugEnabled()) {
								logger.debug("sub ct_Id: "+ctId);
							}
							String xml = cmsConnector.getSceanInfoForIfCms(Long.valueOf(ctId));
							Das das2 = convertorService.unMarshaller(xml.toLowerCase());
							String flPath = StringUtils.defaultString(das2.getCorner().getFlPath(), "");
							String flNm = StringUtils.defaultString(das2.getCorner().getWrkFileNm(), "");

							String mediaId = StringUtils.defaultString(das2.getCorner().getMediaId(), "");
							asset.setProperty("media_id", mediaId.toUpperCase());
							String duration = StringUtils.defaultString(das2.getCorner().getCtLeng(), "");
							asset.setProperty("duration", duration);

							if(logger.isDebugEnabled()) {
								logger.debug("media_id: "+mediaId);
								logger.debug("duration: "+duration);
							}

							asset.setProperty("title", das2.getCorner().getTitle());

							//원본경로 비교용
							String orgflPath =StringUtils.defaultString(das2.getCorner().getFlPath(), "");
							if(StringUtils.isNotBlank(flPath) && StringUtils.isNotBlank(flNm)) {

								flPath = flPath.trim();
								flNm = flNm.trim();

								if(flPath.indexOf("\\") > -1) {
									flPath = flPath.replace("\\", "/");
								}

								if(!flPath.startsWith("/")) {
									flPath=flPath.replaceAll("net_mp4", "");
									flPath=flPath.replaceAll("mp4", "");

									flPath = "/"+flPath;
									flPath=flPath.replaceAll("//", "/");

								}else{
									flPath=flPath.replaceAll("net_mp4", "");
									flPath=flPath.replaceAll("mp4", "");
									flPath=flPath.replaceAll("//", "/");
								}

								if(!flPath.endsWith("/")) {
									flPath =flPath+ "/";
								}

								if(orgflPath.matches(".*net_mp4.*")){
									asset.setProperty("preview_url", netmp4StreamUrl+flPath+flNm);
									logger.debug("mp4 play path   : "+netmp4StreamUrl+flPath+flNm);
								}else{
									asset.setProperty("preview_url", mp4StreamUrl+flPath+flNm);
									logger.debug("mp4 play path   : "+mp4StreamUrl+flPath+flNm);	
								}
								asset.setProperty("description","");
								asset.setProperty("media_format","XDCAM MXF");
								logger.debug("#################media_format + "+ "XDCAM MXF");

							}

							list.add(asset);
						}
					}

				}
			}
			logger.debug("#############################################"+list.size());
		} catch (RemoteException e) {
			throw new CMSRuntimeException(e);
		} catch (Exception e) {
			throw new CMSRuntimeException(e);
		}
		return list;
	}

	/**
	 * 영상ID별 샷정보
	 */
	public Storyboard getStoryboard(String id) {
		Storyboard storyboard = new Storyboard();
		try {
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl(url);
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			String xml = cmsConnector.getSceanInfoForIfCms(Long.valueOf(id.substring(1)));
			if(logger.isDebugEnabled()) {
				logger.debug("getStoryboard ct_id: "+id.substring(1));
				logger.debug("return xml: "+xml);
			}
			Das das = convertorService.unMarshaller(xml);

			java.util.Map<String, Object> configMap = PropertyLoader.getPropertyHash("config");

			/*
			 * 스토리보드에서 사용할 이미지 압축 파일을 지정된 경로에 다운로드받아 압축을 푼다.
			 * 영상별 샷 이미지 경로는 존재하지 않을 수 있다. (예: TC 오류)
			 */
			String kfrmPath = das.getCorner().getKfrmPath();
			if(logger.isDebugEnabled()) {
				logger.debug("getStoryboard kfrmPath: "+kfrmPath);
				logger.debug("getStoryboard kfrmSize: "+das.getCorner().getKfrmSize());
			}

			if(StringUtils.isNotBlank(kfrmPath)) {

				if(!kfrmPath.startsWith("/")) kfrmPath = "/"+kfrmPath;
				if(!kfrmPath.endsWith("/")) kfrmPath = kfrmPath+"/";
				kfrmPath = kfrmPath.replace("kfrm", "KFRM");

				/*
				 * 2012.12.18 (Kang M.S)
				 * mer 다운로드 방식에서 URLStream을 이용하여 txt 정보를 읽어 URL Link를 반환하는 방식으로 변경
				 * txt 안에는 keyframe index 정보가 ',' 구분자로 모두 존재하고 있어야 하며 CT_ID.txt로 존재해야한다. 
				 */
				BufferedReader in = null;
				try {
					logger.debug("txt down url: "+(String)configMap.get("file.download.url")+kfrmPath+id.substring(1)+".txt");
					URL txtURL = new URL((String)configMap.get("file.download.url")+kfrmPath+id.substring(1)+".txt");
					in = new BufferedReader(new InputStreamReader(txtURL.openStream()));
				} catch (Exception e) {
					logger.error("url read error : "+(String)configMap.get("file.download.url")+kfrmPath+id.substring(1)+".txt");
					return null;
				}

				try {

					List<Long> frameList = new ArrayList<Long>();

					String readLine = "";
					while ((readLine = in.readLine()) != null) {
						if(logger.isDebugEnabled()) {
							logger.debug("readLine : "+readLine);
						}
						if(readLine.indexOf(",") > -1) {
							String[] indexes = readLine.split("\\,");
							for(String index : indexes) {
								if(StringUtils.isNotBlank(index) && Utility.isNumeric(index))
									frameList.add(Long.valueOf(index));
							}
						} else {
							if(StringUtils.isNotBlank(readLine) && Utility.isNumeric(readLine))
								frameList.add(Long.valueOf(readLine));
						}
					}

					if(!frameList.isEmpty()) {
						String imageUrl = (String)configMap.get("shot.image.url");

						Segment segment = null;
						for(CornerItem item : das.getCorner().getItems()) {
							segment = new Segment();

							Long som = Utility.changeTimeCode(item.getSom());
							Long eom = Utility.changeTimeCode(item.getEom());
							String cn_info = item.getCnNm() + "\n"+item.getCnInfo();
							segment.setDescription(cn_info);
							Map<String, String> annotList = new HashMap<String, String>();
							Annot annot = item.getAnnot();
							if(annot != null && !annot.getItems().isEmpty()) {
								List<AnnotInfo> annotInfos = annot.getItems();
								for(int i=0; i<annotInfos.size(); i++) {
									long aSom = Utility.changeTimeCode(annotInfos.get(i).getSom());
									long aEom = Utility.changeTimeCode(annotInfos.get(i).getEom());
									annotList.put(aSom+","+aEom, annotInfos.get(i).getAnnotClfCd());
									if(logger.isDebugEnabled()) {
										logger.debug(aSom+","+aEom+"===>"+annotInfos.get(i).getAnnotClfCd());
									}
								}
							}

							Shot shot = null;

							// URL Link 생성
							for(Long shotnum : frameList) {
								// 코너(segment)의 som, eom 구간에 포함되는 이미지만을 해당 segment에 추가한다. 
								if(som <= shotnum && shotnum <= eom) {

									if(shot != null) {
										shot.setEndTimecode(Utility.changeDuration(shotnum-1));
										segment.addShot(shot);
									}

									/*
									 * 영상에대한 카탈로그 이미지 압축파일을 다운로드 받아서 CT_ID로 폴더를 생성한 후 안에 압축을 푼다.
									 * txt파일을 읽어서 idx 별 timecode의 som, eom을 설정한 후 Segment에 Shot을 추가한다.
									 */
									shot = new Shot();

									// 대표이미지 설정
									if(item.getRpimgKfrmSeq() == shotnum.intValue()) {
										segment.setImageURL(imageUrl+kfrmPath+shotnum+".jpg");
									}
									shot.setImageURL(imageUrl+kfrmPath+shotnum+".jpg");
									shot.setStartTimecode(Utility.changeDuration(shotnum));

									/*
									 * 코너별 사용등급제한에대한 구간은 여러개가 존재할 수 있고, 샷 이미지는 어느 제한 구간에
									 * 속하는지 확인이 필요하므로 사용제한 구간별 체크를 해야한다.
									 */
									if(!annotList.isEmpty()) {
										Iterator<String> it = annotList.keySet().iterator();
										while(it.hasNext()) {
											String keys = it.next();
											String[] key = keys.split("\\,");
											Long sSom = Long.valueOf(key[0]);
											Long sEom = Long.valueOf(key[1]);
											if(sSom <= shotnum && shotnum <= sEom) {
												if(logger.isDebugEnabled()) {
													logger.debug("shotnum : "+shotnum+", grade: "+annotList.get(keys));
												}
												shot.setUseGrade(annotList.get(keys));
												break;
											}
										}
									}
								}
							}

							if(shot != null) {
								shot.setEndTimecode(item.getEom());
								segment.addShot(shot);
							}

							storyboard.addSegment(segment);
						}
					}
				} catch (Exception e) {
					logger.error("storyboard pasing error", e);
					throw new CMSRuntimeException(e);
				} finally {
					if(in != null) {
						in.close();
					}
				}


				/*
				File f = new File((String)configMap.get("file.download.tmp")+kfrmPath+id.substring(1)+".mer");
				if(f.exists()) {

				 * 스토리보드 파일의 사이즈를 받아서 기존에 다운로드 받은 파일이 있다면 기존 파일과 사이즈를 비교.
				 * 만약, 다르다면 기존 스토리보드 파일을 삭제하고 이미지 파일도 삭제하여 신규로 다운로드하여 압축해제

					if(das.getCorner().getKfrmSize() != f.length()) {
						try {
							FileUtils.forceDelete(f);
							if(logger.isDebugEnabled()) {
								logger.debug("kfrmPath mer forceDelete: "+f.getAbsolutePath());
							}
							f = new File((String)configMap.get("file.download.tmp")+kfrmPath);
							if(f.exists()) {
								FileUtils.forceDelete(f);
								if(logger.isDebugEnabled()) {
									logger.debug("kfrmPath folder forceDelete: "+f.getAbsolutePath());
								}
							}

							try {
								Utility.download((String)configMap.get("file.download.url"), kfrmPath+id.substring(1)+".mer");
							} catch (Exception e) {
								logger.error("storyboard download error", e);
								return null;
							}
							if(logger.isDebugEnabled()) {
								logger.debug("kfrmPath mer downloaded1: "+(String)configMap.get("file.download.tmp")+kfrmPath+id.substring(1)+".mer");
							}
						} catch (Exception e) {
							logger.error("mer or folder delete error", e);
						}

					}
				} else {
					try {
						Utility.download((String)configMap.get("file.download.url"), kfrmPath+id.substring(1)+".mer");
					} catch (Exception e) {
						logger.error("storyboard download error", e);
						return null;
					}
					if(logger.isDebugEnabled()) {
						logger.debug("kfrmPath mer downloaded2: "+(String)configMap.get("file.download.tmp")+kfrmPath+id.substring(1)+".mer");
					}
				}
				f = null;

				// 스토리보드 내용이 존재할경우 실행
				f = new File((String)configMap.get("file.download.tmp")+kfrmPath+id.substring(1)+".mer");
				java.io.FileInputStream fis = null;
				if(f.exists()) {
					try {

				 * 다운로드 받은 위치에서 압축 파일을 읽어 지정된 디렉토리에 이미지를 생성한다.

						fis = new java.io.FileInputStream((String)configMap.get("file.download.tmp")+kfrmPath+id.substring(1)+".mer");
						byte[] b = new byte[4];

						// version
						fis.read(b);

						// image count
						fis.read(b);
						int size = BitConverter.toInt(b, -1);

						List<Long> shotList = new ArrayList<Long>();

						byte[] image = null;
						for(int i=0; i<size; i++) {
							// frame num
							fis.read(b);
							long num = BitConverter.toInt(b, -1);

							// image size
							fis.read(b);
							int leng = BitConverter.toInt(b, -1);
							image = new byte[leng];
							fis.read(image);


				 * 샷 이미지별 byte로 이미지 파일 생성

							Utility.byte2Image((String)configMap.get("file.download.tmp")+kfrmPath+num+".jpg", image);

							shotList.add(num);
						}

						String imageUrl = (String)configMap.get("shot.image.url");

						Segment segment = null;
						for(CornerItem item : das.getCorner().getItems()) {
							segment = new Segment();

							Long som = Utility.changeTimeCode(item.getSom());
							Long eom = Utility.changeTimeCode(item.getEom());
							String cn_info = item.getCnNm() + "\n"+item.getCnInfo();
							segment.setDescription(cn_info);
							Map<String, String> annotList = new HashMap<String, String>();
							Annot annot = item.getAnnot();
							if(annot != null && !annot.getItems().isEmpty()) {
								List<AnnotInfo> annotInfos = annot.getItems();
								for(int i=0; i<annotInfos.size(); i++) {
									long aSom = Utility.changeTimeCode(annotInfos.get(i).getSom());
									long aEom = Utility.changeTimeCode(annotInfos.get(i).getEom());
									annotList.put(aSom+","+aEom, annotInfos.get(i).getAnnotClfCd());
									if(logger.isDebugEnabled()) {
										logger.debug(aSom+","+aEom+"===>"+annotInfos.get(i).getAnnotClfCd());
									}
								}
							}
							Shot shot = null;
							for(Long shotnum : shotList) {
								// 코너(segment)의 som, eom 구간에 포함되는 이미지만을 해당 segment에 추가한다. 
								if(som <= shotnum && shotnum <= eom) {

									if(shot != null) {
										shot.setEndTimecode(Utility.changeDuration(shotnum-1));
										segment.addShot(shot);
									}


				 * 영상에대한 카탈로그 이미지 압축파일을 다운로드 받아서 CT_ID로 폴더를 생성한 후 안에 압축을 푼다.
				 * txt파일을 읽어서 idx 별 timecode의 som, eom을 설정한 후 Segment에 Shot을 추가한다.

									shot = new Shot();

									// 대표이미지 설정
									if(item.getRpimgKfrmSeq() == shotnum.intValue()) {
										segment.setImageURL(imageUrl+kfrmPath+shotnum+".jpg");
									}
									shot.setImageURL(imageUrl+kfrmPath+shotnum+".jpg");
									shot.setStartTimecode(Utility.changeDuration(shotnum));


				 * 코너별 사용등급제한에대한 구간은 여러개가 존재할 수 있고, 샷 이미지는 어느 제한 구간에
				 * 속하는지 확인이 필요하므로 사용제한 구간별 체크를 해야한다.

									if(!annotList.isEmpty()) {
										Iterator<String> it = annotList.keySet().iterator();
										while(it.hasNext()) {
											String keys = it.next();
											String[] key = keys.split("\\,");
											Long sSom = Long.valueOf(key[0]);
											Long sEom = Long.valueOf(key[1]);
											if(sSom <= shotnum && shotnum <= sEom) {
												if(logger.isDebugEnabled()) {
													logger.debug("shotnum : "+shotnum+", grade: "+annotList.get(keys));
												}
												shot.setUseGrade(annotList.get(keys));
												break;
											}
										}
									}
								}
							}

							if(shot != null) {
								shot.setEndTimecode(item.getEom());
								segment.addShot(shot);
							}

							storyboard.addSegment(segment);
						}
					} catch (Exception e) {
						logger.error("storyboard pasing error", e);
						throw new CMSRuntimeException(e);
					} finally {
						if(fis != null) {
							fis.close();
							fis = null;
						}
					}

				}
				if(f != null) {
					f = null;
				}
				 */
			}
		} catch (RemoteException e) {
			throw new CMSRuntimeException(e);
		} catch (Exception e) {
			throw new CMSRuntimeException(e);
		}
		return storyboard;
	}

	/**
	 * 메타데이타 편집
	 */
	public WorkflowManager getWorkflowManager() {
		return new D2NetWorkflowManager(this);
	}

	public void logout() {
		token = null;
		id = null;
		name = null;
	}

	public void updateAsset(String id, List<Property> properties) {
		throw new OperationNotSupportedException();
	}

	public void updateAsset(String id, Properties properties) {
		throw new OperationNotSupportedException();

	}

	public boolean hasPermission(String id, String action) {
		return true;
	}

	public boolean isRelatedVideo(String masterId, String ctId) {
		boolean related = false;
		try {
			if(logger.isInfoEnabled()) {
				logger.info("##################start isRelatedVideo++################################"+ctId);
				logger.info("masterId: "+masterId+", ctId: "+ctId);
			}
			DasCmsConnector cmsConnector = new DasCmsConnectorImpl(url);
			
			if(masterId.startsWith("M")) {
				masterId = masterId.substring(1);
			}
			if(ctId.startsWith("C")) {
				ctId = ctId.substring(1);
			}
			
			String relatedYn = cmsConnector.isVideoRelatedYN(Long.valueOf(masterId), Long.valueOf(ctId));
			if(StringUtils.defaultIfEmpty(relatedYn, "N").equals("Y")) related = true;
			else related = false;
			logger.info("================   isRelatedVideo related             " + related);
		} catch (Exception e) {
			logger.error("isRelatedVideo error", e);
			throw new CMSRuntimeException(e);
		}
		return related;
	}

}
