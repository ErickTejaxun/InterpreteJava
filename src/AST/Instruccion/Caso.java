/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Entorno.Entorno;
import AST.Expresion.Expresion;

/**
 *
 * @author erick
 */
public class Caso implements Instruccion
{
    public int linea, columna;
    Expresion condicion;
    Bloque instrucciones;
    
    public Caso(Expresion e, Bloque b,  int l, int c)
    {
        this.condicion = e;
        this.instrucciones = b;
        this.linea = l;
        this.columna = c;
    }
    @Override
    public Object ejectuar(Entorno entorno) 
    {
        return instrucciones.ejectuar(entorno);
    }

    @Override
    public int linea() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int columna() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
