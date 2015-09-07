package com.konantech.search.data;

/** 검색 결과  Value Object.
 * @author KONAN Technology
 * @since 2010.04.01
 * @version 1.0
 */

public class ResultVO {
	/** 총 검색결과 수 */
    private int total;
    
    /** 검색결과로 가져온 레코드 수 */
    private int rows;
    
    /** 검색결과 필드수 */
    private int cols;

    /** score */
    private int[] scores;

    /** rowID */
    private int[] rowIds;
    
    /** 검색결과 레코드 */
    private String[][] fdata;	
    
    /** 검색결과 필드명 **/
    private String[] colNames;
    
    private int groupCount;
    
	private int[] groupSize;
    
    private String[][] groupResult;
    
	
	public String[] getColNames() {
		return colNames;
	}

	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

	public int getCols() {
		return cols;
	}
	
	public void setCols(int cols) {
		this.cols = cols;
	}

	public String[][] getFdata() {
		return fdata;
	}

	public void setFdata(String[][] fdata) {
		this.fdata = fdata;
	}

	public int[] getRowIds() {
		return rowIds;
	}

	public void setRowIds(int[] rowIds) {
		this.rowIds = rowIds;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int[] getScores() {
		return scores;
	}

	public void setScores(int[] scores) {
		this.scores = scores;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
    public int getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	public int[] getGroupSize() {
		return groupSize;
	}

	public void setGroupSize(int[] groupSize) {
		this.groupSize = groupSize;
	}

	public String[][] getGroupResult() {
		return groupResult;
	}

	public void setGroupResult(String[][] groupResult) {
		this.groupResult = groupResult;
	}

	
}