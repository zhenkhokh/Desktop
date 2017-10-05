package root;

import java.rmi.Remote;

public interface IInvoker extends IHandler,Remote {
                void updateService();

}
