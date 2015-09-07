package com.app.das.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.IfCmsArchiveDO;
import com.app.das.business.transfer.PdsArchiveDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.CommonUtl;
import com.app.das.util.XmlUtil;
import com.app.das.util.jutil;

/**
 *  pds 아카이브 정보 관련 XML파서
 * @author asura207
 *
 */
public class IfCmsArchiveDOXML extends DOXml{

	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	XmlUtil xmlutil = new XmlUtil();
	/**
	 * 해더1
	 */
	private String generator = "generator";	
	/**
	 * 버전
	 */
	private String generator_version = "generator_version";	
	/**
	 * 일반정보 헤더
	 */
	private String general_infomation = "general_infomation";	
	/**
	 * 트랜젝션ID
	 */
	private String transaction_id = "transaction_id";	
	/**
	 * 에셋ID
	 */
	private String asset_id = "asset_id";	
	/**
	 * 그룹소속 여부, 0:그룹 없음(소재인 경우), 1:그룹 있음(프로그램인 경우)
	 */
	private String belong_to_group = "belong_to_group";	
	/**
	 * group_id
	 */
	private String group_id = "group_id";	
	/**
	 * 그룹에 포함된 총 에셋 수
	 */
	private String total_number_of_asset_in_group = "total_number_of_asset_in_group";	
	/**
	 * Transaction 종류, transfer(전송)/archive(아카이브)
	 */
	private String transaction_type = "transaction_type";	
	/**
	 * 에셋 종류, video/audio/cg
	 */
	private String asset_type = "asset_type";	
	/**
	 * 타겟 폴더 헤더
	 */
	private String target_folder = "target_folder";	
	/**
	 * 타겟 폴더(종속)
	 */
	private String storage_id = "storage_id";	
	/**
	 * 타겟 폴더(종속)
	 */
	private String folder_id = "folder_id";	
	/**
	 * 파일 헤더
	 */
	private String files = "files";	
	/**
	 * 파일 
	 */
	private String file = "file";	
	/**
	 * source 파일원본명
	 */
	private String source = "source";	
	/**
	 * 파일 size
	 */
	private String size = "size";	
	/**
	 * 파일유형
	 */
	private String file_type = "file_type";	
	/**
	 * 메타데이터 헤더
	 */
	private String metadata = "metadata";	
	/**
	 * 방송일시
	 */
	private String datetime_onair = "datetime_onair";	
	/**
	 * 촬영일시
	 */
	private String datetime_shooting = "datetime_shooting";	
	/**
	 * 제목
	 */
	private String title = "title";	
	/**
	 * 부제
	 */
	private String title_sub = "title_sub";	
	/**
	 * 회차명
	 */
	private String program_sequence = "program_sequence";	
	/**
	 * 대분류
	 */
	private String genre_l = "genre_l";	
	/**
	 * 중분류
	 */
	private String genre_m = "genre_m";	
	/**
	 * 소분류
	 */
	private String genre_s = "genre_s";	
	/**
	 * 시청등급, 0:모든연령시청가, 7:7세, 12:12세, 15:15세, 19:19세, IMP:방송불가, EXP:고지예외, NONE:없음
	 */
	private String parents_guidence = "parents_guidence";	
	/**
	 * 시청률
	 */
	private String viewing_rate = "viewing_rate";	
	/**
	 * 저작권형태, 001:일체소유, 002:일부소유, 003:저작권없음, 004:미확인
	 */
	private String copyright_type = "copyright_type";	
	/**
	 * 사용등급, 007:무제한, 004:확인후사용, 002:방송심의제재, 003:사용금지, 005:사내심의사항, 006:심의제한
	 */
	private String usegrade = "usegrade";	
	/**
	 * 사용등급설명
	 */
	private String usegrade_desc = "usegrade_desc";	
	/**
	 * 사용범위
	 */
	private String user_range = "user_range";	
	/**
	 * 연출
	 */
	private String creator_sub = "creator_sub";	
	/**
	 * 작가
	 */
	private String writer_name = "writer_name";	
	/**
	 * 프로듀서명
	 */
	private String creator = "creator";	
	/**
	 * 촬영감독
	 */
	private String director_shooting = "director_shooting";	
	/**
	 * 외부제작사
	 */
	private String publisher_external = "publisher_external";	
	/**
	 * 제작구분, 001:본사제작, 002:전체외주, 003:부분외주, 004:구매프로(영화,만화 등), 005:없음, 006:네트워크
	 */
	private String production_type = "production_type";	
	/**
	 * 촬영장소
	 */
	private String location_shooting = "location_shooting";	
	/**
	 * 키워드
	 */
	private String keyword = "keyword";	
	/**
	 * 특이사항
	 */
	private String special_info = "special_info";	
	/**
	 * 콘텐츠명
	 */
	private String contents_name = "contents_name";	
	/**
	 * 아티스트
	 */
	private String artist = "artist";	
	/**
	 * 국가구분, 001:한국어, 002:영어권, 003:유럽권, 004:제3세계권, 005:일어권, 006:중국어권, 007:기타
	 */
	private String country = "country";	
	/**
	 * 보존/보관 기간, d1:1일, d2:2일, d3:3일, d4:4일, d5:5일, w1:1주, w2:2주, w3:3주, w4:4주, m1:한달, m6: 6개월
    y1:1년, y2:2년, y3:3년, 5:5년, y10:10년, y20:20년, p:permanent
	 */
	private String retention_period = "retention_period";	
	/**
	 * 화면설명
	 */
	private String contents_desc = "contents_desc";	
	/**
	 * 미디어ID
	 */
	private String media_id = "media_id";	
	/**
	 * 프로그램ID
	 */
	private String program_id = "program_id";	
	/**
	 * 화면비율, 002:4:3, 001:16:9, 003:16:9SP, 004:14:9
	 */
	private String aspectratio = "aspectratio";	
	/**
	 * 화질, 0:SD, 1:HD, 2:SD(HD), 3:4K, 4:3D
	 */
	private String resolution = "resolution";	
	/**
	 * 아카이브 경로
	 */
	private String arch_path = "arch_path";	
	/**
	 * 회사코드=발행처=콘텐츠 소속사 company_cd=publisher=contents_owner, A:SBS 아트텍, C:SBS 콘텐츠 허브, E:SBS CNBC, G:SBS 골프, H:SBS 미디어 홀딩스, L:SBS 인터네셔널, N:SBS 뉴스텍, 
      Q:SBS 스포츠, S:SBS, U:SBS 플러스, Z:SBS E플러스, D:SBS 상조, R:SBS 미디어렙, T:SBS MTV, K:SBS 니켈로디언, V:SBS 바이아컴
	 */
	private String publisher = "publisher";	
	/**
	 * 아카이브 전용, 채널코드, A:SBS, P:SBS Plus, E:SBS E!, M:SBS MTV, N:NICK, S:SBS ESPN, G:SBS Golp, C:SBS CNBC
	 */
	private String channel_cd = "channel_cd";	
	/**
	 * 녹음방식=오디오 송출형태, M:모노, S:스테레오, B:음성다중, F:5.1CH, Y:화면해설, O:8채널, A:4채널
	 */
	private String audio_type = "audio_type";	
	/**
	 * 방송길이, HH:MM:SS:FF
	 */
	private String brd_leng = "duration";	
	/**
	 * 파일명
	 */
	private String file_name = "file_name";	
	/**
	 * 콘텐츠유형  001:전타이틀, 002:후타이틀, 003:본방송, 004:CM, 005:타이틀, 006:ID, 007:MovingCM, 009:이어서, 010:PR
	 */
	private String contents_type = "contents_type";	

