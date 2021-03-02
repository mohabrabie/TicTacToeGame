package dbconnection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerConnection {
    private Socket socket;
    private DataInputStream dis;
    private PrintStream ps;
    private int port = 55111;
    //private ObjectMapper mapper;

    public void startConnection() throws IOException
    {
        socket = new Socket("127.0.0.1", port);
        dis = new DataInputStream(socket.getInputStream());
        ps = new PrintStream(socket.getOutputStream());
    }
    public void serialaize(String requestType,Player p)
    {
        Map<String, Player> elements = new HashMap();
        elements.put(requestType, p);
        //SortedMap<String, Object> revelements = new TreeMap();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(elements);
            System.out.println(json);
            ps.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    public Map<String, Player> deserialize() throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String replyMsg = dis.readLine();
        Map<String, Player> elements = objectMapper.readValue(replyMsg,new TypeReference<Map<String, Player>>() {});

        return elements;
    }
    public Map<String, ArrayList<Player>> deserializeList()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String replyMsg = null;
        Map<String, ArrayList<Player>> elements;
        try {
            replyMsg = dis.readLine();
            System.out.println(replyMsg);
            elements = objectMapper.readValue(replyMsg,new TypeReference<Map<String, ArrayList<Player>>>() {});

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        return elements;
    }

    public void closeConnection()
    {
        try
        {
            ps.close();
            dis.close();
            socket.close();
        } catch(Exception ex)
        {
            //ex.printStackTrace();
            System.out.println("error closing the connection");
        }
    }
}
