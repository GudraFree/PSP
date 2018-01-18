/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenes.trimestre1.ex1.lanzaTareas;

/**
 *
 * @author Perig
 */
public class TareaJuguete {
    public static void main(String[] args) {
        int returnValue = 0;
        try {
            float res = Float.parseFloat(args[0]) / Float.parseFloat(args[1]);
        } catch(IndexOutOfBoundsException e) {
            returnValue = 1;
        } catch (ArithmeticException e) {
            returnValue = 2;
        }
        System.out.println(returnValue+":TareaJuguete");
        System.exit(returnValue);
    }
}
