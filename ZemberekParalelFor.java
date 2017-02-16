/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author omerfarukkoc
 * 24.Mart.2016
 */

import edu.rit.pj.IntegerForLoop;
import edu.rit.pj.ParallelRegion;
import edu.rit.pj.ParallelTeam;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;


public class ZemberekParalelFor {
    
    public static int paralelTeamSayisi = 8 ;
     
    public static int kelimeSayisi = 20000;
    
    public static String kelimeDizisi[] = new String[kelimeSayisi];
    public static String palindromKelimeDizisi[] = new String[kelimeSayisi];
    public static int palindromSayisi=0;
    
    public static long baslamaZamani ;
    public static long bitisZamani ;
    
    public static long okumaThreadBaslamaZamani;
    public static long okumaThreadBitisZamani;
    
    public static long yazdirmaThreadBaslamaZamani;
    public static long yazdirmaThreadBitisZamani;
    
    
    public static class dosyadanOkumaThread extends Thread{
    
        public void run() {
            Kelimeler kel = new Kelimeler(kelimeSayisi);
            kelimeDizisi = kel.KelimeOku();
        }    
    }
    
    
    public static class yazdirmaThread extends Thread{
    
        public void run() {
            System.out.println("Bulunan İki Kelimeli Palindromlar");
            for(int i=0; i< palindromSayisi; i++)
            {
                System.out.println(i + ". " + palindromKelimeDizisi[i]);
            }
        }    
    }
    
    public static void PalindromBul(int basla,int bitir){
        
        for(int i=basla; i<=bitir; i++)
             {
                for(int j=0; j< kelimeDizisi.length-1; j++)
                {
                    if(i != j)
                    {
                        String birinciKelimeninIlkHarfi = kelimeDizisi[i].substring(0,1);
                        String ikinciKelimeninSonHarfi = kelimeDizisi[j].substring((kelimeDizisi[j].length()-1) , (kelimeDizisi[j].length()));

                        if(birinciKelimeninIlkHarfi.equals(ikinciKelimeninSonHarfi))
                        {
                            String ikiliKelime = kelimeDizisi[i] + kelimeDizisi[j];
                            ikiliKelime = ikiliKelime.trim();
                            String ceviri = new StringBuilder(ikiliKelime).reverse().toString();

                                if(ikiliKelime.equals(ceviri))
                                {
                                    palindromKelimeDizisi[palindromSayisi]=ikiliKelime;
                                    palindromSayisi++;
                                }
                        }
                    }
                }
            }
    }
    
    public static void main(String[] args) throws IOException 
    {
        okumaThreadBaslamaZamani = System.currentTimeMillis();
        dosyadanOkumaThread okuu = new dosyadanOkumaThread();
        okuu = new dosyadanOkumaThread();
        okuu.start();
        
        
        boolean notDone = true;
        while(notDone){
            
            notDone = false;
                if(okuu.isAlive())
                    notDone = true;
            
                try{
                    Thread.sleep(100);
                }catch(Exception e){
            }
        }
       
        okumaThreadBitisZamani = System.currentTimeMillis();
        
        
        ParallelTeam team = new ParallelTeam(paralelTeamSayisi);
       
        try {
            team.execute(new ParallelRegion() {

                public void run() {
                    try {
                        execute(0, kelimeSayisi-1, new IntegerForLoop() {
                              
                            public void run(int basla,int bitir) {
                                    baslamaZamani = System.currentTimeMillis();
                                    PalindromBul(basla,bitir);
                                    bitisZamani = System.currentTimeMillis();
                               

                            }
                        });
                    } catch (Exception ex) {
                        Logger.getLogger(ZemberekParalelFor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(ZemberekParalelFor.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        yazdirmaThreadBaslamaZamani = System.currentTimeMillis();
        yazdirmaThread yazdirr = new yazdirmaThread();
        yazdirr = new yazdirmaThread();
        yazdirr.start();
        
        boolean notDone1 = true;
        while(notDone1){
            
            notDone1 = false;
                if(yazdirr.isAlive())
                    notDone1 = true;
            
                try{
                    Thread.sleep(100);
                }catch(Exception e){
            }
        }
        yazdirmaThreadBitisZamani = System.currentTimeMillis();
        
        /*
        for (int i = 0; i< paralelTeamSayisi; i++)
        {
            e[i]=(bitisZamani[i]-baslamaZamani[i]);
            //System.out.println(i+"baslama"+ (bitisZamani[i]-baslamaZamani[i]));
            //System.out.println(i+"bitis"+ bitisZamani[i]);
            
        }
        */
        System.out.println();
        System.out.println("Taranan Toplam Kelime Sayısı: " + kelimeSayisi);
        System.out.println();
        System.out.println("Çalışan Thread Sayısı: " + paralelTeamSayisi);
        System.out.println();
        System.out.println("Bulunan Toplam Palindrom Sayısı: " + palindromSayisi);
        System.out.println(); 
        System.out.println("Okuma İşlemi İçin Geçen Süre: " + (okumaThreadBitisZamani-okumaThreadBaslamaZamani) + " ms");
        System.out.println("Palindrom Bulma İşlemi İçin Geçen Süre: " + (bitisZamani-baslamaZamani) + " ms");
        System.out.println("Ekrana Yazdırma İşlemi İçin Geçen Süre: " + (yazdirmaThreadBitisZamani-yazdirmaThreadBaslamaZamani) + " ms");
        System.out.println();
    }
    
}
