/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import AST.Clase.Clase;
import AST.Expresion.Funcion.Retorno;
import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import static AST.Entorno.Simbolo.Rol.*;
import AST.Expresion.Expresion;
import AST.Expresion.Funcion.DeclaracionAtributo;
import AST.Expresion.Funcion.Funcion;
import AST.Instruccion.Bloque;
import AST.Instruccion.Instruccion;
import AST.Nodo;
import interprete.Interfaz;
import java.util.ArrayList;
import java.util.Enumeration;

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
        v.entornoGlobal = global;
        primerPasada(global);
        segundaPasada(global);
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
    
    public Object segundaPasada(Entorno entorno)
    {        
        Enumeration item = entorno.tabla.keys();
        while(item.hasMoreElements())
        {
            Entorno local = new Entorno(entorno.getGlobalClase(),entorno.ventana);
            Object clave = item.nextElement();            
            Simbolo simbolo = entorno.obtener(clave.toString());
            if(simbolo.rol ==CLASE)
            {
                Clase actual = (Clase)simbolo;
                if(actual.existePrincipal())
                {
                    actual.getValor(local);
                    break;
                }                
            }            
        }                           
        return null;
    }
}
