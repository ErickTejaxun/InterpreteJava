/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Funcion;

import AST.Clase.Objeto;
import AST.Entorno.Entorno;
import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;
import AST.Expresion.Expresion;
import AST.Expresion.Variable;
import Utilidades.ErrorC;
import java.util.ArrayList;

/**
 *
 * @author erick
 */
public class Llamada implements Expresion
{
    public int linea, columna;
    public String nombreMetodo;
    public ArrayList<Expresion> parametros;
    public Expresion origen;
    public Tipo tipo;
    
    /*Metodos sin parametros*/
    public Llamada(Expresion o, String n, int l, int c)
    {
        this.origen = o;
        this.linea = l;
        this.columna = c;
        this.nombreMetodo = n;
    }
    
    /*Método con parametros*/
    public Llamada(Expresion o, String n, ArrayList<Expresion> p, int l, int c)
    {
        this.origen = o;
        this.nombreMetodo = n;
        this.parametros = p;
        this.linea = l;
        this.columna = c;
    }

    public Llamada(Expresion o, ArrayList<Expresion> lista, int l, int c) 
    {
        this.origen = o;
        this.parametros = lista;
        this.linea = l;
        this.columna = c;
    }
    
    
    @Override
    public Object getValor(Entorno entorno) 
    {                
        /*Hay que generar la firma para poder llamar al método*/
        Entorno local = entorno;
        Entorno entornoLLamada = new Entorno(local.getGlobalClase(),local.ventana);
        if(origen!=null)
        {
            Object origenFuncion = null; //origen.getValor(entorno);
            if(origen instanceof Variable)
            {
                Variable var = (Variable)origen;
                Simbolo s = entorno.obtener(var.id);
                if(s instanceof Objeto)
                {
                    Objeto obj = (Objeto)s;
                    origenFuncion = obj;
                    //local = obj.entornoObjeto;
                }   
            }
            else
            {
                origenFuncion = origen.getValor(entorno);
            }
            
            if(origenFuncion==null)
            {
                Utilidades.Singlenton.registrarError(Utilidades.Singlenton.nombreVariable, "No encontrada" , ErrorC.TipoError.SEMANTICO, linea, columna);
                return null;
            }
            if(origenFuncion instanceof Objeto)
            {                
                Objeto objeto = (Objeto)origenFuncion;
                local = objeto.entornoObjeto;
                entornoLLamada = new Entorno(local,local.ventana);
            }
        }
                
        String firma = nombreMetodo;
        ArrayList<Object> resultados = new ArrayList<>();
        ArrayList<Tipo> tipos = new ArrayList<>();
        tipo = new Tipo("");
        for(Expresion e:parametros)
        {
            if(e instanceof Variable)
            {
                Variable var = (Variable)e;
                if(local.obtener(var.id)==null)
                {
                    Utilidades.Singlenton.registrarError(var.id, "Variable no declarada" , ErrorC.TipoError.SEMANTICO, var.linea, var.columna);
                    return null;
                }
                else
                {
                    Object tmp = e.getValor(local); 
                    if(tmp!=null)
                    {
                        firma +="$"+e.getTipo().nombreTipo().toLowerCase();
                        resultados.add(tmp);
                    }                     
                }

            }            
            else
            {
                Object tmp = e.getValor(local); 
                if(tmp!=null)
                {
                    firma +="$"+e.getTipo().nombreTipo().toLowerCase();
                    resultados.add(tmp);
                }
            }

        }     
        /*En caso de que la firma sea esa, es decir no hay que realizar casteos*/
        firma+="$";
        Simbolo f = local.getFuncion(firma);
        if(f!=null)
        {            
            if(f instanceof Funcion)
            {
                Funcion funcion = (Funcion)f;                
                /*Agregamos los valores a los parametros formales*/
                int cont = 0;                
                for(Object item:resultados)
                {
                    ParametroFormal formal = funcion.parametrosFormales.get(cont);                    
                    formal.valor =item;                    
                    cont++;
                }                
                /*Cremos un nuevo entorno*/
                
                
                Object resultado =  funcion.getValor(entornoLLamada);
                tipo = funcion.getTipo();
                return resultado;
            }                
        }
            
            
//            int combinaciones = combinaciones(parametros.size());
//            /*Todas las iteraciones para encontrar el método.*/
//            for(int i = 0 ; i < combinaciones ; i++ )
//            {
//                f = entorno.getGlobal().getFuncion(firma);
//                if(f==null)
//                {            
//                    continue;
//                }
//                if(f instanceof Funcion)
//                {
//                    Funcion funcion = (Funcion)f;                
//                    /*Agregamos los valores a los parametros formales*/
//                    int cont = 0;
//                    for(Object item:resultados)
//                    {
//                        ParametroFormal formal = funcion.parametrosFormales.get(cont);
//                        formal.valor =item;
//                        cont++;
//                    }
//                    Entorno local = new Entorno(entorno.getGlobal(),entorno.ventana);
//                    return funcion.getValor(local);
//                }                              
//            }            
            Utilidades.Singlenton.registrarError(nombreMetodo, "Metodo no encontrado con los parametros actuales enviados. "+firma , ErrorC.TipoError.SEMANTICO, linea, columna);    
        return null;
    }
    
    public Object ejecutarFuncion(Entorno entorno, String firma)
    {
        Simbolo f = entorno.getFuncion(firma);
        if(f==null)
        {            
            return null;
        }
        if(f instanceof Funcion)
        {
            Funcion funcion = (Funcion)f;                
            return funcion.getValor(entorno);
        }
        return null;
    }
    
    public Object ejecutarNativa(Entorno entorno)
    {
        if(origen!=null)
        {
            Object valor = origen.getValor(entorno);        
            switch(this.nombreMetodo.toLowerCase())
            {
                case "length":
                    if(origen.getTipo().isString())
                    {                    
                        return valor.toString().length();
                    }
                return null;
                case "otra":
                    return true;                
            }                    
        }
        
        return null;
    }
    
    public int combinaciones(int numero)
    {
        double combinaciones = Math.pow(numero, numero);
        if(combinaciones < Utilidades.Singlenton.maxInt)
        {
            String parte[] = String.valueOf(combinaciones).split("\\.");
            return Integer.parseInt(parte[0]);
        }
        return 1;
    }
           
    public boolean isNativa()
    {
        switch(nombreMetodo.toLowerCase())
        {
            case "length":                
            case "otra":
                return true;                
        }
        return false;
    }

    @Override
    public int linea() {
        return linea;
    }

    @Override
    public int columna() {
        return columna;
    }


    @Override
    public Tipo getTipo() {
        return tipo;
    }
    
    public int factorial(int n) 
    {
        int a = 1932053504;
        return n == 0 ? 1 : n * factorial(n - 1);
    }
    
    public int fibonaci(int n){
        return  (n==1 || n==2) ? 1 : fibonaci(n-1) + fibonaci(n-2);
    }     
    
}
