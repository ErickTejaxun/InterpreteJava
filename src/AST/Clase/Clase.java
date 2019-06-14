/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Clase;

import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import AST.Expresion.Funcion.Funcion;
import AST.Instruccion.Instruccion;
import AST.Nodo;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Clase extends Simbolo implements Instruccion
{    
    public ArrayList<Funcion> listaFunciones;
    public ArrayList<Nodo> listaAtributos;
    public ArrayList<Clase> listaClaseMiembros;
    public String padre;
    public ArrayList<String> listaHijos;
    public ArrayList<String> modificadores;
            
    public Clase(ArrayList<String> lmod, String nombre, String padre, ArrayList<Funcion> lf)
    {
        this.modificadores = lmod;
        this.id = nombre;
        this.padre = padre;
        this.listaFunciones = lf;
    }
    
    @Override
    public Object ejectuar(Entorno entorno) 
    {
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
