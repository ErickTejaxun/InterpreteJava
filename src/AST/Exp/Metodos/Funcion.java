/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Exp.Metodos;

import AST.Ent.Entorno;
import AST.Ent.Simbolo;
import AST.Ent.Tipo;
import AST.Instruccion.Instruccion;

/**
 *
 * @author erick
 */
public class Funcion extends Simbolo implements Instruccion
{
    
    public Funcion(Tipo t, String id, int dim, int l, int c) 
    {
        super(t, id, dim, l, c);
    }

    @Override
    public Object ejectuar(Entorno entorno) 
    {
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
