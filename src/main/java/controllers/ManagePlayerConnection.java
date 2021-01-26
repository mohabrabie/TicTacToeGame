package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.Player;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagePlayerConnection {

    private Socket socket;
    private DataInputStream dis;
    private PrintStream ps;
    private ServerSocket serverSocket;
    //private ObjectMapper mapper;
    public void startConnection(ServerSocket serverSocket) throws IOException
    {
        this.serverSocket = serverSocket;
        //serverSocket = new ServerSocket(5005);
        socket = serverSocket.accept();
        dis = new DataInputStream(socket.getInputStream());
        ps = new PrintStream(socket.getOutputStream());
    }
    public void serialaize(String isExist, Player p)
    {
        Map<String, Player> elements = new HashMap();
        elements.put(isExist, p);
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
    public void serialaizeList(String isExist, ArrayList<Player> list)
    {
        Map<String, ArrayList<Player>> elements = new HashMap();
        elements.put(isExist, list);
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
        String msg = dis.readLine();
        System.out.println(msg);

        // Convert from string to map<str,player>
        Map<String, Player> elements = objectMapper.readValue(msg,new TypeReference<Map<String, Player>>() {});
        System.out.println(elements.values().toArray()[0]);

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
            ex.printStackTrace();
        }
    }
}
