/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Entorno.Entorno;
import static AST.Entorno.Tipo.TypePrimitive.*;
import AST.Expresion.Expresion;
import Utilidades.ErrorC;

/**
 *
 * @author erick
 */
public class For implements Instruccion
{
    int linea, columna;
    public Instruccion Inicio;
    public Expresion condicion;
    public Expresion Actualizacion;
    public Bloque instrucciones;    
    
    public For(Instruccion i, Expresion cond, Expresion act, Bloque inst, int l, int c)
    {
        this.Inicio = i;
        this.condicion = cond;
        this.Actualizacion = act;
        this.linea = l;
        this.columna = c;
        this.instrucciones = inst;
    }        
    
    @Override    
    public Object ejectuar(Entorno entorno) 
    {              
        Entorno local = new Entorno(entorno,entorno.ventana);
        /*Se genera la declaración o asignación*/
        Inicio.ejectuar(entorno);        
        /*Se realiza la verificación de la condición*/
        Object condicional = condicion.getValor(entorno);                     
        if(condicion.getTipo().isBoolean())
        {            
            while((boolean)condicional)
            {
                local = new Entorno(entorno,entorno.ventana);
                Object resultado = instrucciones.ejectuar(local);
                if(resultado instanceof Break)
                {
                    break;
                }
                Actualizacion.getValor(entorno);
                condicional = condicion.getValor(entorno);
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
