package src.manager;

import java.rmi.Naming;

public class Client {  
   private Client() {}  
   public static void main(String[] args) {  
      try 
      {
         BeaconListenerService stub = (BeaconListenerService) Naming.lookup("rmi://localhost/BeaconListener");

         System.out.println(stub.deposit());
         
         // System.out.println("Remote method invoked"); 
      } catch (Exception e) {
         System.err.println("Client exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
}