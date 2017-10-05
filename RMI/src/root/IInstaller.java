package root;

public interface IInstaller extends IHandler  {
		boolean update();
		boolean install();
                IParser getParser();
                IFilter getFilter();
                Integer getCurVersion();
                public boolean isInstalled();
                public void testParser();
                //setters are prohibited  
}
