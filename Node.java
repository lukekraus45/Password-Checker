
class Node{	
	public Node child;
	public Node sibling;
	public int letter;//stored as its ascii char
	public int flag;
	public double time;
	

public Node(){
	child = null;
	sibling = null;
	letter = 0;
	flag = 0;
	time = 0;
}
public Node(int d,Node c, Node s, int f, double t){
    letter = d;
    child = c;
    sibling = s;
    flag = f;
    time = t;
}    
public void setSibling(Node n)
{
    sibling = n;
   
}   
public void setChild(Node c){
	child = c;
}
public void setLetter(int d)
{
    letter = d;
}  
public Node getChild()
{
    return child;
}    
public Node getSibling(){
	return sibling;
}
public int getData()
{
    return letter;
}
}