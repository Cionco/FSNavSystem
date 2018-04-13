package test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import server.Pathfinding;

public class AstarTestGui extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				java.util.Scanner in = null;
				try {
					in = new java.util.Scanner(System.in);
					int size = in.nextInt();
					
					AstarTestGui frame = new AstarTestGui(size);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					in.close();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AstarTestGui(int size) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(100, 100, size*10, size*10);
		DrawPane pane = new DrawPane(size);
		pane.addMouseListener(new MouseListener() {
			
			int press_x, press_y;
			
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				press_x = e.getX() / 10;
				press_y = e.getY() / 10;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				pane.paintFrame();
				
				
				int dX = e.getX() / 10 - press_x;
				int dY = e.getY() / 10 - press_y;
				
				if(Math.abs(dX) == Math.abs(dY))
					for(int i = 0; i <= Math.abs(dX); i++) {
						int index = (int) (press_x + i * Math.signum(dX)) + (int) ((press_y + i * Math.signum(dY)) * pane.size);
						if(!pane.listcontains(pane.blacks, index)) pane.blacks.add(index);//pane.paintSingleSquare((int) (press_x + i * Math.signum(dX)), (int) (press_y + i * Math.signum(dY)));
					}
				else	
					for(int i = 0; i <= Math.abs((Math.abs(dX) > Math.abs(dY))?dX:dY); i++) {
						int x, y;
						x = (Math.abs(dX) > Math.abs(dY))
								?((int) (press_x + i * Math.signum(dX)))
								: press_x;
						y = (Math.abs(dX) > Math.abs(dY))
								? press_y
								: (int) (press_y + i * Math.signum(dY));
						
						pane.blacks.add(x + y * pane.size);
						pane.paintSingleSquare(x, y);
					}
				
				Pathfinding.setTerrain(pane.blacks);
				
				ArrayList<Integer> indexes = new ArrayList<Integer>();
				try {
					for(Pathfinding.Node n : Pathfinding._astar(0, 1599))
						indexes.add(n.getIndex());
				
					pane.paintAll(indexes);
				} catch(IndexOutOfBoundsException ex) {
					pane.paintAll(indexes);
					for(int i = 0; i < size; i++)
						for(int j = 0; j < size; j++)
							if(!pane.listcontains(pane.blacks, j + i * size)) pane.paintSingleSquare(j, i, Color.RED);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		setContentPane(pane);
	}

	
	class DrawPane extends JPanel {
		private static final long serialVersionUID = 1L;
		
		
		int size;
		ArrayList<Integer> blacks = new ArrayList<Integer>();
		
		public DrawPane(int size) {
			this.size = size;
		}
		
		public void paintComponent(Graphics g){

		}
		
		public void paintFrame() {
			Graphics g = getGraphics();
			g.clearRect(0, 0, getWidth(), getHeight());
			for(int i = 0; i < size; i++) 
				for(int j = 0; j < size; j++) 
					g.drawRect(i * 10, j * 10, 10, 10);
		}
		
		public void paintAll(ArrayList<Integer> indexes) {
			//astar.setTerrain(blacks);
			System.out.println(blacks.toString());
			for(int i : blacks) {
				//if(!listcontains(blacks, i)) {
					int[] xy = getXY(i);
					paintSingleSquare(xy[0], xy[1], Color.BLACK);
				//}
			}
			
			for(int i : indexes){	
				int[] xy = getXY(i);
				paintSingleSquare(xy[0], xy[1], Color.GREEN);
			}
		}
		
		public void paintSingleSquare(int x, int y) {
			paintSingleSquare(x, y, Color.BLACK);
		}
		
		public void paintSingleSquare(int x, int y, Color c) {
			Graphics g = getGraphics();
			g.setColor(c);
			g.fillRect(x * 10, y * 10, 10, 10);
		}
		
		private int[] getXY(int index) {
			return new int[]{ index % size, index / size };
		}
		
		private boolean listcontains(ArrayList<Integer> al, int i) {
			for(Integer ai : al)
				if(ai.intValue() == i) return true;
			return false;
		}
	}
}
