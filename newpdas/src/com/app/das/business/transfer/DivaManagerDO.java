package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;


/**
 * diva매니져 필요정보를  포함하고 있는 dataObject
 * @author asura207
 *
 */
public class DivaManagerDO {


		

	
		/**
		 *  ct_id
		 */
		private long ct_id;

		/**
		 *  das_eq_id
		 */
		private String das_eq_id = Constants.BLANK;
		/**
		 *  das_eq_ps_cd
		 */
		private String das_eq_ps_cd = Constants.BLANK;
		/**
		 *  cti_id
		 */
		private long cti_id;
		/**
		 *  priority
		 */
		private String priority = Constants.BLANK;
		/**
		 *  sql_group_nm
		 */
		private String sql_group_nm = Constants.BLANK;
		/**
		 *  job_id
		 */
		private String job_id = Constants.BLANK;
		/**
		 *  som
		 */
		private String som = Constants.BLANK;
		/**
		 *  eom
		 */
		private String eom = Constants.BLANK;
		/**
		 *  file_path
		 */
		private String file_path = Constants.BLANK;
		/**
		 *  req_id
		 */
		private String req_id = Constants.BLANK;
		/**
		 *  regrid
		 */
		private String regrid = Constants.BLANK;
		/**
		 *  master_id
		 */
		private long master_id;
		/**
		 *  pgm_id
		 */
		private long pgm_id;
		public long getCt_id() {
			return ct_id;
		}
		public void setCt_id(long ctId) {
			ct_id = ctId;
		}
		public String getDas_eq_id() {
			return das_eq_id;
		}
		public void setDas_eq_id(String dasEqId) {
			das_eq_id = dasEqId;
		}
		public String getDas_eq_ps_cd() {
			return das_eq_ps_cd;
		}
		public void setDas_eq_ps_cd(String dasEqPsCd) {
			das_eq_ps_cd = dasEqPsCd;
		}
		
		public long getCti_id() {
			return cti_id;
		}
		public void setCti_id(long ctiId) {
			cti_id = ctiId;
		}
		public String getPriority() {
			return priority;
		}
		public void setPriority(String priority) {
			this.priority = priority;
		}
		public String getSql_group_nm() {
			return sql_group_nm;
		}
		public void setSql_group_nm(String sqlGroupNm) {
			sql_group_nm = sqlGroupNm;
		}
		public String getJob_id() {
			return job_id;
		}
		public void setJob_id(String jobId) {
			job_id = jobId;
		}
		public String getSom() {
			return som;
		}
		public void setSom(String som) {
			this.som = som;
		}
		public String getEom() {
			return eom;
		}
		public void setEom(String eom) {
			this.eom = eom;
		}
		public String getFile_path() {
			return file_path;
		}
		public void setFile_path(String filePath) {
			file_path = filePath;
		}
		public String getReq_id() {
			return req_id;
		}
		public void setReq_id(String reqId) {
			req_id = reqId;
		}
		public String getRegrid() {
			return regrid;
		}
		public void setRegrid(String regrid) {
			this.regrid = regrid;
		}
		public long getMaster_id() {
			return master_id;
		}
		public void setMaster_id(long masterId) {
			master_id = masterId;
		}
		public long getPgm_id() {
			return pgm_id;
		}
		public void setPgm_id(long pgmId) {
			pgm_id = pgmId;
		}
		
		
		


}
