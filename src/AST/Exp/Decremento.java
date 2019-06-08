/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Exp;

import AST.Ent.Entorno;
import AST.Ent.Simbolo;
import AST.Ent.Tipo;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class Decremento implements Expresion
{
    int linea, columna;
    Expresion exp;
    Tipo tipo;
    
    public Decremento(Expresion e, int l, int c)
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
    public Object getValor(Entorno entorno) {
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
                        tmp = (int)tmp - 1;
                        break;
                    case DOUBLE:
                        tmp = (Double)tmp - 1;                        
                        break;
                    case CHAR:
                        tmp = (char)tmp - 1;                        
                }
                simbolo.valor = tmp;
                entorno.actualizar(simbolo);
                return tmp;
            }
            else
            {
                Utilidades.Singlenton.registrarError("++", "Esta operación solo puede aplicarse a tipos numericos" , ErrorC.TipoError.SEMANTICO, linea, linea);    
            }
            return null;
        }
        else
        {
            Utilidades.Singlenton.registrarError("++", "Esta operación solo puede aplicarse sobre variables" , ErrorC.TipoError.SEMANTICO, linea, linea);
            return null;
        }        
    }

    @Override
    public Tipo getTipo() 
    {
        return tipo;
        
    }
    
}
