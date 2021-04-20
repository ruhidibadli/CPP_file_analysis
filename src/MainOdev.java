/**
*
* @author Ruhid Ibadlı ruhid.ibadli@gmail.com
* @since 10.04.2021
* <p>
* Açıklama yok.
* </p>
*/

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class MainOdev {
  public static void main(String[] args) {
    try {
      File myObj = new File("src/Program.cpp");
      Scanner myReader = new Scanner(myObj);
      int temp_cls = 0;
      ArrayList<kalitim> kalitimlar = new ArrayList<kalitim>();
      ArrayList<classes> classList = new ArrayList<classes>();
      
      Pattern pt_cls = Pattern.compile("(?<=class)(\\s+?)(?<sinif>\\w+)");
      Pattern pt_cons = Pattern.compile("(friend(\\s+)?)?(?<returns>\\S+? )?(\\s+)?(?<funcName>\\W?\\w+(\\W+)?)\\((\\s*)(\\W+(?<kalitim>\\w+).)?(\\s+)?(?<params>.*?)\\)(\\s+)?(const)?(\\s+)?\\{?");
      Pattern pt_inheritance = Pattern.compile("(?<=:)(\\s+)?(\\w+)?(\\s+)?(?<kalitim>.+)\\{?");
      Pattern pt_param = Pattern.compile("(\\s+)?(const)?(\\s+)?(?<param>.*?)(\\s+?)(?<var>\\w+(\\W+)?)?");
      Pattern pt_special = Pattern.compile("(\\w+)?(\\s+?)?(?<kalitim>\\w+)?");
      
      
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        String new_data = data.trim();
        Matcher cls = pt_cls.matcher(new_data);
        Matcher inheritance = pt_inheritance.matcher(new_data);
    	if (new_data.contains("int main")) {
    		while(true) {
    			String test_data = myReader.nextLine();
    			if(test_data.contains("}")) break;
    		}
    	}
    	if(new_data.contains("/*")) {
    		while(true) {
    			String test_data1 = myReader.nextLine();
    			if(test_data1.contains("*/")) break;
    		}
    	}
    	if (inheritance.find()) {
    		String[] inheritances = inheritance.group("kalitim").split(",");
    		for(int i=0; i<inheritances.length; i++) {
    			Matcher special = pt_special.matcher(inheritances[i].trim());
    			obj:
    			if(special.find()) {
    				for(int j=0; j<kalitimlar.size();j++) {
    					if(special.group("kalitim") != null) {
    						if(special.group("kalitim").equals(kalitimlar.get(j).name)) {
    							int temp_sayi = kalitimlar.get(j).getSayi();
    							temp_sayi++;
    							kalitimlar.get(j).setSayi(temp_sayi);
    							break obj;
    						}
    					}
    					else {
    						if(special.group().equals(kalitimlar.get(j).name)) {
    							int temp_sayi = kalitimlar.get(j).getSayi();
    							temp_sayi++;
    							kalitimlar.get(j).setSayi(temp_sayi);
    							break obj;
    						}
    					}
    				}
    				if(special.group("kalitim") != null) {
    					kalitim temp = new kalitim(special.group("kalitim"),1);
    					kalitimlar.add(temp);
    				}
    				else {
    					kalitim temp = new kalitim(special.group(),1);
    					kalitimlar.add(temp);
    				}
    			}
    		}
    	}
      
      
	  	if (cls.find()) {
			classes cl1 = new classes(cls.group("sinif"));
			classList.add(cl1);
		    int temp_func = 0;
			obj:
				while(true) {
					String data1 = myReader.nextLine();
					if(data1.contains("public:")){
						while(true) {
							String data2 = myReader.nextLine();
							String new_data1 = data2.trim();
							Matcher cons = pt_cons.matcher(new_data1);
							if(new_data1.equals("};") || new_data1.equals("protected:") || new_data1.equals("private:")) break obj;
							if(cons.find()) {
								if(cons.group("funcName").equals("switch") || cons.group("funcName").contains("if") || cons.group("funcName").contains("for") || cons.group("funcName").contains("while")) continue;
								String data3 = myReader.nextLine();
								String new_data3 = data3.trim();
								if(new_data3.equals("{") || new_data1.contains("{")) {
									function func1 = new function(cons.group("funcName"), cons.group("returns"));
									classList.get(temp_cls).func.add(func1);
									if(cons.group("params") != null) {
										String[] params = cons.group("params").split(",");
										for(int i=0; i<params.length; i++) {
											Matcher param = pt_param.matcher(params[i]);
											if(param.find()) {
												classList.get(temp_cls).func.get(temp_func).parameters.add(param.group("param"));
											}
										}
									}
									temp_func++;
								}
							}
						}
					}
				}
			temp_cls++;	
		}
  }
   
      
      for(int i=0;i<classList.size();i++){
    	  if(classList.get(i) != null) {
    		  System.out.println("class : " + classList.get(i).name);
	    	  for(int j=0; j<classList.get(i).func.size();j++) {
	    		  if(classList.get(i).func.get(j) != null) {
	    			  System.out.println("	function : " + classList.get(i).func.get(j).name);
	    			  System.out.print("		Parameters : " + classList.get(i).func.get(j).parameters.size() + " (");
	    			  for(int k=0; k<classList.get(i).func.get(j).parameters.size();k++) {
	    				  if(classList.get(i).func.get(j).parameters.get(k) != null)
	    					  System.out.print(classList.get(i).func.get(j).parameters.get(k) + ",");
	    			  }
	    			  System.out.print(")\n");
	    			  if(classList.get(i).func.get(j).returns != null)
	    				System.out.println("		Returns: " + classList.get(i).func.get(j).returns);
	    			  else {
		    			  if(classList.get(i).func.get(j).name.equals(classList.get(i).name))
	    					  System.out.println("		Returns : Nesne Adresi");
		    			  else
		    				  System.out.println("		Returns: void"); 
	    			  }
	    		  }
	    	  }
    	  }
      }
      
      
      System.out.println("Super siniflar:");
      for(int a=0; a<kalitimlar.size(); a++) {
    	  if(kalitimlar.get(a) != null)
			  System.out.println("	" + kalitimlar.get(a).name + " : " + kalitimlar.get(a).getSayi());  
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}