	/**
	 * 파일크기
	 */
	private String file_size = "file_size";	
	/**
	 * 요청일시
	 */
	private String datatime_request = "datatime_request";	
	/**
	 * 요청자ID
	 */
	private String request_id = "request_id";	

	/**
	 * som
	 */
	private String som = "som";	
	/**
	 * eom
	 */
	private String eom = "eom";	
	/**
	 * 수평 해상도
	 */
	private String vd_hresol = "vd_hresol";	
	/**
	 * 수직 해상도
	 */
	private String vd_vresol = "vd_vresol";	
	/**
	 * 자막파일(한국어) 파일명
	 */
	private String caption_kr = "caption_kr";	
	/**
	 * 자막파일(일본어) 파일명
	 */
	private String caption_jp = "caption_jp";	
	/**
	 * 자막파일(영어) 파일명
	 */
	private String caption_en = "caption_en";	
	/**
	 * 자막파일(중국어) 파일명
	 */
	private String caption_cn = "caption_cn";	
	/**
	 * Preview Note 파일명
	 */
	private String preview_kr = "preview_kr";	
	/**
	 * type(파일종류), isfolder(폴더여부) attribute, source(소스파일명) attribute, 타겟파일명 element value
      type 코드값 : 001(미디어파일), 002(프리뷰노트), 003(한국어자막), 004(일본어자막), 005(영어자막), 006(중국어자막)
	 */
	private String type = "type";	

	/**
	 * 에피소드 번호
	 */
	private String episode_no = "episode_no";	
	/**
	 * Drop frame 여부, Y/N
	 */
	private String drp_frm_yn = "drp_frm_yn";	
	/**
	 * 스토리지경로
	 */
	private String storage_path = "storage_path";	
	/**
	 * 오디오 여부
	 */
	private String audio_yn = "audio_yn";	
	/**
	 * 비트레이트
	 */
	private String bit_rt = "bit_rt";	
	/**
	 * 초당 프래임수
	 */
	private String frm_per_sec = "frame_per_second";	
	/**
	 * 오디오 샘플링레이트
	 */
	private String aud_samp_frq = "aud_samp_frq";	

	/**
	 * 저작권설명
	 */
	private String copyright_desc = "copyright_desc";	

	/**
	 * 저작권자
	 */
	private String copyright_owner = "copyright_owner";	

	/**
	 * 콘텐츠구분>콘텐츠제작구분으로 변경, 001:원본, 010:개편본, 011:종편본, 005:Clean, 006:방송본
	 */
	private String contents_class = "contents_class";	

	/**
	 * 콘텐츠 소속 채널, A:SBS, P:SBS Plus, E:SBS E!, M:SBS MTV, N:NICK, S:SBS ESPN, G:SBS Golf, C:SBS CNBC
	 */
	private String contents_channel = "contents_channel";	
	/**
	 * 소재 구분, PGM:프로그램, Material:소재-
	 */
	private String material_type = "material_type";	

	/**
	 * 프리뷰노트경로
	 */
	private String preview_path = "preview_path";	

	/**
	 * 자막경로
	 */
	private String caption_path = "caption_path";	

	private static Logger logger = Logger.getLogger(IfCmsArchiveDOXML.class);	


