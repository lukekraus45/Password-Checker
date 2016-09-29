
public class LinkedList {

		public Node first;
		public Node last;
		public int size;
		public int flag;
		
	public LinkedList(){
		first = null;
		last = null;
		size = 0;
		flag = 0;
		
	}
	public int getSize(){
		return size;
	}
	public boolean empty(){
		if(first == null){
			return true;
		}else{
			return false;
		}
	}
	public Node getChild(Node n){
		return n.getChild();
	}
	public void setSibling(Node curr, Node sibling){
		curr.setSibling(sibling);
		size++;
	}
	public void setChild(Node curr, Node child){
		curr.setChild(child);
		size++;
	}
	public Node getSibling(Node n){
		return n.getSibling();
	}
	
	public void insertChild(int val , int pos)
    {
        Node nptr = new Node(val, null, null,0, 0);                

		if(size == 0){
			first = nptr;
			last = nptr;
			size++;
		}
		else{
        Node ptr = first;
        pos = pos - 1 ;
        for (int i = 0; i < size; i++) 
        {
            if (i == pos) 
            {
                Node tmp = ptr.getChild() ;
                ptr.setChild(nptr);
                nptr.setChild(tmp);
                
            }
            ptr = ptr.getChild();
        }
        size++ ;
		}
    }
	public Node get(int loc){
		Node ptr = first;
		for(int i = 0; i < loc; i++){
			ptr = ptr.getSibling();
		}
		return ptr;
	}
	public void insertSibling(int val , int pos)
    {
        Node nptr = new Node(val, null, null, 0, 0);                

		if(size == 0){
			first = nptr;
			last = nptr;
			size++;
		}
		else{
        Node ptr = first;
        pos = pos - 1 ;
        for (int i = 0; i < size; i++) 
        {
            if (i == pos) 
            {
                Node tmp = ptr.getSibling() ;
                ptr.setSibling(nptr);
                nptr.setSibling(tmp);
                last = nptr;
                break;
            }
            ptr = ptr.getSibling();
            
        }
        size++ ;
		}
    }
	public void printChild(int pos){
		Node ptr = first;
		for(int i = 0; i <= pos; i++){
			
			if(i == pos){
				int data = ptr.getData();
				System.out.println((char)data);	
				break;
			}
			ptr = ptr.getChild();//get next node
		}
	}
	public void printSibling(int pos){
		Node ptr = first;
		System.out.println(first.getData());
		for(int i = 0; i <= pos; i++){
			
			if(i == pos){
				int data = ptr.getData();
				System.out.println((char)data);
			}
			ptr = ptr.getSibling();//get next node
		}
	}
}
