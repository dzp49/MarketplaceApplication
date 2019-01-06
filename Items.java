// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Items
{
    
    public void display()
    {
        HashMap<String, Integer> item=new HashMap();
                item.put("Samsung TV",100);
                item.put("Iphone X",200);
                item.put("Adidas Yeezy",300);
                item.put("Gatorade",400);
                item.put("Macbook Pro",500);
        for (Map.Entry<String, Integer> entry : item.entrySet()) {
		System.out.println("Item : " + entry.getKey() + " Price : $" + entry.getValue()); }           
			
    }

    public void purchase()
	{
		String name;
      	Scanner s=new Scanner(System.in);
		System.out.println("Type item name for purchase: \n");
		name=s.next();
		//item.remove(name);
		System.out.println("Item Purchased!");
	}

}
