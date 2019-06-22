/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Debugger;

import AST.AST;
import interprete.Interfaz;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author erick
 */
public class Hilo implements Runnable
{  
    public AST programa;
    public Interfaz ventana;  
    public RTextScrollPane scrollPanel;

    public Hilo(AST raiz)
    {      
        this.programa = raiz;
    }

    public Hilo(AST programa, Interfaz ventana, RTextScrollPane scrollPanel) {
        this.programa = programa;
        this.ventana = ventana;
        this.scrollPanel = scrollPanel;
    }
  
    public Hilo(Interfaz ventana, RTextScrollPane scrollPanel) {       
        this.ventana = ventana;
        this.scrollPanel = scrollPanel;
    }  
  
    @Override
    public void run()
    {            
        /*Ejecutamos */
        for(int linea: Utilidades.Singlenton.breakPoints)
        {
            esperarXsegundos(ventana.getVelocidad());
            System.out.println(linea);
        }
    }
  
    private void esperarXsegundos(int segundos) {
            try {
                    Thread.sleep(segundos * 20);
            } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
            }
    }  
}
