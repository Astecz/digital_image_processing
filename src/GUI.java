
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JFrame implements ActionListener {
	
	FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens" , "jpg");
	FileNameExtensionFilter filtro2 = new FileNameExtensionFilter("YCrCb" , "YIQ");
	GUI isso = this;
	File file = null;
	
	
	private ImageIcon IM_original;
	private ImageIcon IM_alterada;
	private JLabel box_original;
	private JLabel box_alterada;
	private String im1, im2, path = "";
	private ImageIcon imagem;
	
	//declaracao dos botoes de funcao
	JMenuItem abrir;
	JMenuItem salvar;
	JMenuItem sair;
	JMenuItem salvarYIQ;
	JMenuItem abrirYIQ;
	
	JMenuItem cred;
	
	
	JMenuItem neg;
	JMenuItem RtoY;
	JMenuItem media;
	JMenuItem mediana;
	JMenuItem aguca;
	JMenuItem convo;
	JMenuItem sobel;
	JMenuItem expan;
	JMenuItem equa;
	JMenuItem cadap;
	
	
	
	public void setIm1(String im1){
		this.im1 = im1;
	}
	public void setIm2(String im2){
		this.im2 = im2;
	}
	
	public void newFrame(GUI novo){
		isso = novo;
	}
	
	public GUI(String im1, String im2){
		setIm1(im1);
		setIm2(im2);
		
		// configuracoes de inicializacao da janela
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		this.setTitle("PDI - Trabalho 3");
		
		
		// definicao da barra de menu
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		JMenu arq = new JMenu("Arquivo");	
		JMenu opc = new JMenu("Opcoes");	
		JMenu info = new JMenu("Ajuda");	
		menu.add(arq);	
		menu.add(opc);
		menu.add(info);
		
		
		
		//definicao de cada opcao do menu
		//arquivo
		abrir = new JMenuItem("Abrir");				abrir.addActionListener(this);
		salvar = new JMenuItem("Salvar");			salvar.addActionListener(this);
		abrirYIQ = new JMenuItem("Abrir YIQ");		abrirYIQ.addActionListener(this);
		salvarYIQ = new JMenuItem("Salvar YIQ");	salvarYIQ.addActionListener(this);
		sair = new JMenuItem("Sair");				sair.addActionListener(this);
		
		
		arq.add(abrir);
		arq.add(salvar);
		arq.addSeparator();
		arq.add(abrirYIQ);
		arq.add(salvarYIQ);
		arq.addSeparator();
		arq.add(sair);
		
		//opcoes
		neg = new JMenuItem("Negativo");
		media = new JMenuItem("Filtro de Media");
		mediana = new JMenuItem("Filtro de Mediana");
		aguca = new JMenuItem("Filtro de Agucamento");
		convo = new JMenuItem("Convolucao");
		sobel = new JMenuItem("Gradiente de Sobel");
		expan = new JMenuItem("Expansao");
		equa = new JMenuItem("Equalizacao");
		cadap = new JMenuItem("Controle de Contraste Adaptativo"); 
		
		
		
		opc.add(neg);									neg.addActionListener(this);
		opc.add(media);									media.addActionListener(this);
		opc.add(mediana);								mediana.addActionListener(this);
		opc.add(aguca);									aguca.addActionListener(this);
		opc.add(convo);									convo.addActionListener(this);
		opc.add(sobel);									sobel.addActionListener(this);
		opc.add(expan);									expan.addActionListener(this);
		opc.add(equa);									equa.addActionListener(this);
		opc.add(cadap);									cadap.addActionListener(this);
		

		
		
		//info
		cred = new JMenuItem("Autores");
		
		info.add(cred);									cred.addActionListener(this);
		
		//fim do menu
		
		
		// tela adaptavel ao tamanho das imagens
		setLayout(new FlowLayout());
		
		IM_original = new ImageIcon(im1);
				
		box_original = new JLabel(IM_original);
		add(box_original);
		
		IM_alterada = new ImageIcon(im2);
		box_alterada = new JLabel(IM_alterada);
		add(box_alterada);
		
		this.setExtendedState(MAXIMIZED_BOTH); 
		setResizable(false);
	}

	public static void main(String[] args) {
		
		GUI t = new GUI("","");
			
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser f;
		
		if(e.getSource() == abrir){
			
			f = new JFileChooser();
			
			f.setFileFilter(filtro);
			int retorno = f.showOpenDialog(null);
			
			
			
			if (retorno == JFileChooser.APPROVE_OPTION) {
				  file = f.getSelectedFile();
				  
				  im1 = file.toString();
				  path = file.getParent() + "/";
				  ImageIcon ig = new ImageIcon(im1); // pega a outra imagem do mesmo diretorio	
				  IM_original = ig; 
				  box_original.setIcon(ig);	
				  ImageIcon i = new ImageIcon("");
				  IM_alterada = i;
				  box_alterada.setIcon(i);
				  
				  
				  box_original.repaint(); 
				  box_alterada.repaint();
				  // faz algo aqui com o arquivo
			} else {
			//	JOptionPane.showMessageDialog(null, "Cancelou!");
			}
			
			
			
		}else if(e.getSource() == abrirYIQ){
			
			f = new JFileChooser();
			
			f.setFileFilter(filtro2);
			int retorno = f.showOpenDialog(null);
			
			
			
			if (retorno == JFileChooser.APPROVE_OPTION) {
				  file = f.getSelectedFile();
				  im1 = file.toString();
				  path = file.getParent() + "/";
				  
				  ImageIcon ig;
				  try {
					  imagem = ig = Img.YIQtoRGB(im1);
					  
					// pega a outra imagem do mesmo diretorio	
				  IM_original = ig; 
				  box_original.setIcon(ig);	
				  ImageIcon i = new ImageIcon("");
				  IM_alterada = i;
				  box_alterada.setIcon(i);
				  
				  box_original.repaint(); 
				  box_alterada.repaint();
				  
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  // faz algo aqui com o arquivo
			} else {
			//	JOptionPane.showMessageDialog(null, "Cancelou!");
			}
			
			
			
		}else if(e.getSource() == salvar){
			String nameout = JOptionPane.showInputDialog("A imagem será salva na pasta do arquivo original.\nDigite o nome do arquivo:");
			
			
			Image image = imagem.getImage();  
			RenderedImage rendered = null;  
			if (image instanceof RenderedImage)  
			{  
			    rendered = (RenderedImage)image;  
			}  
			else  
			{  
			    BufferedImage buffered = new BufferedImage(  
			        imagem.getIconWidth(),  
			        imagem.getIconHeight(),  
			        BufferedImage.TYPE_INT_RGB  
			    );  
			    Graphics2D g = buffered.createGraphics();  
			    g.drawImage(image, 0, 0, null);  
			    g.dispose();  
			    rendered = buffered;  
			}  
					
			
			try {
				ImageIO.write(rendered, "jpg", new File(path+nameout+".jpg"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}else if(e.getSource() == salvarYIQ){
			String nameout = JOptionPane.showInputDialog("O arquivo em YIQ será salvo na pasta do arquivo original.\nDigite o nome do arquivo:");
			
			BufferedImage image;

	    	try {
				image = ImageIO.read(file);
			
				Img.RGBtoYIQ(image, file.getParent()+ "/" + nameout + ".YIQ"); //Gera o arquivo pra mim...
			
	    	} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(e.getSource() == neg){ // se selecionou efeito negativo
			
			try {
				ImageIcon imIcon = Img.negativo(im1);
				imagem = imIcon;
				ImageIcon ig = imIcon; // pega a outra imagem do mesmo diretorio	
				IM_alterada = ig; 
				box_alterada.setIcon(ig);	
				box_alterada.repaint(); 
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			
		
		}else if(e.getSource() == media){
			int n;
			n = Integer.parseInt(JOptionPane.showInputDialog("Informe o valor de n (n x n)"));
			try {
				ImageIcon imIcon = Img.filtroMedia(im1, n);
				imagem = imIcon;
				ImageIcon ig2 = imIcon; // pega a outra imagem do mesmo diretorio	
				IM_alterada = ig2; 
				box_alterada.setIcon(ig2);	
				box_alterada.repaint(); 
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			
		}else if(e.getSource() == sair){
			
			System.exit(0);
			
			
		}else if(e.getSource() == mediana){
			int n;
			n = Integer.parseInt(JOptionPane.showInputDialog("Informe o valor de n (n x n)"));
			try {
				ImageIcon imIcon = Img.filtroMediana(im1, n);
				imagem = imIcon;
				ImageIcon ig2 = imIcon; // pega a outra imagem do mesmo diretorio	
				IM_alterada = ig2; 
				box_alterada.setIcon(ig2);	
				box_alterada.repaint(); 
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			
		
			
		}else if(e.getSource() == aguca){
			
			try {
				ImageIcon imIcon = Img.conv(im1, 1, 2, 0);
				imagem = imIcon;
				ImageIcon ig2 = imIcon; // pega a outra imagem do mesmo diretorio	
				IM_alterada = ig2; 
				box_alterada.setIcon(ig2);	
				box_alterada.repaint(); 
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			
		}else if(e.getSource() == convo){
			int n, n1=1;
			n = Integer.parseInt(JOptionPane.showInputDialog("Deseja fazer Offset?\n1-Sim\n2-Nao"));
			if(n == 1)
				n1 = Integer.parseInt(JOptionPane.showInputDialog("Informe o valor do Offset (-255 a 255):"));
				
			try {
				ImageIcon imIcon = Img.conv(im1, 3, n , n1);
				imagem = imIcon;
				ImageIcon ig2 = imIcon; // pega a outra imagem do mesmo diretorio	
				IM_alterada = ig2; 
				box_alterada.setIcon(ig2);	
				box_alterada.repaint(); 
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			
		}else if(e.getSource() == sobel){
			
			try {
				ImageIcon imIcon = Img.sobel(im1);
				imagem = imIcon;
				ImageIcon ig2 = imIcon; // pega a outra imagem do mesmo diretorio	
				IM_alterada = ig2; 
				box_alterada.setIcon(ig2);	
				box_alterada.repaint(); 
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			
		}else if(e.getSource() == expan){
			
			try {
				ImageIcon imIcon = Img.expan(im1);
				imagem = imIcon;
				ImageIcon ig2 = imIcon; // pega a outra imagem do mesmo diretorio	
				IM_alterada = ig2; 
				box_alterada.setIcon(ig2);	
				box_alterada.repaint(); 
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			
		}else if(e.getSource() == cadap){
			
			try {
				ImageIcon imIcon = Img.constrateAdaptivo(im1);
				imagem = imIcon;
				ImageIcon ig2 = imIcon; // pega a outra imagem do mesmo diretorio	
				IM_alterada = ig2; 
				box_alterada.setIcon(ig2);	
				box_alterada.repaint(); 
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			
		}else if(e.getSource() == equa){
			
			try {
				ImageIcon imIcon = Img.equa(im1);
				imagem = imIcon;
				ImageIcon ig2 = imIcon; // pega a outra imagem do mesmo diretorio	
				IM_alterada = ig2; 
				box_alterada.setIcon(ig2);	
				box_alterada.repaint(); 
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			
		}else if(e.getSource() == cred){
			
			JOptionPane.showMessageDialog(null, "Autores:\nAellison Cassimiro\nJose Alves\nLucas Aversari\n\nPDI - 2014.2");
			
		}
		
	}

}
