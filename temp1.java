import java.io.*;

class temp
{
	public static void main(String arg[])
	{
		data.Spocen thng=new data.Spocen();
		data.Block b1=new data.Block(4, 20, 20, 20);
		thng.addBlock(b1, new data.point(0,0,0));
		thng.writeToFile(new File("back\\Blank.tng"));
/*		for(int i=0; i<20; i++)
		{
			for(int j=0; j<20; j++)
			{
				for(int k=0; k<20; k++)
				{
					if(i==0) b1.setData(1,i,j,k);
					if(j==0) b1.setData(2,i,j,k);
					if(k==0) b1.setData(5,i,j,k);
					if(i==19) b1.setData(3,i,j,k);
					if(j==19) b1.setData(6,i,j,k);
					if(k==19) b1.setData(4,i,j,k);				
					//if((30-i)*(30-i)+(30-j)*(30-j)+(30-k)*(30-k)<625) b1.setData(1,i,j,k);
					//if(i>10&&i<60&& ((35-j)*(35-j)+(35-k)*(35-k)<900) ) b1.setData(1, i, j, k);
				}
			}
		}
		thng.addBlock(b1, new data.point(0,20,0));
		data.Block b2=new data.Block(4, 20, 20 , 20);
		for(int i=0; i<20; i++)
		{
			for(int j=0; j<20; j++)
			{
				for(int k=0; k<20; k++)
				{
					if((10-i)*(10-i)+(10-j)*(10-j)+(10-k)*(10-k)<81&&j<10) b2.setData(1,i,j,k);
					if((10-i)*(10-i)+(10-j)*(10-j)+(10-k)*(10-k)<81&&j>10) b2.setData(4,i,j,k);
				}
			}
		}
		thng.addBlock(b2, new data.point(0,0,0));
		thng.setDesc("Colored Tomb");
		thng.writeToFile(new File("back\\colortomb.tng"));		*/
		//thng.setDesc("Sphere");		
		//thng.writeToFile(new File("back\\sphere.tng"));
		//thng.setDesc("Cylinder");		
		//thng.writeToFile(new File("back\\cylinder.tng"));
	}
}