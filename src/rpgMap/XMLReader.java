package rpgMap;

import java.io.IOException;
import java.io.StringReader;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLReader {
	String[] shoe;

	XMLReader() {
		load();

	}

	public void load() {
		String xml = "<signs>   <sign>     <ID>1</ID>     <text>1st Sign</text>   </sign>     <sign>     <ID>2</ID>     <text>2nd Sign</text>     </sign> </signs>";

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml));
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(is);
			NodeList listOfSigns = doc.getElementsByTagName("sign");

			shoe = new String[listOfSigns.getLength()];

			for (int s = 0; s < listOfSigns.getLength(); s++) {

				Node signNode = listOfSigns.item(s);

				if (signNode.getNodeType() == Node.ELEMENT_NODE) {
					Element firstSignElement = (Element) signNode;

					// -------

					NodeList idList = firstSignElement
							.getElementsByTagName("ID");
					Element idElement = (Element) idList.item(0);

					//
					NodeList textIDList = idElement.getChildNodes(); //
					System.out.println("ID : " + //
							((Node) textIDList.item(0)).getNodeValue().trim());

					// -------
					NodeList textList = firstSignElement
							.getElementsByTagName("text");
					Element signElement = (Element) textList.item(0);

					NodeList textTextist = signElement.getChildNodes(); //
					System.out.println("Last Name : " + //
							((Node) textTextist.item(0)).getNodeValue().trim());
					shoe[s] = ((Node) textTextist.item(0)).getNodeValue()
							.trim();
				}// end of if clause

			}// end of for loop with s var
		} catch (SAXException e) {
			// handle SAXException
		} catch (IOException e) {
			// handle IOException
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	// System.exit (0);

}// end of main

