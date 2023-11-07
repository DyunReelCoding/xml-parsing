import java.io.File;
import java.util.Scanner;
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

public class Assignment2 {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("grades.xml");
            Document doc;

            if (xmlFile.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.parse(xmlFile);
            } else {
                // Create a new XML document with the root element if the file doesn't exist
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                doc = dBuilder.newDocument();
                doc.appendChild(doc.createElement("grades"));
            }

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. Add Grade");
                System.out.println("2. Edit Grade");
                System.out.println("3. Remove Grade");
                System.out.println("4. View Grades");
                System.out.println("5. Save and Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addGrade(doc, scanner);
                        break;
                    case 2:
                        editGrade(doc, scanner);
                        break;
                    case 3:
                        removeGrade(doc, scanner);
                        break;
                    case 4:
                        viewGrades(doc);
                        break;
                    case 5:
                        saveAndExit(doc, xmlFile);
                        return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addGrade(Document doc, Scanner scanner) {
        Element gradeElement = doc.createElement("grade");

        System.out.print("Enter subject: ");
        String subject = scanner.next();

        System.out.print("Enter grade: ");
        String grade = scanner.next();

        gradeElement.setAttribute("subject", subject);
        gradeElement.setTextContent(grade);

        doc.getDocumentElement().appendChild(gradeElement);
    }

    private static void editGrade(Document doc, Scanner scanner) {
        System.out.print("Enter subject to edit: ");
        String subject = scanner.next();

        NodeList gradeNodes = doc.getElementsByTagName("grade");

        for (int i = 0; i < gradeNodes.getLength(); i++) {
            Element gradeElement = (Element) gradeNodes.item(i);
            String currentSubject = gradeElement.getAttribute("subject");

            if (currentSubject.equals(subject)) {
                System.out.print("Enter new grade: ");
                String newGrade = scanner.next();

                gradeElement.setTextContent(newGrade);
                System.out.println("Grade for " + subject + " updated.");
                return;
            }
        }

        // If the subject doesn't exist, create a new grade entry
        System.out.print("Enter new grade: ");
        String newGrade = scanner.next();
        addGrade(doc, subject, newGrade);
        System.out.println("Grade for " + subject + " added.");
    }

    private static void addGrade(Document doc, String subject, String grade) {
        Element gradeElement = doc.createElement("grade");
        gradeElement.setAttribute("subject", subject);
        gradeElement.setTextContent(grade);
        doc.getDocumentElement().appendChild(gradeElement);
    }

    private static void removeGrade(Document doc, Scanner scanner) {
        System.out.print("Enter subject to remove: ");
        String subject = scanner.next();

        NodeList gradeNodes = doc.getElementsByTagName("grade");

        for (int i = 0; i < gradeNodes.getLength(); i++) {
            Element gradeElement = (Element) gradeNodes.item(i);
            String currentSubject = gradeElement.getAttribute("subject");

            if (currentSubject.equals(subject)) {
                gradeElement.getParentNode().removeChild(gradeElement);
                System.out.println("Grade for " + subject + " removed.");
                return;
            }
        }
        System.out.println("Subject not found.");
    }

    private static void viewGrades(Document doc) {
        NodeList gradeNodes = doc.getElementsByTagName("grade");

        if (gradeNodes.getLength() > 0) {
            for (int i = 0; i < gradeNodes.getLength(); i++) {
                Element gradeElement = (Element) gradeNodes.item(i);
                String subject = gradeElement.getAttribute("subject");
                String grade = gradeElement.getTextContent();
                System.out.println(subject + ": " + grade);
            }
        } else {
            System.out.println("No grades found.");
        }
    }

    private static void saveAndExit(Document doc, File xmlFile) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
            System.out.println("Grades saved. Exiting.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
