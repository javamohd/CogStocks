/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cogentstocks;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author IS Mohammed
 */
public class SysParam {
    
    public static Map CurrentBill = new HashMap();
    
    public static int tmpPrice = 0;
    
    public static int itemCount = 0;
    
    public static ProgressFrame pf = null;
    
    public static Map<String, ItemObj> barCodeMappings = new HashMap();
    
}