	public Object setDO(String _xml) {
		IfCmsArchiveDO item= new IfCmsArchiveDO();
		setDO(item);

		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;



			if(_nodeName.equals(generator)) {
				IfCmsArchiveDO item1= new IfCmsArchiveDO();
				item1 =(IfCmsArchiveDO) setData((Element)_node);
				item.setVersion(item1.getVersion());
			}else if(_nodeName.equals(general_infomation)){
				IfCmsArchiveDO item2= new IfCmsArchiveDO();
				item2 =(IfCmsArchiveDO) setData2((Element)_node);
				item.setTransaction_id(item2.getTransaction_id());
				item.setAsset_id(item2.getAsset_id());
				item.setBelong_to_group(item.getBelong_to_group());
				item.setGroup_id(item2.getGroup_id());
			}else if(_nodeName.equals(metadata)){
				IfCmsArchiveDO item3= new IfCmsArchiveDO();
				item3 =(IfCmsArchiveDO) setData3((Element)_node);
				item.setBrd_dd(item3.getBrd_dd());
				item.setFm_dt(item3.getFm_dt());
				item.setTitle(item3.getTitle());
				item.setSub_ttl(item3.getSub_ttl());
				item.setEpis_no(item3.getEpis_no());
				item.setCtgr_l_cd(item3.getCtgr_l_cd());
				item.setCtgr_m_cd(item3.getCtgr_m_cd());
				item.setCtgr_s_cd(item3.getCtgr_s_cd());
				item.setView_gr_cd(item3.getView_gr_cd());
				item.setView_rate(item3.getView_rate());
				item.setCprt_type(item3.getCprt_type());
				item.setLimited_use(item3.getLimited_use());
				item.setLimited_use_cont(item3.getLimited_use_cont());
				item.setUser_range(item3.getUser_range());
				item.setCreator_sub(item3.getCreator_sub());
				item.setWriter_name(item3.getWriter_name());
				item.setCreator(item3.getCreator());
				item.setDirector_shooting(item3.getDirector_shooting());
				item.setPublisher_external(item3.getPublisher_external());
				item.setProduction_type(item3.getProduction_type());
				item.setLocation_shooting(item3.getLocation_shooting());
				item.setKeyword(item3.getKeyword());
				item.setSpecial_info(item3.getSpecial_info());
				item.setContents_name(item3.getContents_name());
				item.setArtist(item3.getArtist());
				item.setCountry(item3.getCountry());
				item.setRetention_period(item3.getRetention_period());
				item.setContents_desc(item3.getContents_desc());
				item.setMedia_id(item3.getMedia_id());
				item.setProgram_id(item3.getProgram_id());
				item.setAspectratio(item3.getAspectratio());
				item.setResolution(item3.getResolution());
				item.setArch_path(item3.getArch_path());
				item.setPublisher(item3.getPublisher());
				item.setAudio_type(item3.getAudio_type());
				item.setBrd_leng(item3.getBrd_leng());
				item.setFile_name(item3.getFile_name());
				item.setContents_type(item3.getContents_type());
				item.setFile_size(item3.getFile_size());
				item.setRequest_id(item3.getRequest_id());
				item.setSom(item3.getSom() );
				item.setEom(item3.getEom());
				item.setVd_hresol(item3.getVd_hresol());
				item.setVd_vresol(item3.getVd_vresol());
				item.setCaption_kr(item3.getCaption_kr());
				item.setCaption_jp(item3.getCaption_jp());
				item.setCaption_en(item3.getCaption_en());
				item.setCaption_cn(item3.getCaption_cn());
				item.setPreview_kr(item3.getPreview_kr());
				item.setEpisode_no(item3.getEpisode_no());
				item.setDrp_frm_yn(item3.getDrp_frm_yn());
				item.setAudio_yn(item3.getAudio_yn());
				item.setBit_rt(item3.getBit_rt());
				item.setFrm_per_sec(item3.getFrm_per_sec());
				item.setAud_samp_frq(item3.getAud_samp_frq());
				item.setKr_size(item3.getKr_size());
				item.setCn_size(item3.getCn_size());
				item.setJp_size(item3.getJp_size());
				item.setEn_size(item3.getEn_size());
				item.setMxf_size(item3.getMxf_size());
				item.setSource(item3.getSource());
				item.setType(item3.getType());
				item.setCopyright_desc(item3.getCopyright_desc());
				item.setCopyright_owner(item3.getCopyright_owner());
				item.setContents_class(item3.getContents_class());
			}


		}
		return getDO();
	}


	//버전체크
	public Object setData(Element pElement) {

		IfCmsArchiveDO infoDO = (IfCmsArchiveDO)getDO();


		String file_nms="";
		String preview_subj="";
		String preview_cont="";
		String total ="";
		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(generator_version)){
				infoDO.setVersion(_nodeValue);

			}
		}

		return infoDO;

	}	




	//파일정보
	public Object setData2(Element pElement) {

		IfCmsArchiveDO infoDO = (IfCmsArchiveDO)getDO();


		String file_nms="";
		String preview_subj="";
		String preview_cont="";
		String total ="";
		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = getNodeValue(_node);

			if(_nodeName.equals(transaction_id)){
				infoDO.setTransaction_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(asset_id)){
				infoDO.setAsset_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(belong_to_group)){
				infoDO.setBelong_to_group(_nodeValue);
			}else if(_nodeName.equals(group_id)){
				infoDO.setGroup_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(files)){
				NodeList underList = _node.getChildNodes();
				int under_length = underList.getLength();
				for(int k = 0; k < under_length; k++) {
					Node _node2 = underList.item(k);
					String _undername = _node2.getNodeName() ;
					String _underValue2 = getNodeValue(_node2);
					NamedNodeMap _underattbute = _node2.getAttributes();

					if(_undername.equals(file)) {
						String file_nm="";
						String file_size="";
						for(int w = 0; w<_underattbute.getLength();w++){
							Node attr = _underattbute.item(w);
							String nodeName = attr.getNodeName() ;
							String att= CommonUtl.transXMLText(attr.getNodeValue());
							if(nodeName.equals(type)){
								infoDO.setType(att);

							}else if(nodeName.equals(size)){
								file_size=att;
							}else if(nodeName.equals(source)){
								file_nm=att;
							}




							if(!infoDO.getType().equals("")){
								if(infoDO.getType().equals("001")){

									infoDO.setMxf_size(file_size);
									infoDO.setSource(file_nm);
								}else if(infoDO.getType().equals("002")){
									infoDO.setPreview_kr(file_nm);
									infoDO.setPre_size(file_size);
								}else if(infoDO.getType().equals("003")){
									infoDO.setCaption_kr(file_nm);
									infoDO.setKr_size(file_size);
								}else if(infoDO.getType().equals("004")){
									infoDO.setCaption_jp(file_nm);
									infoDO.setJp_size(file_size);
								}else if(infoDO.getType().equals("005")){
									infoDO.setCaption_en(file_nm);
									infoDO.setEn_size(file_size);
								}else if(infoDO.getType().equals("006")){
									infoDO.setCaption_cn(file_nm);
									infoDO.setCn_size(file_size);
								}

							}


						}

					}


				}
			}
		}
		return infoDO;

	}	


	/*   

	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}*/

	//메타데이터
	public Object setData3(Element pElement) {

		IfCmsArchiveDO infoDO = (IfCmsArchiveDO)getDO();


		String file_nms="";
		String preview_subj="";
		String preview_cont="";
		String total ="";
		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
			//System.out.println(" setData3  nodeName ======"+_nodeName);
			//System.out.println(" setData3  _nodeValue ======"+_nodeValue);
			if(_nodeName.equals(datetime_onair)){
				if(!_nodeValue.equals("")){
					String brd_dd = _nodeValue.replace("-", "");

					infoDO.setBrd_dd(brd_dd.substring(0, 8));
				}else{
					infoDO.setBrd_dd("19000101");
				}
			}else if(_nodeName.equals(datetime_shooting)){
				if(!_nodeValue.equals("")){
					String fm_dt = _nodeValue.replace("-", "");

					infoDO.setFm_dt(fm_dt.substring(0, 8));
				}else{
					infoDO.setFm_dt("19000101");
				}
			}else if(_nodeName.equals(title)){		
				infoDO.setTitle(_nodeValue);

			}else if(_nodeName.equals(title_sub)){
				infoDO.setSub_ttl(_nodeValue);

			}
			//else if(_nodeName.equals(program_sequence)){
			//infoDO.setEpis_no(_nodeValue);
			//20130111 최효정과장요청 episode_no-->program_sequence
			else if(_nodeName.equals(program_sequence)){	
				if(_nodeName.equals("")){
					infoDO.setEpisode_no("0");	
				}else{
					infoDO.setEpisode_no(_nodeValue);
				}
			}else if(_nodeName.equals(genre_l)){
				if(_nodeValue.equals("001")){
					infoDO.setCtgr_l_cd("100");
				}else if(_nodeValue.equals("002")){
					infoDO.setCtgr_l_cd("200");
				}else if(_nodeValue.equals("003")){
					infoDO.setCtgr_l_cd("300");	
				}else{
					infoDO.setCtgr_l_cd(_nodeValue);
				}
			}else if(_nodeName.equals(genre_m)){
				infoDO.setCtgr_m_cd(_nodeValue);

			}else if(_nodeName.equals(genre_s)){
				infoDO.setCtgr_s_cd(_nodeValue);

			}else if(_nodeName.equals(parents_guidence)){
				//20130108  아카이브요청 컬럼 누락 관련 오류수정				
				String view_cd = xmlutil.changViewGrade(_nodeValue);
				infoDO.setView_gr_cd(view_cd);

			}else if(_nodeName.equals(viewing_rate)){
				infoDO.setView_rate(_nodeValue);

			}else if(_nodeName.equals(copyright_type)){
				infoDO.setCprt_type(_nodeValue);

			}else if(_nodeName.equals(usegrade)){
				infoDO.setLimited_use(_nodeValue);

			}else if(_nodeName.equals(usegrade_desc)){

				infoDO.setLimited_use_cont(_nodeValue);

			}else if(_nodeName.equals(user_range)){
				infoDO.setUser_range(_nodeValue);

			}else if(_nodeName.equals(creator_sub)){


				infoDO.setCreator_sub(_nodeValue);

			}else if(_nodeName.equals(writer_name)){
				infoDO.setWriter_name(_nodeValue);

			}else if(_nodeName.equals(creator)){

				infoDO.setCreator(_nodeValue);

			}else if(_nodeName.equals(director_shooting)){

				infoDO.setDirector_shooting(_nodeValue);
			}else if(_nodeName.equals(publisher_external)){

				infoDO.setPublisher_external(_nodeValue);

			}else if(_nodeName.equals(production_type)){
				infoDO.setProduction_type(_nodeValue);

			}else if(_nodeName.equals(location_shooting)){

				infoDO.setLocation_shooting(_nodeValue);

			}else if(_nodeName.equals(keyword)){

				infoDO.setKeyword(_nodeValue);

			}else if(_nodeName.equals(special_info)){

				infoDO.setSpecial_info(_nodeValue);

			}else if(_nodeName.equals(contents_name)){
				infoDO.setContents_name(_nodeValue);

			}else if(_nodeName.equals(artist)){
				infoDO.setArtist(_nodeValue);

			}else if(_nodeName.equals(country)){
				infoDO.setCountry(_nodeValue);

			}else if(_nodeName.equals(retention_period)){
				//infoDO.setRetention_period( xmlutil.changRsvPrdCd(_nodeValue));
				infoDO.setRetention_period(_nodeValue);
			}else if(_nodeName.equals(contents_desc)){					
				infoDO.setContents_desc(_nodeValue);
			}else if(_nodeName.equals(media_id)){
				infoDO.setMedia_id(_nodeValue);

			}else if(_nodeName.equals(program_id)){
				infoDO.setProgram_id(_nodeValue);

			}else if(_nodeName.equals(aspectratio)){
				infoDO.setAspectratio(_nodeValue);

			}else if(_nodeName.equals(resolution)){

				if(_nodeValue.equals("0")){
					infoDO.setResolution("002");
				}else if(_nodeValue.equals("1")){
					infoDO.setResolution("001");
				}else if(_nodeValue.equals("2")){
					infoDO.setResolution("003");
				}else{
					infoDO.setResolution(_nodeValue);	
				}


			}else if(_nodeName.equals(arch_path)){
				infoDO.setArch_path(_nodeValue);

			}else if(_nodeName.equals(publisher)){
				infoDO.setPublisher(_nodeValue);

			}else if(_nodeName.equals(channel_cd)){
				infoDO.setChannel_cd(_nodeValue);

			}else if(_nodeName.equals(audio_type)){
				String record_type = xmlutil.changRecordCode(_nodeValue);
				infoDO.setAudio_type(record_type);

			}else if(_nodeName.equals(brd_leng)){
				infoDO.setBrd_leng(_nodeValue);

			}else if(_nodeName.equals(file_name)){
				infoDO.setFile_name(_nodeValue);

			}else if(_nodeName.equals(contents_type)){
				infoDO.setContents_type(_nodeValue);

			}else if(_nodeName.equals(file_size)){
				infoDO.setFile_size(_nodeValue);

			}else if(_nodeName.equals(request_id)){
				infoDO.setRequest_id(_nodeValue);

			}else if(_nodeName.equals(som)){
				infoDO.setSom(_nodeValue);

			}else if(_nodeName.equals(eom)){
				infoDO.setEom(_nodeValue);

			}else if(_nodeName.equals(vd_hresol)){
				if(_nodeValue.equals("")){
					infoDO.setVd_hresol("0");
				}else{
					infoDO.setVd_hresol(_nodeValue);	
				}


			}else if(_nodeName.equals(vd_vresol)){
				if(_nodeValue.equals("")){
					infoDO.setVd_vresol("0");
				}else{
					infoDO.setVd_vresol(_nodeValue);	
				}


			}else if(_nodeName.equals(caption_kr)){
				infoDO.setCaption_kr(_nodeValue);

			}else if(_nodeName.equals(caption_jp)){
				infoDO.setCaption_jp(_nodeValue);

			}else if(_nodeName.equals(caption_en)){
				infoDO.setCaption_en(_nodeValue);

			}else if(_nodeName.equals(caption_cn)){
				infoDO.setCaption_cn(_nodeValue);

			}else if(_nodeName.equals(preview_kr)){
				infoDO.setPreview_kr(_nodeValue);
			}

			/*}else if(_nodeName.equals(episode_no)){
					if(_nodeValue.equals("")){
					infoDO.setEpisode_no("0");
					}else{
						infoDO.setEpisode_no(_nodeValue);	
					}
				}*/
			//20130111 최효정과장요청 episode_no-->program_sequence
			else if(_nodeName.equals(episode_no)){
				if(_nodeValue.equals("")){
					infoDO.setEpis_no("0");
				}else{
					infoDO.setEpis_no(_nodeValue);
				}
			}



			else if(_nodeName.equals(drp_frm_yn)){
				infoDO.setDrp_frm_yn(_nodeValue);

			}else if(_nodeName.equals(audio_yn)){
				infoDO.setAudio_yn(_nodeValue);

			}else if(_nodeName.equals(bit_rt)){
				infoDO.setBit_rt(_nodeValue);

			}else if(_nodeName.equals(frm_per_sec)){
				infoDO.setFrm_per_sec(_nodeValue);

			}else if(_nodeName.equals(aud_samp_frq)){
				infoDO.setAud_samp_frq(_nodeValue);

			}else if(_nodeName.equals(copyright_desc)){

				infoDO.setCopyright_desc(_nodeValue);

			}else if(_nodeName.equals(copyright_owner)){

				infoDO.setCopyright_owner(_nodeValue);

			}else if(_nodeName.equals(contents_class)){
				infoDO.setContents_class(_nodeValue);

			}else if(_nodeName.equals(storage_path)){
				String path ="";
				String[] tmp;
				String tmp_path = _nodeValue;
				tmp_path =tmp_path.replaceAll("\\\\", "/");
				tmp =tmp_path.split("/");
				for(int j =0; j<tmp.length;j++){
					if(path.equals("")){
						path = tmp[j];
					}else if(j<tmp.length){
						path = path +"/"+tmp[j];
					}else if(j==tmp.length){
						path = path+tmp[j];;
					}
				}
				infoDO.setStorage_path(path);



			}else if(_nodeName.equals(preview_path)){
				String path ="";
				String[] tmp;
				String tmp_path = _nodeValue;
				tmp_path =tmp_path.replaceAll("\\\\", "/");
				tmp =tmp_path.split("/");
				for(int j =0; j<tmp.length;j++){
					if(path.equals("")){
						path = tmp[j];
					}else if(j<tmp.length){
						path = path +"/"+tmp[j];
					}else if(j==tmp.length){
						path = path+tmp[j];;
					}
				}
				infoDO.setPreview_path(path);

			}else if(_nodeName.equals(caption_path)){
				String path ="";
				String[] tmp;
				String tmp_path = _nodeValue;
				tmp_path =tmp_path.replaceAll("\\\\", "/");
				tmp =tmp_path.split("/");
				for(int j =0; j<tmp.length;j++){
					if(path.equals("")){
						path = tmp[j];
					}else if(j<tmp.length){
						path = path +"/"+tmp[j];
					}else if(j==tmp.length){
						path = path+tmp[j];;
					}
				}
				infoDO.setCaption_path(path);

			}

		}

		return infoDO;

	}	


	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSubXML() {
		IfCmsArchiveDO infoDO = (IfCmsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<sbsgua xmlns=\"urn:sbsgua2012\">\n");
		_xml.append(" <generator>      <generator_name>SBSGUA</generator_name> \n" +
				" <generator_version>2.0</generator_version>   </generator>\n");;

				_xml.append("<" + general_infomation + ">");
				_xml.append("<" + transaction_id + ">"+infoDO.getTransaction_id() +"</" + transaction_id + ">");
				_xml.append("<" + "callback_url" + ">"+infoDO.getCallback_url() +"</" + "callback_url" + ">");
				_xml.append("<" + asset_id + ">"+infoDO.getCart_no()+ "</" + asset_id + ">");
				if(infoDO.getCtgr_l_cd().equals("100")){
					_xml.append("<" + belong_to_group + ">0</" + belong_to_group + ">");
				}else if(infoDO.getCtgr_l_cd().equals("200")){
					_xml.append("<" + belong_to_group + ">1</" + belong_to_group + ">");	
				}
				_xml.append("<" + group_id + ">"+infoDO.getMaster_id()+ "</" + group_id  + ">");
				_xml.append("<" + total_number_of_asset_in_group + ">1</" + total_number_of_asset_in_group + ">");
				_xml.append("<" + transaction_type + ">transfer</" + transaction_type + ">");
				_xml.append("<" + asset_type + ">video</" + asset_type + ">");
				_xml.append("<" + target_folder + ">");
				_xml.append("<" + storage_id + ">"+ infoDO.getStorage_nm()+"</" + storage_id + ">");
				_xml.append("<" + folder_id + ">"+ infoDO.getPhyical_tree() +"</" + folder_id + ">");
				_xml.append( "</" +  target_folder + ">");
				_xml.append("<files total_size=\""+infoDO.getFile_size() + "\">");
				//_xml = _xml + "<file  type =\"001\"  " +"isfolder=\"false\" source=\"" +infoDO.getCart_no()+"_"+infoDO.getFile_name()  + "\"  size=\""+infoDO.getFile_size()+"\">"+ infoDO.getCart_no()+"_"+infoDO.getFile_name()  + "</file>";
				//_xml = _xml + "<file  type =\"001\"  " +"isfolder=\"false\" source=\"" +infoDO.getFile_name()  + "\"  size=\""+infoDO.getFile_size()+"\">"+ infoDO.getRefile_nm()+".mxf"  + "</file>";
				_xml.append("<file  type =\"001\"  " +"isfolder=\"false\" source=\"" +infoDO.getCart_no()+"_"+infoDO.getFile_name()  + "\"  size=\""+infoDO.getFile_size()+"\">"+ infoDO.getRefile_nm()+".mxf"  + "</file>");
				_xml.append("</" + files + ">");
				_xml.append("</" + general_infomation + ">");
				_xml.append("<" + metadata + ">");
				_xml.append("<title>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getTitle(), "")) +"</title>");
				_xml.append("<title_sub>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getSub_ttl(), "")) +"</title_sub>");

				//20121205 최효정 과장님 요청으로 패치
				_xml.append("<" + program_id + "></" + program_id + ">");
				_xml.append("<program_name></program_name>");
				_xml.append("<corner_title>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCorner_title(), "")) +"</corner_title>");
				_xml.append("<corner_contents>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCorner_contents(), "")) +"</corner_contents>");
				_xml.append("<program_sequence>"+ infoDO.getEpisode_no() +"</program_sequence>");
				_xml.append("<creator>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCreator(), "")) + "</creator>");
				_xml.append("<creator_sub>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCreator_sub(), "")) + "</creator_sub>");
				_xml.append("<" + publisher + ">"+infoDO.getPublisher()+ "</" + publisher + ">");
				_xml.append("<" + publisher_external + ">"+CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getPublisher_external(), "")) + "</" + publisher_external + ">");
				_xml.append("<" + genre_l + ">"+ infoDO.getCtgr_l_cd() + "</" + genre_l + ">");
				_xml.append("<" + genre_m + ">"+ infoDO.getCtgr_m_cd() + "</" + genre_m + ">");
				_xml.append("<" + genre_s + ">"+ infoDO.getCtgr_s_cd() + "</" + genre_s + ">");
				//20121205 최효정 과장님 요청으로 인해 패치
				if(infoDO.getCtgr_l_cd().equals("100")){
					_xml.append("<" + datetime_onair + "></" + datetime_onair + ">");
					_xml.append("<" + datetime_shooting + ">"+ infoDO.getFm_dt() +" 00:00:00" + "</" + datetime_shooting + ">");
				}else{
					_xml.append("<" + datetime_onair + ">"+ infoDO.getBrd_dd() +" 00:00:00"+"</" + datetime_onair + ">");
					_xml.append("<" + datetime_shooting + "></" + datetime_shooting + ">");
				}
				_xml.append("<" + location_shooting + ">"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getLocation_shooting(), "")) + "</" + location_shooting + ">");
				_xml.append("<" + keyword + ">"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getKeyword(), "")) + "</" + keyword + ">");
				_xml.append("<synopsis_dir></synopsis_dir>");
				_xml.append("<synopsis_kr></synopsis_kr>");
				_xml.append("<synopsis_download_url></synopsis_download_url>");
				//121223 최효정과정 요청사항
				_xml.append("<description>" + CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getSpecial_info(), "")) + "</description>");
				_xml.append("<copyright_type>"+infoDO.getCopyright_type() +  "</copyright_type>");
				_xml.append("<copyright_owner>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCopyright_owner(), "")) + "</copyright_owner>");
				_xml.append("<copyright_desc>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCopyright_desc(), "")) + "</copyright_desc>");
				_xml.append("<production_type>"+ infoDO.getProduction_type() + "</production_type>");
				_xml.append("<" +usegrade  + ">"+ infoDO.getLimited_use()+ "</" + usegrade + ">");
				_xml.append("<" + usegrade_desc + ">"+ CommonUtl.transXmlText(infoDO.getLimited_use_cont()) +"</" + usegrade_desc + ">");
				_xml.append("<parents_guidence>"+ XmlUtil.changViewGrade2(infoDO.getView_gr_cd()) +"</" + parents_guidence + ">");
				_xml.append("<name_host>"+CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getName_host(), ""))+ "</name_host>");
				_xml.append("<name_guest>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getName_guest(), ""))		+"</name_guest>");
				_xml.append("<artist>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getArtist(), "")) + "</" + artist + ">");
				_xml.append("<" +country  + ">"+ infoDO.getCountry() +"</" + country + ">");
				_xml.append("<music_info>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getMusic_info(), "")) +"</music_info>");
				_xml.append("<media_id>"+ infoDO.getMedia_id() + "</media_id>");
				_xml.append("<media_format>"+"</media_format>");
//
//				if(infoDO.getResolution().equals("002")){
//					infoDO.setResolution("0");
//				}else if(infoDO.getResolution().equals("001")){
//					infoDO.setResolution("1");
//				}else if(infoDO.getResolution().equals("003")){
//					infoDO.setResolution("2");
//				}
				_xml.append("<resolution>"+XmlUtil.changeResolution(infoDO.getResolution())+ "</resolution>");
				_xml.append("<aspectratio>"+infoDO.getAspectratio()+ "</aspectratio>");
				_xml.append("<duration>"+ infoDO.getBrd_leng()+"</duration>");
				_xml.append("<audio_type>"+XmlUtil.changRecordCode2((infoDO.getAudio_type()))+ "</audio_type>");
				_xml.append("<caption_dir></caption_dir>");
				_xml.append("<caption_kr>"+ "</caption_kr>");
				_xml.append("<caption_kr_download_url>"+ "</caption_kr_download_url>");
				_xml.append("<caption_jp>"+ "</caption_jp>");
				_xml.append("<caption_jp_download_url>"+ "</caption_jp_download_url>");
				_xml.append("<caption_en>"+ "</caption_en>");
				_xml.append("<caption_en_download_url>"+ "</caption_en_download_url>");
				_xml.append("<caption_cn>"+ "</caption_cn>");
				_xml.append("<caption_cn_download_url>"+ "</caption_cn_download_url>");
				_xml.append("<preview_dir>"+ "</preview_dir>");
				_xml.append("<preview_kr>"+ "</preview_kr>");
				_xml.append("<preview_url>"+ "</preview_url>");
				_xml.append("<thumbnail_url>"+ "</thumbnail_url>");
				_xml.append("<datetime_regist>"+infoDO.getDatatime_request()+ "</datetime_regist>");
				_xml.append("<contents_owner>"+CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCopyright_owner(), ""))	+"</contents_owner>");
				_xml.append("<contents_channel>"+infoDO.getChannel_cd()+ "</contents_channel>");
				_xml.append("<material_type>"+infoDO.getCtgr_l_cd()+ "</material_type>");
				_xml.append("<file_name>"+infoDO.getCart_no()+"_"+infoDO.getFile_name()+ "</file_name>");
				_xml.append("<som>"+ infoDO.getSom() + "</som>");
				_xml.append("<eom>"+ infoDO.getEom() + "</eom>");
				_xml.append("<vd_hresol>"+infoDO.getVd_hresol() + "</vd_hresol>");
				_xml.append("<vd_vresol>"+ infoDO.getVd_vresol() + "</vd_vresol>");
				_xml.append("<file_size>"+infoDO.getFile_size()+ "</file_size>");
				_xml.append("<bit_rate>"+ infoDO.getBit_rt() + "</bit_rate>");
				_xml.append("<aud_samp_frq>"+ infoDO.getAud_samp_frq() + "</aud_samp_frq>");
				_xml.append("<aud_bandwidth>"+infoDO.getAud_bandwidth() +"</aud_bandwidth>");
				_xml.append("<frame_per_second>"+ infoDO.getFrm_per_sec() +"</frame_per_second>");
				// 20120810 메일내용기준으로 변경	
				//_xml = _xml + "<broadcast_event_type>"+ infoDO.getBroadcast_event_type() + "</broadcast_event_type>";
				_xml.append("<contents_type>"+ infoDO.getBroadcast_event_type() + "</contents_type>");
				_xml.append("<contents_class>"+ infoDO.getContents_class() + "</contents_class>");
				_xml.append("<bgn_time_onair>"+ infoDO.getBgn_time_onair() +"</bgn_time_onair>");
				_xml.append("<end_time_onair>"+ infoDO.getEnd_time_onair() + "</end_time_onair>");
				_xml.append("<worker_id>"+ infoDO.getWorker_id() + "</worker_id>");
				_xml.append("<download_comment>"+ CommonUtl.transXmlText(infoDO.getDownload_comment()) +"</download_comment>");
				_xml.append("</" + metadata + ">");
				_xml.append("</sbsgua>");

				return _xml.toString();
	}



	public String getSubXMLForStorage() {
		IfCmsArchiveDO infoDO = (IfCmsArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<sbsgua xmlns=\"urn:sbsgua2012\">\n");;
		_xml.append(" <generator>      <generator_name>SBSGUA</generator_name> \n" +
				" <generator_version>2.0</generator_version>   </generator>\n");;

				_xml.append("<" + general_infomation + ">");
				_xml.append("<" + transaction_id + ">"+infoDO.getTransaction_id() +"</" + transaction_id + ">");
				_xml.append("<" + "callback_url" + ">"+infoDO.getCallback_url() +"</" + "callback_url" + ">");
				_xml.append("<" + asset_id + ">"+infoDO.getCart_no()+ "</" + asset_id + ">");
				if(infoDO.getCtgr_l_cd().equals("100")){
					_xml.append("<" + belong_to_group + ">0</" + belong_to_group + ">");
				}else if(infoDO.getCtgr_l_cd().equals("200")){
					_xml.append("<" + belong_to_group + ">1</" + belong_to_group + ">");	
				}
				_xml.append("<" + group_id + ">"+infoDO.getMaster_id()+ "</" + group_id  + ">");
				_xml.append("<" + total_number_of_asset_in_group + ">1</" + total_number_of_asset_in_group + ">");
				_xml.append("<" + transaction_type + ">transfer</" + transaction_type + ">");
				_xml.append("<" + asset_type + ">video</" + asset_type + ">");
				_xml.append("<" + target_folder + ">");
				_xml.append("<" + storage_id + ">"+ infoDO.getStorage_nm()+"</" + storage_id + ">");
				_xml.append("<" + folder_id + ">"+ infoDO.getPhyical_tree() +"</" + folder_id + ">");
				_xml.append( "</" +  target_folder + ">");
				_xml.append("<files total_size=\""+infoDO.getFile_size() + "\">");
				//_xml = _xml + "<file  type =\"001\"  " +"isfolder=\"false\" source=\"" +infoDO.getCart_no()+"_"+infoDO.getFile_name()  + "\"  size=\""+infoDO.getFile_size()+"\">"+ infoDO.getCart_no()+"_"+infoDO.getFile_name()  + "</file>";
				_xml.append("<file  type =\"001\"  " +"isfolder=\"false\" source=\"" +infoDO.getFile_name()+".mxf"  + "\"  size=\""+infoDO.getFile_size()+"\">"+ infoDO.getRefile_nm()+".mxf"  + "</file>");
				//_xml = _xml + "<file  type =\"001\"  " +"isfolder=\"false\" source=\"" +infoDO.getFile_name()  + "\"  size=\""+infoDO.getFile_size()+"\">"+ infoDO.getRefile_nm()+".mxf"  + "</file>";
				_xml.append("</" + files + ">");
				_xml.append("</" + general_infomation + ">");
				_xml.append("<" + metadata + ">");

				_xml.append("<title>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getTitle(), "")) +"</title>");
				_xml.append("<title_sub>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getSub_ttl(), "")) +"</title_sub>");
				//20121205 최효정 과장님 요청으로 패치
				/*_xml = _xml + "<" + program_id + ">"+ infoDO.getProgram_id() + "</" + program_id + ">";
				_xml = _xml + "<program_name>"+ infoDO.getProgram_name() + "</program_name>";*/
				_xml.append("<" + program_id + "></" + program_id + ">");
				_xml.append("<program_name></program_name>");
				_xml.append("<corner_title>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCorner_title(), "")) +"</corner_title>");
				_xml.append("<corner_contents>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCorner_contents(), "")) +"</corner_contents>");
				_xml.append("<program_sequence>"+ infoDO.getEpisode_no() +"</program_sequence>");
				_xml.append("<creator>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCreator(), "")) + "</creator>");
				_xml.append("<creator_sub>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCreator_sub(), "")) + "</creator_sub>");
				_xml.append("<" + publisher + ">"+infoDO.getPublisher()+ "</" + publisher + ">");
				_xml.append("<" + publisher_external + ">"+CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getPublisher_external(), "")) + "</" + publisher_external + ">");
				_xml.append("<" + genre_l + ">"+ infoDO.getCtgr_l_cd() + "</" + genre_l + ">");
				_xml.append("<" + genre_m + ">"+ infoDO.getCtgr_m_cd() + "</" + genre_m + ">");
				_xml.append("<" + genre_s + ">"+ infoDO.getCtgr_s_cd() + "</" + genre_s + ">");
				//20121205 최효정 과장님 요청으로 인해 패치
				if(infoDO.getCtgr_l_cd().equals("100")){
					_xml.append("<" + datetime_onair + "></" + datetime_onair + ">");
					_xml.append("<" + datetime_shooting + ">"+ infoDO.getFm_dt() +" 00:00:00" + "</" + datetime_shooting + ">");
				}else{
					_xml.append("<" + datetime_onair + ">"+ infoDO.getBrd_dd() +" 00:00:00"+"</" + datetime_onair + ">");
					_xml.append("<" + datetime_shooting + "></" + datetime_shooting + ">");
				}
				_xml.append("<" + location_shooting + ">"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getLocation_shooting(), "")) + "</" + location_shooting + ">");
				_xml.append("<" + keyword + ">"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getKeyword(), "")) + "</" + keyword + ">");
				_xml.append("<synopsis_dir></synopsis_dir>");
				_xml.append("<synopsis_kr></synopsis_kr>");
				_xml.append("<synopsis_download_url></synopsis_download_url>");
				_xml.append("<description>" + CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getSpecial_info(), "")) + "</description>");
				_xml.append("<copyright_type>"+infoDO.getCopyright_type() +  "</copyright_type>");
				_xml.append("<copyright_owner>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCopyright_owner(), "")) + "</copyright_owner>");
				_xml.append("<copyright_desc>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCopyright_desc(), "")) + "</copyright_desc>");
				_xml.append("<production_type>"+ infoDO.getProduction_type() + "</production_type>");
				_xml.append("<" +usegrade  + ">"+ infoDO.getLimited_use()+ "</" + usegrade + ">");
				_xml.append("<" + usegrade_desc + ">"+ CommonUtl.transXmlText(infoDO.getLimited_use_cont()) +"</" + usegrade_desc + ">");
				_xml.append("<parents_guidence>"+ XmlUtil.changViewGrade2(infoDO.getView_gr_cd()) +"</" + parents_guidence + ">");
				_xml.append("<name_host>"+CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getName_host(), ""))+ "</name_host>");
				_xml.append("<name_guest>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getName_guest(), ""))+"</name_guest>");
				_xml.append("<artist>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getArtist(),"")) + "</" + artist + ">");
				_xml.append("<" +country  + ">"+ infoDO.getCountry() +"</" + country + ">");
				_xml.append("<music_info>"+ CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getMusic_info(), "")) +"</music_info>");
				_xml.append("<media_id>"+ infoDO.getMedia_id() + "</media_id>");
				_xml.append("<media_format>"+"</media_format>");

