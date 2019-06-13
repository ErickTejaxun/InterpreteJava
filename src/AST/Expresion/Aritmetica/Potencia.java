/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Aritmetica;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import AST.Expresion.Expresion;
import static AST.Entorno.Tipo.TypePrimitive.*;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */

public class Potencia implements Expresion
{
    public int linea, columna;
    public Tipo tipo;
    public Expresion operadori, operadord;
    public Object valor;
    public Potencia(Expresion i, Expresion d, int l, int c)
    {
        this.operadori = i;
        this.operadord = d;
        this.linea = l;
        this.columna = c;
    }
    
    
    @Override
    public Object getValor(Entorno ent) 
    {
        Object valori = operadori.getValor(ent);
        Object valord = operadord.getValor(ent);
        tipo = new Tipo(DOUBLE);
        tipo.typeprimitive = determinarTipo(operadori.getTipo().typeprimitive, operadord.getTipo().typeprimitive);
        if(tipo!=null)
        {
            switch(tipo.typeprimitive)
            {
                case DOUBLE:
                    if(operadori.getTipo().typeprimitive == CHAR)
                    {
                        valor = Math.pow((char)valori , (Double)valord);
                    }
                    else
                    if(operadord.getTipo().typeprimitive == CHAR)
                    {
                        valor = Math.pow((Double)valori ,(char)valord);
                    }
                    else
                    {
                        valor = Math.pow(Double.parseDouble(valori.toString()) , Double.parseDouble(valord.toString()));
                    }                                        
                break;
                case INT:
                case CHAR:                      
                    if(operadori.getTipo().typeprimitive == CHAR)
                    {
                        //valor = (int)Math.pow((char)valori , (int)valord);                        
                        valor = Math.pow((operadori.getTipo().isInt()? (int)valori:(char)valori),(operadord.getTipo().isInt()? (int)valord:(char)valord));
                    }
                    else
                    if(operadord.getTipo().typeprimitive == CHAR)
                    {
                        valor = Math.pow((int)valori , (char)valord);
                    }
                    else
                    {
                        valor = Math.pow((int)valori , (int)valord);
                    }                                         
                break;
            } 
            tipo.typeprimitive = DOUBLE;
        }
        else
        {
            Utilidades.Singlenton.registrarError("Potencia", "No se puede operar tipos " + operadori.getTipo() + " ^ " +operadord.getTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);
        }
        return (tipo.isDouble())? (Double)valor:valor;
        //return valor;
    }

    
    public AST.Entorno.Tipo.TypePrimitive determinarTipo(AST.Entorno.Tipo.TypePrimitive tipo1, AST.Entorno.Tipo.TypePrimitive tipo2)
    {
        if(tipo1 == STRING || tipo2 == STRING)
        {
            return null;
        }
        else
        if(tipo1 == DOUBLE || tipo2 == DOUBLE)
        {
            if(isNumeric(tipo1)&& isNumeric(tipo2))
            {
                return DOUBLE;
            }
            else
            {
                return null;
            }
        }
        else
        if(tipo1 == INT || tipo2 == INT)
        {
            if(isNumeric(tipo1)&& isNumeric(tipo2))
            {                
                return INT;
            }
            else
            {
                return null;
            }
        } 
        else
        if(tipo1==CHAR && tipo2 ==CHAR)
        {
            return CHAR;
        }
        else
        {
            return null;
        }        
    }
    
    public boolean isNumeric(AST.Entorno.Tipo.TypePrimitive e)
    {
        switch(e)
        {
            case INT:
            case DOUBLE:
            case CHAR:
                return true;               
        }
        return false;
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
