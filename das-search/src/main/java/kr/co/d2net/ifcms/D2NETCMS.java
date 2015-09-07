package kr.co.d2net.ifcms;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import kr.co.d2net.commons.system.DasCmsConnector;
import kr.co.d2net.commons.system.DasCmsConnectorImpl;
import kr.co.d2net.model.Das;
import kr.co.d2net.model.Storage;
import kr.co.d2net.model.TokenInfo;
import kr.co.d2net.service.XmlConvertorService;
import kr.co.d2net.service.XmlConvertorServiceImpl;

import com.sbs.ifcms.LoginException;
import com.sbs.ifcms.spi.CMS;
import com.sbs.ifcms.spi.Session;

/**
 * Sub CMS 에 접근할 url 및 id, pass를 설정
 * @author Kang, M.S
 *
 */
public class D2NETCMS implements CMS {

	private static Logger logger = Logger.getLogger(D2NETCMS.class);

	private static final String VERSION = "0.8";

	private String url;
	private String id;
	private String name;
	
	
	/*
	 * 개발서버에 Jeus LDAP 서비스가 설치가 되어 있지 않기때문에 AD인증을 할 수가 없다.
	 * 운영오픈전까지 인증절차를 거치지 않도록 해야하는데 해당 부분을 isOpen 여부로 판단하기로 한다.
	 * 오픈시점에 'ture'로 설정하게 되면 정상적으로 AD인증 절차를 거치게 된다.
	 * 2012.09.14
	 */
	private boolean isOpen = true;

	public String getDescriptor(String key) {
		if(REP_VENDOR_DESC.equals(key)) {
			return "D2Net Co., Ltd.";
		}else if(REP_VENDOR_DESC.equals(key)) {
			return VERSION;
		}else if(OPTION_ONELEVEL_FOLDER_SUPPORTED.equals(key)) {
			return "false";
		}else if(OPTION_WORKFLOW_SUPPORTED.equals(key)) {
			return "true";
		}else if(OPTION_STORYBOARD_SUPPORTED.equals(key)) {
			return "false";
		}else if(OPTION_UPDATE_SUPPORTED.equals(key)) {
			return "false";
		}else if(SUPPORTED_WORKFLOWS.equals(key)) {
			return "download,archive_register";
		}else if(OPTION_STREAMING_SUPPORTED.equals(key)) {
			return "true";
		}else if(SUPPORTED_TRANSACTIONS_AS_TARGET.equals(key)) {
			return "archive";
		}else if(OPTION_PARTIAL_DOWNLOAD_SUPPORTED.equals(key)) {
			return "true";
		}else if(SUPPORTED_ARCHIVE_MEDIA_FORMAT.equals(key)) {
			return "XDCAM MXF";
		}else if(SUPPORTED_TRANSFER_MEDIA_FORMAT.equals(key)) {
			return "XDCAM MXF";
		}else{
			return "";
		}
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getURL() {
		return url;
	}

	public void setID(String identifier) {
		id = identifier;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setURL(String url) throws MalformedURLException {
		this.url = url;
	}

	public Session login(String username, String password)
			throws LoginException {

		DasCmsConnector dasCmsConnector;

		String sessionID = "";
		String userId = "";
		String userName = "";
		try {
			if (logger.isDebugEnabled())
				logger.debug(url);
			dasCmsConnector = new DasCmsConnectorImpl(url);

			Das das = new Das();
			TokenInfo tokenDO = new TokenInfo();
			tokenDO.setHex("DAS");
			//tokenDO.setUserId("S522522");
			tokenDO.setUserId(username);
			//tokenDO.setPasswd("522522");
			tokenDO.setPasswd(password);
			das.setTokenInfo(tokenDO);

			if(isOpen) {
				XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
				logger.debug("login xml   "+ convertorService.createMarshaller(das));
				das = convertorService.unMarshaller(dasCmsConnector.login(convertorService.createMarshaller(das)));
				if (logger.isDebugEnabled())
					logger.debug("login: "+das.getTokenInfo().getResult());
				if(StringUtils.isNotBlank(das.getTokenInfo().getResult()) && das.getTokenInfo().getResult().startsWith("0:")) {
					sessionID = das.getTokenInfo().getToken();

					userId = das.getTokenInfo().getUserId();
					userName = das.getTokenInfo().getUserNm();
				} else {
					throw new LoginException("Login Fail");
				}
			} else {
				logger.info("Login Pass! - DAS TEST");
				userId = username;
				userName = username;
			}
		} catch (RemoteException e) {
			logger.error("Login Fail", e);
			throw new LoginException(e);
		} catch (Exception e) {
			throw new LoginException(e);
		}


		return new D2NETCMSSession(url, sessionID, userId, userName, isOpen);
	}

	/**
	 * <pre>
	 * source_cms_id : ex) pds, nds, medianet
	 *                 해당 값이 null 이거나 추가로 정의된 값이 있다면 default: medianet
	 * material_type  : ex) PGM(방송), Material(비방송)
	 *                 해당 값이 null 이라면 Material
	 *                 미디어넷 방송본 : /nearline/MediaNet/onAir, 비방송: /nearline/MediaNet/manual
	 *                 SBS 방송본  : /nearline/SBS/onAir, 비방송: /nearline/SBS/manual
	 * </pre>
	 */


	public long getUsableSpace(String source, String materialType) {
		if(logger.isDebugEnabled()) {
			logger.debug("source : "+source);
			logger.debug("materialType : "+materialType);
		}
		try {
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			DasCmsConnector dasCmsConnector = new DasCmsConnectorImpl(url);

			Das das = new Das();
			Storage storage = new Storage();

			String systemId = "";

			if(StringUtils.isNotBlank(source) && StringUtils.isNotBlank(materialType)) {

				if((source.indexOf("nds") > -1) || (source.indexOf("pds") > -1) || (source.indexOf("das") > -1)) {
					systemId = "SBS";
				} else if((source.indexOf("medianet") > -1)) {
					systemId = "MEDIANET";
				}

				if(materialType.equals("PGM")) {
					systemId += "ONAIR";
				} else {
					systemId += "NON";
				}

				storage.setSystemId(systemId);
			}
			das.setStorage(storage);
			String xml = dasCmsConnector.getDiskSpace(convertorService.createMarshaller(das));
			if(logger.isDebugEnabled()) {
				logger.debug("return xml : "+xml);
			}

			das = convertorService.unMarshaller(xml);
			if(das.getStorage() != null) {

				return das.getStorage().getPassibleSize()*1024;
			} else {
				return 0L;
			}
		} catch (Exception e) {
			logger.error("disk space check error", e);
			return 0L;
		}
	}

}
