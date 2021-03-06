/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Aritmetica;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.*;
import AST.Expresion.Arreglo.Arreglo;
import AST.Expresion.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class Suma implements Expresion
{
    public int linea, columna;
    public Tipo tipo;
    public Expresion operadori, operadord;
    public Object valori;
    public Suma(Expresion i, Expresion d, int l, int c)
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
        tipo = new Tipo("");
        tipo.typeprimitive = determinarTipo(operadori.getTipo().typeprimitive, operadord.getTipo().typeprimitive);                
        if(tipo.typeprimitive!=null)
        {
            switch(tipo.typeprimitive)
            {
                case STRING:
                    if(valori==null){valori="Nulo";}
                    if(valord==null){valord="Nulo";}                    

                    
                    
                    if(valori instanceof Simbolo)
                    {
                        Simbolo s = (Simbolo)valori;
                        if(s.tipo.typeClass.equals(""))
                        {
                            valori = s.valor.toString();
                        }                        
                    }

                    if(valord instanceof Simbolo)
                    {
                        Simbolo s = (Simbolo)valord;
                        if(s.tipo.typeClass.equals(""))
                        {
                            valord = s.valor.toString();
                        }                        
                    }                                       
                    
                    
                    this.valori =  valori.toString() + valord.toString();
                    break;
                case DOUBLE:
                    if(operadori.getTipo().typeprimitive == CHAR)
                    {
                        this.valori = (char)valori + (Double)valord;
                    }
                    else
                    if(operadord.getTipo().typeprimitive == CHAR)
                    {
                        this.valori = (Double)valori + (char)valord;
                    }
                    else
                    {
                        this.valori = Double.parseDouble(valori.toString()) + Double.parseDouble(valord.toString());
                    }                                        
                break;
                case INT:  
                    this.valori = (operadori.getTipo().typeprimitive == CHAR ? (char)valori:(int)valori)+( operadord.getTipo().typeprimitive == CHAR? (char)valord:(int)valord);
                    /*if(operadori.getTipo() == CHAR)
                    {
                        valor = (char)valori + (int)valord;
                    }
                    else
                    if(operadord.getTipo() == CHAR)
                    {
                        valor = (int)valori + (char)valord;
                    }
                    else
                    {
                        valor = (int)valori + (int)valord;
                    }*/  
                break;
                case CHAR:
                     tipo.typeprimitive = INT;
                     this.valori = (char)valori + (char)valord;
                break;
            }            
        }
        else
        {
            Utilidades.Singlenton.registrarError("Suma", "No se puede operar tipos " + operadori.getTipo() + " + " +operadord.getTipo() , ErrorC.TipoError.SEMANTICO, linea, columna);
        }
        return this.valori;
    }

    
    public Tipo.TypePrimitive determinarTipo(Tipo.TypePrimitive tipo1, Tipo.TypePrimitive tipo2)
    {
        if(tipo1== null || tipo2==null){return null;}
        if(tipo1 == STRING || tipo2 == STRING)
        {
            return STRING;
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
    
    public boolean isNumeric(Tipo.TypePrimitive e)
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
