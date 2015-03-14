package controller.tfg.eprail;

import javax.xml.XMLConstants; 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.*; 
import org.w3c.dom.Document;
import org.xml.sax.SAXException; 
import java.io.*; 

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			// crear y configurar la factory de parsers de documentos XML
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);  // activar soporte para namespaces

			// cargar el documento XML
			DocumentBuilder parser = dbf.newDocumentBuilder();
			Document doc = parser.parse(new File("C:\\Users\\Javier\\Desktop\\Manifest.xml"));

			// crear una SchemaFactory preparada para interpretar esquemas XML W3C
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			// cargar el esquema XSD
			Schema schema = sf.newSchema(new File("C:\\Users\\Javier\\Desktop\\Manifest.xsd"));

			// crear el objeto validator, que será el responsable de validar el XML
			Validator validator = schema.newValidator();

			// validar el documento XML
			validator.validate(new DOMSource(doc));

			// si se llega a este punto, el documento es válido
			System.out.println("DOCUMENTO VÁLIDO");
		}
		catch (SAXException e)
		{
			// esta excepción indica fallo de validación
			System.err.println("DOCUMENTO INVÁLIDO");
			e.printStackTrace();
		}
		catch (ParserConfigurationException e)
		{
			// errores en la configuración del parser
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// errores de lectura
			e.printStackTrace();
		}
	}

}
