import org.w3c.dom.Element;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by jakubinyi on 2017.04.04..
 */
public class Client {

    public void run() throws Exception {
        Document document = createXML("30", "40");
        Socket socket = new Socket("localhost", 6789);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(document);
        System.out.println("Send document!");
    }

    public Document createXML(String x, String y) {
        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("measurements");
            Element measurement1 = document.createElement("measurement");
            Element measurement2 = document.createElement("measurement");
            Element rate1 = document.createElement("rate");
            Element rate2 = document.createElement("rate");

            rate2.setTextContent(x);
            rate1.setTextContent(y);

            measurement1.appendChild(rate1);
            measurement2.appendChild(rate2);
            root.appendChild(measurement1);
            root.appendChild(measurement2);
            document.appendChild(root);

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
