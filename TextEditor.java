import java.util.Scanner;
import java.io.*;
public class TextEditor
{

    Scanner sc;    
    int choice,nol;
    static String fileName;
    String str;
    
    public static void main() throws IOException
    {
        TextEditor t=new TextEditor();
        t.homePage();
        System.out.println("DO YOU WANT TO EDIT THIS FILE");
        if("YES".equalsIgnoreCase(t.sc.next()))
        {           
            t.editor(); 
        }
        else
        {          
            t.exit();  
        }
    }
    public TextEditor()
    {
        sc=new Scanner(System.in);
        fileName="Sample.txt";
        choice = 1;
        nol=0;
        str="";
    }
    
    public void createFile() throws IOException
    {   
        String st="";
        sc.nextLine();
        BufferedWriter bw= new BufferedWriter(new FileWriter(fileName));
        PrintWriter pw = new PrintWriter(bw);
        System.out.println("Your document has been created successfully\nPlease start entering the text to your file line by line");
        System.out.println("Enter \"DONE\"when you finish the document");
        while(st.equalsIgnoreCase("DONE")==false)
        {   
            st=sc.nextLine();            
            if(st.equalsIgnoreCase("Done")==false)
                pw.println(st);
        }
        System.out.println("Saving File.....\nYour file has been saved successfully");
        System.out.println("\f");
        pw.close();
        bw.close();
        openFile();
    }
    
    public void openFile() throws IOException
    {   
        System.out.println("Here is your file  :");
        try 
        {
            String st;
            FileReader fr = new FileReader(fileName);
            BufferedReader br= new BufferedReader(fr);
            while((st=br.readLine())!=null)
            {
                System.out.println(st);
            }
            br.close();  
            fr.close();
        }
        catch(FileNotFoundException f)
        {   
            System.out.println("ERROR!!Unable to find "+fileName);
            System.out.println("Please enter your file name with correct path OR enter 0 to exit");
            fileName=sc.next();
            if(fileName.equals("0"))
            {  
                exit();
            }
            else     
                openFile();
        }
        count_No_Of_Line();
    }
    
    
    void count_No_Of_Line()throws IOException
    {
        FileReader fr = new FileReader(fileName);
        BufferedReader br= new BufferedReader(fr);            
        int i=0;
        String st;
        while((st=br.readLine())!=null)
        {
            i++;
        }
        nol=i;
        br.close();
        fr.close();
    }
    
