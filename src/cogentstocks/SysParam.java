/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import Cart.ItemObj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author IS Mohammed
 */
public class SysParam {
    
    //public static Map<String,ItemObj> CurrentBill = new HashMap();
    
    //public static int tmpPrice = 0;
    
    //public static int itemCount = 0;
    
    public static ProgressFrame pf = null;
    
    public static Dashboard_1 dashb = null;
    
    public static ArrayList<String> customers = new ArrayList<>();
    
    public static Map custCredits = new HashMap();
    
    public static Map<String, ItemObj> barCodeMappings = new HashMap();
    public static Map<String, Integer> quantityMappings = new HashMap();
    
}
