/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import clientRMI.CpFiles;
import clientRMI.Parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import root.IGenerator;

class Case {
        /*
        * server side app location
        */    
        static Properties dirs = new Properties();
        /**
         * exe name or user side exe-name
         */
        static Properties bins = new Properties();
        /*
        * ticket name or user side folder target
        */        
        static Properties names = new Properties();
        /*
        * 3 for Invoker, 1 for Alfa, 0 for rest
        */
        static Properties recursion = new Properties();        
        
        public Case(String suffix)
    { // sed -r 's/\\/\\\\/g' names.txt>names1.txt;native2ascii names1.txt names2.txt 
     //  sed -r 's/\\/\\\\/g' exe.txt>exe1.txt;native2ascii exe1.txt exe2.txt
        /*
        sed -r 's/\\\\fileserver\\//g' dirs.txt|sed -r 's/\\\\FileServer\\//g'|sed -r 's/\\\\FILESERVER\\//g'|sed -r 's/\\\\Fileserver\\//g'|sed -r 's/\\\\PDM\\IM_TechCard\\//g'|sed -r 's/Soft/soft/g'|sed -r 's/SOFT/soft/g'|sed -r 's/TechCard/techcard/g'|sed -r 's/Manufacturingsoft/manufacturingsoft/g'|sed -r 's/Prgman/prgman/g'|sed -r 's/PrgMan/prgman/g'|sed -r 's/\\/\//g'|iconv -f ISO-8859-1 -t UTF-8 >dirs1.txt;native2ascii dirs1.txt dirs2.txt
        */
        try {
            dirs.load(new FileInputStream("dirs2"+suffix));
            bins.load(new FileInputStream("exe2"+suffix));
            names.load(new FileInputStream("names2"+suffix));
            recursion.load(new FileInputStream("rec2"+suffix));
            //Sheduller.dirs.load(new FileInputStream("test1.txt"));
            //Sheduller.bins.load(new FileInputStream("test2.txt"));
            //Sheduller.names.load(new FileInputStream("test3.txt"));
            
            //System.out.println(dirs);            System.out.println(names);            System.out.println(bins);
        } catch (IOException ex) {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
/**
 *
 * @author 35-khei
 */
public class Task extends RecursiveTask<Boolean>{
        /**
         * xml strore here
         */        
        static String updater = "\\\\FS2\\sheduler\\updater\\";
        /*
        * generator
        */        
        static String app = "Vniia.Generator.exe";
        /*
        * if true then app is used 
        */
        static boolean APP = false;
        /*
        * logging dir
        */
        static String out = "out\\";
        /*
        * initial base data, use sufix in constructor arg for data files
        * default value is .txt , for step() only 
        */
        static Case keys = null;//new Case(".txt");
        /*
        * -1 is main task. Others should be positive
        */        
        int index=-1;
        /*
        * log to logging dir
        */
        static boolean LOG = true;
        /*
        * use if verision > Integer.MAX see Generator.setCritical
        */
        
        
        //Integer RECURSIVE=0;//3 is minimal for Invoker
public static void main(String s[]){
}
    public Task(int i){
        super();
        index=i;
    }
    void logging() throws FileNotFoundException{
        File d = new File(out);
        if (!d.exists())
            d.mkdir();
        FileOutputStream fos = new FileOutputStream(new File(out+"out.txt"));
        System.setOut(new PrintStream(fos));
        fos = new FileOutputStream(out+"err.txt");
        System.setErr(new PrintStream(fos));
    }
    void exec(int recursion,File file,String ... cmd){
        /*
        Parser parser;
            try {
                parser = new Parser(file);
                System.out.println("version is "+parser.getVersion()+" app name is "+cmd[2]);
            } catch (Exception ex) {
                System.err.println("parser is broken");
            }
        */
        if (!APP){
            Generator gen = new Generator(recursion);
            gen.exe(file, cmd);
        }else{
            try {
                ProcessBuilder pb = new ProcessBuilder(cmd);
                //System.out.println("   "+pb.environment());
                pb.redirectOutput(Redirect.to(file));
                //pb.redirectInput(Redirect.INHERIT)
                //             .redirectOutput(Redirect.INHERIT)
                //             .redirectError(Redirect.INHERIT);
                pb.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
        FileOutputStream fos;
        OutputStream os;
            try {
                fos = new FileOutputStream(file);
                os = (OutputStream) Runtime.getRuntime().getLocalizedOutputStream((java.io.OutputStream)fos);
                Runtime.getRuntime().exec(cmd);
                os.flush();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        */
    }
    void step1() throws Exception{
        Case c = new Case("Inv.txt");
        Case.bins = c.bins;
        Case.dirs = c.dirs;
        Case.names = c.names;
        Case.recursion = c.recursion;
        step();
    }    
    void step2() throws Exception{
        Case c = new Case(".txt");
        Case.bins = c.bins;
        Case.dirs = c.dirs;
        Case.names = c.names;
        Case.recursion = c.recursion;
        step();
    }
    void step3() throws Exception{
        Case c = new Case("Alfa.txt");
        Case.bins = c.bins;
        Case.dirs = c.dirs;
        Case.names = c.names; 
        Case.recursion = c.recursion;
        step();
    }

    void step() throws Exception{
        if (LOG)
            logging();
        //start Vniia.AppUpdater.exe dir bin name > updater\bin.xml
        File u = new File(updater);
        if (!u.exists())
            u.mkdir();        
        ForkJoinPool pool = new ForkJoinPool();       
        // all keys are initilized in Case constructor        
        ForkJoinTask mainTask = new Task(-1);
        pool.invoke(mainTask);
        if (!(Boolean)mainTask.join())
            throw new Exception("some file is currupted or empty");
    }
    void atomicStep(String next) throws Exception{
            String dir = null;
            String bin = null;
            String name = null;
            String rec = null;
            if (Case.dirs.get(next)!=null)
                dir = (String) Case.dirs.get(next);
            if (Case.bins.get(next)!=null)
                bin = (String)Case.bins.get(next);
            if (Case.names.get(next)!=null)
                name = (String)Case.names.get(next);
            if (Case.recursion.get(next)!=null)
                rec = (String)Case.recursion.get(next);
            if (name==null||bin==null||dir==null||rec==null)
                throw new Exception("wrong list");
            // xml folder and generator's folder must be diffrent 
            //File n = new File(updater+"\\"+next);            n.mkdir();

            name = name+"_"+getName(bin);
            System.out.println("bin "+bin);
            //synchronize with serverRMI.Installer
            if (bin.indexOf(IGenerator.ALFAHRMKEY)==-1
                    &&bin.indexOf(IGenerator.ALFASALARYKEY)==-1
                    &&bin.indexOf(IGenerator.ALFAADMINKEY)==-1)
                bin = getName(bin)+".exe";
            else if (bin.indexOf(IGenerator.ALFAHRMKEY)!=-1)
                name=IGenerator.ALFAHRM;               
            else if (bin.indexOf(IGenerator.ALFASALARYKEY)!=-1)
                name=IGenerator.ALFASALARY;
            else if (bin.indexOf(IGenerator.ALFAADMINKEY)!=-1)
                name=IGenerator.ALFAADMIN;
            
            System.out.println(name+"  "+bin.replace("\\", ""));

            File file = new File(removeDoubleSlashes(updater+"\\"+name+".xml"));
            if (!file.exists())
                file.createNewFile();
            exec(
                    new Integer(rec)
                    ,file
                    ,app
                    ,dir
                    ,bin
                    ,name
                    ,dir
            );
            //Thread.currentThread().join(9000);
            System.out.println(next+":");
            System.out.println(dir);
            System.out.println(bin);
            System.out.println(name);
            System.out.println("Recursion:"+rec);
            //throw new Exception();
            //exec(new File(updater+getName(bin)+".xml"),app,dir,getName(bin)+".exe",name);        
    }
    static String getName(String bin){
        return bin.replace(".exe", "").replace(".EXE", "");
    }

    @Override
    protected Boolean compute() {
            if (this.index!=-1){
            try {
                String key = String.valueOf(index);
                atomicStep(key);
                //Thread.currentThread().join(6000);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
            return true;
        }
        // devide task
        ArrayList<Task> subtasks = new ArrayList<Task>();
        for (Iterator iterator = Case.dirs.keySet().iterator(); iterator.hasNext();) {
            Integer num = new Integer((String)iterator.next());
            Task subtask = new Task(num);
            subtask.fork();
            subtasks.add(subtask);
        }
        // join result
        Boolean out = true;
        for (Iterator iterator = subtasks.iterator(); iterator.hasNext();) {
                Task next = (Task)iterator.next();
                Boolean out1 = (Boolean)next.join();
                out = out & out1;
            }
        return out;
    }
    public static String removeDoubleSlashes(String s){
        StringBuilder sb = new StringBuilder(s);
      	int b = sb.toString().lastIndexOf("\\\\");
        while(b!=-1){
        	if(b!=0)
        		sb.delete(b, b+1);
        	else
        		break;
        	b=sb.toString().lastIndexOf("\\\\");
        }
        return sb.toString();
    }
}