    void editor() throws IOException
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("MENU");
        System.out.println("1.Cut");
        System.out.println("2.Copy");
        System.out.println("3.Paste");
        System.out.println("4.Delete");
        System.out.println("5.Exit");
        choice =sc.nextInt();
        int i;
        System.out.print("\f"); 
        FileReader fr= new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        for(i=1;i<=nol;i++)
        {
            System.out.println(i+"\t"+br.readLine());
        }
        str="";
        br.close();
        fr.close();
        switch(choice)
        {
            case 1:                
                cut();
                break;
            case 2:
                copy();
                break;
            case 3:
                paste(); 
                break;
            case 5:  
                exit();
                break;
            case 4:  
                delete(); 
                break;
            default:
                System.out.println("Wrong input");              
                editor();
        }        
        openFile();
        System.out.println("OPTIONS");
        System.out.println("1.Edit more\n2.Close the text editor");
        int ch=sc.nextInt();
        switch(ch)
        {
            case 1:
                editor();                
                break;
            default:
                System.out.println("Exiting...");
                exit();
        }
        sc.close();
        br.close();
    }
    
    void copy() throws IOException
    {
         String st;        
         str="";
         int i;
         FileReader fr= new FileReader(fileName);
         BufferedReader br = new BufferedReader(fr);
         System.out.println("These are a few options for copying ");
         System.out.println("1.Copy entire file");
         System.out.println("2.Copy a group of lines");
         System.out.println("3.Copy a entire line");
         System.out.println("4.Copy a sequence of characters");
         int choice = sc.nextInt();
         int stind,endind,l,u;
         switch(choice)
         {
             case 1:
                while((st=br.readLine())!=null)
                {
                    str=str+st+"\n";
                }
                break;
             case 2:
                System.out.println("Enter the numbers of first and last line to be copied:");
                stind=sc.nextInt();
                endind=sc.nextInt();
                if(stind<=0||stind>nol||endind<=0||endind>nol)
                {
                    System.out.println("Wrong input");
                    copy();
                }
                for(i=1;i<=nol;i++)
                {
                    st=br.readLine();
                    if(i>=stind&&i<=endind)
                        str=str+"\n"+st;
                }
                break;
             case 3:
                System.out.println("Enter the number of line to be copied");
                stind=sc.nextInt();
                if(stind<=0||stind>nol)
                {
                    System.out.println("Wrong input");
                    copy();
                }
                for(i=1;i<=nol;i++)
                {
                    st=br.readLine();
                    if(i==stind)
                        str=st;
                }
                break;
             case 4:
                System.out.println("Enter the line number in which the characters to be copied exists");
                stind=sc.nextInt();
                if(stind<=0||stind>nol)
                {
                    System.out.println("Wrong input");
                    copy();
                }
                System.out.println("Enter the index of character from which sequence is to be copied :");
                l=sc.nextInt();
                System.out.println("Enter the index of character till which sequence is to be copied :");
                u=sc.nextInt();                
                for(i=1;i<=nol;i++)
                {
                    st=br.readLine();
                    int length=st.length();
                    if(l<0||u<0||l>u||l>=length||u>length)
                    {
                        System.out.println("Wrong input");
                        copy();
                    }
                    else if(i==stind)
                        str=st.substring(l,u);
                }
                break;
             default :
                 System.out.println("Wrong input");
                 copy();
         }
         System.out.println("Now press enter then TAB then CTRL+A and then press CTRL+C to copy , also press enter when done");
         sc.nextLine();
         sc.nextLine();
         System.out.print("\f");
         System.out.print(str);
         sc.nextLine();
         System.out.println();
         br.close();
         fr.close();
    }
    
    void paste() throws IOException
    {
        System.out.println("Press CTRL+V and then ENTER");
        sc.nextLine();
        String st,past=sc.nextLine();
        FileReader fr= new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);        
        System.out.println("Enter the line number in which this is to be pasted :");
        int line_no=sc.nextInt();
        System.out.println("Enter the index after which sequence is to be pasted :");
        int index=sc.nextInt();
        for(int i=1;i<=nol;i++)
        {
            st=br.readLine();
            if(i==line_no)
            {
                for(int j=0;j<st.length();j++)
                {
                    if(j==index)
                    {
                        str=str+st.charAt(j)+past;
                    }
                    else if(j==st.length()-1)
                    {
                        str=str+st.charAt(j)+"\n";
                    }
                    else
                    {
                        str=str+st.charAt(j);
                    }
                }
            }
            else
            {
                str=str+st+"\n";
            }
        }
        br.close();
        fr.close();
        FileWriter fw= new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println(str);
        pw.close();      
        bw.close();      
        sc.close();
        fr.close();        
    }
    
    void cut() throws IOException 
    {  
        int stind,l,u,i,j;
        String cuttxt="",st;
        FileReader fr= new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr); 
        System.out.println("Enter the line number in which the characters to be cutted exists:");
        stind=sc.nextInt();
        if(stind<=0||stind>nol)
        {
            System.out.println("Wrong input");
            cut();
        }
        System.out.println("Enter the index of character from which sequence is to be cutted :");
        l=sc.nextInt();
        System.out.println("Enter the index of character till which sequence is to be copied :");
        u=sc.nextInt();                
        for(i=1;i<=nol;i++)
        {
            st=br.readLine();
            int length=st.length();
            if(l<0||u<0||l>u||l>=length||u>length)
            {
                System.out.println("Wrong input");
                cut();
            }
            else if(i==stind)
            {
                cuttxt=st.substring(l,u);
                for(j=0;j<length;j++)
                {
                    if(j==length-1)
                        str=str+st.charAt(j)+"\n";
                    else if(j<l||j>u)
                        str=str+st.charAt(j);
                }                
            }
            else
            {
                str=str+st+"\n";
            }
        }
        fr.close(); 
        br.close();
        FileWriter fw= new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println(str);
        pw.close();       
        bw.close();
        fw.close();
        System.out.println("Now press enter then TAB then CTRL+A and then press CTRL+X to cut , also press enter when done");
        sc.nextLine();
        sc.nextLine();
        System.out.print("\f");
        System.out.print(cuttxt);
        sc.nextLine();
        System.out.println();        
    }
    
    void delete()throws IOException
    {
        String st;   
        str="";
        int stind,l,u,i,j;
        FileReader fr= new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr); 
        System.out.println("Enter the line number in which the sequence of characters to be deleted exists");
        stind=sc.nextInt();
        if(stind<=0||stind>nol)
        {
            System.out.println("Wrong input");
            delete();
        }
        System.out.println("Enter the index of character from which sequence is to be deleted :");
        l=sc.nextInt();
        System.out.println("Enter the index of character till which sequence is to be deleted :");
        u=sc.nextInt();                
        for(i=1;i<=nol;i++)
        {
            st=br.readLine();
            int length=st.length();
            if(l<0||u<0)
            {
                System.out.println("Wrong input");
                delete();
            }
            else if(i==stind)
            {            
                for(j=0;j<length;j++)
                {
                    if(j==length-1)
                        str=str+st.charAt(j)+"\n";
                    else if(j<l||j>u)
                        str=str+st.charAt(j);
                }                
            }
            else
            {
                str=str+st+"\n";
            }
        }
        fr.close(); 
        br.close();
        FileWriter fw= new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println(str);
        pw.close();       
        bw.close();
        fw.close();
    }
    
    public void homePage() throws IOException
    {       
        System.out.println("|⌈¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯⌉|  ");
        System.out.println("||   \\      /\\      / |===== |      |====== /=====\\ |\\    /| |=====    ||");
        System.out.println("||    \\    /  \\    /  |_____ |      |       |     | | \\  / | |_____    || ");
        System.out.println("||     \\  /    \\  /   |      |      |       |     | |  \\/  | |         || ");
        System.out.println("||      \\/      \\/    |===== |_____ |====== \\=====/ |      | |=====    || ");
        System.out.println("||                          TO KK's TEXT EDITOR                        ||   ");                                                   
        System.out.println("||     MENU:-                                                          ||   ");       
        System.out.println("||   1.CREATE A NEW DOCUMENT                                           ||   ");
        System.out.println("||   2.TO READ AN EXISTING DOCUMENT                                    ||   ");
        System.out.println("||   3.CLOSE TEXT EDITOR                                               ||   ");               
        System.out.println("|⌊_____________________________________________________________________⌋|   ");
        System.out.print("ENTER YOUR CHOICE::");
        choice = sc.nextInt();
        
        switch(choice)
        {
            case 1:
                System.out.print("Enter the name of new document(with extension)");
                fileName=sc.next();
                createFile();     
                break;
            case 2:
                System.out.print("Enter the document name(with path):");
                fileName=sc.next();
                System.out.println("\f");
                openFile();
                System.out.println();
                break;
            case 3:
                exit();
                break;
            default:
                exit();
        }            
    }
    
    public void exit() throws IOException
    {
        System.out.println("Are you sure want to exit?");
        if("YES".equalsIgnoreCase(sc.next()))
        {
            System.out.println("|⌈¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯⌉|  ");
            System.out.println("|| ---------- |     |    /\\    |\\    | |  /      \\     / |=====| |    |   ||  ");
            System.out.println("||     ||     |     |   /  \\   | \\   | | /        \\   /  |     | |    |   ||  ");
            System.out.println("||     ||     |=====|  /====\\  |  \\  | |/          \\=/   |     | |    |   ||  ");
            System.out.println("||     ||     |     | /      \\ |   \\ | |\\           |    |     | |    |   ||  ");
            System.out.println("||     ||     |     |/        \\|    \\| | \\          |    |=====| |====|   ||  ");
            System.out.println("|⌊________________________________________________________________________⌋|  ");
            System.exit(0);
        }
        else          
        {
            homePage();
        }
    }
    
}
