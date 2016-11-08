

import java.util.*;

import javax.swing.*;  

import java.awt.*;  
import java.awt.image.*; 

import javax.imageio.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File; 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
  
public class Img {  
	
	
	public static int desvioR = 0;
	public static int desvioG = 0;
	public static int desvioB = 0;
	
    //CODIGO EM COMUM PARA ALGUMAS APLICACOES
	//COPIA UMA IMAGEM JÁ ABERTA PARA TRABALHAR SEM ALTERAR A ORIGINAL.
	//PARÂMETRO DE ENTRADA: IMAGEM ABERTA DE ARQUIVO.
	//Saída: imagem já copiada
    private static BufferedImage deepCopy(BufferedImage image) { //Copia a imagem de uma forma mais eficiente do que fazer um for que prejudica o tempo de execucao do programa
        ColorModel cm = image.getColorModel();
        boolean caracteristicaAlfa = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, caracteristicaAlfa, null);
    }
          


    //CODIGO DA TRANSFORMACAO DA IMAGEM NORMAL PARA IMAGEM EM NEGATIVO
    //FAZ O NEGATIVO DE UMA IMAGEM ABERTA
    //PARAMETRO DE ENTRADA: IMAGEM ABERTA (JÁ COPIADA)
    //Saída: imagem já em negativo (ImageIcon)
    public static ImageIcon negativo(String namein) throws Exception{  // EFEITO NEGATIVO
    	
    	BufferedImage image;
        
        int pixel;
        Color cor;

    	image = ImageIO.read(new File(namein));
	    
        for(int i = 0; i<image.getWidth(); i++){
            for(int j = 0; j<image.getHeight(); j++){
                pixel =  image.getRGB(i, j);
                cor = new Color(pixel, true); //Cria cor RGB baseado no RGB da variavel especifica (pixel)
                cor = new Color(255 - cor.getRed(), 255 - cor.getGreen(), 255 - cor.getBlue());
                image.setRGB(i, j, cor.getRGB());
            }
        }

        return new ImageIcon( image );  
    }  
    
    //CODIGO DA APLICACAO DO FILTRO DA MEDIA EM CADA PIXEL
    //PARAMETROS DE ENTRADA: image - Imagem aberta ja copiada
    //						 "i" e "j" - posicao do pixel que estamos calculando a media
    //							"n"		- tamanho da matriz quadrada da mascara
    // SAÍDA: Cor média do pixel i, j
    private static Color valPixel(BufferedImage image, int i, int j, int n){
        int pixel = image.getRGB(i, j);
        Color cor = new Color(pixel, true);
        
        int mediaR = 0;
        int mediaG = 0;
        int mediaB = 0;

        int aux = n*n;

        for(int a = i-Math.round((float)n/2); a<i+(n/2); a++){
            for (int b = j-Math.round((float)n/2); b<j+(n/2); b++) {
                if(a<0 || b<0 || a>(image.getWidth()-1) || b>(image.getHeight()-1)){
                    aux--; //Desconsidero Ã¡reas foras da matriz da imagem
                }else{
                    pixel = image.getRGB(a, b);
                    cor = new Color(pixel, true);
                    mediaR += cor.getRed();
                    mediaG += cor.getGreen();
                    mediaB += cor.getBlue();
                 
                }
            }
        }

        mediaR = Math.round(mediaR/aux);
        mediaG = Math.round(mediaG/aux);
        mediaB = Math.round(mediaB/aux);
        
        cor = new Color(mediaR, mediaG, mediaB);

        return cor;
    }

    
    //CÓDIGO DA CONVERSÃO RGB PARA YIQ
    //PARAMETROS DE ENTRADA: image - imagem aberta já copiada
    // 						 nameout - nome do arquivo .iyq de saída da imagem convertida
    //Saída: já escreve no arquivo do caminho "nameout"
    public static void RGBtoYIQ (BufferedImage image, String nameout) throws Exception{
		
		File file = new File(nameout);
			// se o arquivo nÃ£o existir, crie!
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		for(int h=0;h<image.getHeight();h++){
			for(int w=0;w<image.getWidth();w++){

				int pixel = image.getRGB(w, h);
				Color cor = new Color(pixel,true);
				int red = cor.getRed();
				int green = cor.getGreen();
				int blue = cor.getBlue();
				float y = ((0.299f * red) + (0.587f * green) + (0.114f * blue)) / 256;
				float i = ((0.596f * red) - (0.274f * green) - (0.322f * blue)) / 256;
				float q = ((0.212f * red) - (0.523f * green) + (0.311f * blue)) / 256;

				try {

					String content = y + "," + i + "," + q + "\t"+"\n";

					if((h==0) && (w==0))
						bw.write(image.getWidth()+","+image.getHeight()+"\t"+"\n");//Escreve a largura e altura pra poder recuperar na volta								
					bw.write(content);


					} catch (IOException e) {
						e.printStackTrace();
					}
    		}
		}
		bw.close();
    }

    //CÓDIGO DA CONVERSÃO YIQ PARA RGB
    //PARAMETROS DE ENTRADA: namein - caminho do arquivo .yiq
    //SAÍDA: imagem já recuperada, (imageIcon)

    public static ImageIcon YIQtoRGB(String namein) throws Exception{
	    BufferedImage result = null;
		BufferedReader br = new BufferedReader(new FileReader(namein));
		int counter = 0;
		int poswidth = 0;
		int posheight = 0;
		String width ="";
		String height ="";
			try {
    				String line = br.readLine();
				
					while((line.charAt(counter) != ',') && (counter<=line.length())){
						width += Character.toString(line.charAt(counter));
						counter++;
					}
					
					counter++;
				
					int widthnumber = Integer.parseInt(width);

					while((line.charAt(counter) != '\t') && (counter<line.length())){
						height += Character.toString(line.charAt(counter));
						counter++;
					}
					
					counter++;
				
					int heightnumber = Integer.parseInt(height);
					BufferedImage dstImage = new BufferedImage(widthnumber,heightnumber,BufferedImage.TYPE_INT_RGB);
					line = br.readLine();

    				while ((line != null) && (posheight < heightnumber-1)){
						int counter1 = 0;
						String y_guardado = "";
						String i_guardado = "";
						String q_guardado = "";

						while((line.charAt(counter1) != ',') && (counter1<=line.length())){
							y_guardado += Character.toString(line.charAt(counter1));
							counter1++;
						}
						//System.out.println(y_guardado);

						
						counter1++;
				
						while((line.charAt(counter1) != ',') && (counter1<=line.length())){
							i_guardado += Character.toString(line.charAt(counter1));
							counter1++;
						}
						//System.out.println(i_guardado);

						counter1++;

						while((line.charAt(counter1) != '\t') && (counter1<line.length())){
							q_guardado += Character.toString(line.charAt(counter1));
							counter1++;
						}
						//System.out.println(q_guardado);


						float yres = Float.parseFloat(y_guardado);
						float ires = Float.parseFloat(i_guardado);
						float qres = Float.parseFloat(q_guardado);
						float rf = (yres + (0.956f * ires) + (0.621f * qres));
						float gf = (yres - (0.272f * ires) - (0.647f * qres));
						float bf = (yres - (1.105f * ires) + (1.702f * qres));
						int r = (int)( rf * 256 );
						int g = (int)( gf * 256 );
						int b = (int)( bf * 256 );
						if(r>255)
							r=255;
						if(g>255)
							g=255;
						if(b>255)
							b=255;
						if(r<0)
							r=0;
						if(g<0)
							g=0;
						if(b<0)
							b=0;
					
						if(poswidth == widthnumber){
							poswidth=0;
							posheight++;
						}
						
						Color colocar = new Color(r,g,b);
						int rgb = colocar.getRGB();
						dstImage.setRGB(poswidth,posheight,rgb);
						poswidth++;
        				line = br.readLine();
    				}
					result = dstImage;

			}catch(Exception E){
				E.printStackTrace();	

			}finally {
    			br.close();
			}
		return new ImageIcon(result);
}

      
      //APLICA O FILTRO DA MÉDIA USANDO O VALOR MÉDIO DO PIXEL (FUNÇÃO valPixel)
     //PARÂMETROS DE ENTRADA: namein - caminho do arquivo de imagem de entrada
    //						  n - tamanho da matriz da máscara para passar para valPixel
    // Saída: imagem já com o filtro da média aplicado
    public static ImageIcon filtroMedia(String namein, int n) throws Exception{
        BufferedImage image, output;

        int pixel;
        Color cor;

        image = ImageIO.read(new File(namein));
        output = deepCopy(image);
     
        
        for(int i = 0; i<image.getWidth(); i++){
            for(int j = 0; j<image.getHeight(); j++){
                cor = valPixel(image, i, j, n);
                output.setRGB(i, j, cor.getRGB());
            }
        }

        //ImageIO.write(output, "jpg", new File(nameout)); //CRIO A IMAGEM NA PASTA COM O NOME DADO EM nameout E DO TIPO JPG

        return new ImageIcon( output );  
    }  




    //CODIGO DA APLICACAO DE FILTRO DE MEDIANA, FAZ A MEDIANA DE CADA PIXEL
    //ENTRADA: image - imagem já carregada e copiada
    //			i e j - valora da posição do pixel que estamos calculando a mediana
    //			n - tamanho da matriz de vizinhança
    //SAÍDA: Cor mediana do pixel i, j
    private static Color valPixelMediana(BufferedImage image, int i, int j, int n){
        int pixel = image.getRGB(i, j);
        Color cor = new Color(pixel, true);
        
        int medianaR = 0;
        int medianaG = 0;
        int medianaB = 0;

        int []auxR = new int[n*n];
        int []auxG = new int[n*n];
        int []auxB = new int[n*n];

        for(int z = 0; z<auxR.length ; z++){
            auxR[z] = 0;
            auxB[z] = 0;
            auxG[z] = 0;
        }

        int cont = 0;
        for(int a = i-Math.round((float)n/2); a<i+(n/2); a++){
            for (int b = j-Math.round((float)n/2); b<j+(n/2); b++) {
                if(a<0 || b<0 || a>(image.getWidth()-1) || b>(image.getHeight()-1)){
                    ; //Areas fora da matriz valem 0
                }else{
                    pixel = image.getRGB(a, b);
                    cor = new Color(pixel, true);
                    auxR[cont] = cor.getRed();
                    auxG[cont] = cor.getGreen();
                    auxB[cont] = cor.getBlue();
                }
                cont++;
            }
        }

        Arrays.sort(auxR);
        Arrays.sort(auxG);
        Arrays.sort(auxB);

        medianaR = auxR[(int)auxR.length/2];
        medianaG = auxG[(int)auxR.length/2];
        medianaB = auxB[(int)auxR.length/2];

        cor = new Color(medianaR, medianaG, medianaB);

        return cor;
    }

    
    //CODIGO DA APLICACAO DE FILTRO DE MEDIANA, USANDO A MEDIANA DE CADA PIXEL
    //ENTRADA: namein - caminho do arquivo da imagem a ser usada
    //			n - tamanho da matriz de vizinhança
    //SAÍDA: Imagem com o filtro da mediana aplicado (ImageIcon)
      
    public static ImageIcon filtroMediana(String namein, int n) throws Exception{  
        BufferedImage image, output;
        int pixel;
        Color cor;

        image = ImageIO.read(new File(namein));
        output = deepCopy(image);
      
        
        for(int i = 0; i<image.getWidth(); i++){
            for(int j = 0; j<image.getHeight(); j++){
                cor = valPixelMediana(image, i, j, n);
                output.setRGB(i, j, cor.getRGB());
            }
        }

        return new ImageIcon( output );  
    } 
    
    
    //as maravilhas de lucas
    
    //REALIZA CONVOLUÇÃO COM MATRIZ NA IMAGEM
    //PARAMETROS DE ENTRADA: namein - caminho para o arquivo da imagem a ser usada
    //						select - seleciona se o filtro é de aguçamento
    //TESTAR PARA PARAMETRIZAR OS VALORES PEDIDOS PELA FUNÇAO, PARA SEPARAR AGUÇAMENTO DE CONVOLUÇAO
    //SAÍDA: IMAGEM CONVOLUCIONADA ou AGUÇADA
    public static ImageIcon conv(String namein, int select, int selectOffset, int offset) throws Exception{  
    	
    	String nameout = "catlokao.jpg";
		ArrayList<Float> auxiliar = new ArrayList<Float>();
    	BufferedImage image, output;
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
		
		image = ImageIO.read(new File(namein));

		BufferedImage dstImage = null;

		Kernel kernel = new Kernel(3, 3, sharpen);
		ConvolveOp op = new ConvolveOp(kernel); //Faz a convoluÃ§Ã£o multithread do Java... Depois posso aplicar o offset
		dstImage = op.filter(image, null);
		
		
		if(selectOffset==1){
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

    	return new ImageIcon(dstImage);  
    }  

    
    //APLICA O GRADIENTE DE SOBEL
    //ENTRADA: CAMINHO PARA A IMAGEM A SER USADA
    //SAÍDA: IMAGEM COM FILTRO APLICADO EM MEMÓRIA (ImageIcon)
    public static ImageIcon sobel(String namein) throws Exception{  
    
    	BufferedImage image,copy1,copy2,output;
    	image = ImageIO.read(new File(namein));
		float [] sharpen = new float[1];
		float [] sobelh = new float [] {-1,0,1,-2,0,2,-1,0,1};
		float [] sobelv = new float [] {1,2,1,0,0,0,-1,-2,-1};

		output = image;
		sharpen = sobelh;
		Kernel kernel = new Kernel(3, 3, sharpen);
		ConvolveOp op = new ConvolveOp(kernel); //Faz a convoluÃ§Ã£o multithread do Java... Depois posso aplicar o offset
		copy1 = op.filter(image, null);

		sharpen = sobelv;
		kernel = new Kernel(3, 3, sharpen);
		op = new ConvolveOp(kernel); //Faz a convoluÃ§Ã£o multithread do Java... Depois posso aplicar o offset
		copy2 = op.filter(image, null);

		for(int h=0;h<image.getHeight();h++){
			for(int w=0;w<image.getWidth();w++){
				int pixel1 = copy1.getRGB(w,h);
				Color cor1 = new Color(pixel1,true);
				int pixel2 = copy2.getRGB(w,h);
				Color cor2 = new Color(pixel2,true);
				int somaR = (int) Math.sqrt((cor1.getRed()*cor1.getRed())+(cor2.getRed()*cor2.getRed()));
				int somaG = (int) Math.sqrt((cor1.getGreen()*cor1.getGreen())+(cor2.getGreen()*cor2.getGreen()));
				int somaB = (int) Math.sqrt((cor1.getBlue()*cor1.getBlue())+(cor2.getBlue()*cor2.getBlue()));
				if(somaR>255)
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
				Color correal = new Color(somaR,somaG,somaB);
				output.setRGB(w,h,correal.getRGB());
			}
		}



    	return new ImageIcon(output);
	}  

    //SELECIONA O VALOR MEDIO DO PIXEL PARA APLICAR O CONTRASTE ADAPTATIVO
    //ENTRADA: image - Imagem carregada em memória
    //			i e j - posição do pixel que está se tirando a média
    //				n - tamanho da matriz para média
    //Saída: contraste médio do pixel
    private static Color valPixelContraste(BufferedImage image, int i, int j, int n){
    	int pixel = image.getRGB(i, j);
    	Color cor = new Color(pixel, true);

    	int mediaR = 0;
    	int mediaG = 0;
    	int mediaB = 0;



    	int aux = n*n;

    	for(int a = i-Math.round((float)n/2); a<i+(n/2); a++){
    	    for (int b = j-Math.round((float)n/2); b<j+(n/2); b++) {
    		if(a<0 || b<0 || a>(image.getWidth()-1) || b>(image.getHeight()-1)){
    		    aux--; //Desconsidero áreas foras da matriz da imagem
    		}else{
    		    pixel = image.getRGB(a, b);
    		    cor = new Color(pixel, true);
    		    mediaR += cor.getRed();
    		    mediaG += cor.getGreen();
    		    mediaB += cor.getBlue();

    		}
    	    }
    	}


    	mediaR = Math.round(mediaR/aux);
    	mediaG = Math.round(mediaG/aux);
    	mediaB = Math.round(mediaB/aux);


    	for(int a = i-Math.round((float)n/2); a<i+(n/2); a++){
    	    for (int b = j-Math.round((float)n/2); b<j+(n/2); b++) {
    		if(a<0 || b<0 || a>(image.getWidth()-1) || b>(image.getHeight()-1)){
    		    aux--; //Desconsidero áreas foras da matriz da imagem
    		}else{
    		    pixel = image.getRGB(a, b);
    		    cor = new Color(pixel, true);
    		    desvioR += (cor.getRed()-mediaR)*2; //Obtenho a variancia de R, G e B 
    		    desvioG += (cor.getGreen()-mediaG)*2;
    		    desvioB += (cor.getBlue()-mediaB)*2;

    		}
    	    }
    	}

    	desvioR = (int)Math.sqrt(desvioR);
    	desvioG = (int)Math.sqrt(desvioG);
    	desvioB = (int)Math.sqrt(desvioB);

    	cor = new Color(mediaR, mediaG, mediaB);

    	return cor;
    }
    
    //FAZ O CONTRASTE ADAPTATIVO USANDO A FUNÇÃO ANTERIOR
    //ENTRADA: caminho para a imagem a ser usada
    //SAÍDA: IMAGEM EM MEMÓRIA COM O CONTRASTE APLICADO
    //PARAMETRIZAR OS VALORES PEDIDOS!!!
    public static ImageIcon constrateAdaptivo(String namein) throws Exception{  
            
            String nameout = "catboladao.jpg";
            BufferedImage image, output;
            Scanner input = new Scanner(System.in);
            int n;
            float c;
            int pixel;

            n = Integer.parseInt(JOptionPane.showInputDialog("Informe o 'n' das mascaras: "));

            c = Float.parseFloat(JOptionPane.showInputDialog("Informe o 'c' da equacao (entre 0 e 1): "));

            while(c>1 || c<0){
                c = Float.parseFloat(JOptionPane.showInputDialog("Informe o 'c' da equacao (ENTRE 0 E 1): "));
            }

            System.out.println("");

            Color cor, mi, corO; //cor da média e cor original

            int R = 0;
            int G = 0;
            int B = 0;

            image = ImageIO.read(new File(namein));
            output = deepCopy(image);
          
            
            for(int i = 0; i<image.getWidth(); i++){
                for(int j = 0; j<image.getHeight(); j++){
                    pixel = image.getRGB(i, j);
                    corO = new Color(pixel, true);
                    mi = valPixelContraste(image, i, j, n);

                    if(desvioR == 0){
                        R = corO.getRed();
                    }else{
                        R = mi.getRed() + Math.round((c/desvioR)*(corO.getRed()-mi.getRed()));
                    }

                    if(desvioG == 0){
                        G = corO.getGreen();
                    }else{
                        G = mi.getGreen() + Math.round((c/desvioG)*(corO.getGreen()-mi.getGreen()));
                    }

                    if(desvioB == 0){
                        B = corO.getBlue();
                    }else{
                        B = mi.getBlue() + Math.round((c/desvioB)*(corO.getBlue()-mi.getBlue()));
                    }

                    //System.out.println("R: " + R + " G: " + G + " B: " + B);                    

                    cor = new Color(R, G, B);

                    output.setRGB(i, j, cor.getRGB());
                }
            }

            ImageIO.write(output, "jpg", new File(nameout)); //CRIO A IMAGEM NA PASTA COM O NOME DADO EM nameout E DO TIPO JPG

            return new ImageIcon( output );  
    }  
    
    
    
    
    
    
    
    //expan e equa
    
   //EXPANSAO DE HISTOGRAMA
    //DEVOLVE UMA IMAGEM EM MEMORIA COM O HISTOGRAMA EXPANDIDO
    //TEM COMO ENTRADA O CAMINHO PARA A IMAGEM A SER USADA
   public static ImageIcon expan(String namein)throws Exception{
    	
    	String nameout = "expan.jpg";
    	BufferedImage image;
        int pixel;
        Color cor;
       

    	image = ImageIO.read(new File(namein));
    	
    	int rmin = 255, gmin = 255, bmin = 255;
    	int rmax = 0, gmax = 0, bmax = 0;
    	PX []R = new PX[256];
      	PX []G = new PX[256];  	
      	PX []B = new PX[256];
      	int red, green, blue;
    	for(int i=0; i<256; i++){
    		
    		R[i] = new PX(i);
    		G[i] = new PX(i);
    		B[i] = new PX(i);
    	}
      	
      	for(int i = 0; i<image.getWidth(); i++){
            for(int j = 0; j<image.getHeight(); j++){
                pixel =  image.getRGB(i, j);
                cor = new Color(pixel, true); //Cria cor RGB baseado no RGB da variavel especifica (pixel)
                red = cor.getRed();
                green = cor.getGreen();
                blue = cor.getBlue();
                
                //
                if(red < rmin) rmin = red;
                if(red > rmax) rmax = red;
                
                if(green < gmin) gmin = green;
                if(green > gmax) gmax = green;
                
                if(blue < bmin) bmin = blue;
                if(blue > bmax) bmax = blue;
                               
                //
                R[red].inc();
                G[green].inc();
                B[blue].inc();
            }
        }
    	
      	
      	for(int i = 0; i<256; i++){
      		R[i].valorF = Math.round((((float)i - rmin )/(rmax -rmin)) * 255);
      		G[i].valorF = Math.round((((float)i - gmin )/(gmax -gmin)) * 255);
      		B[i].valorF = Math.round((((float)i - bmin )/(bmax -bmin)) * 255);
      		
      	}
    	
      	
      	
    	//////////////////////////////////////////////////////////////   	
    	
    	
    	 
        for(int i = 0; i<image.getWidth(); i++){
            for(int j = 0; j<image.getHeight(); j++){
                pixel =  image.getRGB(i, j);
                cor = new Color(pixel, true); //Cria cor RGB baseado no RGB da variavel especifica (pixel)
                
                
                cor = new Color(R[cor.getRed()].valorF, G[cor.getGreen()].valorF ,B[cor.getBlue()].valorF);
                image.setRGB(i, j, cor.getRGB());
            }
        }


    	return new ImageIcon( image );  
    		
    }
   
   
   //EQUALIZAÇAO DE HISTOGRAMA
   //DEVOLVE UMA IMAGEM EM MEMORIA COM O HISTOGRAMA EQUALIZADO
   //TEM COMO ENTRADA O CAMINHO PARA A IMAGEM A SER USADA
   	public static ImageIcon equa(String namein)throws Exception{
   	
   		
   		BufferedImage image;
   		int pixel;
   		Color cor;
      

   		image = ImageIO.read(new File(namein));
   	
   		
   		PX []R = new PX[256];
     	PX []G = new PX[256];  	
     	PX []B = new PX[256];
     	int red, green, blue;
     	for(int i=0; i<256; i++){
   		
     		R[i] = new PX(i);
   			G[i] = new PX(i);
   			B[i] = new PX(i);
     	}
     	
     	for(int i = 0; i<image.getWidth(); i++){
     		for(int j = 0; j<image.getHeight(); j++){
     			pixel =  image.getRGB(i, j);
     			cor = new Color(pixel, true); //Cria cor RGB baseado no RGB da variavel especifica (pixel)
     			red = cor.getRed();
     			green = cor.getGreen();
     			blue = cor.getBlue();
               
     			R[red].inc();
     			G[green].inc();
     			B[blue].inc();
           }
     	}
   	
     	
     	float C = (float)255/(image.getWidth() * image.getHeight());
     	int auxR = 0, auxG=0, auxB=0;
     	for(int i = 0; i<256; i++){
     		auxR += R[i].qntd;
     		auxG += G[i].qntd;
     		auxB += B[i].qntd;
     		
     		R[i].valorF = Math.round(C * auxR);
     		G[i].valorF = Math.round(C * auxG);
     		B[i].valorF = Math.round(C * auxB);

     		
     	}
          	
     //////////////////////////////////////////////////////////////   	
   	
   	
   	 
       for(int i = 0; i<image.getWidth(); i++){
           for(int j = 0; j<image.getHeight(); j++){
               pixel =  image.getRGB(i, j);
               cor = new Color(pixel, true); //Cria cor RGB baseado no RGB da variavel especifica (pixel)
               
               
               cor = new Color(R[cor.getRed()].valorF, G[cor.getGreen()].valorF ,B[cor.getBlue()].valorF);
               image.setRGB(i, j, cor.getRGB());
           }
       }


   	return new ImageIcon( image );  
   		
   }
   
    
    
    
    
}
