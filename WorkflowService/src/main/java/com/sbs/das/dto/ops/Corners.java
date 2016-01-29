package com.sbs.das.dto.ops;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("corners")
public class Corners {

	@XStreamImplicit(itemFieldName="corner")
	private List<Corner> corners = new ArrayList<Corner>();

	public List<Corner> getCorners() {
		return corners;
	}
	public void setCorners(List<Corner> corners) {
		this.corners = corners;
	}
	public void addCorner(Corner corner) {
		this.corners.add(corner);
	}
	
}
