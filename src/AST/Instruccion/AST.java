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
import interprete.Interfaz;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class AST 
{
    public ArrayList<Nodo> nodos;
    
    public AST(ArrayList<Nodo> n)
    {
        this.nodos = n;
    }
    
    
    public Object ejecutar(Interfaz v)
    {
        Entorno global = new Entorno(null,v);
        primerPasada(global);
        return null;
    }
    
    public Object primerPasada(Entorno entorno)
    {             
        for(Nodo nodo: nodos)
        {
            if(nodo instanceof Instruccion)
            {
                Entorno local = entorno;
                if(nodo instanceof Bloque)
                {
                    local = new Entorno(entorno, entorno.ventana);
                }
                Object resultado = (((Instruccion) nodo).ejectuar(local));
                if(resultado !=null)
                {
                    if(resultado instanceof Retorno)
                    {
                        return resultado;
                    }                    
                }
            }
            else if (nodo instanceof Expresion)
            {
                return ((Expresion) nodo).getValor(entorno);
            }            
        }               
        return null;
    }
    
    public Object segundaPasada()
    {
        return null;
    }
}
