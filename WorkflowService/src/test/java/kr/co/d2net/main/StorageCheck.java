package kr.co.d2net.main;

import java.io.File;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.sbs.das.dto.xml.Attach;

public class StorageCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Attach attach = new Attach();
		attach.setFlPath("D:\\tmp");
		attach.setOrgFileNm("SBA,DA+DE-060(화면설계서),DAS기존화면+V1.0.ppt");
		if(StringUtils.isNotBlank(attach.getOrgFileNm()) && StringUtils.isNotBlank(attach.getFlPath())) {

			String flPath = attach.getFlPath().replaceAll("\\\\", "/");
			File f = new File(flPath, attach.getOrgFileNm());

			if(attach.getOrgFileNm().indexOf("+") > -1 || attach.getOrgFileNm().indexOf(",") > -1) {
				String tmp = attach.getOrgFileNm();
				System.out.println("filename is contained spcial charactor '+' or ',' : "+f.getAbsolutePath());
				try {
					attach.setOrgFileNm(attach.getOrgFileNm().replaceAll("([\\+]|[\\,])", "_"));
					File nf = new File(flPath, attach.getOrgFileNm());
					f.renameTo(nf);
					System.out.println(nf.getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
					attach.setOrgFileNm(tmp);
				}
			}
			System.out.println(attach.getOrgFileNm());
		}
	}
}
