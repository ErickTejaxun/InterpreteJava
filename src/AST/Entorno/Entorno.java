/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Entorno;

import static AST.Entorno.Simbolo.Rol.FUNCION;
import Utilidades.ErrorC;
import interprete.Interfaz;
import interprete.Ventana;
import java.util.Enumeration;
import java.util.Hashtable;

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

    public String Tabla()
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
        return reporte;
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
