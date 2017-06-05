class HollowableBlock				//The thing will be composed of many Blocks. They will have different dimensions/resolutions
{
	final int dsize=64;			//Size in bits of the type of arr	
	int x, y, z, nbits;			//dimensions of the block, no. of bits per cell
	long arr[];					//arr is a reference to the memory block storing the thing
	String desc;				//Descriptive string which will consist mainly of name=vakue pairs
	
	class HollowLL				//A linked list class to store the Hollows in the Block
	{
		class HollowNode		//A single node of the linked list
		{
			Hollow h;			
			HollowNode next;
		}
	
		HollowNode START;
		int count;		
		
		void addElement(Hollow toadd)				//Add Hollow object to end of linked list
		{
			System.out.println("Adding Hollow ("+toadd.p1.x+","+toadd.p1.y+","+toadd.p1.z+")-("+toadd.p2.x+","+toadd.p2.y+","+toadd.p2.z+") to linked list");
			HollowNode newnode=new HollowNode();
			newnode.h=toadd;
			newnode.next=null;
						
			HollowNode temp=START;
			if(temp==null) 
			{
				START=newnode;
				count++;				
				return;
			}
			while(temp.next!=null) temp=temp.next;
			temp.next=newnode;
			count++;
		}
		
		void addElement(Hollow toadd, int pos)		//Add Hollow object at position i
		{
			System.out.println("Adding Hollow ("+toadd.p1.x+","+toadd.p1.y+","+toadd.p1.z+")-("+toadd.p2.x+","+toadd.p2.y+","+toadd.p2.z+") to linked list");
			HollowNode newnode=new HollowNode();
			newnode.h=toadd;
			newnode.next=null;
						
			HollowNode temp=START;
			if(temp==null) 
			{
				START=newnode;
				count++;
				return;
			}
			for(int i=0; i<pos; i++) temp=temp.next;
			temp.next=newnode;
			count++;
		}	
		
		Hollow getElement()							//Return the Hollow object at the start of the linked list
		{
			return START.h;
		}
		
		Hollow getElement(int i)					//Return Hollow object at position i
		{
			HollowNode temp=START;
			
			for(int count=0; count<i; count++)
			{
				if(temp.next!=null) temp=temp.next;
				else return null;
			}
			return temp.h;			
		}				
		
		void remElement()							//Remove the first element
		{
			if(count==0) 
			{
				System.out.println("Attempting to remove Element from linked list. No elements found");
				return;
			}
			START=START.next;
			System.out.println("Removed first Element from linked list");
			count--;
		}
		
		void remElement(int i)						//Remove the element at position i
		{
			if(count==0) 
			{
				System.out.println("Attempting to remove Element from linked list. No elements found");
				return;
			}
			
			if(i==0) START=START.next;
			
			else
			{
				HollowNode temp=START;			
			
				for(int count=0; count<i-1; count++)
				{
					if(temp.next!=null) temp=temp.next;			
				}
				temp.next=temp.next.next;				
			}
			count--;
			System.out.println("Removed Element "+i+" from linked list");
		}
				
		int length()								//Returns the number of elements in the linked list
		{
			return count;
		}
		
		int getPosition(point p)
		{
			if(count==0) return -1;
			Hollow temp;
			for(int i=0; i<count; i++)
			{
				temp=linklist.getElement(i);
				if(temp.isInside(p)) return i;			
			}
			return -1;
		}
		
		int getPosition(int x, int y, int z)
		{
			if(count==0) return -1;
			Hollow temp;
			for(int i=0; i<count; i++)
			{
				temp=linklist.getElement(i);
				if(temp.isInside(x,y,z)) return i;			
			}
			return -1;
		}
	
	}
	
	HollowLL linklist=new HollowLL();	//The reference to the linked list storing the Hollow objects
	
	HollowableBlock()
	{
		System.out.println("HollowableBlock Constructor : Creating uninitialised Block");
	}
	
	HollowableBlock(int bits, int dx, int dy, int dz)	//Initialise the Block and reserve memory space
	{
		System.out.println("HollowableBlock Constructor : Creating a block of size ("+dx+","+dy+","+dz+") with "+bits+" bits per cell");
		nbits=bits; x=dx; y=dy; z=dz;
		arr=new long[(int)Math.ceil((float)x*y*z*nbits/dsize)];		
		System.out.println("Memory Allocated for this block : "+(arr.length*dsize/8)+" bytes");
	}

	void getDesc(String str)	//Get the descriptive string
	{
		desc=str;
	}	
	
	void initialise(int bits, int dx, int dy, int dz)	//Initialise the Block again
	{
		System.out.println("Initialising block of size ("+dx+","+dy+","+dz+") with "+bits+" bits per cell" );
		x=dx; y=dy; z=dz; nbits=bits;
		arr=new long[(int)Math.ceil((float)x*y*z*nbits/dsize)];
		System.out.println("Memory Allocated for this block : "+(arr.length*dsize/8)+" bytes");	
	}
	
	point getSize()	//Returns the x,y,z dimensions as a point object 
	{
		point p=new point();
		p.x=x; p.y=y; p.z=z;
		return p;
	}
	
	void addHollow(long cont, int x1, int y1, int z1, int x2, int y2, int z2) //Add a Hollow to the Block
	{
		Hollow temp=new Hollow(cont, x1, y1, z1, x2, y2, z2);
		System.out.println("Adding Hollow ("+x1+","+y1+","+z1+")-("+x2+","+y2+","+z2+") to Block containing data : "+cont);
		linklist.addElement(temp);
		compress(linklist.length()-1);
	}
	
	void addHollow(int pos, long cont, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		Hollow temp=new Hollow(cont, x1, y1, z1, x2, y2, z2);
		System.out.println("Adding Hollow ("+x1+","+y1+","+z1+")-("+x2+","+y2+","+z2+") to Block at position "+pos+" containing data : "+cont);
		linklist.addElement(temp, pos);
		compress(pos);
	}
	
	void addHollow(long cont, point p1, point p2)
	{
			Hollow temp=new Hollow(cont, p1, p2);
			System.out.println("Adding Hollow ("+p1.x+","+p1.y+","+p1.z+")-("+p2.x+","+p2.y+","+p2.z+") to Block containing data :"+cont);
			linklist.addElement(temp);
			compress(linklist.length()-1);
	}
	
	void addHollow(int pos, long cont, point p1, point p2)
	{
		Hollow temp=new Hollow(cont, p1, p2);
		System.out.println("Adding Hollow ("+p1.x+","+p1.y+","+p1.z+")-("+p2.x+","+p2.y+","+p2.z+") to Block containing data :"+cont+" at position : "+pos);
		linklist.addElement(temp, pos);
		compress(pos);
	}
	
	void addHollow(Hollow h)
	{
		System.out.println("Adding Hollow ("+h.p1.x+","+h.p1.y+","+h.p1.z+")-("+h.p2.x+","+h.p2.y+","+h.p2.z+") to block");
		linklist.addElement(h);
		compress(linklist.length()-1);
	}
	
	void addHollow(int pos, Hollow h)
	{
		System.out.println("Adding Hollow ("+h.p1.x+","+h.p1.y+","+h.p1.z+")-("+h.p2.x+","+h.p2.y+","+h.p2.z+") at position : "+pos);
		linklist.addElement(h, pos);
		compress(pos);
	}
	
	void remHollow(int i)		//Remove Hollow at position i from the Block
	{
		System.out.println("Attempting to remove Hollow from Block at position : "+i);
		linklist.remElement(i);
	}
	
	void remHollow()
	{
		System.out.println("Attempting to Remove Hollow from Block");
		linklist.remElement();
	}

	void setInArray(long data, int pos)
	{
		long temp=pos*nbits;
				
		long stored=data;
		long temp1=arr[(int)temp/dsize];
		
		long filter=0;
		for(int i=0; i<nbits; i++)
		{
			filter=filter<<1; filter++;
		}
		
		filter=filter<<temp%dsize;
		data=data<<temp%dsize;
		
		filter=~filter;
		temp1=temp1&filter;
		temp1=temp1|data;
		arr[(int)temp/dsize]=temp1;
			
		if(temp%dsize>dsize-nbits)
		{
			temp1=arr[((int)temp/dsize)+1];
			filter=0;
			for(int i=0; i<nbits-dsize+temp%dsize; i++)
			{
				filter=filter<<1; filter++;
			}
			filter=~filter;
			temp1=temp1&filter;
			stored=stored>>>(dsize-temp%dsize);
			temp1=temp1|stored;		
			arr[(int)temp/dsize+1]=temp1;			
		}
	}
	
	void setInArray(long data, int pos, long arr[])
	{
		long temp=pos*nbits;
				
		long stored=data;
		long temp1=arr[(int)temp/dsize];
		
		long filter=0;
		for(int i=0; i<nbits; i++)
		{
			filter=filter<<1; filter++;
		}
		
		filter=filter<<temp%dsize;
		data=data<<temp%dsize;
		
		
		filter=~filter;
		temp1=temp1&filter;
		temp1=temp1|data;
		arr[(int)temp/dsize]=temp1;
			
		if(temp%dsize>dsize-nbits)
		{
			temp1=arr[((int)temp/dsize)+1];
			filter=0;
			for(int i=0; i<nbits-dsize+temp%dsize; i++)
			{
				filter=filter<<1; filter++;
			}
			filter=~filter;
			temp1=temp1&filter;
			stored=stored>>>(dsize-temp%dsize);
			temp1=temp1|stored;		
			arr[(int)temp/dsize+1]=temp1;			
		}
	}
	
	long getFromArray(int pos)
	{
		long temp=pos*nbits;		
		long temp1=arr[(int)temp/dsize];
		long toret;
		
		long filter=0;
		for(int i=0; i<nbits; i++)
		{
			filter=filter<<1; filter++;
		}		
		
		filter=filter<<temp%dsize;		
		toret=temp1&filter;
		toret=toret>>>temp%dsize;		
		
		if(temp%dsize>dsize-nbits)
		{
			temp1=arr[((int)temp/dsize)+1];
			filter=0;
			for(int i=0; i<nbits-dsize+temp%dsize; i++)
			{
				filter=filter<<1; filter++;
			}
			temp1=temp1&filter;			
			temp1=temp1<<(dsize-temp%dsize);
			toret=toret|temp1;
		}
		
		return toret;	
	}	
	
	void expand(int pos)
	{
		Hollow toexpand=linklist.getElement(pos);
		long tofill=toexpand.retContent();
		linklist.remElement(pos);
		point pts[]=toexpand.getPoints();
		int size=1;
		if(pts[0].x<pts[1].x) size*=(pts[0].x-pts[1].x-1);
		else size*=(pts[0].x-pts[1].x+1);
		if(pts[0].y<pts[1].y) size*=(pts[0].y-pts[1].y-1);
		else size*=(pts[0].y-pts[1].y+1);
		if(pts[0].z<pts[1].z) size*=(pts[0].z-pts[1].z-1);
		else size*=(pts[0].z-pts[1].z+1);		
		if(size<0) size*=-1;
		
		long newarr[]=new long[(int)Math.ceil((float)(x*y*z+size)*nbits/dsize)];
		
		int posarr=0, posnewarr=0;
		for(int i=0; i<x; i++)
		{
			for(int j=0; j<y; j++)
			{
				for(int k=0; k<z; k++)
				{	
					if(toexpand.isInside(i,j,k))
					{						
						setInArray(tofill, posnewarr, newarr);
						posnewarr++;						
					}
					else if(linklist.getPosition(i,j,k)==-1)
					{
						setInArray(getFromArray(posarr), posnewarr, newarr);
						posarr++;
						posnewarr++;					
					}
				}
			}
			
		}
		arr=newarr;
	}
	
	void compress(int pos)
	{
		System.out.println(pos);
		Hollow tocomp=linklist.getElement(pos);		
		point pts[]=tocomp.getPoints();
		int size=1;
		if(pts[0].x<pts[1].x) size*=(pts[0].x-pts[1].x-1);
		else size*=(pts[0].x-pts[1].x+1);
		if(pts[0].y<pts[1].y) size*=(pts[0].y-pts[1].y-1);
		else size*=(pts[0].y-pts[1].y+1);
		if(pts[0].z<pts[1].z) size*=(pts[0].z-pts[1].z-1);
		else size*=(pts[0].z-pts[1].z+1);		
		if(size<0) size*=-1;
	
		long newarr[]=new long[(int)Math.ceil((float)(x*y*z-size)*nbits/dsize)];
		
		int posarr=0, posnewarr=0;
		for(int i=0; i<x; i++)
		{
			for(int j=0; j<y; j++)
			{
				for(int k=0; k<z; k++)
				{	
					if(tocomp.isInside(i,j,k))
					{							
						posarr++;						
					}
					else if(linklist.getPosition(i,j,k)==-1)
					{
						setInArray(getFromArray(posarr), posnewarr, newarr);
						posnewarr++; posarr++;
					}
				}
			}
			
		}
		arr=newarr;		
	}
	
	void setData(long data, int posx, int posy, int posz)
	{
		int find=linklist.getPosition(posx,posy,posz);
		if(find!=-1) expand(find);
		int pos=0;
		getout:
		for(int i=0; i<x; i++)
		{
			for(int j=0; j<y; j++)
			{
				for(int k=0; k<z; k++)
				{
					if(i==posx&&j==posy&&k==posz) break getout;
					if(linklist.getPosition(i,j,k)==-1) pos++;
				}
			}
		}
		setInArray(data, pos);
	}
	
	long getData(int posx, int posy, int posz)
	{
		int find=linklist.getPosition(posx,posy,posz);
		if(find!=-1) return linklist.getElement(find).retContent();
		int pos=0;
		getout:
		for(int i=0; i<x; i++)
		{
			for(int j=0; j<y; j++)
			{
				for(int k=0; k<z; k++)
				{
					if(i==posx&&j==posy&&k==posz) break getout;
					if(linklist.getPosition(i,j,k)==-1) pos++;
				}
			}
		}
		return getFromArray(pos);
	}	

}
	
		
class Hollow			//To save space some portion of the thing will be declared Hollow
{
	point p1,p2;				//Two points which define the cubical hollow
	long content;				//Contents of the Hollow
	
