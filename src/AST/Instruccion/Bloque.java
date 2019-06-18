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
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Bloque implements Instruccion
{
    public ArrayList<Nodo> instrucciones;
    public int linea, columna;
    
    public Bloque(ArrayList<Nodo> list, int l, int c)
    {
        this.instrucciones = list;
        this.linea = l;
        this.columna = c;        
    }        
    
    @Override    
    public Object ejectuar(Entorno entorno) 
    {
        Entorno global = entorno;// new Entorno(null);                
        for(Nodo nodo: instrucciones)
        {
            if(nodo instanceof Instruccion)
            {
                Entorno local = global;
                if(nodo instanceof Bloque)
                {
                    local = new Entorno(global, global.ventana);
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
                    return ((Llamada)nodo).getValor(global);
                }                  
                
                return ((Expresion)nodo).getValor(global);
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
    
}

