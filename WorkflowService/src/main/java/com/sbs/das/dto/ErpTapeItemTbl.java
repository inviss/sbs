package com.sbs.das.dto;

public class ErpTapeItemTbl extends BaseObject {
	
	private static final long serialVersionUID = 6335162632116476402L;
	
	String 		tapeItemId;    		// CHARACTER(12)       
	String 		tapeId;         	// CHARACTER(12)       
	Integer		scnCnt;         	// INTEGER             
	String 		tapeClf;       		// CHARACTER(3)        
	Integer		scnNo;          	// INTEGER             
	String 		reqNo;          	// CHARACTER(9)        
	String 		scnTtl;         	// VARCHAR(160)        
	String 		subTtl;         	// VARCHAR(200)        
	String 		rptr;            	// VARCHAR(100)        
	String 		cmrMan;         	// VARCHAR(100)        
	String 		deptCd;         	// CHARACTER(6)        
	String 		cmrPlace;       	// VARCHAR(300)        
	String 		cmrDd;         		// CHARACTER(8)        
	String 		rstCont;       		// VARCHAR(1500)       
	Integer		len;             	// INTEGER             
	String 		casting;         	// VARCHAR(1200)       
	String 		orgTtl;         	// VARCHAR(160)        
	String 		prdtCoNm;      		// VARCHAR(50)         
	String 		dirt;            	// VARCHAR(100)        
	String 		prdtYyyy;       	// CHARACTER(4)        
	String 		timeCd;         	// VARCHAR(30)         
	Integer		srisNo;         	// INTEGER             
	Integer		episNo;         	// INTEGER             
	String 		brdDd;          	// CHARACTER(8)        
	String 		keyWords;       	// VARCHAR(2000)       
	String 		photoMethod;    	// VARCHAR(200)        
	String 		scnCont;        	// CLOB(1048576)       
	String 		musicInfo;      	// VARCHAR(200)        
	String 		snps;            	// VARCHAR(4000)       
	String 		audioCd;        	// CHARACTER(3)        
	String 		colorCd;        	// CHARACTER(3)        
	String 		recordTypeCd;  		// CHARACTER(3)        
	String 		meCd;           	// CHARACTER(3)        
	String 		cprtr;           	// VARCHAR(100)        
	String 		cprtType;       	// CHARACTER(3)        
	String 		wtchGrNm;      		// VARCHAR(20)         
	String 		awardInfo;      	// VARCHAR(500)        
	String 		accessRighter;  	// VARCHAR(50)         
	String 		rightOwner;     	// VARCHAR(50)         
	String 		licenseOption;  	// VARCHAR(200)        
	String 		maxUsageCount; 		// VARCHAR(100)        
	String 		wtchGr;         	// CHARACTER(3)        
	String 		dlbrGr;         	// CHARACTER(3)        
	String 		keepPrtCd;     		// CHARACTER(3)        
	String 		dataStatCd;    		// CHARACTER(3)        
	String 		regr;            	// VARCHAR(20)         
	String 		archiveDd;      	// CHARACTER(8)        
	String 		mc;              	// VARCHAR(100)        
	String 		author;          	// VARCHAR(100)        
	String 		orgPrdr;        	// VARCHAR(300)        
	String 		staff;           	// VARCHAR(1000)       
	String 		prdtTypeCd;    		// CHARACTER(3)        
	String 		regDd;          	// CHARACTER(8)        
	String 		pgmNm;          	// VARCHAR(200)        
	String 		dataKind;       	// VARCHAR(6)          
	String 		useGradeCd;    		// CHARACTER(3)        
	String 		workStat;       	// CHARACTER(3)        
	String 		lockStat;       	// VARCHAR(15)         
	String 		rltText1;       	// VARCHAR(600)        
	String 		rltText2;       	// VARCHAR(600)        
	String 		rltText3;       	// VARCHAR(600)        
	byte[] 		rltFile1;       	// BLOB(10485760) 
	byte[] 		rltFile2;       	// BLOB(10485760) 
	byte[] 		rltFile3;       	// BLOB(10485760) 
	String 		bgnTime;        	// CHARACTER(8)        
	String 		endTime;        	// CHARACTER(8)        
	String 		review;          	// VARCHAR(300)        
	String 		schdWeekDd;    		// VARCHAR(20)         
	String 		pgmCd;          	// CHARACTER(12)       
	String 		selCont;       		// VARCHAR(100)        
	String 		buyPrc;        		// VARCHAR(100)        
	String 		prdtr;           	// VARCHAR(100)        
	String 		gameNm;         	// VARCHAR(50)         
	String 		cmntr;           	// VARCHAR(50)         
	long   		totCnt;         	// BIGINT              
	String 		buyCont;        	// VARCHAR(100)        
	String 		wordCd;         	// CHARACTER(3)        
	String 		viewGr;         	// CHARACTER(1)        
	String 		rlyBrd;         	// VARCHAR(100)        
	String 		workSeq;        	// CHARACTER(3)        
	String 		ingestStatus;   	// CHARACTER(3)        
	String 		ingestDd;       	// CHARACTER(8) 
	
