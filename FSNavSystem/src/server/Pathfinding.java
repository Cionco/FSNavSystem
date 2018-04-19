package server;

import java.util.ArrayList;

public class Pathfinding {

	final static int HEIGHT = 20;
	public final static int WIDTH = 20;
	static ArrayList<Node> field = new ArrayList<Node>();
	
	
	public static int[] terrain = new int[]{8, 9, 10 };
	
	public static void main(String[] args) {
		for(int i = 0; i < HEIGHT; i++)
			for(int j = 0; j < WIDTH; j++) {
				field.add(new Node(j, i));
			}
		
		for(int i : terrain)
			field.get(i).setTraversable(false);
		
		for(Node n : _astar(0, 12)) 
			System.out.println(n.getIndex() + ": " + n.x + "/" + n.y);
	}
	
	
	public static ArrayList<Node> _astar(int start_index, int target_index) throws IndexOutOfBoundsException {
		if(field.size() == 0)
			for(int i = 0; i < HEIGHT; i++)
				for(int j = 0; j < WIDTH; j++) {
					field.add(new Node(j, i));
				}
		
		for(int i : terrain)
			field.get(i).setTraversable(false);
		
		ArrayList<Node> path = new ArrayList<Node>();
		ArrayList<Node> open = new ArrayList<Node>();
		ArrayList<Node> closed = new ArrayList<Node>();
		
		open(field.get(start_index), open, field.get(start_index), field.get(target_index));
		
		while(true) {
			Node current = null;
			try {
				current = getLowestF_costNode(open);
			} catch(IndexOutOfBoundsException e) {
				System.out.println("No way possible!");
				throw e;
			}
			
			
			open.remove(current);
			closed.add(current);
			
			if(current.equals(field.get(target_index))) {
				System.out.println("Found, " + current.x + "/" + current.y);
				path.add(current);
				break;
			}
			
			for(Node n : current.getNeighbors()) {
				if(!n.traversable || closed.contains(n)) continue;
		
				
				int newmovementcost = current.g_cost + calcCost(current, n);
				if(newmovementcost < n.g_cost || !open.contains(n)) {
					open(n, open, field.get(start_index), field.get(target_index));
					n.setGcost(newmovementcost);
					n.setParent(current);
				}
			}
		}
		
		while(path.get(0).getParent() != null)
			path.add(0, path.get(0).getParent());
		
		return path;
	}
	
	
	public static class Node {
		boolean traversable = true;
		Node parent = null;
		int x;
		int y;
		int g_cost;
		int h_cost;
		int[] neighbors;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
			this.neighbors = getNeighborIndexes(getIndex(x, y));
		}

		public Node(int x, int y, boolean traversable) {
			this.x = x; 
			this.y = y;
			this.traversable = traversable;
			this.neighbors = getNeighborIndexes(getIndex(x, y));
		}
		
		public boolean equals(Node n) {
			return (this.x == n.x && this.y == n.y);
		}
		
		public void setTraversable(boolean b) {
			traversable = b;
		}
		
		public void setGcost(int g) {
			g_cost = g;
		}
		
		public int getFcost() {
			return g_cost + h_cost;
		}
		
		public int getIndex() {
			return getIndex(x, y);
		}
		
		private static int[] getNeighborIndexes(int index) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			boolean top, left, right, bot; //does the node touch any border?
			top = index < WIDTH;
			left = index % WIDTH == 0;
			right = index % WIDTH == WIDTH - 1;
			bot = index >= WIDTH * (HEIGHT - 1);
			
			int mask = 0xFF;
			if(top) mask &= 0x1F;
			if(bot) mask &= 0xF8;
			if(left) mask &= 0x6B;
			if(right) mask &= 0xD6;
			
			if((mask & 0x80) > 0) list.add(index - WIDTH - 1);
			if((mask & 0x40) > 0) list.add(index - WIDTH);
			if((mask & 0x20) > 0) list.add(index - WIDTH + 1);
			
			if((mask & 0x10) > 0) list.add(index - 1);
			if((mask & 0x08) > 0) list.add(index + 1);
			
			if((mask & 0x04) > 0) list.add(index + WIDTH - 1);
			if((mask & 0x02) > 0) list.add(index + WIDTH);
			if((mask & 0x01) > 0) list.add(index + WIDTH + 1);
			
			int[] neighbors = new int[list.size()];
			for(int i = 0; i < list.size(); i++)
				neighbors[i] = list.get(i);
			return neighbors;
			
		}
		
		public void setParent(Node n) {
			parent = n;
		}
	
		public Node getParent() {
			return parent;
		}
		
		public Node[] getNeighbors() {
			Node[] n = new Node[neighbors.length];
			
			for(int i = 0; i < n.length; i++)
				n[i] = field.get(neighbors[i]);
			
			return n;
		}
		
		private static int getIndex(int x, int y) {
			return y * WIDTH + x;
		}
	}
	
	public static Node open(Node n, ArrayList<Node> open, Node start, Node target) {
		n.g_cost = calcCost(n, start);
		n.h_cost = calcCost(n, target);
		if(!open.contains(n)) open.add(n);
		return n;
	}
	
	public static int calcCost(Node start, Node end) {
		if(start.equals(end)) return 0;
		
		int dX = start.x - end.x;
		int dY = start.y - end.y;
		
		if(dX == 0) return 10 + calcCost(new Node(start.x, (int) (start.y - Math.signum(dY))), end);
		if(dY == 0) return 10 + calcCost(new Node((int) (start.x - Math.signum(dX)), start.y), end);
		return 14 + calcCost(new Node((int) (start.x - Math.signum(dX)), (int) (start.y - Math.signum(dY))), end);
	}
	
	public static Node getLowestF_costNode(ArrayList<Node> n) throws IndexOutOfBoundsException {
		int[] f_costs = new int[n.size()];
		for(int i = 0; i < n.size(); i++) f_costs[i] = n.get(i).getFcost();
		
		int min = 0;
		for(int i = 0; i < f_costs.length; i++)
			if(f_costs[i] < min)
				min = i;
		
		return n.get(min);
	}
	
	public static void setTerrain(ArrayList<Integer> al) {
		int[] newterrain = new int[al.size()];
		for(int i = 0; i < al.size(); i++) {
			newterrain[i] = al.get(i).intValue();
		}
		terrain = newterrain;
	}
}