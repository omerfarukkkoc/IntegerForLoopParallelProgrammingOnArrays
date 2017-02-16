/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Muhammed Emin YÖRÜK
 * 20.Mart.2016
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Kelimeler {
    
    public static String kelimeler[]; 
    
    public Kelimeler(int kelimeSayisi) {
        kelimeler = new String[kelimeSayisi];
}
    
    
    
    public static String[] KelimeOku() {
        
        try
        {
            FileReader fr = new FileReader("zemberek.txt");
            BufferedReader br = new BufferedReader(fr);
            
            String str;
            int i =0;
            while((str = br.readLine()) != null)
            {
                if(i < kelimeler.length)
                {
                    kelimeler[i] = str;
                }
                i++;
            }
            br.close();
            
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return kelimeler;
    }
}
