/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Instruccion.Ciclos.Continuar;
import AST.Instruccion.Ciclos.Break;
import AST.Expresion.Funcion.Retorno;
import AST.Entorno.Entorno;
import AST.Expresion.Expresion;
import AST.Expresion.Funcion.Llamada;
import AST.Nodo;
import static Utilidades.Singlenton.ModoEjecucion.DEBUG;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erick
 */
public class Bloque implements Instruccion, Runnable
{
    public ArrayList<Nodo> instrucciones;
    public int linea, columna;
//    boolean seguirHilo=false;
//    boolean hiloIniciado=false;
//    Thread hilo;
    int cont=0;    
    
    public Bloque(ArrayList<Nodo> list, int l, int c)
    {
        this.instrucciones = list;
        this.linea = l;
        this.columna = c;        
    }        
    
    @Override    
    public Object ejectuar(Entorno entorno) 
    {        
        //iniciarHilo();        
        Entorno global = entorno;// new Entorno(null);                
        for(Nodo nodo: instrucciones)
        {
            try 
            {
                if(Utilidades.Singlenton.modoEjecucion == DEBUG)
                {                                            
                    entorno.ventana.entorno = entorno;
                    if(Utilidades.Singlenton.isBreakPoint(nodo.linea()))
                    {
                        entorno.ventana.resaltarLinea(nodo.linea()-1);
                        
                        entorno.ventana.pararHIlo(true);
                    }
                    entorno.ventana.resaltarLinea(nodo.linea());
                    entorno.ventana.hilo.sleep(entorno.ventana.getVelocidad());                       
                }                
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Bloque.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(nodo instanceof Instruccion) 
            {
                Entorno local = global;
                if(nodo instanceof Bloque)
                {
                    local = new Entorno(global, global.ventana);
                    return ((Bloque)nodo).ejectuar(local);
                }

                Object resultado = (((Instruccion) nodo).ejectuar(local));
                
                if(resultado !=null)
                {
                    if(resultado instanceof Retorno)
                    {
                        return resultado;
                    }
                    if(resultado instanceof Break)
                    {
                        return resultado;
                    }
                    if(resultado instanceof Continuar)
                    {
                        return resultado;
                    }
                    return resultado;
                }
            } 
            else if (nodo instanceof Expresion)
            {
                if(nodo instanceof Retorno)
                {            
                    return ((Retorno)nodo).getValor(global);
                }
                
                if(nodo instanceof Llamada)
                {
                    Object resultadoLlamada =  ((Llamada)nodo).getValor(global);
                    if(resultadoLlamada!=null)
                    {
                        return resultadoLlamada;
                    }
                }
                else
                {
                    return ((Expresion)nodo).getValor(global);
                }
            }
        }        
        return null;
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }
        
    public void arreglo(int a[][])
    {
        
    }

    @Override
    public void run() 
    {
        
//        while(true)
//        {
//            pararHIlo(!Utilidades.Singlenton.continuarEjecucion );
//        }
        
    }
    
//    public void iniciarHilo()
//    {
//        hilo=new Thread(this);
//        hilo.start();
//        hiloIniciado=true;
//    }
    /*método para parar el hilo*/
//    public void pararHIlo()
//    {        
//        if(Utilidades.Singlenton.continuarEjecucion)
//        {
//            hilo.suspend();
//        }
//        else
//        {
//            hilo.resume();
//        }                
//        
//    }      
    
}

