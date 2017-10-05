
import serverRMI.Invoker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 35-khei
 */
public class ServiceUpdater {
        public static void main(String[] args) {
            System.out.println("\nStart update service");
            Invoker invoker = new Invoker();
            invoker.updateService();
    }
}
