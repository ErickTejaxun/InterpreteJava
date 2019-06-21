/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion.Ciclos;

import AST.Entorno.Entorno;
import static AST.Entorno.Tipo.TypePrimitive.*;
import AST.Expresion.Expresion;
import AST.Instruccion.Bloque;
import AST.Instruccion.Instruccion;
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
        Inicio.ejectuar(local);        
        /*Se realiza la verificación de la condición*/
        Object condicional = condicion.getValor(local);                     
        if(condicion.getTipo().isBoolean())
        {            
            while((boolean)condicional)
            {
                Entorno localFor = new Entorno(local,entorno.ventana);
                Object resultado = instrucciones.ejectuar(localFor);
                if(resultado instanceof Break)
                {
                    break;
                }
                Actualizacion.getValor(local);
                condicional = condicion.getValor(local);
            }
        }
        else
        {
            Utilidades.Singlenton.registrarError("While", "La condición debe ser una expresión de tipo booleano." , ErrorC.TipoError.SEMANTICO, linea, columna);
        }
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
