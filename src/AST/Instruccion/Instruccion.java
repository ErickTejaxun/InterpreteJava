/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AST.Instruccion;

import AST.Entorno.Entorno;
import AST.Nodo;
import AST.Nodo;

/**
 *
 * @author erick
 */
public interface Instruccion extends Nodo
{           
    Object ejectuar(Entorno entorno);
}
