/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.BOOL;

/**
 *
 * @author erick
 */
public class Ternario implements Expresion
{
    public int linea, columna;
    public Expresion condicion;
    public Expresion valorV, valorF;
    public Tipo tipo;
    
    public Ternario(Expresion c, Expresion v, Expresion f, int l, int col)
    {
        this.condicion = c;
        this.valorF= f;
        this.valorV = v;
        this.linea = l;
        this.columna = col;
    }
    
    @Override
    public Object getValor(Entorno ent) 
    {
        Object result = condicion.getValor(ent);
        if(condicion.getTipo().typeprimitive == BOOL)
        {
            Object tmp = null;
            if((boolean)result)
            {
                tmp = valorV.getValor(ent);
                tipo = valorV.getTipo();
            }
            else
            {
                tmp = valorF.getValor(ent);
                tipo = valorF.getTipo();
            }
            return tmp;
        }        
        return null;
    }

    @Override
    public Tipo getTipo() 
    {
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