	String		encodeYn;
	String		encodeDd;
	String		ingestYn;
	
	
	public String getEncodeYn() {
		return encodeYn;
	}
	public void setEncodeYn(String encodeYn) {
		this.encodeYn = encodeYn;
	}
	public String getEncodeDd() {
		return encodeDd;
	}
	public void setEncodeDd(String encodeDd) {
		this.encodeDd = encodeDd;
	}
	public String getIngestYn() {
		return ingestYn;
	}
	public void setIngestYn(String ingestYn) {
		this.ingestYn = ingestYn;
	}
	public String getTapeItemId() {
		return tapeItemId;
	}
	public void setTapeItemId(String tapeItemId) {
		this.tapeItemId = tapeItemId;
	}
	public String getTapeId() {
		return tapeId;
	}
	public void setTapeId(String tapeId) {
		this.tapeId = tapeId;
	}
	public Integer getScnCnt() {
		return scnCnt;
	}
	public void setScnCnt(Integer scnCnt) {
		this.scnCnt = scnCnt;
	}
	public String getTapeClf() {
		return tapeClf;
	}
	public void setTapeClf(String tapeClf) {
		this.tapeClf = tapeClf;
	}
	public Integer getScnNo() {
		return scnNo;
	}
	public void setScnNo(Integer scnNo) {
		this.scnNo = scnNo;
	}
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getScnTtl() {
		return scnTtl;
	}
	public void setScnTtl(String scnTtl) {
		this.scnTtl = scnTtl;
	}
	public String getSubTtl() {
		return subTtl;
	}
	public void setSubTtl(String subTtl) {
		this.subTtl = subTtl;
	}
	public String getRptr() {
		return rptr;
	}
	public void setRptr(String rptr) {
		this.rptr = rptr;
	}
	public String getCmrMan() {
		return cmrMan;
	}
	public void setCmrMan(String cmrMan) {
		this.cmrMan = cmrMan;
	}
	public String getDeptCd() {
		return deptCd;
	}
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	public String getCmrPlace() {
		return cmrPlace;
	}
	public void setCmrPlace(String cmrPlace) {
		this.cmrPlace = cmrPlace;
	}
	public String getCmrDd() {
		return cmrDd;
	}
	public void setCmrDd(String cmrDd) {
		this.cmrDd = cmrDd;
	}
	public String getRstCont() {
		return rstCont;
	}
	public void setRstCont(String rstCont) {
		this.rstCont = rstCont;
	}
	public Integer getLen() {
		return len;
	}
	public void setLen(Integer len) {
		this.len = len;
	}
	public String getCasting() {
		return casting;
	}
	public void setCasting(String casting) {
		this.casting = casting;
	}
	public String getOrgTtl() {
		return orgTtl;
	}
	public void setOrgTtl(String orgTtl) {
		this.orgTtl = orgTtl;
	}
	public String getPrdtCoNm() {
		return prdtCoNm;
	}
	public void setPrdtCoNm(String prdtCoNm) {
		this.prdtCoNm = prdtCoNm;
	}
	public String getDirt() {
		return dirt;
	}
	public void setDirt(String dirt) {
		this.dirt = dirt;
	}
	public String getPrdtYyyy() {
		return prdtYyyy;
	}
	public void setPrdtYyyy(String prdtYyyy) {
		this.prdtYyyy = prdtYyyy;
	}
	public String getTimeCd() {
		return timeCd;
	}
	public void setTimeCd(String timeCd) {
		this.timeCd = timeCd;
	}
	public Integer getSrisNo() {
		return srisNo;
	}
	public void setSrisNo(Integer srisNo) {
		this.srisNo = srisNo;
	}
	public Integer getEpisNo() {
		return episNo;
	}
	public void setEpisNo(Integer episNo) {
		this.episNo = episNo;
	}
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getPhotoMethod() {
		return photoMethod;
	}
	public void setPhotoMethod(String photoMethod) {
		this.photoMethod = photoMethod;
	}
	public String getScnCont() {
		return scnCont;
	}
	public void setScnCont(String scnCont) {
		this.scnCont = scnCont;
	}
	public String getMusicInfo() {
		return musicInfo;
	}
	public void setMusicInfo(String musicInfo) {
		this.musicInfo = musicInfo;
	}
	public String getSnps() {
		return snps;
	}
	public void setSnps(String snps) {
		this.snps = snps;
	}
	public String getAudioCd() {
		return audioCd;
	}
	public void setAudioCd(String audioCd) {
		this.audioCd = audioCd;
	}
	public String getColorCd() {
		return colorCd;
	}
	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}
	public String getRecordTypeCd() {
		return recordTypeCd;
	}
	public void setRecordTypeCd(String recordTypeCd) {
		this.recordTypeCd = recordTypeCd;
	}
	public String getMeCd() {
		return meCd;
	}
	public void setMeCd(String meCd) {
		this.meCd = meCd;
	}
	public String getCprtr() {
		return cprtr;
	}
	public void setCprtr(String cprtr) {
		this.cprtr = cprtr;
	}
	public String getCprtType() {
		return cprtType;
	}
	public void setCprtType(String cprtType) {
		this.cprtType = cprtType;
	}
	public String getWtchGrNm() {
		return wtchGrNm;
	}
	public void setWtchGrNm(String wtchGrNm) {
		this.wtchGrNm = wtchGrNm;
	}
	public String getAwardInfo() {
		return awardInfo;
	}
	public void setAwardInfo(String awardInfo) {
		this.awardInfo = awardInfo;
	}
	public String getAccessRighter() {
		return accessRighter;
	}
	public void setAccessRighter(String accessRighter) {
		this.accessRighter = accessRighter;
	}
	public String getRightOwner() {
		return rightOwner;
	}
	public void setRightOwner(String rightOwner) {
		this.rightOwner = rightOwner;
	}
	public String getLicenseOption() {
		return licenseOption;
	}
	public void setLicenseOption(String licenseOption) {
		this.licenseOption = licenseOption;
	}
	public String getMaxUsageCount() {
		return maxUsageCount;
	}
	public void setMaxUsageCount(String maxUsageCount) {
		this.maxUsageCount = maxUsageCount;
	}
	public String getWtchGr() {
		return wtchGr;
	}
	public void setWtchGr(String wtchGr) {
		this.wtchGr = wtchGr;
	}
	public String getDlbrGr() {
		return dlbrGr;
	}
	public void setDlbrGr(String dlbrGr) {
		this.dlbrGr = dlbrGr;
	}
	public String getKeepPrtCd() {
		return keepPrtCd;
	}
	public void setKeepPrtCd(String keepPrtCd) {
		this.keepPrtCd = keepPrtCd;
	}
	public String getDataStatCd() {
		return dataStatCd;
	}
	public void setDataStatCd(String dataStatCd) {
		this.dataStatCd = dataStatCd;
	}
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
	public String getArchiveDd() {
		return archiveDd;
	}
	public void setArchiveDd(String archiveDd) {
		this.archiveDd = archiveDd;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getOrgPrdr() {
		return orgPrdr;
	}
	public void setOrgPrdr(String orgPrdr) {
		this.orgPrdr = orgPrdr;
	}
	public String getStaff() {
		return staff;
	}
	public void setStaff(String staff) {
		this.staff = staff;
	}
	public String getPrdtTypeCd() {
		return prdtTypeCd;
	}
	public void setPrdtTypeCd(String prdtTypeCd) {
		this.prdtTypeCd = prdtTypeCd;
	}
	public String getRegDd() {
		return regDd;
	}
	public void setRegDd(String regDd) {
		this.regDd = regDd;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getDataKind() {
		return dataKind;
	}
	public void setDataKind(String dataKind) {
		this.dataKind = dataKind;
	}
	public String getUseGradeCd() {
		return useGradeCd;
	}
	public void setUseGradeCd(String useGradeCd) {
		this.useGradeCd = useGradeCd;
	}
	public String getWorkStat() {
		return workStat;
	}
	public void setWorkStat(String workStat) {
		this.workStat = workStat;
	}
	public String getLockStat() {
		return lockStat;
	}
	public void setLockStat(String lockStat) {
		this.lockStat = lockStat;
	}
	public String getRltText1() {
		return rltText1;
	}
	public void setRltText1(String rltText1) {
		this.rltText1 = rltText1;
	}
	public String getRltText2() {
		return rltText2;
	}
	public void setRltText2(String rltText2) {
		this.rltText2 = rltText2;
	}
	public String getRltText3() {
		return rltText3;
	}
	public void setRltText3(String rltText3) {
		this.rltText3 = rltText3;
	}
	public byte[] getRltFile1() {
		return rltFile1;
	}
	public void setRltFile1(byte[] rltFile1) {
		this.rltFile1 = rltFile1;
	}
	public byte[] getRltFile2() {
		return rltFile2;
	}
	public void setRltFile2(byte[] rltFile2) {
		this.rltFile2 = rltFile2;
	}
	public byte[] getRltFile3() {
		return rltFile3;
	}
	public void setRltFile3(byte[] rltFile3) {
		this.rltFile3 = rltFile3;
	}
	public String getBgnTime() {
		return bgnTime;
	}
	public void setBgnTime(String bgnTime) {
		this.bgnTime = bgnTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getSchdWeekDd() {
		return schdWeekDd;
	}
	public void setSchdWeekDd(String schdWeekDd) {
		this.schdWeekDd = schdWeekDd;
	}
	public String getPgmCd() {
		return pgmCd;
	}
	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}
	public String getSelCont() {
		return selCont;
	}
	public void setSelCont(String selCont) {
		this.selCont = selCont;
	}
	public String getBuyPrc() {
		return buyPrc;
	}
	public void setBuyPrc(String buyPrc) {
		this.buyPrc = buyPrc;
	}
	public String getPrdtr() {
		return prdtr;
	}
	public void setPrdtr(String prdtr) {
		this.prdtr = prdtr;
	}
	public String getGameNm() {
		return gameNm;
	}
	public void setGameNm(String gameNm) {
		this.gameNm = gameNm;
	}
	public String getCmntr() {
		return cmntr;
	}
	public void setCmntr(String cmntr) {
		this.cmntr = cmntr;
	}
	public long getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(long totCnt) {
		this.totCnt = totCnt;
	}
	public String getBuyCont() {
		return buyCont;
	}
	public void setBuyCont(String buyCont) {
		this.buyCont = buyCont;
	}
	public String getWordCd() {
		return wordCd;
	}
	public void setWordCd(String wordCd) {
		this.wordCd = wordCd;
	}
	public String getViewGr() {
		return viewGr;
	}
	public void setViewGr(String viewGr) {
		this.viewGr = viewGr;
	}
	public String getRlyBrd() {
		return rlyBrd;
	}
	public void setRlyBrd(String rlyBrd) {
		this.rlyBrd = rlyBrd;
	}
	public String getWorkSeq() {
		return workSeq;
	}
	public void setWorkSeq(String workSeq) {
		this.workSeq = workSeq;
	}
	public String getIngestStatus() {
		return ingestStatus;
	}
	public void setIngestStatus(String ingestStatus) {
		this.ingestStatus = ingestStatus;
	}
	public String getIngestDd() {
		return ingestDd;
	}
	public void setIngestDd(String ingestDd) {
		this.ingestDd = ingestDd;
	}
	
}
