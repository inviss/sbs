package com.sbs.das.dto.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("priview")
public class Preview {
	
	@XStreamImplicit(itemFieldName="file")
	List<PreviewFile> previews = new ArrayList<PreviewFile>();

	public List<PreviewFile> getPreviews() {
		return previews;
	}

	public void setPreviews(List<PreviewFile> previews) {
		this.previews = previews;
	}
	
	public void addPreviews(PreviewFile previewFile) {
		previews.add(previewFile);
	}
}
