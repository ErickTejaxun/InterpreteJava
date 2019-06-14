/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;

/**
 *
 * @author erick
 */
public class Retorno implements Expresion
{
    public int linea, columna;
    public Tipo tipo;
    public Expresion expresion;

    /**
     *
     * @param e Expresion
     * @param l linea
     * @param c columna
     */
    public Retorno(Expresion e, int l, int c)
    {
        this.expresion = e;
        this.linea = l;
        this.columna = c;
    }
    
    @Override
    public Object getValor(Entorno ent) 
    {
        Object resultado = expresion.getValor(ent);
        tipo = expresion.getTipo();
        return resultado;
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
