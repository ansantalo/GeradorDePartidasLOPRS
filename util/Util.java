package util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andre
 */
public class Util {
 
    public static int random(int min, int max)
    {
        return min + (int)(Math.random() * ((max - min) + 1));        
    }
}
