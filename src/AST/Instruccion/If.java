/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Ent.Entorno;
import static AST.Ent.Tipo.TypePrimitive.*;
import AST.Exp.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class If implements Instruccion
{
    int linea, columna;
    public Bloque instrucciones;
    public Expresion condicion;
    public Instruccion elseIf;
    
    
    public If(Expresion e, Bloque b, int l, int c)
    {
        this.condicion = e;
        this.instrucciones = b;
        this.linea = l;
        this.columna = c;                 
    }
    
    public If(Expresion e, Bloque b, Instruccion elseIf, int l, int c)
    {
        this.condicion = e;
        this.instrucciones = b;
        this.linea = l;
        this.columna = c;                 
        this.elseIf = elseIf;
    }    
    
    
    
    
    @Override    
    public Object ejectuar(Entorno entorno) 
    {
        Entorno local = new Entorno(entorno,entorno.ventana);
        Object condicional = condicion.getValor(entorno);             
        if(condicion.getTipo().typeprimitive== BOOL)
        {
            if((boolean)condicional)
            {
                Object resultado = instrucciones.ejectuar(local);
                if(resultado instanceof Break)
                {
                    return resultado;
                }                
            }
            else
            {
                if(elseIf != null)
                {
                    Object resultado = elseIf.ejectuar(entorno);
                    if(resultado instanceof Break)
                    {
                        return resultado;
                    }                    
                }
            }
        }
        else
        {
            Utilidades.Singlenton.registrarError("While", "La condición debe ser una expresión de tipo booleano." , ErrorC.TipoError.SEMANTICO, linea, columna);
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
