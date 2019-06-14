/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Casteo.Explicito;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.*;
import AST.Expresion.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class CharCast implements Expresion
{
    int linea, columna;
    Tipo tipo = new Tipo(INT);
    Expresion expresion;
    
    public CharCast(Expresion e, int l, int c)
    {
        this.expresion = e;
        this.linea = l;
        this.columna = c;
    }

    @Override
    public Object getValor(Entorno ent) 
    {
        Object valor = expresion.getValor(ent);
        if(valor!=null)
        {
            if(expresion.getTipo().isNumeric())
            {
                if(expresion.getTipo().isDouble())
                {
                    Utilidades.Singlenton.registrarError("ToChar", "No es posible convertir de Double a Char" , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());
                }
                else
                {
                    int tmp = expresion.getTipo().isChar()? (char)valor: (int)valor;
                    if(tmp<0 || tmp>256)
                    {
                        Utilidades.Singlenton.registrarError("ToChar", "El valor númerico sobrepasa el valor permitido para caracteres." , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());
                        return ' ';
                    }
                    else
                    {
                        char tmp2 = (char)tmp;
                        return tmp2;                        
                    }                                        
                }
            }
            else
            {
                if(expresion.getTipo().isString())
                {
                    return generarNumero(valor.toString());
                }
                else
                {
                    Utilidades.Singlenton.registrarError("ToChar", "No se puede convertir un tipo no numerico." , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());
                }
                
            }            
        }        
        return null;
    }
    
    public char generarNumero(String cad)
    {
        
        try 
        {
            int numero = 0;
            numero = Integer.parseInt(cad);
            if(numero> 0  && numero<255)
            {
                char tmp = (char)numero;
                return tmp;
            }
            else
            {
                Utilidades.Singlenton.registrarError("ToChar", "El valor númerico sobrepasa el valor permitido para caracteres." , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());
            }
        } 
        catch (Exception e) 
        {
           if(cad.length()==1)
           {
               char tmp = cad.charAt(0);
               return tmp;
           }
           else
           {
                Utilidades.Singlenton.registrarError("ToChar", "Formato numerico " , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());                
           }           
        }               
        return ' ';
    }

    @Override
    public Tipo getTipo() 
    {
        return tipo;
    }

    @Override
    public int linea() 
    {
        return linea;
    }

    @Override
    public int columna() 
    {
        return columna;
    }
    
}
