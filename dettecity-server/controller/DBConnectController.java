package controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONObject;

import commons.DataSource;
import model.ModelTestPool;
import socketserver.ThreadServer;
import view.TestPoolView;



public class DBConnectController {
	private ModelTestPool userModel;
	private TestPoolView shsView;
	private Scanner sc = new Scanner(System.in);
	private ServerSocket socketServeur;
	private Socket socketClient;
	private static int numberOfConnectedClients = 0; 


	public DBConnectController(TestPoolView v) throws SQLException, ClassNotFoundException {
		userModel = new ModelTestPool();
		shsView = v;
	}

	public void start() throws SQLException, IOException  {
		String rep = "";
		List<Connection> co =new ArrayList();
		Connection c = null; 

		System.out.println("Bonjour, Bienvenue sur votre serveur!"); 

		// Socket creation on the server side 
		socketServeur = new ServerSocket(6666);

		System.out.println("====================================================");
		System.out.println("=== Voici le nombre de clients connectés : " + numberOfConnectedClients); 
		System.out.println("====================================================");

		while(true) {

			try {
				// Waiting for customer connection 
				socketClient = socketServeur.accept();

				DBConnectController.clientsState(true); 

				// a connection is assigned to the client 

				co.add(DataSource.getConnection());
				//c = DataSource.getConnection();
				shsView.printScreen("Size of the pool: "+DataSource.getSize());
				shsView.printScreen("Number of connection asked: "+co.size());
				c = co.get(0);
				shsView.printScreen("Size of the pool: "+ DataSource.getSize());
				System.out.println("bonjour je vais traiter votre demande");


				// instanciation of thread client (with a socket  and a connection)
				ThreadServer client = new ThreadServer(socketClient,c);
				client.start(); 

			}

			catch(Exception ex){
				System.err.println(ex.getMessage());
				// path of the exception
				//ex.printStackTrace();
			}

		}

	}
	public static  synchronized void clientsState(boolean isNewConnection) {
		numberOfConnectedClients = (isNewConnection) ? (numberOfConnectedClients + 1) : (numberOfConnectedClients - 1);
		System.out.println("====================================================");
		System.out.println("=== Voici le nombre de clients connectés : " + numberOfConnectedClients); 
		System.out.println("====================================================");

	}

}