//				if(infoDO.getResolution().equals("002")){
//					infoDO.setResolution("0");
//				}else if(infoDO.getResolution().equals("001")){
//					infoDO.setResolution("1");
//				}else if(infoDO.getResolution().equals("003")){
//					infoDO.setResolution("2");
//				}
				_xml.append("<resolution>"+XmlUtil.changeResolution(infoDO.getResolution())+ "</resolution>");
				_xml.append("<aspectratio>"+infoDO.getAspectratio()+ "</aspectratio>");
				_xml.append("<duration>"+ infoDO.getBrd_leng()+"</duration>");
				_xml.append("<audio_type>"+XmlUtil.changRecordCode2(infoDO.getAudio_type())+ "</audio_type>");
				_xml.append("<caption_dir></caption_dir>");
				_xml.append("<caption_kr>"+ "</caption_kr>");
				_xml.append("<caption_kr_download_url>"+ "</caption_kr_download_url>");
				_xml.append("<caption_jp>"+ "</caption_jp>");
				_xml.append("<caption_jp_download_url>"+ "</caption_jp_download_url>");
				_xml.append("<caption_en>"+ "</caption_en>");
				_xml.append("<caption_en_download_url>"+ "</caption_en_download_url>");
				_xml.append("<caption_cn>"+ "</caption_cn>");
				_xml.append("<caption_cn_download_url>"+ "</caption_cn_download_url>");
				_xml.append("<preview_dir>"+ "</preview_dir>");
				_xml.append("<preview_kr>"+ "</preview_kr>");
				_xml.append("<preview_url>"+ "</preview_url>");
				_xml.append("<thumbnail_url>"+ "</thumbnail_url>");
				_xml.append("<datetime_regist>"+infoDO.getDatatime_request()+ "</datetime_regist>");
				_xml.append("<contents_owner>"+CommonUtl.transXmlText(StringUtils.defaultIfEmpty(infoDO.getCopyright_owner(), ""))+"</contents_owner>");
				_xml.append("<contents_channel>"+infoDO.getChannel_cd()+ "</contents_channel>");
				_xml.append("<material_type>"+infoDO.getCtgr_l_cd()+ "</material_type>");
				_xml.append("<file_name>"+infoDO.getFile_name()+".mxf"  + "</file_name>");
				_xml.append("<som>"+ infoDO.getSom() + "</som>");
				_xml.append("<eom>"+ infoDO.getEom() + "</eom>");
				_xml.append("<vd_hresol>"+infoDO.getVd_hresol() + "</vd_hresol>");
				_xml.append("<vd_vresol>"+ infoDO.getVd_vresol() + "</vd_vresol>");
				_xml.append("<file_size>"+infoDO.getFile_size()+ "</file_size>");
				_xml.append("<bit_rate>"+ infoDO.getBit_rt() + "</bit_rate>");
				_xml.append("<aud_samp_frq>"+ infoDO.getAud_samp_frq() + "</aud_samp_frq>");
				_xml.append("<aud_bandwidth>"+infoDO.getAud_bandwidth() +"</aud_bandwidth>");
				_xml.append("<frame_per_second>"+ infoDO.getFrm_per_sec() +"</frame_per_second>");
				// 20120810 메일내용기준으로 변경	
				//_xml = _xml + "<broadcast_event_type>"+ infoDO.getBroadcast_event_type() + "</broadcast_event_type>";
				_xml.append("<contents_type>"+ infoDO.getBroadcast_event_type() + "</contents_type>");
				_xml.append("<contents_class>"+ infoDO.getContents_class() + "</contents_class>");
				_xml.append("<bgn_time_onair>"+ infoDO.getBgn_time_onair() +"</bgn_time_onair>");
				_xml.append("<end_time_onair>"+ infoDO.getEnd_time_onair() + "</end_time_onair>");
				_xml.append("<worker_id>"+ infoDO.getWorker_id() + "</worker_id>");
				_xml.append("<download_comment>"+ CommonUtl.transXmlText(infoDO.getDownload_comment()) +"</download_comment>");
				_xml.append("</" + metadata + ">");
				_xml.append("</sbsgua>");

				return _xml.toString();
	}

}
