/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Exp;

import AST.Ent.Entorno;
import AST.Ent.Tipo;
import static AST.Ent.Tipo.TypePrimitive.INT;
import AST.Instruccion.Instruccion;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Llamada implements Expresion
{
    public int linea, columna;
    public String nombreMetodo;
    public ArrayList<Expresion> parametros;
    public Expresion origen;
    public Tipo tipo;
    
    /*Metodos sin parametros*/
    public Llamada(Expresion o, String n, int l, int c)
    {
        this.origen = o;
        this.linea = l;
        this.columna = c;
        this.nombreMetodo = n;
    }
    
    /*Método con parametros*/
    public Llamada(Expresion o, String n, ArrayList<Expresion> p, int l, int c)
    {
        this.origen = o;
        this.nombreMetodo = n;
        this.parametros = p;
        this.linea = l;
        this.columna = c;
    }
    
    
    @Override
    public Object getValor(Entorno entorno) 
    {
        tipo = new Tipo(INT);
        /*Declaracion de parametros*/
        if(isNativa())
        {
           return ejecutarNativa(entorno); 
        }
        else
        if(parametros!=null)
        {
            int indice = 0;
        }
        return null;
    }
    
    public Object ejecutarNativa(Entorno entorno)
    {
        Object valor = origen.getValor(entorno);        
        switch(this.nombreMetodo.toLowerCase())
        {
            case "length":
                if(origen.getTipo().isString())
                {                    
                    return valor.toString().length();
                }
            return null;
            case "otra":
                return true;                
        }        
        return null;
    }
           
    public boolean isNativa()
    {
        switch(this.nombreMetodo.toLowerCase())
        {
            case "length":                
            case "otra":
                return true;                
        }
        return false;
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }


    @Override
    public Tipo getTipo() {
        return tipo;
    }
    
}
