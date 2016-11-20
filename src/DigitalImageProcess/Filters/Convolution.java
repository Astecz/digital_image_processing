/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorismar
 *
public class Convolution extends DigitalImageProcess.DigitalProcess {

    @Override
    protected int transform(BufferedImage img, int px, int py, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void test(String namein, int select, int selectOffset, int offset) throws IOException {
    	
    	String nameout = "catlokao.jpg";
        ArrayList<Float> auxiliar = new ArrayList<Float>();
    	BufferedImage image = null, output;
        float aux;
        int m=0;
        int n=0;
        float [] sharpen = new float[1];

        if(select == 3){ //mascara digitada pelo ususario
            while((m*n)<9){
                m = Integer.parseInt(JOptionPane.showInputDialog("Digite o tamanho M da mascara (lembre que o produto M*N>=9):"));
                n = Integer.parseInt(JOptionPane.showInputDialog("Digite o tamanho N da mascara (lembre que o produto M*N>=9):"));

                if((m*n)<9)
                    JOptionPane.showMessageDialog(null,"M*N deve ser >= 9!");
            }
            for(int i=0; i<m; i++){
                for(int j=0; j<n; j++){
                    aux = Float.parseFloat(JOptionPane.showInputDialog("Digite o " + (j+1) +"° termo da linha "+ (i+1))); //LÃª a custom mask
                    auxiliar.add(aux);
                }
            }
            //sharpen = convertIntToFloat(auxiliar);//Gera o array que vai virar kernel		
        }

        float[] buffer = new float [m*n];
        
        for(int cont=0;cont<(m*n);cont++)/////////////////MUDOU!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            buffer[cont] = auxiliar.get(cont);
        
        sharpen=buffer;

        if(select == 1){ //filtro de aguçamento!!!!!!
            int c,d;
            c = Integer.parseInt(JOptionPane.showInputDialog("Digite a constante C:"));
            d = Integer.parseInt(JOptionPane.showInputDialog("Digite a constante D:"));
            sharpen = new float [] {-c, -c, -c, -c, (8*c)+d, -c, -c, -c, -c}; //Gera o array q vai virar kerneljÃ¡ com as coordenadas!
        }

        try {
            image = ImageIO.read(new File(namein));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage dstImage = null;

        Kernel kernel = new Kernel(3, 3, sharpen);
        ConvolveOp op = new ConvolveOp(kernel); //Faz a convoluÃ§Ã£o multithread do Java... Depois posso aplicar o offset
        dstImage = op.filter(image, null);

        if(selectOffset==1) {
            for (int i = 0; i < dstImage.getHeight(); i++) {
                for (int j = 0; j < dstImage.getWidth(); j++) {
                    int pixel = dstImage.getRGB(i, j);
                    Color cor = new Color(pixel,true);
                    int somaR = cor.getRed();
                    int somaG = cor.getGreen();
                    int somaB = cor.getBlue();
                    somaR+=offset;
                    somaG+=offset;
                    somaB+=offset;
                    if(somaR>255) //Pra nÃ£o estourar!
                            somaR=255;
                    if(somaG>255)
                            somaG=255;
                    if(somaB>255)
                            somaB=255;
                    if(somaR<0)
                            somaR=0;
                    if(somaG<0)
                            somaG=0;
                    if(somaB<0)
                            somaB=0;
                    cor = new Color(somaR,somaG,somaB);					
                    dstImage.setRGB(i, j, cor.getRGB());
                }
            }
        }

        ImageIO.write(dstImage, "jpg", new File(nameout)); //CRIO A IMAGEM NA PASTA COM O NOME DADO EM nameout E DO TIPO JPG

//    	return new ImageIcon(dstImage);  
    }

}*/
