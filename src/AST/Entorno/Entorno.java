/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Entorno;

import static AST.Entorno.Simbolo.Rol.FUNCION;
import Utilidades.ErrorC;
import interprete.Interfaz;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erick
 */
public class Entorno 
{
    public Entorno anterior;
    public Hashtable<String, Simbolo> tabla;
    public Interfaz ventana;   
    
    public Entorno(Entorno entorno)
    {
        this.anterior = entorno;
        this.tabla = new Hashtable<String, Simbolo>();
    }
    
    public Entorno(Entorno entorno, Interfaz v)
    {
        this.ventana = v;
        this.anterior = entorno;
        this.tabla = new Hashtable<String, Simbolo>();
    }    
        
    public boolean insertar(Simbolo simbolo)
    {        
        Simbolo tmp = tabla.get(simbolo.id); 
        /*Error de variable ya declarada.*/
        if(tmp !=null)
        {
            if(tmp.rol==simbolo.rol)
            {
                Utilidades.Singlenton.registrarError(simbolo.id, simbolo.rol +" ya declarado ", ErrorC.TipoError.SEMANTICO, simbolo.linea, simbolo.columna);
                return false;                
            }
        }
        tabla.put(simbolo.id, simbolo);        
        return true;
    }
    
    public boolean actualizar(Simbolo simbolo)
    {
        Entorno tmp = this;
        while(tmp!=null)
        {      
            Simbolo s = tmp.tabla.get(simbolo.id);
            if(s!=null)
            {
                tmp.tabla.put(simbolo.id, simbolo);
                return true;
            }
            tmp = tmp.anterior;
        }
        return false;
    }
    
    int suma()
    {
        return 1;
    }
    
    public Simbolo obtener(String id)
    {
        Entorno tmp = this;
        while(tmp!=null)
        {      
            Simbolo s = tmp.tabla.get(id);
            if(s!=null)
            {
                return s;
            }
            tmp = tmp.anterior;
        }
        //Utilidades.Singlenton.registrarError(id,"Variable no declarada", ErrorC.TipoError.SEMANTICO, 0,0);
        return null;
    }
    
    public void quitarSimbolo(String id)
    {
        this.tabla.remove(id);
    }
    
    
    public void ReporteEntorno()
    {
        /*System.out.println("----------------------------------------------");
        Enumeration num = this.tabla.keys();
        while(num.hasMoreElements())
        {
            Object clave = num.nextElement();
            Simbolo s = tabla.get(clave);
            System.out.println(s.getMessage());            
        }*/
    }

    public String Tabla() throws IOException
    {
        Entorno auxiliar = this;
        String reporte ="";
        while(auxiliar!=null)
        {
            reporte += "\n" + "--------------Entorno local-----------";
            Enumeration num = auxiliar.tabla.keys();
            while(num.hasMoreElements())
            {
                Object clave = num.nextElement();
                Simbolo s = auxiliar.tabla.get(clave);
                if(reporte.equals(""))
                {
                    reporte+=s.getMessage();            
                }            
                else
                {
                    reporte+="\n"+s.getMessage();            
                }
            }             
            auxiliar = auxiliar.anterior;
        }       

        generarGrafica();
        return reporte;
    }
    
    public String Grafica()
    {
        String cadena = "digraph D {";
        cadena+="node [shape=plaintext fontname=\"Sans serif\" fontsize=\"8\"];";
        /*Aquí comienza la gráfica*/
        Entorno auxiliar = this;        
        while(auxiliar!=null)
        {            
            cadena+=auxiliar.hashCode()+"[ label=<";
            cadena+="<table border=\"1\" cellborder=\"0\" cellspacing=\"1\">";
            Enumeration num = auxiliar.tabla.keys();
            while(num.hasMoreElements())
            {
                Object clave = num.nextElement();
                Simbolo s = auxiliar.tabla.get(clave);
                cadena+="<tr>"
                        + "<td align=\"left\">"+s.id+"</td>"
                        + "<td align=\"left\">"+s.tipo.nombreTipo()+"</td>"
                        + "<td align=\"left\">"+s.rol+"</td>"
                        + "<td align=\"left\">"+s.valor+"</td>"
                        + "<td align=\"left\">"+s.dimensiones+"</td>"
                        + "<td align=\"left\">"+s.linea+"</td>"
                        + "<td align=\"left\">"+s.columna+"</td>"
                        + "</tr>";
            }         
            cadena+=" </table>>];";
            auxiliar = auxiliar.anterior;
        }
        auxiliar = this;
        while(auxiliar.anterior!=null)
        {
            cadena+= auxiliar.hashCode()+"        -> "+auxiliar.anterior.hashCode()+";";
            auxiliar = auxiliar.anterior;
        }                
        cadena +="}";    
        return cadena;
    }
    
    public void generarGrafica() throws IOException 
    {
        System.out.println("Graficando");

        String dotPath = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";        
        String direccionSalida = this.ventana.PathActual()+"\\entorno.jpg";
        String direccionEntrada = this.ventana.PathActual()+"\\entorno.txt";
        //System.out.println("Graficando");
        String cadena = Grafica();
        //System.out.println(cadena);
        /*---------------------------------------------------------------------------------*/
        try (  PrintWriter writer = new PrintWriter(direccionEntrada)) {
            writer.print(cadena);            
        } 
        /*---------------------------------------------------------------------------------*/
        
        
        String tParam = "-Tpng";
        String tOParam = "-o";
        
        //dot -Tpng ast.txt -o ast.jpg

        String[] cmd = new String[5];
              cmd[0] = dotPath;
              cmd[1] = tParam;
              cmd[2] = direccionEntrada;
              cmd[3] = tOParam;
              cmd[4] = direccionSalida;

              Runtime rt = Runtime.getRuntime();

              rt.exec( cmd );
              //"\\ast.jpg";
    }
    
    public Simbolo getFuncion(String firma)
    {
        Entorno tmp = this;
        while(tmp!=null)
        {      
            Simbolo s = tmp.tabla.get(firma);
            if(s!=null)
            {
                if(s.rol == FUNCION)
                {
                    return s;
                }                
            }
            tmp = tmp.anterior;
        }
        //Utilidades.Singlenton.registrarError(id,"Variable no declarada", ErrorC.TipoError.SEMANTICO, 0,0);
        return null;
    }
    
    public Entorno getGlobalClase()
    {              
        Entorno auxiliar = this;
        Entorno auxiliar2 = this;          
        while(auxiliar2.anterior!=null)
        {
            auxiliar = auxiliar2;
            auxiliar2 = auxiliar2.anterior;
        }        
        return auxiliar;        
    }
    
    
    public Entorno getGlobalObjeto()
    {              
        Entorno auxiliar = new Entorno(null);
        Entorno auxiliar2 = new Entorno(null);
        auxiliar2 = this;         
        while(auxiliar2.anterior!=null)
        {
            auxiliar = auxiliar2;
            auxiliar2 = auxiliar2.anterior;
        }        
        return auxiliar;        
    }    
    
    public Entorno getGlobal()
    {
        Entorno auxiliar = this;
        while(auxiliar.anterior!=null)
        {
            auxiliar = auxiliar.anterior;
        }
        return auxiliar;
    }
}