	Hollow()	
	{
		p1=new point();
		p2=new point();
		System.out.println("Created Hollow from ("+p1.x+","+p1.y+","+p1.z+") to ("+p1.x+","+p1.y+","+p1.z+")");
	}				
	
	Hollow(long cont, int x1, int y1, int z1, int x2, int y2, int z2) //Constructor to initialise the hollow with x,y,z coordinates
	{
		p1=new point();
		p2=new point();
		
		p1.x=x1; p1.y=y1; p1.z=z1; p2.x=x2; p2.y=y2; p2.z=z2;
		content=cont;
		System.out.println("Created Hollow from ("+p1.x+","+p1.y+","+p1.z+") to ("+p1.x+","+p1.y+","+p1.z+") containing data "+content);
	}
	
	Hollow(long cont, point pp1, point pp2)	//Constructor to initialise the Hollow object with point objects
	{
		p1=new point();
		p2=new point();

		p1.x=pp1.x; p1.y=pp1.y; p1.z=pp1.z;
		p2.x=pp2.x; p2.y=pp2.y; p2.z=pp2.z;
		content=cont;
		System.out.println("Created Hollow from ("+p1.x+","+p1.y+","+p1.z+") to ("+p1.x+","+p1.y+","+p1.z+") containing data "+content);
	}
	
