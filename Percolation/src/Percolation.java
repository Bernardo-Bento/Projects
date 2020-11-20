import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final int altura;
	private final WeightedQuickUnionUF uf;
    private boolean [] opened;
   
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
	  this.altura = n;
	  this.uf = new WeightedQuickUnionUF(n * n + 2);
	  this.opened = new boolean [n*n+2];
	}
	
	private int to1d(int i, int j) {
		int pos = altura * (i - 1) + j;
        return pos;	
	}
	
	// opens the site (row, col) if it is not open already
	 public void open(int i, int j) {
		 if (i <= 0 || i > altura || j <= 0 || j > altura) {
			  throw new IllegalArgumentException("Out of Bounds!");
		 }
		 int currentSide = to1d(i, j); 
		 
		 // abrir o site
		 
		 if (opened[currentSide]) {
		    return;
	     }
		 
	      opened[currentSide] = true;  
		
		 // ligar ao topo
	      
	      if (i == 1 && uf.find(currentSide) != 0) {
	    	  uf.union(currentSide, 0);
	    	}
	      
	      // ligar ao fim
	   if (i == altura) {
	    	 uf.union(currentSide, altura * altura + 1);
	      }
	    // ligar a celula anterior
	      
	      if (i > 1) {
	    	if (isOpen(i - 1, j)) {
	    		uf.union(currentSide, to1d(i - 1, j));
	      } 
	      }
	      
	    // ligar a ceula de baixo
	      
	      if (i < altura) {
	    	  if (isOpen(i + 1, j)) {
	    		  uf.union(currentSide, to1d(i + 1, j));
	    	  }
	      }
	    // ligar a celula da direita
	      if (j < altura) {
	    	  if (isOpen(i, j + 1))
	    		  uf.union(currentSide, to1d(i, j + 1));
	    	}
	      // ligar a celula da esquerda
	      if (j > 1) {
	    	  if (isOpen(i, j - 1))
	    		  uf.union(currentSide, to1d(i, j - 1));
	    	}
	 }
	 // is the site (row, col) open?
	 public boolean isOpen(int i, int j) {
		 if (i <= 0 || i > altura || j <= 0 || j > altura) {
			  throw new IllegalArgumentException("Out of Bounds!");
		 }
		  int num = to1d(i, j);
		   
		  return opened[num];
	 } 
	 // is the site (row, col) full?
	 public boolean isFull(int i, int j) {
		 if (i <= 0 || i > altura || j <= 0 || j > altura) {
			  throw new IllegalArgumentException("Out of Bounds!");
		 }
		 int p = to1d(i, j);
		return uf.find(0) == uf.find(p);
	 }
	 // returns the number of open sites
	 public int numberOfOpenSites() {
		 int count = 0;
		 for (int i = 0; i < opened.length; i++) {
			if (opened[i]) {
				count++;
		 }
		 }
		 return (count);
	 }
	 // does the system percolate?
	 public boolean percolates() {
		return uf.find(0) == uf.find(altura * altura + 1);
	} 
	 
	 // test client (optional)
	 public static void main(String[] args) {
		 int p = 10;
		 Percolation per = new Percolation(p);
		 per.isFull(3, 4);
	}
}

