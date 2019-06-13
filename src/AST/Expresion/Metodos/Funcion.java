/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Metodos;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import AST.Expresion.Expresion;
import AST.Instruccion.Instruccion;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Funcion extends Simbolo implements Instruccion
{
    ArrayList<Parametro> parametros;
    
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
