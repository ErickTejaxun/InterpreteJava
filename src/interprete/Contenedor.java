/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interprete;
import Utilidades.ErrorC;
import java.util.ArrayList;
/**
 *
 * @author erick
 */
public class Contenedor 
{
    public static ArrayList<ErrorC> listaErrores = new ArrayList<ErrorC>();
    public static void agregarError(ErrorC.TipoError tipo, String descripcion, String archivo, int linea, int columna)
    {
        listaErrores.add(new ErrorC(tipo,descripcion, archivo, linea, columna));
    }
    public static void agregarError(ErrorC.TipoError tipo, String descripcion, int linea, int columna)
    {
        listaErrores.add(new ErrorC(tipo,descripcion, linea, columna));
    }    
}
