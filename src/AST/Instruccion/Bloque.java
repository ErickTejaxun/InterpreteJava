/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Expresion.Retorno;
import AST.Entorno.Entorno;
import AST.Expresion.Expresion;
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
                }
            }
            else if (nodo instanceof Expresion)
            {                
                ((Expresion)nodo).getValor(global);
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
    
}

