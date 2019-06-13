/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Entorno.Simbolo;
import AST.Expresion.Expresion;
import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.CHAR;
import AST.Expresion.Arreglo.Arreglo;
import AST.Expresion.Arreglo.ExpresionArreglo;

import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class Declaracion implements Instruccion
{
    public Tipo tipo;
    public String id;
    public Expresion expresion;
    public int linea, columna;
    public int dimensiones;
    
    public Declaracion(Tipo t, String id, int d, int l,int c)
    {
        this.tipo = t;
        this.id = id;
        this.linea = l;
        this.columna = c;
        this.dimensiones = d;
    }  
    
    public Declaracion(Tipo t, String id, int d, Expresion e, int l,int c)
    {
        this.tipo = t;
        this.id = id;
        this.linea = l;
        this.columna = c;
        this.dimensiones = d;
        this.expresion = e;
    }    
    
    public Declaracion(Tipo t, String id, int l,int c)
    {
        this.tipo = t;
        this.id = id;
        this.linea = l;
        this.columna = c;
        this.dimensiones = 0;
    }
    
    public Declaracion(Tipo t, String id, Expresion e, int l, int c)
    {
        this.tipo = t;
        this.id = id;
        this.expresion = e;
        this.linea = l;
        this.columna = c;        
    }
        
    @Override
    public Object ejectuar(Entorno entorno) 
    {        
        Object valor = null;
        /*Verificamos si se le ha asignado un valor inicial.*/
        if(expresion!=null)
        {
            if(expresion instanceof ExpresionArreglo)
            {
                if(((ExpresionArreglo)expresion).tipo == null)
                {
                    ((ExpresionArreglo)expresion).tipo = tipo;
                }
            }            
            valor = expresion.getValor(entorno);     
            if(valor instanceof Arreglo)
            {
                if(((Arreglo)valor).tipo==null)
                {
                    ((Arreglo)valor).tipo = expresion.getTipo();
                }               
            }
            if(expresion.getTipo().typeprimitive != tipo.typeprimitive)
            {
                switch(this.tipo.typeprimitive)
                {
                    case CHAR:
                        if(!expresion.getTipo().isChar())
                        {
                            if(expresion.getTipo().isInt())
                            {
                                int a = (int)valor;
                                if(0<=a && a <=255)
                                {
                                    valor = (char)a;   
                                }   
                                else
                                {
                                    valor = 0;
                                    Utilidades.Singlenton.registrarError(id, 
                                            "Valor no válido para caracter, debe ser positivo y menor a 255. ", 
                                            ErrorC.TipoError.SEMANTICO,linea, columna);
                                }
                            }
                            else
                            {
                                Utilidades.Singlenton.registrarError(id, "No se le puede asignar un valor de tipo "+ expresion.getTipo().nombreTipo() +" a un tipo "+this.tipo.nombreTipo(), ErrorC.TipoError.SEMANTICO,linea, columna);
                                return null;                                
                            }
                        }
                    break;
                    case INT:   
                        switch(expresion.getTipo().typeprimitive)
                        {
                            case CHAR:
                                valor =(char)valor+0;
                                break;
                            default:
                                Utilidades.Singlenton.registrarError(id, "No se le puede asignar un valor de tipo "+ expresion.getTipo().nombreTipo() +" a un tipo "+this.tipo.nombreTipo(), ErrorC.TipoError.SEMANTICO,linea, columna);
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
                                Utilidades.Singlenton.registrarError(id, "No se le puede asignar un valor de tipo "+ expresion.getTipo().nombreTipo() +" a un tipo "+this.tipo.nombreTipo(), ErrorC.TipoError.SEMANTICO,linea, columna);
                                return null; 
                        }                        
                    break;   
                    default:
                        if(expresion.getTipo() != this.tipo)
                        {
                            Utilidades.Singlenton.registrarError(id, "No se le puede asignar un valor de tipo "+ expresion.getTipo().nombreTipo() +" a un tipo " + this.tipo.nombreTipo(), ErrorC.TipoError.SEMANTICO,linea, columna);
                            return null;                        
                        }                    
                    break;                                                         
                }                 
            }  
        }  
        /*Si no se le asigna un valor de inicio, hay que inicializar */
        else
        if(this.dimensiones ==0)
        {                            
            switch(this.tipo.typeprimitive)
            {
                case INT:
                    valor = 0;                    
                    break;
                case STRING:
                    valor = "";
                    break;                
                case CHAR:
                    valor = "";
                    break;                                
                case DOUBLE:
                    valor = 0.00;
                    break;                                
                case BOOL:
                    valor = false;
                    break;                    
            }             
        }
        
        if(valor instanceof Arreglo)
        {
            Arreglo arregloTmp = (Arreglo)valor;
            System.out.println(arregloTmp.getCadena());
            if(arregloTmp.tamaniosDimensiones.size() != dimensiones)
            {
                Utilidades.Singlenton.registrarError(id, "No coincide el número de las dimensiones del valor a asignar", ErrorC.TipoError.SEMANTICO,linea, columna);
                return this;
            }            
        }
        
        
        Simbolo s = this.dimensiones == 0 ? new Simbolo(tipo,id,valor,linea,columna):new Simbolo(tipo,id,valor,dimensiones ,linea,columna);
        if(!entorno.insertar(s))
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
