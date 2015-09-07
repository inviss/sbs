package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="node")
@XmlAccessorType(XmlAccessType.FIELD)
public class Node {
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="auth")
	private String auth;
	@XmlElement(name="depth")
	private Integer depth;
	@XmlElement(name="menuId")
	private Integer menuId;
	@XmlElement(name="permId")
	private Integer permId;
	
	
	public Integer getPermId() {
		return permId;
	}
	public void setPermId(Integer permId) {
		this.permId = permId;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}


	@XmlElement(name="node")
	List<Node> nodes = new ArrayList<Node>();
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public void addNode(Node node) {
		this.nodes.add(node);
	}
	
}
