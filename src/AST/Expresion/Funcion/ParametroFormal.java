/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Funcion;

import AST.Entorno.Simbolo;
import AST.Entorno.Tipo;

/**
 *
 * @author erick
 */
public class ParametroFormal extends Simbolo
{
    boolean isFinal = false;
    
    public ParametroFormal(Tipo t, String id, int dim, int l, int c) 
    {
        super(t, id, dim, l, c);
    }
    
    public ParametroFormal(Tipo t, String id, boolean f, int dim, int l, int c) 
    {
        super(t, id, dim, l, c);
        isFinal = f;
    }    
    
}
