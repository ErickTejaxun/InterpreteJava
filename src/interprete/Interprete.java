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
        ventana.show();
        
    }
    
    public static void println(Object v)
    {
        System.out.println(v);
    }
    
       
}
