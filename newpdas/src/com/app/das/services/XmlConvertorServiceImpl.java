package com.app.das.services;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

import com.app.das.business.transfer.Das;
import com.app.das.business.transfer.MetaDataInfo;
import com.app.das.business.transfer.Node;

public class XmlConvertorServiceImpl<T> implements XmlConvertorService<T> {

	private static Logger logger = Logger.getLogger(XmlConvertorServiceImpl.class);

	private static final JAXBContext jaxbContext = initContext();
	private static JAXBContext initContext() {
		try {
			return JAXBContext.newInstance(
						Das.class,
						Node.class,
						MetaDataInfo.class
					);
		} catch (JAXBException e) {
			logger.error("initContext error", e);
			return null;
		}
	}

	public XmlConvertorServiceImpl() {
		if(jaxbContext == null) initContext();
	}

	@SuppressWarnings("restriction")
	public String createMarshaller(T t) throws JAXBException {
		StringWriter writer = new StringWriter();

		Marshaller m = jaxbContext.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		/*XMLOutputFactory xof = XMLOutputFactory.newInstance();
		CDataXMLStreamWriter cdataStreamWriter = null;
		try {
			XMLStreamWriter streamWriter = xof.createXMLStreamWriter(writer);
			cdataStreamWriter = new CDataXMLStreamWriter( streamWriter );
			
			m.marshal(t, cdataStreamWriter);
			
			cdataStreamWriter.flush();
			cdataStreamWriter.close();
		} catch (XMLStreamException e) {
			throw new JAXBException(e);
		}*/
		
		m.marshal(t, writer);
		
		return writer.toString();
	}

	@SuppressWarnings("unchecked")
	public T unMarshaller(String xml) throws JAXBException {
		Unmarshaller unmarshal = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(xml);
		InputSource source = new InputSource(reader);
		T t = (T)unmarshal.unmarshal(source);
		
		return t;
	}

}
