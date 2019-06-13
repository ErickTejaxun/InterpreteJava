/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Expresion.Metodos;

import AST.Entorno.Tipo;

/**
 *
 * @author erick
 */
public class Parametro 
{
    public String id;
    public Tipo tipo;
    
    public Parametro(String i, Tipo t)
    {
        this.tipo = t;
        this.id = i;
    }
}
