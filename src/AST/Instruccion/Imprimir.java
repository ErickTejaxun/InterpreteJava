/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Entorno.Entorno;
import AST.Expresion.Arreglo.Arreglo;
import AST.Expresion.Expresion;

/**
 *
 * @author erick
 */
public class Imprimir implements Instruccion
{
    public int linea, columna;
    public Expresion valor;
    public boolean salto;

    public Imprimir(Expresion v, int l, int c)
    {
        this.valor = v;
        this.linea = l;
        this.columna = c;
        this.salto = true;
    }
        
    public Imprimir(Expresion v, boolean s, int l, int c)
    {
        this.valor = v;
        this.linea = l;
        this.columna = c;
        this.salto = s;
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
                    Imprimir( entorno,((Arreglo)valor).getCadena());
                }
                else
                {
                    Imprimir(entorno,valor.toString());
                }                
            }                           
            else
            {
                Imprimir(entorno,"NULO");
            }
        }
        return null;
    }
    
    public void Imprimir(Entorno e, String c)
    {
        if(salto)
        {
            e.ventana.ImprimirLn(c);
            System.out.println(c);
        }
        else
        {
            e.ventana.Imprimir(c);
            System.out.print(c);
        }
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
