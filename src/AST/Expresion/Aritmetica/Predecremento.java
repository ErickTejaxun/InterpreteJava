/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Aritmetica;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import AST.Expresion.Expresion;
import AST.Expresion.Variable;
import static AST.Entorno.Tipo.TypePrimitive.*;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class Predecremento implements Expresion
{
    int linea, columna;
    Expresion exp;
    Tipo tipo;
    
    public Predecremento(Expresion e, int l, int c)
    {
        this.exp = e;
        this.linea = l;
        this.columna = c;
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
    public Object getValor(Entorno entorno) 
    {
        tipo = new Tipo(INT);
        if(exp instanceof Variable)
        {            
            String nombre = ((Variable)exp).id;
            Simbolo simbolo = entorno.obtener(nombre);
            Object tmp = exp.getValor(entorno);            
            if(tmp==null)
            {
                return null;
            }           
            if(exp.getTipo().isNumeric())
            {
                switch(exp.getTipo().typeprimitive)
                {
                    case INT:
                        tipo = new Tipo(INT);
                        simbolo.valor = (int)tmp - 1;
                        break;
                    case DOUBLE:
                        tipo = new Tipo(DOUBLE);
                        simbolo.valor = (Double)tmp - 1;                        
                        break;
                    case CHAR:
                        tipo = new Tipo(INT);
                        simbolo.valor = (char)tmp - 1;                        
                }                                
                entorno.actualizar(simbolo);
                return simbolo.valor;
            }
            else
            {
                Utilidades.Singlenton.registrarError("++", "Esta operación solo puede aplicarse a tipos numericos" , ErrorC.TipoError.SEMANTICO, linea, columna);    
            }
            return null;
        }
        else
        {
            Utilidades.Singlenton.registrarError("++", "Esta operación solo puede aplicarse sobre variables" , ErrorC.TipoError.SEMANTICO, linea, columna);
            return null;
        }        
    }

    @Override
    public Tipo getTipo() 
    {
        return tipo;
        
    }
    
}
