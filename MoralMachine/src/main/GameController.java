package main;

import gui.Background; 
import gui.Board;
import game.MatrixPanel;
import gui.Information;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.lang.*;
import algorithm.Algorithm;
import algorithm.*;

public class GameController extends JFrame implements ActionListener, MouseListener 
{
	private JPanel mainpanel;
 
	private Background backgroundstartpanel; 
	private MatrixPanel matrixpanel;
	private Information information;
	private int button_selection; 
	private Algorithm minimum_path;


public GameController() {
		
		
		mainpanel = new JPanel(); 
		backgroundstartpanel = new Background(); 	
		button_selection = 0; 
		information = new Information();
		this.add_characteristics(); 
		this.add_components(); 
		this.addActionListener(); 
		addMouseListener(this); 
		this.setVisible(true); 
		
	}

	public void add_characteristics() { 
		
		this.setLayout(null); 
		this.setTitle("Moral Machine"); 
		this.setSize(new Dimension(800,800)); 
		this.setResizable(false);
		this.setLocationRelativeTo(null); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

		
		mainpanel.setSize(new Dimension(this.getWidth(),(this.getHeight() ) ) ); 
		mainpanel.setLocation(0,0);
		mainpanel.setVisible(false); 
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(mainpanel); 
		
	}
	
	public void add_components() { 
		
		

		this.add(mainpanel); 
		this.add(backgroundstartpanel); 
		
		
	}
	
