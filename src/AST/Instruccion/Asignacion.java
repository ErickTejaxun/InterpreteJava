/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Ent.Simbolo;
import AST.Exp.Expresion;
import AST.Ent.Entorno;
import static AST.Ent.Tipo.TypePrimitive.*;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class Asignacion implements Instruccion
{    
    public String id;
    public Expresion expresion;
    public int linea, columna;
    
    public Asignacion(String id, int l,int c)
    {        
        this.id = id;
        this.linea = l;
        this.columna = c;
    }
    
    public Asignacion(String id, Expresion e, int l, int c)
    {        
        this.id = id;
        this.expresion = e;
        this.linea = l;
        this.columna = c;        
    }
        
    @Override
    public Object ejectuar(Entorno entorno) 
    {        
        Simbolo simbolo = entorno.obtener(this.id);
        if(simbolo==null)
        {
            Utilidades.Singlenton.registrarError(id, "La variable no ha sido declarada.", ErrorC.TipoError.SEMANTICO,linea, columna);
            return null;
        }
                
        Object valor = null;
        if(expresion!=null)
        {
            valor = expresion.getValor(entorno);
            if(expresion.getTipo().typeprimitive != simbolo.tipo.typeprimitive)
            {
                switch(simbolo.tipo.typeprimitive)
                {
                    case CHAR:
                        if(expresion.getTipo().typeprimitive != CHAR)
                        {
                            Utilidades.Singlenton.registrarError(id, "No se le puede asignar un valor de tipo "+ expresion.getTipo().nombreTipo() +" a un tipo "+simbolo.tipo.nombreTipo(), ErrorC.TipoError.SEMANTICO,linea, columna);
                            return null;
                        }
                    break;
                    case INT:   
                        switch(expresion.getTipo().typeprimitive)
                        {
                            case CHAR:
                                valor =(char)valor+0;
                                break;
                            default:
                                Utilidades.Singlenton.registrarError(id, "No se le puede asignar un valor de tipo "+ expresion.getTipo().nombreTipo() +" a un tipo "+simbolo.tipo.nombreTipo(), ErrorC.TipoError.SEMANTICO,linea, columna);
                                return null; 
                        }
                    break;
                    case DOUBLE:
                        switch(expresion.getTipo().typeprimitive)
                        {
                            case CHAR:
                                valor =(char)valor+0.0;
                                break;
                            case INT:
                                valor = (double) (int)valor;
                                break;
                            default:
                                Utilidades.Singlenton.registrarError(id, "No se le puede asignar un valor de tipo "+ expresion.getTipo().nombreTipo() +" a un tipo "+simbolo.tipo.nombreTipo(), ErrorC.TipoError.SEMANTICO,linea, columna);
                                return null; 
                        }                        
                    break;   
                    default:
                        if(!expresion.getTipo().nombreTipo().equals(simbolo.tipo.nombreTipo()))
                        {
                            Utilidades.Singlenton.registrarError(id, "No se le puede asignar un valor de tipo "+ expresion.getTipo().nombreTipo() +" a un tipo " + simbolo.tipo.nombreTipo(), ErrorC.TipoError.SEMANTICO,linea, columna);
                            return null;                        
                        }                    
                    break;                                                         
                }                 
            }  
        }  
        else
        {                            
            switch(simbolo.getTipo().typeprimitive)
            {
                case INT:
                    valor = 0;
                    break;
                case STRING:
                    valor = "";
                    break;                
                case CHAR:
                    valor = ' ';
                    break;                                
                case DOUBLE:
                    valor = 0.00;
                    break;                                
                case BOOL:
                    valor = false;
                    break;                    
            }             
        }
        Simbolo s = new Simbolo(simbolo.getTipo(),id,valor,simbolo.linea,simbolo.columna);
        if(!entorno.actualizar(s))
        {
            //System.out.println("Error, variable " +s.id + " ya declarada.");
        }
        return this;
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
