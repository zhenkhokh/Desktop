
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 35-khei
 */
public class Sheduller {
    public static void main(String[] args) throws Exception {
        if (Case.dirs.size()!=Case.bins.size()||Case.bins.size()!=Case.names.size())
            throw new Exception("wrong list");        
        if (args.length==1){
            Task sheduller = new Task(-1);
            if (args[0].equalsIgnoreCase("1"))
                sheduller.step1();
            if (args[0].equalsIgnoreCase("2"))
                sheduller.step2();
        }
        if (args.length==2){
            if (args[0].equalsIgnoreCase("1"))
                System.out.println(Task.keys.dirs.get(new Integer(args[1]).toString()));
            if (args[0].equalsIgnoreCase("2"))
                System.out.println(Task.getName((String) Task.keys.bins.get(new Integer(args[1]).toString())));
            if (args[0].equalsIgnoreCase("3"))
                System.out.println(Task.keys.names.get(new Integer(args[1]).toString()));
        }
        if (args.length==0){ 
            Task sheduler = new Task(-1);
            sheduler.step1();           
            sheduler.step2();
            sheduler.step3(); 
        }
    }
}
