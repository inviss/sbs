package com.sbs.das.commons.system;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sbs.das.commons.utils.XStreamCDATA;
import com.sbs.das.dto.ops.CartContent;
import com.sbs.das.dto.ops.Corner;
import com.sbs.das.dto.ops.Corners;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.dto.ops.DownCart;
import com.sbs.das.dto.ops.Metadata;
import com.sbs.das.dto.xml.ArchiveError;
import com.sbs.das.dto.xml.ArchiveRequest;
import com.sbs.das.dto.xml.ArchiveResponse;
import com.sbs.das.dto.xml.Content;
import com.sbs.das.dto.xml.ContentMap;
import com.sbs.das.dto.xml.DBTable;
import com.sbs.das.dto.xml.Das;
import com.sbs.das.dto.xml.Master;
import com.sbs.das.dto.xml.Program;
import com.sbs.das.dto.xml.Report;
import com.sbs.das.dto.xml.RequestInfo;
import com.sbs.das.dto.xml.ServerResource;
import com.sbs.das.dto.xml.StorageInfo;
import com.sbs.das.dto.xml.WorkLog;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * <p>Object를 기준으로 XML 생성</p>
 * @author Kang Myeong Seong
 * <pre>
 * 
 * </pre>
 */
public class XmlStreamImpl implements XmlStream {

	private Logger logger = Logger.getLogger(getClass());
	
	private List<Class> clsList = new ArrayList<Class>();
	private XStream xstream;

	public XmlStreamImpl() {
		/*
		 * XML Parsing 할때 일반적으로 DomDriver를 사용하지만, XppDriver가 속도면에서 좀더 빠르단다.
		 * alias명으로 '_' 언더바(underscore)가 존재하면 '__'로 두개가 출력이 된다. 치환을 해줘야할 필요가 있다.
		 */
		xstream = new XStream(new PureJavaReflectionProvider(), new XppDriver(new XmlFriendlyReplacer("__", "_")));
		/*
		xstream = new XStream(new PureJavaReflectionProvider(), new XppDriver(new XmlFriendlyReplacer("__", "_")) {
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					boolean cdata = false;  
                    Class<?> targetClass = null;  
                    @Override  
                    public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {  
                    	System.out.println("clazz====>"+clazz);
                        super.startNode(name, clazz);  
                        System.out.println("name====>"+name);
                        System.out.println("targetClass====>"+targetClass);
                        if(!name.equals("xml")){
                            cdata = needCDATA(targetClass, name);  
                        }else{  
                            targetClass = clazz;  
                        }  
                    }  
  
                    @Override  
                    protected void writeText(QuickWriter writer, String text) {  
                        if (cdata) {  
                            writer.write("<![CDATA[" + text + "]]>");  
                        } else {  
                            writer.write(text);  
                        }  
                    }
				};
			}
		});
		*/
		xstream.autodetectAnnotations(true);
		
		/** DAS Workflow에서 사용하는 Class를 모두 등록 **/
		clsList.add(Das.class);
		clsList.add(RequestInfo.class);
		clsList.add(DBTable.class);
		clsList.add(Program.class);
		clsList.add(Master.class);
		clsList.add(Content.class);
		clsList.add(ContentMap.class);
		clsList.add(Report.class);
		clsList.add(WorkLog.class);
		
		/** Diva Connector와 관련된 Class를 모두 등록 **/
		clsList.add(ArchiveError.class);
		clsList.add(ArchiveRequest.class);
		clsList.add(ArchiveResponse.class);
		
		/** 2012.06.05 added **/
		clsList.add(StorageInfo.class);
		clsList.add(ServerResource.class);
		
		/** 2016.01.15 added **/
		clsList.add(Data.class);
		clsList.add(Program.class);
		clsList.add(Metadata.class);
		clsList.add(Corners.class);
		clsList.add(Corner.class);
		clsList.add(DownCart.class);
		clsList.add(CartContent.class);
		
		try {
			setAnnotationAlias(clsList);
		} catch (Exception e) {
			logger.error("###################### ["+ e.getMessage()+"] ###########################");
		}
		
	}

	/**
	 * xml을 받아서 파라미터로 Object로 변환
	 */
	public Object fromXML(String xml) {
		return xstream.fromXML(xml);
	}
	
	/**
	 * xml을 받아서 파라미터로 Object로 변환
	 */
	public Object fromXML(String xml, Class cls) {
		setAnnotationAlias(cls);
		return xstream.fromXML(xml);
	}

	/**
	 * 클래스 리스트를 받아서 xml로 생성
	 */
	public Object fromXML(String xml, List<Class> clsList)
	throws ClassNotFoundException {
		setAnnotationAlias(clsList);
		return xstream.fromXML(xml);
	}

	public String toXML(Object obj) {
		return xstream.toXML(obj);
	}
	
	public String toXML(Object obj, Class cls) {
		setAnnotationAlias(cls);
		return xstream.toXML(obj);
	}
	
	public String toXML(Object obj, List<Class> clsList) throws ClassNotFoundException {
		setAnnotationAlias(clsList);
		return xstream.toXML(obj);
	}

	public void setAlias(String name, Class cls) {
		xstream.alias(name, cls);
	}

	public void setAnnotationAlias(Class cls) {
		xstream.processAnnotations(cls);
	}

	public void setAnnotationAlias(List<Class> clsList)
	throws ClassNotFoundException {
		Iterator<Class> it = clsList.iterator();
		while(it.hasNext()){
			Class cls = it.next();
			if(logger.isDebugEnabled()) {
				logger.debug("class_to_alias - "+cls.getName());
			}
			xstream.processAnnotations(cls);
		}
	}
	
	private static boolean needCDATA(Class<?> targetClass, String fieldAlias){  
        boolean cdata = false;  
        System.out.println("targetClass===>"+targetClass+", fieldAlias====>"+fieldAlias);
        //first, scan self  
        cdata = existsCDATA(targetClass, fieldAlias);  
        if(cdata) return cdata;  
        //if cdata is false, scan supperClass until java.lang.Object  
        Class<?> superClass = targetClass.getSuperclass();  
        while(!superClass.equals(Object.class)){  
            cdata = existsCDATA(superClass, fieldAlias);  
            if(cdata) return cdata;  
            superClass = superClass.getClass().getSuperclass();  
        }  
        return false;  
    }
	
	private static boolean existsCDATA(Class<?> clazz, String fieldAlias){ 
		System.out.println("class==========>"+clazz);
        //scan fields
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("field============>"+fields);
        for (Field field : fields) {  
            //1. exists XStreamCDATA  
            if(field.getAnnotation(XStreamCDATA.class) != null ){  
                XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);  
                //2. exists XStreamAlias  
                if(null != xStreamAlias){  
                    if(fieldAlias.equals(xStreamAlias.value()))//matched  
                        return true;  
                }else{// not exists XStreamAlias  
                    if(fieldAlias.equals(field.getName()))  
                        return true;  
                }  
            }  
        }  
        return false;  
    }

}
