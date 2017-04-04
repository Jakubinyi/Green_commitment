import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by jakubinyi on 2017.04.04..
 */
public class Client {

    public void run() throws Exception {
        Document document = createXML();

        Socket socket = new Socket("localhost", 6789);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(document);
        System.out.println("Send document!");
        out.flush();
        out.close();
        socket.close();
    }

    public Document createXML() {
        Document document = null;
        Integer valueX = 1;
        Integer valueY = 2;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("measure");
            doc.appendChild(rootElement);

            //generate datas
            for(int i = 1; i <= 31; i++){
                Element rate = doc.createElement("rate");
                Attr y = doc.createAttribute("y");
                valueY = (int) (Math.random()*100);
                y.setValue(valueY.toString());
                rate.setAttributeNode(y);

                Attr x = doc.createAttribute("x");
                valueX = i;
                x.setValue(valueX.toString());
                rate.setAttributeNode(x);
                rootElement.appendChild(rate);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/sensordata.xml"));
            transformer.transform(source, result);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
