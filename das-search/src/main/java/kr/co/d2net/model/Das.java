package kr.co.d2net.model;

import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="das")
@XmlAccessorType(XmlAccessType.FIELD)
public class Das {
	
	@XmlElement(name="orderBy")
	private String orderBy;
	@XmlElement(name="startNum")
	private Integer startNum = 1;
	@XmlElement(name="pageSize")
	private Integer pageSize = 1;
	@XmlElement(name="total")
	private Integer total = 0;
	@XmlElement(name="rows")
	private Integer rows = 0;
	
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public Integer getStartNum() {
		return startNum;
	}
	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	@XmlElement(name="searchInfo")
	private SearchInfo searchInfo;

	public SearchInfo getSearchInfo() {
		return searchInfo;
	}
	public void setSearchInfo(SearchInfo searchInfo) {
		this.searchInfo = searchInfo;
	}
	
	@XmlElement(name="DAS_SEARCH")
	private List<DasSearch> searchList = new ArrayList<DasSearch>();

	public List<DasSearch> getSearchList() {
		return searchList;
	}
	public void setSearchList(List<DasSearch> searchList) {
		this.searchList = searchList;
	}
	public void addSearchList(DasSearch dasSearch) {
		this.searchList.add(dasSearch);
	}
	
	@XmlElement(name="tokeninfo")
	private TokenInfo tokenInfo;

	public TokenInfo getTokenInfo() {
		return tokenInfo;
	}
	public void setTokenInfo(TokenInfo tokenInfo) {
		this.tokenInfo = tokenInfo;
	}
	
	@XmlElement(name="meta")
	private MetaDataInfo metaDataInfo;
	public MetaDataInfo getMetaDataInfo() {
		return metaDataInfo;
	}
	public void setMetaDataInfo(MetaDataInfo metaDataInfo) {
		this.metaDataInfo = metaDataInfo;
	}
	
	@XmlElement(name="corner")
	private Corner corner;
	public Corner getCorner() {
		return corner;
	}
	public void setCorner(Corner corner) {
		this.corner = corner;
	}
	
	@XmlElement(name="relation")
	private Relation relation;
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	@XmlElement(name="pdsarhchive")
	private Archive archive;
	public Archive getArchive() {
		return archive;
	}
	public void setArchive(Archive archive) {
		this.archive = archive;
	}
	
	@XmlElement(name="info")
	private ContentInfo contentInfo;
	public ContentInfo getContentInfo() {
		return contentInfo;
	}
	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}
	
	@XmlElement(name="node")
	private List<Node> nodes = new ArrayList<Node>();
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public void addNode(Node node) {
		this.nodes.add(node);
	}
	
	@XmlElement(name="downCart")
	private DownCart downCart;
	public DownCart getDownCart() {
		return downCart;
	}
	public void setDownCart(DownCart downCart) {
		this.downCart = downCart;
	}
	
	@XmlElement(name="cartContents")
	private CartContent cartContent;
	public CartContent getCartContent() {
		return cartContent;
	}
	public void setCartContent(CartContent cartContent) {
		this.cartContent = cartContent;
	}
	
	@XmlElement(name="cartItem")
	private CartItem cartItem;
	public CartItem getCartItem() {
		return cartItem;
	}
	public void setCartItem(CartItem cartItem) {
		this.cartItem = cartItem;
	}
	
	@XmlElement(name="cartItems")
	private List<CartItem> cartItems = new ArrayList<CartItem>();
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public void addCartItem(CartItem cartItem) {
		this.cartItems.add(cartItem);
	}
	
	@XmlElement(name="monitoring")
	private List<Monitoring> monitorings = new ArrayList<Monitoring>();
	public List<Monitoring> getMonitorings() {
		return monitorings;
	}
	public void setMonitorings(List<Monitoring> monitorings) {
		this.monitorings = monitorings;
	}
	public void addMonitorings(Monitoring monitoring) {
		this.monitorings.add(monitoring);
	}
	
	@XmlElement(name="storage")
	private Storage storage;
	public Storage getStorage() {
		return storage;
	}
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	
}
