/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Exp.Casteo;

import AST.Ent.Entorno;
import AST.Ent.Tipo;
import AST.Exp.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class Casteo implements Expresion
{
    public int linea, columna;
    public Tipo tipo;
    public Expresion expresion;
    
    public Casteo(Expresion e, Tipo t, int l, int c)
    {
        this.expresion = e;
        this.tipo = t;
        this.linea = l;
        this.columna = c;
    }
    
    public Casteo(Expresion e, String t, int l, int c)
    {
        this.expresion = e;
        this.tipo = new Tipo(t);
        this.linea = l;
        this.columna = c;        
    }
    
    
    @Override
    public Object getValor(Entorno ent) 
    {
        Object valor = expresion.getValor(ent);
        switch(this.tipo.nombreTipo().toLowerCase())
        {
            case "int":
                if(expresion.getTipo().isNumeric())
                {                    
                    if(expresion.getTipo().isInt())
                    {
                        return valor;
                    }
                    else
                    if(expresion.getTipo().isChar())
                    {
                        return (char)valor + 0;
                    }
                    else
                    {
                        Utilidades.Singlenton.registrarError("Casteo a "+tipo.nombreTipo(), "No es posible converitir a entero un valor de tipo " + expresion.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);
                    }
                }
                else
                {
                    Utilidades.Singlenton.registrarError("Casteo a "+tipo.nombreTipo(), "No es posible converitir a entero un valor de tipo " + expresion.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);
                }
                break;
            case "double":
                if(expresion.getTipo().isNumeric())
                {    
                    return (expresion.getTipo().isChar())? (char)valor+0.00: (expresion.getTipo().isInt() ? (int)valor+0.00: valor);
                }
                else
                {
                    Utilidades.Singlenton.registrarError("Casteo a "+tipo.nombreTipo(), "No es posible converitir a entero un valor de tipo " + expresion.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);
                }
            case "char":
                if(expresion.getTipo().isNumeric()&&expresion.getTipo().isChar())
                {    
                    return valor;
                }
                else
                {
                    Utilidades.Singlenton.registrarError("Casteo a "+tipo.nombreTipo(), "No es posible converitir a entero un valor de tipo " + expresion.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);
                }                 
            break;
            default:
                if(expresion.getTipo().isNumeric() && expresion.getTipo().isChar())
                {
                    return valor;
                }
                else
                {
                    
                }
            break;
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
