/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import AST.Instruccion.Instruccion;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author erick
 */
public class Singlenton 
{
    public static ArrayList<ErrorC> listaErrores = new ArrayList<ErrorC>();
    public static LinkedList<String> pilaArchivos = new LinkedList<String>(); 
    public static LinkedList<Instruccion> pilaCiclos = new LinkedList<Instruccion>();
    
    
    
    public static void apliarCiclo(Instruccion e)
    {
        pilaCiclos.push(e);
    }
    
    public static void desaplicarCliclo()
    {
        pilaCiclos.pop();
    }
    
    public static Instruccion getCicloActual()
    {
        return pilaCiclos.getLast();
    }
    
    public static void addArchivo(String s)
    {
        pilaArchivos.push(s);
    }
    
    public static void quitarArchivo()
    {
        pilaArchivos.pop();
    }
    
    public static String getArchivoActual()
    {
        return pilaArchivos.getLast();
    }
            
    public static void registrarError(String id, String desc, ErrorC.TipoError t, int l, int c)
    {
        //Error(ErrorC.Tipo tipo, String descripcion, String archivo, int linea, int columna)
        listaErrores.add(new ErrorC( id, t, desc, getArchivoActual(), l, c));
    }
}
