/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hlabrana
 */
public class Sockets implements Runnable {
    String IP1;
    String IP2;
    int port1;
    int port2;
    /**
     */
    public Sockets(int p1,int p2,String m1,String m2){
        this.IP1 = m1;
        this.IP2 = m2;
        this.port1 = p1;
        this.port2 = p2;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.print("\n IP M1: ");
        Scanner in = new Scanner(System.in);
        String ipM1 = in.nextLine();
        
        
        System.out.print("\n Puerto M1: ");
        Scanner in2 = new Scanner(System.in);
        String portM1 = in2.nextLine();
        
        System.out.print("\n IP M2: ");
        Scanner in3 = new Scanner(System.in);
        String ipM2 = in3.nextLine();
        
        System.out.print("\n Puerto M2: ");
        Scanner in4 = new Scanner(System.in);
        String portM2 = in4.nextLine();
        
        Runnable subproceso = new Sockets(Integer.parseInt(portM1),Integer.parseInt(portM2),ipM1,ipM2);
        new Thread(subproceso).start();
	while(true){        
        System.out.print("\n Ingrese mensaje a enviar: ");
        Scanner in5 = new Scanner(System.in);
        String mensaje = in5.nextLine();
        
        try {
            //CLIENTE
            InetAddress addr = InetAddress.getByName(ipM2);
            Socket socketcliente = new Socket(addr,Integer.parseInt(portM2));
            DataOutputStream data = new DataOutputStream(socketcliente.getOutputStream());
            data.writeUTF(mensaje);
            data.close();
        } catch (IOException ex) {
            Logger.getLogger(Sockets.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
        
        
    }

    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(this.port1);
            while(true){
                Socket socket = servidor.accept();
                DataInputStream data = new DataInputStream(socket.getInputStream());
                String mensaje = data.readUTF();
                System.out.println("\nMensaje Recibido: "+mensaje+"\n");
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Sockets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
