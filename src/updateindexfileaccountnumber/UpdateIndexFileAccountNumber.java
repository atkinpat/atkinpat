/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package updateindexfileaccountnumber;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;


public class UpdateIndexFileAccountNumber {
    
    public UpdateIndexFileAccountNumber(){
        //default constructor
    }
    
    //read all TXT index file names to a list
    public static void main(String[] args) throws IOException {
                
        File dir = new File("E:\\ERM\\Keystone\\Consumer"); //AA index file directory
        String[] extensions = new String[] { "txt" };
        
        UpdateIndexFileAccountNumber instance = new UpdateIndexFileAccountNumber();
        
        System.out.println("Getting all .txt files in " + dir.getCanonicalPath()
                        + " including those in subdirectories");
        
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        
        //iterate through files in directory
        for (File file : files) {
            System.out.println("file: " + file.getCanonicalPath());
            instance.readIndexFile(file);
        }
    }
    
    //read file, tokenize input by pipe delimiter, look for account number in SECOND position 
    public void readIndexFile(File filename){
        Scanner sc = null;
        String contents = "";
        
        try {
          sc = new Scanner(filename);

          // Check if there is another line of input
          while(sc.hasNextLine()){
            String str = sc.nextLine();
            String[] indexArray = str.split("\\|");
            
            if(indexArray[1].equalsIgnoreCase("")){
                indexArray[1] = "-105";          
                
                for(int i=0; i<indexArray.length; i++){
                    
                    if(!indexArray[i].contains("pdf")){
                        contents += indexArray[i] + "|";
                    }
                    else{
                        contents += indexArray[i];
                    }
                }
                                
                overwriteIndexFile(filename, contents);
            }
          }
        } catch (IOException  exp) {
          // TODO Auto-generated catch block
          exp.printStackTrace();
        }finally{
          if(sc != null)
            sc.close();
        }	  		
    }
    
    //if no account number, write "-105" to the account number
    public void overwriteIndexFile(File fileName, String fileContents) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(fileContents);

        writer.close();
    }
}
