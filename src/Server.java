import org.w3c.dom.Document;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jakubinyi on 2017.04.04..
 */
public class Server {

      public void run() throws Exception {
          ServerSocket serverSocket = new ServerSocket(6789);
          Socket socket = serverSocket.accept();
          ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
          Document doc = (Document)in.readObject();
          System.out.println("Recieved document!");
      }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
