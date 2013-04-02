package rpgMap;

import java.io.File;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLReader {
	static String[] signs;

	XMLReader() {
		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder
					.parse(new File("src/data/text/signs.xml"));

			NodeList listOfSigns = doc.getElementsByTagName("sign");
			
			signs = new String[listOfSigns.getLength()];
			
			for (int s = 0; s < listOfSigns.getLength(); s++) {
				
				Node signNode = listOfSigns.item(s);
				
				if (signNode.getNodeType() == Node.ELEMENT_NODE) {
					Element firstSignElement = (Element) signNode;
					
					// -------
					NodeList idList = firstSignElement
							.getElementsByTagName("ID");
					Element idElement = (Element) idList.item(0);

					//NodeList textIDList = idElement.getChildNodes();
					// System.out.println("ID : " +
					// ((Node)textIDList.item(0)).getNodeValue().trim());

					// -------
					NodeList textList = firstSignElement
							.getElementsByTagName("text");
					Element signElement = (Element) textList.item(0);

					NodeList textTextist = signElement.getChildNodes();
					// System.out.println("Last Name : " +
					// ((Node)textTextist.item(0)).getNodeValue().trim());
					signs[s]=((Node)textTextist.item(0)).getNodeValue().trim();
				}// end of if clause

			}// end of for loop with s var

		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());

		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();

		} catch (Throwable t) {
			t.printStackTrace();
		}
		// System.exit (0);

	}// end of main
}
