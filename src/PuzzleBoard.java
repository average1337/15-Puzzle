
public class PuzzleBoard {
private final int xsize = 4;
private final int ysize = 4;
private int[][] tiles;

public PuzzleBoard(){
	tiles = new int[xsize][ysize];
	int count = 1;
	for(int i = 0;i<xsize;i++){
		for(int n = 0;n<ysize;n++){
			tiles[i][n] = count;
			count++;
		}
	}
	tiles[3][3] = 0;
}

public int getTileValue(int row, int col){
	return tiles[row][col];
}

public int getYSize(){
	return ysize;
}

public int getXSize(){
	return xsize;
}

public int[] findBlank(){
	for ( int i = 0; i < xsize; i++ ) {
	    for ( int j = 0; j < ysize; j++ ) {
	    	if(tiles[i][j] == 0)
	            return new int[] {i,j};
	         else if(tiles[j][i] == 0)
	            return new int[] {j,i};
	    }
	}
	return null;
}

public void swap(int[] location1, int[] location2){
	int temp = tiles[location1[0]][location1[1]];
	tiles[location1[0]][location1[1]] = tiles[location2[0]][location2[1]];
	tiles[location2[0]][location2[1]] = temp;
}
public void shuffle(){
	for(int i = 0; i<5000; i++){
		int rand = (int)(Math.random()*4 +1);
		int[] location1 = findBlank();
		switch(rand){
		case 1:
			int ydown = location1[0] + 1;
			if(ydown < ysize) swap(location1, new int[] {ydown,location1[1]});
			else swap(location1, new int[] {ydown - 2,location1[1]});
			break;
		case 2:
			int xright = location1[1] + 1;
			if(xright < xsize) swap(location1, new int[] {location1[0],xright});
			else swap(location1, new int[] {location1[0],xright-2});
			break;
		case 3:
			int yup = location1[0] - 1;
			if(yup >= 0) swap(location1, new int[] {yup,location1[1]});
			else swap(location1, new int[] {yup + 2,location1[1]});
			break;
		case 4:
			int xleft = location1[1] - 1;
			if(xleft >= 0) swap(location1, new int[] {location1[0],xleft});
			else swap(location1, new int[] {location1[0],xleft+2});
			break;

		}
	}
}

public boolean isSolved(){
	int count = 1;
	for(int i = 0; i < ysize;i++){
		for(int n = 0;n<xsize;n++){
			int temp = getTileValue(i,n);
			if(temp != 0 && temp !=count) return false;
			count++;
		}
	}
	return true;
}

@Override
public String toString(){
	String str = "";
	for(int i = 0;i<xsize;i++){
		for(int n = 0;n<ysize;n++){
			str += tiles[i][n] + " ";
		}
		str = str.substring(0,str.length()-1) + "\n";
	}
	return str;
}
}