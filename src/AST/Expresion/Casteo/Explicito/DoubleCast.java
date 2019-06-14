/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Casteo.Explicito;

import AST.Entorno.Entorno;
import AST.Entorno.Tipo;
import static AST.Entorno.Tipo.TypePrimitive.DOUBLE;
import AST.Expresion.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class DoubleCast implements Expresion
{
    int linea, columna;
    Tipo tipo = new Tipo(DOUBLE);
    Expresion expresion;
    
    public DoubleCast(Expresion e, int l, int c)
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
                Double tmp = expresion.getTipo().isDouble()? (Double)valor: (expresion.getTipo().isInt()? (int)valor:(char)valor);
                return tmp;
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

    public double generarNumero(String cad)
    {        
        try 
        {
            Double numero = 0.0;
            numero = Double.parseDouble(cad);
            return numero;
        } 
        catch (Exception e) 
        {
            Utilidades.Singlenton.registrarError("ToDouble", "Formato numerico incorrecto al tratar de convertir el valor a double." , ErrorC.TipoError.SEMANTICO, expresion.linea(), expresion.columna());                         
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
