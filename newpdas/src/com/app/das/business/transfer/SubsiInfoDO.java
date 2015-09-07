package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.DTO;



/**
 * 계열사 수신서버 정보를 포함하고 있는 DataObject
 * @author asura207
 *
 */
public class SubsiInfoDO  extends DTO  {

	
	
	
	
		/**
		 * 순번
		 */
		private int seq;
		/**
		 * 계열사 ID
		 */
		private String subsi_id = Constants.BLANK;
		/**
		 * 계열사 명
		 */
		private String subsi_nm = Constants.BLANK;
		/**
		 * 수신서버 ip
		 */
		private String receve_server_ip = Constants.BLANK;
		/**
		 * 수신서버 명
		 */
		private String receve_server_nm = Constants.BLANK;
		/**
		 * 수신서버 port
		 */
		private int receve_server_port;
		public int getReceve_server_port() {
			return receve_server_port;
		}
		public void setReceve_server_port(int receveServerPort) {
			receve_server_port = receveServerPort;
		}
		/**
		 * 비밀번호
		 */
		private String password = Constants.BLANK;
		/**
		 * 목적지 폴더경로
		 */
		private String path = Constants.BLANK;
		
		
		
		private int page;
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		/**
		 * 관리자 ID
		 */
		private String admin_id = Constants.BLANK;
		public String getAdmin_id() {
			return admin_id;
		}
		public void setAdmin_id(String adminId) {
			admin_id = adminId;
		}
		public int getSeq() {
			return seq;
		}
		public void setSeq(int seq) {
			this.seq = seq;
		}
		public String getSubsi_id() {
			return subsi_id;
		}
		public void setSubsi_id(String subsiId) {
			subsi_id = subsiId;
		}
		public String getSubsi_nm() {
			return subsi_nm;
		}
		public void setSubsi_nm(String subsiNm) {
			subsi_nm = subsiNm;
		}
		public String getReceve_server_ip() {
			return receve_server_ip;
		}
		public void setReceve_server_ip(String receveServerIp) {
			receve_server_ip = receveServerIp;
		}
		public String getReceve_server_nm() {
			return receve_server_nm;
		}
		public void setReceve_server_nm(String receveServerNm) {
			receve_server_nm = receveServerNm;
		}
		
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		
	

	}


