package test;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.dao.ExternalDAO;
import com.app.das.business.transfer.TcBeanDO;
import com.app.das.services.TransferDOXML;
import com.app.das.util.CalendarUtil;

public class CompleteDownTest {

	private static ExternalDAO externalDAO = ExternalDAO.getInstance();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TransferDOXML _doXML = new TransferDOXML();

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		try{
			/**
			 * tc 여부를 판단하고 tc 요청을 한다
			 */
			TcBeanDO tccd = externalDAO.selectNewTcInfo(637712);

			String mp4 = "mp4";
			String net_mp4 = "net_mp4";

			if(org.apache.commons.lang.StringUtils.isBlank(tccd.getLR_FL_PATH())) {
				String hrPath = (org.apache.commons.lang.StringUtils.isBlank(tccd.getArch_path())) ? tccd.getHR_FL_PATH() : tccd.getArch_path();
				String low_prefix = ("M".equals(tccd.getCocd())) ? net_mp4 : mp4;
				String lrPath = low_prefix;
				if(org.apache.commons.lang.StringUtils.isBlank(hrPath)) {
					lrPath = lrPath+"/"+CalendarUtil.dateToString(new Date(), "yyyyMM")+
							"/"+CalendarUtil.dateToString(new Date(), "dd")+"/"+tccd.getCt_id();
				} else {
					String[] paths = hrPath.split("/");
					lrPath = lrPath+"/"+paths[paths.length-2]+"/"+paths[paths.length-1]+"/"+tccd.getCt_id();
				}
				tccd.setLR_FL_PATH(lrPath);
			}
			if(org.apache.commons.lang.StringUtils.isBlank(tccd.getOut_put_ct_path())) {
				tccd.setOut_put_ct_path(tccd.getLR_FL_PATH()+"/KFRM");
			}
			
			String nearline = "nearline";
			
			if(tccd.getJob_path() != null && tccd.getJob_path().startsWith("/"))
				tccd.setJob_path(nearline+tccd.getJob_path());
			else
				tccd.setJob_path(nearline+"/"+tccd.getJob_path());
			
			tccd.setReq_cd("CT");
			
			if(StringUtils.isNotBlank(tccd.getReq_cd())) {
				if(tccd.getReq_cd().equals("CT")){
					_processor.recreateKFRM(tccd, "");
				}else if(tccd.getReq_cd().equals("LR")){
					_processor.recreateWMV(tccd, "");
				}else if(tccd.getReq_cd().equals("LRCT")){
					_processor.recreateWMV_KFRM(tccd, "");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
