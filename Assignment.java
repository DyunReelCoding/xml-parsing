import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Assignment {

    public static void main(String[] args) {

        try {
            File inputFile = new File("car.xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            Node cars = doc.getFirstChild();

            // Create new XML elements for SUV, Electric Car, Compact Car, and Convertible
            Element suv = doc.createElement("suv");
            suv.setAttribute("company", "Jeep");
            suv.appendChild(createCarNameElement(doc, "Jeep Grand Cherokee"));
            suv.appendChild(createCarNameElement(doc, "Jeep Wrangler"));
            suv.appendChild(createCarNameElement(doc, "Jeep Compass"));

            Element electricCar = doc.createElement("electric_car");
            electricCar.setAttribute("company", "Tesla");
            electricCar.appendChild(createCarNameElement(doc, "Tesla Model 3"));
            electricCar.appendChild(createCarNameElement(doc, "Tesla Model S"));
            electricCar.appendChild(createCarNameElement(doc, "Tesla Model X"));

            Element compactCar = doc.createElement("compact_car");
            compactCar.setAttribute("company", "Honda");
            compactCar.appendChild(createCarNameElement(doc, "Honda Civic"));
            compactCar.appendChild(createCarNameElement(doc, "Honda Fit"));
            compactCar.appendChild(createCarNameElement(doc, "Honda Accord"));

            Element convertible = doc.createElement("convertible");
            convertible.setAttribute("company", "Porsche");
            convertible.appendChild(createCarNameElement(doc, "Porsche 911 Carrera Cabriolet"));
            convertible.appendChild(createCarNameElement(doc, "Porsche 718 Boxster"));
            convertible.appendChild(createCarNameElement(doc, "Porsche 911 Targa"));

            // Append the new elements to the "cars" element
            cars.appendChild(suv);
            cars.appendChild(electricCar);
            cars.appendChild(compactCar);
            cars.appendChild(convertible);

            // Save the modified XML to a file with proper formatting
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes"); // Enable indentation
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); // Indent by 2 spaces
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("modified_car.xml"));
            transformer.transform(source, result);

            System.out.println("XML file modified and saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Element createCarNameElement(Document doc, String carName) {
        Element carname = doc.createElement("carname");
        carname.appendChild(doc.createTextNode(carName));
        return carname;
    }
}
