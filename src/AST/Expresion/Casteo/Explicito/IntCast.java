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
public class IntCast implements Expresion
{
    int linea, columna;
    Tipo tipo = new Tipo(INT);
    Expresion expresion;
    
    public IntCast(Expresion e, int l, int c)
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
                    Double doubleAux = (Double)valor;
                    if( doubleAux< Utilidades.Singlenton.minInt || doubleAux > Utilidades.Singlenton.maxInt)
                    {
                       Utilidades.Singlenton.registrarError("ToInt", "El valor double sobrepasa el límite del contenedor int" , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna()); 
                    }
                    else
                    {                        
                        String numero[] = doubleAux.toString().split("\\.");
                        int intAux = Integer.parseInt(numero[0]);
                        return intAux;
                    }
                }
                else
                {
                    int tmp = expresion.getTipo().isChar()? (char)valor: (int)valor;
                    return tmp;
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
                    Utilidades.Singlenton.registrarError("ToInt", "No se puede convertir un tipo no numerico." , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());
                }
                
            }           
        }        
        return null;
    }

    public int generarNumero(String cad)
    {        
        try 
        {
            Double numero = 0.0;
            numero = Double.parseDouble(cad);
            if(numero> Utilidades.Singlenton.minInt  && numero<Utilidades.Singlenton.maxInt)
            {              
                String partes[] = numero.toString().split("\\.");
                int intAux = Integer.parseInt(partes[0]);
                return intAux;
            }
            else
            {
                Utilidades.Singlenton.registrarError("ToInt", "El valor double sobrepasa el límite del contenedor int" , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());
            }
        } 
        catch (Exception e) 
        {
            Utilidades.Singlenton.registrarError("ToChar", "Formato numerico incorrecto al tratar de convertir el valor a int." , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());                         
        }               
        return 0;
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
