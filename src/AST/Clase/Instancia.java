/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Clase;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import AST.Expresion.Expresion;
import AST.Expresion.Funcion.ParametroFormal;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Instancia implements Expresion
{
    public Tipo tipo;
    public ArrayList<Expresion> parametrosActuales;    
    public int linea, columna;
    
    public Instancia(Tipo t, ArrayList<Expresion> lp, int l, int c)
    {
        this.tipo = t;
        this.parametrosActuales = lp;
        this.linea = l;
        this.columna = c;
    }
    
    
    @Override
    public Object getValor(Entorno ent) 
    {
        return null;
    }

    @Override
    public Tipo getTipo() 
    {
        return tipo;
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
