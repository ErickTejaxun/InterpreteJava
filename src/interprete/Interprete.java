/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interprete;

/**
 *
 * @author erick
 */
public class Interprete {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {                              
        Interfaz ventana = new Interfaz();                      
        //Ventana ventana = new Ventana();
        //Hanoi(3,1,2,3);
        
        
        ventana.show();
    }
    
    public static void println(Object v)
    {
        System.out.println(v);
    }
    
    public static void Hanoi(int n, int origen,  int auxiliar, int destino)
    {
      if(n==1)
      System.out.println("mover disco de " + origen + " a " + destino);
      else{
         Hanoi(n-1, origen, destino, auxiliar);
         System.out.println("mover disco de "+ origen + " a " + destino);
         Hanoi(n-1, auxiliar, origen, destino);
       }
     }
       
}
