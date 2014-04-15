package com.luxoft.bankapp.networking;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.DataVerifyException;
import com.luxoft.bankapp.model.Client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Sergey Popov on 15.04.14.
 */
public class BankClientMock extends BankClientBase {
    //Socket requestSocket;
    //ObjectOutputStream out;
    //ObjectInputStream in;
    String message;
    //String value;
    //int cmdNumber;
    private Client client;
    //static final String SERVER = "localhost";
    //List<String> commands;

    public BankClientMock(Client client) {
        this.client = client;
    }

    public void runWithdrawCommand(float amount) {
        //InputStreamReader streamReader = new InputStreamReader(System.in);
        //BufferedReader bufferedReader = new BufferedReader(streamReader);

        //BankClientMock clientMock = new BankClientMock();
        if (client == null) {
            System.out.println("Client null");
            return;
        }
        commands = ClientServerCommands.getBankClientCommands();
        try {
            requestSocket = new Socket(SERVER, PORT);
            //System.out.println("Connected to localhost in port 2014");
            // 2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            // 3: Communicating with the server
            //do {
            try {
                message = (String) in.readObject();

                sendMessage("authorize " + client.getName());
                message = (String) in.readObject();
                if (!message.equals("Authorization OK")) {
                    throw new ClientNotFoundException(client.getName());
                }

                sendMessage("withdraw " + amount);
                message = (String) in.readObject();
                sendMessage("exit");

            } catch (ClassNotFoundException classNot) {
                System.err.println("dao received in unknown format");
            } catch (ClientNotFoundException e) {
                e.printStackTrace();
            }
            //} while (!message.equals("exit"));
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            // 4: Closing connection
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
