/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Entorno.Simbolo;
import AST.Expresion.Expresion;
import AST.Entorno.Entorno;
import static AST.Entorno.Tipo.TypePrimitive.*;
import AST.Expresion.Arreglo.Arreglo;
import Utilidades.ErrorC;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class AsignacionVector implements Instruccion
{    
    public String id;
    public Expresion expresion;
    public int linea, columna;
    public ArrayList<Expresion> coordenas;
    
    public AsignacionVector(String id, int l,int c)
    {        
        this.id = id;
        this.linea = l;
        this.columna = c;
    }
    
    
    
    public AsignacionVector(String id, ArrayList<Expresion> cor, Expresion e, int l, int c)
    {        
        this.id = id;
        this.expresion = e;
        this.linea = l;
        this.columna = c;        
        this.coordenas = cor;
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
        if(!(simbolo.valor instanceof Arreglo))
        {
            Utilidades.Singlenton.registrarError(id, "El valor de la variable no es un arreglo.", ErrorC.TipoError.SEMANTICO,linea, columna);
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
                        if(!expresion.getTipo().isChar())
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
        
        Arreglo arregloActual = (Arreglo)simbolo.valor;                
        ArrayList<Integer> cordenadas = new ArrayList<Integer>();
        for(Expresion exp : coordenas)
        {
            Object resultado = exp.getValor(entorno);
            if(resultado==null){return null;};
            if(exp.getTipo().isNumeric())
            {
                if(exp.getTipo().typeprimitive == INT)
                {
                    cordenadas.add((int)resultado);
                }
                else
                if(exp.getTipo().typeprimitive == CHAR)
                {
                    int aux = (char)resultado;
                    cordenadas.add(aux);
                }   
                else
                {                                                       
                    Utilidades.Singlenton.registrarError("Indice", "Se requiere un valor entero, no es posible convertir de double a int." , ErrorC.TipoError.SEMANTICO, exp.linea(), exp.columna());  
                }
            }
            else
            {
                Utilidades.Singlenton.registrarError("indice", "El valor debe ser de tipo numerico." , ErrorC.TipoError.SEMANTICO, exp.linea(), exp.columna());  
                return null;
            }
        } 
        arregloActual.setValor(cordenadas, valor);                        
        return null;
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
