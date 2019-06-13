/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Ent.Entorno;
import AST.Exp.Arreglo.Arreglo;
import AST.Exp.Expresion;

/**
 *
 * @author erick
 */
public class Imprimir implements Instruccion
{
    public int linea, columna;
    public Expresion valor;

    public Imprimir(Expresion v, int l, int c)
    {
        this.valor = v;
        this.linea = l;
        this.columna = c;
    }
        
    @Override
    public Object ejectuar(Entorno entorno) 
    {
        Object exp = valor.getValor(entorno);
        //System.out.println(exp);        
        if(entorno.ventana!=null)
        {
            if(exp!=null)
            {
                Object valor = exp;//.toString()
                if(valor instanceof Arreglo)
                {
                    entorno.ventana.Imprimir(((Arreglo)valor).getCadena());
                }
                else
                {
                    entorno.ventana.Imprimir(valor.toString());
                }
                
            }                           
            else
            {
                entorno.ventana.Imprimir("NULO :'v");
            }
        }
        return this;
    }

    @Override
    public int linea() 
    {
        return linea;
    }

    @Override
    public int columna() 
    {
        return columna;
    }    
}
