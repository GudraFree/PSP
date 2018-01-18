/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenes.trimestre1.exTrimestral.ejercicioHilos;

import java.util.ArrayList;

/**
 *
 * @author Perig
 */
public class SumaProductos {
    public static void main(String[] args) {
        SumaProductos sp = new SumaProductos();
        ArrayList<String> operacion = sp.agrupadorOperaciones(sp.descomponOperacion(args[0]));
        sp.calculadora(operacion);
        
    }
    
    private ArrayList<String> descomponOperacion(String operacion) {
        ArrayList<String> opDescomp = new ArrayList();
        String[] caracteres = operacion.split("");
        String num = "";
        for (String c : caracteres) {
            if(!c.equals("+") && !c.equals("*")) num+=c; //es un número, se añade al número que se está procesando ahora
            else { //hemos dado con un operando
                opDescomp.add(num); //lo añadimos como elemento string al array de la operación
                opDescomp.add(c); //añadimos también la operación
                num="";
            }
        } // cuando terminamos el bucle añadimos el último número a la operación
        opDescomp.add(num);
        return opDescomp;
    }
    
    private ArrayList<String> agrupadorOperaciones(ArrayList<String> operacion) {
        ArrayList<String> operacionAgrupada = new ArrayList();
//        String anterior = "";
        boolean saltateElSiguiente = false;
        int size = operacion.size();
        for (int i=1; i<size; i+=2) {
//            if(!saltateElSiguiente) {
                String o = operacion.get(i);
//                System.out.println(o);
                if(o.equals("*")) { // estamos en un producto
//                    System.out.println("estamos en un producto");
                    String anterior = operacion.get(i-1);
                    if(!saltateElSiguiente) { // NO hay una operación ya agrupada como miembro anterior
//                        System.out.println("NO hay una operación ya agrupada como miembro anterior");
                        String producto = "("+anterior+o+operacion.get(i+1)+")";
                        saltateElSiguiente = true;
                        operacionAgrupada.add(producto);
                    } else { // no podemos agrupar, así que añadimos el símbolo * y los números que lo rodean
//                        System.out.println("no podemos agrupar, así que añadimos el símbolo * y los números que lo rodean");
                        if(!saltateElSiguiente) operacionAgrupada.add(anterior);
                        operacionAgrupada.add(o);
                        if(!saltateElSiguiente) operacionAgrupada.add(operacion.get(i+1));
                        saltateElSiguiente = false;
                    }
                } else { // fin producto
//                    System.out.println("No era un producto");
                        if(!saltateElSiguiente) operacionAgrupada.add(operacion.get(i-1));
                        operacionAgrupada.add(o);
                        saltateElSiguiente = false;
                }
                
                
//                else { //estamos en un dígito
//                    if (!saltateElSiguiente) { // si no se me ha ordenado que lo salte, lo introduzco a la operación
//                        operacionAgrupada.add(o);
//                    } else { //si me lo ha ordenado, reinicio el booleano
//                        saltateElSiguiente = false;
//                    }
//                }
//            }
        }
        imprimeArray(operacionAgrupada);
        System.out.println("");
        
        
        
//        operacion = operacionAgrupada;
//        operacionAgrupada = new ArrayList();
//        size = operacion.size();
//        
//        for (int i=1; i<size; i+=2) {
//                String o = operacion.get(i);
//            if (o.equals("+")) { // estamos en una suma
//                    String dosAntes = "";
//                    String anterior = operacion.get(i-1);
//                    String posterior = operacion.get(i+1);
//                    try {
//                        dosAntes = operacion.get(i-2);
//                    } catch (IndexOutOfBoundsException e) {} // me trago el error si existe
//                    if(dosAntes.equals("") || dosAntes.equals("+")) { //si no hay un producto antes, seguimos comprobacion
//                        if(i==size-2) { //si estamos en el último operando, seguimos comprobacion
//                            if(!saltateElSiguiente) { // NO hay una operación ya agrupada como miembro anterior
//                                String suma = "("+anterior+o+posterior+")";
//                                saltateElSiguiente = true;
//                                operacionAgrupada.add(suma);
//                            }
//                        } else { //no estamos en el último operador, ergo hay un operador 2 casillas más alante
//                            String dosDespues = operacion.get(i+2);
//                            if(dosDespues.equals("+")) { // nuestra suma está rodeada por sumas y podemos intentar agruparla
//                                if(!anterior.contains("(") && posterior.contains("(")) { // NO hay una operación ya agrupada como miembro anterior
//                                    String suma = "("+anterior+o+operacion.get(i+1)+")";
//                                    saltateElSiguiente = true;
//                                    operacionAgrupada.add(suma);
//                                }
//                            }
//                        }
//                    }// no podemos agrupar, así que añadimos el símbolo * y los números que lo rodean
////                        System.out.println("no podemos agrupar, así que añadimos el símbolo * y los números que lo rodean");
//                        if(!saltateElSiguiente) operacionAgrupada.add(anterior);
//                        operacionAgrupada.add(o);
//                        if(!saltateElSiguiente) operacionAgrupada.add(operacion.get(i+1));
//                        saltateElSiguiente = false;
//                }
//        }
        
        return operacionAgrupada;
    }
    
    private void imprimeArray(ArrayList<String> a) {
        for (String s : a) {
            System.out.print(s+" ");
        }
    }
    
    private void calculadora(ArrayList<String> operacion) {
        if(operacion.size()==3) {
            String n1 = operacion.get(0);
            String n2 = operacion.get(2);
            String op = operacion.get(1);
            if (op.equals("+")) System.out.println("Resultado: "+(Integer.parseInt(n1)+Integer.parseInt(n2)));
            if (op.equals("*")) System.out.println("Resultado: "+(Integer.parseInt(n1)*Integer.parseInt(n2)));
        } else {
            for (int i=0; i<operacion.size(); i++) {
                String op = operacion.get(i);
                if(op.contains("(")) {
                    Thread t = new CalculaThread(operacion, op, i);
                    t.start();
                }
            }

            calculadora(agrupadorOperaciones(operacion));
        }
    }
}