	void setHollow(long cont, int x1,int y1, int z1, int x2, int y2, int z2)	//initialise the data of the Hollow Object with x,y,z coordinates
	{
		p1.x=x1; p1.y=y1; p1.z=z1; p2.x=x2; p2.y=y2; p2.z=z2;
		content=cont;
		System.out.println("Created Hollow from ("+p1.x+","+p1.y+","+p1.z+") to ("+p1.x+","+p1.y+","+p1.z+") containing data "+content);
	}
	
	void setHollow(long cont, point pp1, point pp2) //initialise the Hollow Object with point objects
	{
		p1.x=pp1.x; p1.y=pp1.y; p1.z=pp1.z;
		p2.x=pp2.x; p2.y=pp2.y; p2.z=pp2.z;
		content=cont;
		System.out.println("Created Hollow from ("+p1.x+","+p1.y+","+p1.z+") to ("+p1.x+","+p1.y+","+p1.z+") containing data "+content);
	}
			
	boolean isInside(int x, int y, int z)		//Checks whether (x,y,z) is inside the Hollow
	{
		//System.out.println("Checking whether point ("+x+","+y+","+z+") is inside Hollow ("+p1.x+","+p1.y+","+p1.z+")-("+p2.x+","+p2.y+","+p2.z+")");
		
		if( (p1.x<=x&&p2.x>=x)||(p1.x>=x&&p2.x<=x) )
			if( (p1.y<=y&&p2.y>=y)||(p1.y>=y&&p2.y<=y) )
				if( (p1.z<=z&&p2.z>=z)||(p1.z>=z&&p2.z<=z) )
					return true;
		return false;
	}
	
	boolean isInside(point p)		//Checks whether point p is inside the Hollow
	{
		//System.out.println("Checking whether point ("+p.x+","+p.y+","+p.z+") is inside Hollow ("+p1.x+","+p1.y+","+p1.z+")-("+p2.x+","+p2.y+","+p2.z+")");
		
		if( (p1.x<=p.x&&p2.x>=p.x)||(p1.x>=p.x&&p2.x<=p.x) )
			if( (p1.y<=p.y&&p2.y>=p.y)||(p1.y>=p.y&&p2.y<=p.y) )
				if( (p1.z<=p.z&&p2.z>=p.z)||(p1.z>=p.z&&p2.z<=p.z) )
					return true;
		return false;
		
	}
	
	long retContent()				//Returns the contents of the hollow
	{
		return content;
	}
	
	point[] getPoints()				//Returns the 2 points defining the Hollow
	{
		point toret[]=new point[2];
		toret[0]=p1; toret[1]=p2;
		return toret;	
	}

}

	
				
		