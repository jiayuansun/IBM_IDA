package color;
import java.io.File;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.FileWriter;



public class PrintFieldCategroy {
	
	
	/**
	 * @param args
	 * args[0] original model name
	 * args[1] csv file name
	 */
	
	public static void main(String[] args) {
		PrintFieldCategroy ptest=new PrintFieldCategroy();
       // ptest.printfield("C:\\Users\\js87951\\IBM\\rationalsdp\\workspace\\CASP\\Casp Logical Data Model.ldm");
		ptest.printfield(args[0],args[1]);
		
	}
	
	
	//private static final String FILENAME = "C:\\Users\\js87951\\IBM\\rationalsdp\\workspace\\CASP\\Color Casp Logical Data Model.ldm";
	//private static final String FIELD_LIST = "C:\\Users\\js87951\\IBM\\rationalsdp\\workspace\\CASP\\1.csv";
	private static final String[] PRCLIST={"risk","process"};
	private static final String[] RATLIST={"rating"};
	private static final String[] ASLIST={"assess"};

	public static String checkfiledtype(String fieldname) {
		
		for(int i =0; i < PRCLIST.length; i++){
			 if(fieldname.contains(PRCLIST[i]))
		        {
		            return "PRC";
		        }
		}
		
		for(int i =0; i < RATLIST.length; i++){
			 if(fieldname.contains(RATLIST[i]))
		        {
		            return "RATING";
		        }
		}
		
		for(int i =0; i < ASLIST.length; i++){
			 if(fieldname.contains(ASLIST[i]))
		        {
		            return "ASSESS";
		        }
		}
		return "NONE";

	}
	
	public void printfield(String filename,String FIELD_LIST) {
		try {
		File inputFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("attributes");
		BufferedWriter bw = new BufferedWriter(new FileWriter(FIELD_LIST));
		bw.write("entity_name,field_name,category\n");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			Node nParNode=nNode.getParentNode();
			Element eParElement = (Element) nParNode;
			//print parent
			//System.out.println("Entity_name : "	+ eParElement.getAttribute("name"));
			Element eElement = (Element) nNode;
			//print child
			//
			//System.out.println("field_name : "	+ eElement.getAttribute("name"));
			String fieldcategory=checkfiledtype(eElement.getAttribute("name"));
			String fieldname=eElement.getAttribute("name");
			String entityname=eParElement.getAttribute("name");
			


			bw.append(entityname+","+fieldname+","+fieldcategory+"\n");
			
			
	
		}
		bw.close();
		
	}
	
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
}

