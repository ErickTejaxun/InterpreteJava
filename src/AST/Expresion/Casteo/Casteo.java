/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Casteo;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.INT;
import AST.Expresion.Expresion;
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
                    if(expresion.getTipo().isDouble())
                    {
                        Double doubleTmp = (Double)valor;
                        if(doubleTmp > Utilidades.Singlenton.maxInt)
                        {
                           Utilidades.Singlenton.registrarError("Casteo a "+tipo.nombreTipo(), " El valor double sobrepasa el valor de un entero por el límite superior.  " + expresion.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna()); 
                        }
                        else 
                        if (doubleTmp < -1*Utilidades.Singlenton.maxInt)
                        {
                           Utilidades.Singlenton.registrarError("Casteo a "+tipo.nombreTipo(), " El valor double sobrepasa el valor de un entero por el límite inferior.  " + expresion.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());  
                        }
                        else
                        {
                            String partes[] = doubleTmp.toString().split("\\.");
                            valor = Integer.parseInt(partes[0]);
                            tipo = new Tipo(INT);
                            return valor;
                        }
                    }
                    
                    else
                    {
                        Utilidades.Singlenton.registrarError("Casteo a "+tipo.nombreTipo(), "Casteo explicito: no es posible converitir a entero un valor de tipo " + expresion.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());
                    }
                }
                else
                {
                    Utilidades.Singlenton.registrarError("Casteo a "+tipo.nombreTipo(), "Casteo explicito: no es posible converitir a entero un valor de tipo " + expresion.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());
                }
                break;
            case "double":
                if(expresion.getTipo().isNumeric())
                {    
                    return (expresion.getTipo().isChar())? (char)valor+0.00: (expresion.getTipo().isInt() ? (int)valor+0.00: valor);
                }
                else
                {
                    Utilidades.Singlenton.registrarError("Casteo a "+tipo.nombreTipo(), "Casteo explicito: no es posible converitir a entero un valor de tipo " + expresion.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());
                }
            case "char":
                if(expresion.getTipo().isNumeric()&&expresion.getTipo().isChar())
                {    
                    return valor;
                }
                else
                {
                    Utilidades.Singlenton.registrarError("Casteo a "+tipo.nombreTipo(), "Casteo explicito: no es posible converitir a entero un valor de tipo " + expresion.getTipo().nombreTipo() , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());
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
