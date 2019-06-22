/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import AST.Instruccion.Instruccion;
import java.util.ArrayList;
import java.util.LinkedList;
import org.fife.ui.rtextarea.GutterIconInfo;

/**
 *
 * @author erick
 */
public class Singlenton 
{
    public static ArrayList<ErrorC> listaErrores = new ArrayList<ErrorC>();
    public static LinkedList<String> pilaArchivos = new LinkedList<String>(); 
    public static LinkedList<Instruccion> pilaCiclos = new LinkedList<Instruccion>();
    public static long maxInt = 2147483647;
    public static long minInt = -2147483648;
    public static int linea=0;
    public static int columna=0;
    public static boolean continuarEjecucion =true;
    public static String nombreVariable = "";
    public static ArrayList<Integer> breakPoints = new ArrayList<Integer>();
    public static ArrayList<GutterIconInfo> puntos = new ArrayList<>();
    
    
    
    
    public static void addPunto(GutterIconInfo p)
    {
        puntos.add(p);
    }        
    public static void setVariable(String d){nombreVariable = d;}   
    
    public static boolean isBreakPoint(int i)
    {
        for(Integer e: breakPoints)
        {
            if(e==i)
            {
                return true;
            }
        }
        return false;
    }
    
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
