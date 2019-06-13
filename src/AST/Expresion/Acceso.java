/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import AST.Expresion.Arreglo.Arreglo;

/**
 *
 * @author erick
 */
public class Acceso implements Expresion
{
    public int linea, columna;
    public Expresion origen;
    public String atrib;
    public Tipo tipo;
    public Acceso(Expresion e, String i, int l, int c)
    {
        this.origen = e;
        this.atrib = i;
        this.linea = l;
        this.columna = c;
    }
    
    @Override
    public Object getValor(Entorno ent) 
    {
        Object result = origen.getValor(ent);
        if(result instanceof Arreglo)
        {
            if(atrib.equals("length"))
            {
                Arreglo arr = (Arreglo)result;
                return arr.getSizes();
            }
        }
        
        return null;
    }

    @Override
    public Tipo getTipo() {
        return tipo;
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
