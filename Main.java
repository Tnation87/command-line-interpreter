package com.company;

import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String msg = scan.nextLine();

        Main obj = new Main();
        obj.menu(args,msg);
    }

    public void menu(String[] args, String msg){

        if (msg.equals("clear"))
            this.clear(args);
        else if(msg.startsWith("?"))
            this.SpecificHelp(args,msg);
        else if (msg.startsWith("cd"))
            this.cd(args,msg);
        else if (msg.equals("ls"))
            this.ls(args);
        else if (msg.startsWith("cp"))
            this.cp(args,msg);
        else if (msg.startsWith("mv"))
            this.mv(args,msg);
        else if (msg.startsWith("rm"))
            this.rm(args,msg);
        else if (msg.startsWith("mkdir"))
            this.mkdir(args,msg);
        else if (msg.startsWith("rmdir"))
            this.rmdir(args,msg);
        else if (msg.startsWith("cat"))
            this.cat(args,msg);
        else if (msg.startsWith("more"))
            this.more(args,msg);
        else if (msg.equals("pwd"))
            this.pwd(args);
        else if (msg.equals("args"))
            this.args(args);
        else if (msg.equals("date"))
            this.date(args);
        else if (msg.equals("help"))
            this.help(args);
        else if (msg.equals("exit"))
            return;
        else {
            System.out.println("unidentified command");
            main(args);
        }
    }

    public void clear(String[] args) {
        for (int i = 0; i < 50; ++i)
            System.out.println();
        main(args);
    }

    public void cd(String[] args, String msg) {
        File directory = new File(msg.substring(3)).getAbsoluteFile();
        if (directory.exists() || directory.mkdirs())
            if(!(System.setProperty("user.dir", directory.getAbsolutePath()) != null))
                System.out.println("error");

        main(args);
    }

    public void ls(String[] args) {
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++)
                System.out.println(listOfFiles[i].getName());

        main(args);
    }

    public void cp(String[] args, String msg) {
        int j = 0,  NumOfDirectories = 0;
        List<String> l = new ArrayList<>();
        String temp;
        for (int i = 3; i < msg.length(); i++){
            temp = "";
           while (i < msg.length()  && msg.charAt(i) != ' '){
               temp += msg.charAt(i);
               i++;
           }
           File f1 = new File(temp);
           if (f1.isDirectory())
               NumOfDirectories++;
           f1 = null;

           l.add(temp);
           j++;
        }

        File dest = new File(l.get(j-1));
        File[] listOfFiles = dest.listFiles();
        if (NumOfDirectories > 1){
            System.out.println("error! this command can't contain more than one directory as an argument");
            main(args);
        }
        else if (!(dest.isDirectory())) {
            System.out.println("the last argument has to be a directory");
            main(args);
        }

        try {
            for (int i = 0; i < l.size() - 1; i++) {
                File source = new File(l.get(i));
                File output = new File(dest.getPath() + '/' + source.getName());
                if(!output.createNewFile()) {
                    System.out.println("error creating file");
                    main(args);
                }

                for (int k = 0; k < listOfFiles.length; k++){
                    if (source.getName() == listOfFiles[k].getName()){
                        System.out.println("a file with the same name already exists");
                        main(args);
                    }
                }
                FileInputStream in = new FileInputStream(source.getPath());
                FileOutputStream out = new FileOutputStream(output.getPath());
                int n;
                while ((n = in.read()) != -1)
                    out.write(n);

                source = null;
                output = null;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        main(args);
    }

    public void mv(String[] args, String msg) {
        int j = 0,  NumOfDirectories = 0;
        List<String> l = new ArrayList<>();
        String temp;
        for (int i = 3; i < msg.length(); i++){
            temp = "";
            while (i < msg.length()  && msg.charAt(i) != ' '){
                temp += msg.charAt(i);
                i++;
            }
            File f1 = new File(temp);
            if (f1.isDirectory())
                NumOfDirectories++;
            f1 = null;

            l.add(temp);
            j++;
        }

        File dest = new File(l.get(j-1));
        File[] listOfFiles = dest.listFiles();
        if (NumOfDirectories > 1){
            System.out.println("error! this command can't contain more than one directory as an argument");
            main(args);
        }
        else if (!(dest.isDirectory())) {
            System.out.println("the last argument has to be a directory");
            main(args);
        }

        try {
            for (int i = 0; i < l.size() - 1; i++) {
                File source = new File(l.get(i));
                for (int k = 0; k < listOfFiles.length; k++){
                    if (source.getName() == listOfFiles[k].getName()){
                        System.out.println("a file with the same name already exists");
                        main(args);
                    }
                }

                source.renameTo(new File(dest + "/" + source.getName()));
                source = null;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        main(args);
    }

    public void rm(String[] args, String msg) {
        List<String> l = new ArrayList<>();
        String temp;
        for (int i = 3; i < msg.length(); i++){
            temp = "";
            while (i < msg.length()  && msg.charAt(i) != ' '){
                temp += msg.charAt(i);
                i++;
            }
            l.add(temp);
        }

        for (int i = 0; i < l.size(); i++){
            File f = new File(l.get(i));
            if(!f.delete()){
                System.out.println("error");
                main(args);
            }
            f = null;
        }

        main(args);
    }

    public void mkdir(String[] args, String msg) {
        List<String> l = new ArrayList<>();
        String temp;
        for (int i = 6; i < msg.length(); i++){
            temp = "";
            while (i < msg.length()  && msg.charAt(i) != ' '){
                temp += msg.charAt(i);
                i++;
            }
            l.add(temp);
        }

        for (int i = 0; i < l.size(); i++){
            File f = new File(l.get(i));
            if(!f.mkdir()){
                System.out.println("error");
                main(args);
            }
            f = null;
        }

        main(args);
    }

    public void rmdir(String[] args, String msg) {
        List<String> l = new ArrayList<>();
        String temp;
        for (int i = 6; i < msg.length(); i++){
            temp = "";
            while (i < msg.length()  && msg.charAt(i) != ' '){
                temp += msg.charAt(i);
                i++;
            }
            l.add(temp);
        }

        for (int i = 0; i < l.size(); i++) {
            File f = new File(l.get(i));
            File[] listOfFiles = f.listFiles();
            if (listOfFiles.length != 0){
                System.out.println("can't delete non-empty folders");
                main(args);
            }
            else if (!f.delete())
                System.out.println("error");
        }

        main(args);
    }

    public void cat(String[] args, String msg) {
        List<String> l = new ArrayList<>();
        String temp;
        for (int i = 4; i < msg.length(); i++){
            temp = "";
            while (i < msg.length()  && msg.charAt(i) != ' '){
                temp += msg.charAt(i);
                i++;
            }
            l.add(temp);
        }

        for (int i = 0; i < l.size(); i++) {
            try {
                 File file = new File(l.get(i));
                 FileInputStream fis = new FileInputStream(file);

                 int oneByte;
                 while ((oneByte = fis.read()) != -1) {
                 System.out.write(oneByte);
                 }
                 file = null;
                 fis = null;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        main(args);
    }

    public void more(String[] args, String msg) {
        String line = null;
        Scanner s = new Scanner(System.in);
        char c;
        try {
            FileReader fileReader = new FileReader(msg.substring(5));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                System.out.println("press c to continue");
                c = s.next().charAt(0);
                while (c != 'c'){
                    System.out.println("press c to continue");
                    c = s.next().charAt(0);
                }

            }

            bufferedReader.close();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }

        main(args);
    }

    public void pwd(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        main(args);
    }

    public void args(String[] args) {
        System.out.println("the clear command: no arguments");
        System.out.println("the cd command: the path of the wanted directory");
        System.out.println("the ls command: the path of the wanted directory");
        System.out.println("the cp command: the path(s) of the files you want to copy followed by the path of the directory you wish to copy the previous files to");
        System.out.println("the mv command: the path(s) of the files you want to move followed by the path of the directory you wish to move the previous files into");
        System.out.println("the rm command: the path(s) of the files you want to remove");
        System.out.println("the mkdir command: the path(s) of the directories you want to create");
        System.out.println("the rmdir command: the path(s) of the directories you want to remove");
        System.out.println("the cat command: the path(s) of the files you want to display the content of into the console");
        System.out.println("the more command: the path(s) of the files you want to display the content of into the console");
        System.out.println("the pwd command: no arguments");
        main(args);
    }

    public void date(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        main(args);
    }

    public void help(String[] args) {
        System.out.println("the clear command: clears the console");
        System.out.println("the cd command: changes the current working directory");
        System.out.println("the ls command: displays the content of the current directory");
        System.out.println("the cp command: copies the file(s) into the required diectory");
        System.out.println("the mv command: moves the file(s) into the required diectory");
        System.out.println("the rm command: removes the required file(s)");
        System.out.println("the mkdir command: creates the required directory(ies)");
        System.out.println("the rmdir command: removes the required directory(ies)");
        System.out.println("the cat command: displays the content of the required files into the console respectively");
        System.out.println("the more command: displays the content of the required files into the console respectively line by line");
        System.out.println("the pwd command: displays the current working directory");
        main(args);
    }

    public void SpecificHelp(String[] args, String msg){
        if (msg.contains("clear"))
            System.out.println("the clear command: clears the console");
        else if (msg.contains("cd"))
            System.out.println("the cd command: changes the current working directory");
        else if (msg.contains("ls"))
            System.out.println("the ls command: displays the content of the current directory");
        else if (msg.contains("cp"))
            System.out.println("the cp command: copies the file(s) into the required diectory");
        else if (msg.contains("mv"))
            System.out.println("the mv command: moves the file(s) into the required diectory");
        else if (msg.contains("rm"))
            System.out.println("the rm command: removes the required file(s)");
        else if (msg.contains("mkdir"))
            System.out.println("the mkdir command: creates the required directory(ies)");
        else if (msg.contains("rmdir"))
            System.out.println("the rmdir command: removes the required directory(ies)");
        else if (msg.contains("cat"))
            System.out.println("the cat command: displays the content of the required files into the console respectively");
        else if (msg.contains("more"))
            System.out.println("the more command: displays the content of the required files into the console respectively line by line");
        else if (msg.contains("pwd"))
            System.out.println("the pwd command: displays the current working directory");
        else System.out.println("unrecognized command");
        main(args);
    }
}
