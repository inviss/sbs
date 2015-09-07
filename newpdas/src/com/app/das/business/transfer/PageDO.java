package com.app.das.business.transfer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.app.das.business.transfer.DTO;

/**
 * 우리 시스템의 페이지 처리를 하는 화면에서 공통으로 사용되는 Page 관련 DataObject
 * @author ysk523
 *
 */
public class PageDO  extends DTO
{
    /**
     * Page를 구성하는 한 line에 해당하는 DTO를 넣는 List
     */
    private List pageItems;
    
    /**
     * 총 페이지 숫자.
     */
    private int totalPageCount;
    /**
     * 총 검색 숫자
     */
    private int totalCount;
	public PageDO() 
	{
		super();
		
		pageItems = new ArrayList();
	}

	public List getPageItems() {
		return pageItems;
	}

	public void setPageItems(List pageItems) {
		this.pageItems = pageItems;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
