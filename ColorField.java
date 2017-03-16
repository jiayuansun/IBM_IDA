package color;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ColorField {

	/**
	 * @param args
	 *            args[0] csv file name args[1] original model name args[2] new
	 *            model name
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String csvFile = args[0];
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			// open xml
			File inputFile = new File(args[1]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("attributes");
			NodeList nList_cld = doc.getElementsByTagName("children");

			br = new BufferedReader(new FileReader(csvFile));
			String headerLine = br.readLine();
			HashMap<String, String> csvMap = new HashMap<String, String>();
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] fieldinfo = line.split(cvsSplitBy);
				String entityname = fieldinfo[0];
				String fieldname = fieldinfo[1]; // key
				String fieldcate = fieldinfo[2]; // value
				if (!fieldcate.equals("NONE")){
					csvMap.put(fieldname, fieldcate);
					System.out.println("Entity " + fieldinfo[0] + " , Field="+ fieldinfo[1] + "");
				}

			}

			// if the field is marked as any one of the value other than None
			// if(fieldcate.equals(PRC_CATE) || fieldcate.equals(RAGE_CATE)
			// ||fieldcate.equals(AS_CATE)||fieldcate.equals(MTRC_CATE))
			// String colorvalue=colorfield(fieldcate);
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//get the cate from the hashmap
					String fieldcate=csvMap.get(eElement.getAttribute("name"));
					
					if  (fieldcate != null) {
					if (nList_cld != null && nList_cld.getLength() > 0 ) {
						System.out.println(fieldcate);
						String colorvalue=colorfield(fieldcate);
						for (int j = 0; j < nList_cld.getLength(); j++) {
							Element el = (org.w3c.dom.Element) nList_cld.item(j);
							if (el.hasAttribute("element")&& el.getAttribute("element").equals(eElement.getAttribute("xmi:id"))) {
								// set the color
								el.setAttribute("fontColor", colorvalue);
								// System.out.println(el.getAttribute("fontColor"));

							}
						}
					}

				}
				}
				

			}

			prettyPrint(doc, args[2]);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private static final String PRC_CATE = "PRC";
	private static final String RAGE_CATE = "RATING";
	private static final String AS_CATE = "ASSESS";
	private static final String MTRC_CATE = "MTRC";

	public static String colorfield(String category) {
		if (category.equals(RAGE_CATE)) {
			return "10498160";
		}
		if (category.equals(PRC_CATE)) {
			return "5287936";
		}

		if (category.equals(AS_CATE)) {
			return "49407";
		}

		// 15773696
		if (category.equals(MTRC_CATE)) {
			return "15773696";
		}
		return "0";
	}

	public static final void prettyPrint(Document xml, String outputfilename)
			throws Exception {

		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		Writer out = new StringWriter();
		tf.transform(new DOMSource(xml), new StreamResult(out));
		// System.out.println(out.toString());

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(outputfilename));

			// String content = "This is the content to write into file\n";

			bw.write(out.toString());

			// no need to close it.
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