	private void initialize_matrix_panel() { 
		
		int a = Integer.parseInt(backgroundstartpanel.getStartpanel().getColumnas().getText());
		int b = Integer.parseInt(backgroundstartpanel.getStartpanel().getFilas().getText());
		int c =  Integer.parseInt(backgroundstartpanel.getStartpanel().getDificultad().getText());
		
		
		
		matrixpanel = new MatrixPanel(a,b,c); 
		this.add(matrixpanel); 
		this.setSize(new Dimension(800,622)); 
		information.setMatrixInfo(matrixpanel.getGame_board());
		this.add(information);

	}
	
public void addActionListener() { 
	
		backgroundstartpanel.getStartpanel().getButton_start().addActionListener(this); 
		backgroundstartpanel.getStartpanel().getButton_exit().addActionListener(this); 
		information.getButtonExit().addActionListener(this);
		information.getButtonStart().addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { 
		
		
		if(e.getSource() == backgroundstartpanel.getStartpanel().getButton_start()) {
			backgroundstartpanel.setVisible(false); 
			this.initialize_matrix_panel(); 
			matrixpanel.getGame_board();
			matrixpanel.repaint();

		}
		if(e.getSource() == backgroundstartpanel.getStartpanel().getButton_exit()) { 
			System.exit(0); 
		}
		
		if(e.getSource() == information.getButtonExit()) { 
			new GameController();
		}
		
		if(e.getSource() == information.getButtonStart()) { 
			minimum_path = new Algorithm(matrixpanel.getGame_board());
			this.begin();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	
	@Override
	public void mousePressed(MouseEvent e) {} 
	@Override
	public void mouseReleased(MouseEvent e) {} 
	@Override
	public void mouseEntered(MouseEvent e) {} 
	@Override
	public void mouseExited(MouseEvent e) {}

	
	
	public MatrixPanel getMatrixpanel() {
		return matrixpanel;
	}

	public void setMatrixpanel(MatrixPanel matrixpanel) {
		this.matrixpanel = matrixpanel;
	}

	public int getButton_selection() {
		return button_selection;
	}

	public void setButton_selection(int button_selection) {
		this.button_selection = button_selection;
	}
	

	
	public void begin() { 
						  

		Thread beginning = new Thread (new start_class());
		beginning.start(); 
	}

	public class start_class implements Runnable { 

		public void run() { 

			List list_solution = null; 
      		list_solution = minimum_path.beggining(); 
      		
      	
      		
			int pasajeros = 0;
      		
			
      		if(list_solution == null) { 
      			JOptionPane.showMessageDialog(null, "NO EXISTE SOLUCIÓN"); 
      		}
      		else { 
      			
     
      				try { 
      					Thread.sleep(1000);
      				} catch(InterruptedException ex) {} 
      			
      				while(list_solution.empty() == false) {
      					Node auxiliary_node = list_solution.extract(); 
      					int x = auxiliary_node.get_id_x(); 
      					int y = auxiliary_node.get_id_y(); 
      					
      					information.repaint();
      					matrixpanel.getGame_board().setPasajero(pasajeros);
					
		      			for(int i = 0; i < matrixpanel.getGame_board().getM(); i++) {
		      				for(int j = 0; j < matrixpanel.getGame_board().getN(); j++) {
		      					if((matrixpanel.getGame_board().getValue(i,j) >= 7) && (matrixpanel.getGame_board().getValue(i,j) < 11)) { 
		      						if((x < i) && (y == j)) { // CONDICIÓN: Si se produce un movimiento hacia arriba
		      							if((matrixpanel.getGame_board().getValue(x,y) == 4) || (matrixpanel.getGame_board().getValue(x,y) == 5)){
		      							   pasajeros++;
		      							}
			      						matrixpanel.getGame_board().setValue(i,j,0); 
		      							matrixpanel.getGame_board().setValue(x,y,7); 
		      						}
		      						if((x > i) && (y == j)) { // CONDICIÓN: Si se produce un movimiento hacia abajo
		      							if((matrixpanel.getGame_board().getValue(x,y) == 4) || (matrixpanel.getGame_board().getValue(x,y) == 5)){
			      							   pasajeros++;
			      							}
			      						matrixpanel.getGame_board().setValue(i,j,0); 
		      							matrixpanel.getGame_board().setValue(x,y,8); 
		      						}
		      						if((x == i) && (y > j)) { // CONDICIÓN: Si se produce un movimiento hacia a la derecha
		      							if((matrixpanel.getGame_board().getValue(x,y) == 4) || (matrixpanel.getGame_board().getValue(x,y) == 5)){
			      							   pasajeros++;
			      							}
			      						matrixpanel.getGame_board().setValue(i,j,0); 
		      							matrixpanel.getGame_board().setValue(x,y,9); 
		      						}
		      						if((x == i) && (y < j)) { // CONDICIÓN: Si se produce un movimiento hacia a la izquierda
		      							if((matrixpanel.getGame_board().getValue(x,y) == 4) || (matrixpanel.getGame_board().getValue(x,y) == 5)){
			      							   pasajeros++;
			      							}
			      						matrixpanel.getGame_board().setValue(i,j,0); 
		      							matrixpanel.getGame_board().setValue(x,y,10); 
		      						}
		      						
		      					}
		      				}
		      			}
		      			
		      			matrixpanel.repaint(); 
	
		      			try { 
		      				Thread.sleep(125); 
						} catch(InterruptedException ex) {} 		
		      		}
	      			for(int i = 0; i < matrixpanel.getGame_board().getM(); i++) {
	      				for(int j = 0; j < matrixpanel.getGame_board().getN(); j++) {
	      					if((matrixpanel.getGame_board().getValue(i,j) >= 7) && (matrixpanel.getGame_board().getValue(i,j) < 11)) { 
								
									matrixpanel.getGame_board().setValue(i,j,0); 
									matrixpanel.repaint(); 
								
							
	      					}
	      					if(matrixpanel.getGame_board().getValue(i,j) == 6) {
	      						matrixpanel.repaint(); 
	      					}
	      				}
	      			}
      			
      			
      			
	      		try { 
		      		Thread.sleep(1000); 
				} catch(InterruptedException ex) {} 
      			for(int i = 0; i < matrixpanel.getGame_board().getM(); i++) {
      				for(int j = 0; j < matrixpanel.getGame_board().getN(); j++) {
      					if(matrixpanel.getGame_board().getValue(i,j) == 6) {
      						matrixpanel.repaint(); 
      						
      				}
      			}
	      	}
      	}
	  }
		
	}
	
	
	
}