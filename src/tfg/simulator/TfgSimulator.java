
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tfg.simulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
/**
 *
 * @author troko
 */
public class TfgSimulator {

    private static void imprime_resultado(int num, ArrayList<Punto2D> mapacopia) {
        String texto = "";
        
        for(int i =0; i < mapacopia.size(); i++)
        {
            texto = texto + mapacopia.get(i).x + "," + mapacopia.get(i).y + "," + mapacopia.get(i).dato + "\n"; 
        }
       try{
        FileWriter fstream = new FileWriter("prueba" + num +".txt");
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(texto);
        out.close();
        }catch (Exception e){
         System.err.println("Error: " + e.getMessage());
        }
    }
    
    public static class Punto2D{
        int x;
        int y;
        String dato;
        
        public Punto2D(){
            this.x = 0;
            this.y = 0;
            this.dato = "";
        }
        
        public Punto2D(int x, int y, String dato){
            this.x= x;
            this.y = y;
            this.dato = dato;
        }
        
        public void set_dato(String dato)
        {
            this.dato = dato;
        }
        
        public Punto2D copia()
        {
            Punto2D punto = new Punto2D();
            
            punto.x = this.x;
            punto.y = this.y;
            punto.dato = this.dato;
            
            return punto;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Punto2D> mapa = new ArrayList<Punto2D>();
        String mapapuntos[][] = new String[500][500];
        int segundos = 10;
        int primer_punto_x = ThreadLocalRandom.current().nextInt(0, 500 + 1);
        int primer_punto_y = ThreadLocalRandom.current().nextInt(0, 500 + 1);
        Punto2D punto_ini = new Punto2D(primer_punto_x, primer_punto_y, "o");
        mapa.add(new Punto2D(0, 0, "-"));
        mapa.add(new Punto2D(500, 500, "-"));
        mapa.add(punto_ini);
        
        //mapa[primer_punto_x][primer_punto_y] = Punto2D()
        mapapuntos[primer_punto_x][primer_punto_y] = "o";
        mapapuntos[0][0] = "-";
        mapapuntos[499][499] = "-";
        int relleno = 1;
        
        while(relleno < 300){
            int x, y;
        x = ThreadLocalRandom.current().nextInt(0, 500);
        y = ThreadLocalRandom.current().nextInt(0, 500);
        System.out.println("x: " + x + " y: " + y );
        if(mapapuntos[x][y] == null){
            mapapuntos[x][y] = "x";
            
            mapa.add(new Punto2D(x, y, "x"));
            relleno++;
        }
        
        }
        
        imprime_resultado(99, mapa);
        
        for(int times = 0; times < segundos; times++){
        ArrayList<Punto2D> mapacopia = new ArrayList<Punto2D> (mapa.size());
        
        for (Punto2D punto: mapa) {
            mapacopia.add((Punto2D)punto.copia()); 
        }
        
            for(int i = 1; i < mapacopia.size(); i++)
            {
                Punto2D punto = mapacopia.get(i);

                if(punto.dato.equals("x")){
                    boolean conectado = false;
                    for(int j = 0; j < mapacopia.size() && conectado == false; j++){
                        if(j != i){
                            Punto2D compara = mapacopia.get(j);
                            if(compara.dato.equals("x")){
                               double distancia = getDistancia(punto, compara);
                               if(recibeMensaje(distancia))
                               {
                                   mapa.get(i).set_dato("o");
                                   conectado = true;
                                   System.out.println(punto.dato);
                               }
                            }
                        }

                    }
                }
            } 
            
            imprime_resultado(times, mapacopia);
        }
        // TODO code application logic here
    }
    
    private static double getDistancia(Punto2D puntoa, Punto2D puntob)
    {
        return Math.sqrt((puntoa.x -puntob.x)*(puntoa.x-puntob.x) + (puntoa.y -puntob.y)*(puntoa.y-puntob.y));

    }
    
    private static boolean recibeMensaje(double distancia){
        int error = ThreadLocalRandom.current().nextInt(0, 100+1);
        if(distancia < 15)
        {
            return(error > 10);
        }
        else if(distancia < 20)
        {
            return(error > 30);
        }
        else if(distancia < 26)
        {
            return(error > 90);
        }
        else
        {
            return false;
        }
    } 
}
