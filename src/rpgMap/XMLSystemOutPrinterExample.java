package rpgMap;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class XMLSystemOutPrinterExample{
	static String[] signs;
    public static void main (String argv []){
    try {

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File("src/data/text/signs.xml"));

            // normalize text representation
            doc.getDocumentElement ().normalize ();
            System.out.println ("Root element of the doc is " + 
                 doc.getDocumentElement().getNodeName());


            NodeList listOfSigns = doc.getElementsByTagName("sign");
            int totalSigns = listOfSigns.getLength();
            System.out.println("Total no of signs : " + totalSigns);
            signs = new String[listOfSigns.getLength()];
            for(int s=0; s<listOfSigns.getLength() ; s++){


                Node firstSignNode = listOfSigns.item(s);
                if(firstSignNode.getNodeType() == Node.ELEMENT_NODE){


                    Element firstSignElement = (Element)firstSignNode;

                    //-------
                    NodeList idList = firstSignElement.getElementsByTagName("ID");
                    Element idElement = (Element)idList.item(0);

                    NodeList textIDList = idElement.getChildNodes();
                    signs[s]=((Node)textIDList.item(0)).getNodeValue().trim();
                    //System.out.println("ID : " + 
                      //     ((Node)textIDList.item(0)).getNodeValue().trim());

                    //-------
                    NodeList textList = firstSignElement.getElementsByTagName("text");
                    Element signElement = (Element)textList.item(0);

                    NodeList textTextist = signElement.getChildNodes();
                  //  System.out.println("Last Name : " + 
                  //         ((Node)textTextist.item(0)).getNodeValue().trim());

                    System.out.println(signs[s]);
                }//end of if clause


            }//end of for loop with s var


        }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
        //System.exit (0);

    }//end of main


}