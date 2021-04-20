/**
*
* @author Ruhid Ibadlı ruhid.ibadli@gmail.com
* @since 10.04.2021
* <p>
* Açıklama yok.
* </p>
*/


import java.util.ArrayList;

public class function {

	public String name;
	public ArrayList<String> parameters;
	public String returns;
	
	public function(String name, String returns) {
		this.name = name;
		this.parameters = new ArrayList<String>();
		this.returns = returns;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}
