/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Exp.Retorno;
import AST.Ent.Entorno;
import AST.Exp.Expresion;
import AST.Instruccion.Instruccion;
import AST.Nodo;
import interprete.Interfaz;
import interprete.Ventana;
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
        for(Nodo nodo: nodos)
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
                }
            }
            else if (nodo instanceof Expresion)
            {
                return ((Expresion) nodo).getValor(global);
            }            
        }
        return null;
    }
}
