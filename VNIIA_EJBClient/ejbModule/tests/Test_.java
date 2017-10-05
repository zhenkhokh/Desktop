package tests;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import model.Chain;
import model.MsysChain;
//import model.MainResult;
import model.RequestBuilder;
import model.UmrChain;
import model.all.VPersonal;
import model.command.AppsCmd;
import model.command.Invoker;
import model.command.PgmModuleCmd;
import model.command.ModulesCmd;
import model.command.PersonNumCmd;
import model.command.PgmCmd;
import model.command.AppsCmd;
import model.command.UserCmd;
import model.decorator.Name;
import model.decorator.PersonNum;
import model.decorator.SERVICE_NAMES;
import model.msysAdmin.VMenu;
import model.msysAdmin.VUsers;
import model.service.GetAppsSrv;
import model.service.GetPersonalSrv;
import model.service.IEjbGetApps;
import model.service.IEjbGetPgmModule;
import model.service.IEjbGetModules;
import model.service.IEjbGetPerson;
import model.service.IEjbGetUser;
import model.umr.VIndustrialPgms;
import model.umr.VPgm;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.NotSupportedException;

//import org.apache.tools.ant.taskdefs.condition.Matches;


public class Test_ {
   public static String JNDI_SERVICE1=SERVICE_NAMES.JNDI_USER_DEFAULT;//UserCmd.JNDI_SERVICE;//"java:global/VNIIA_EJB/user";
   public static String JNDI_SERVICE2=SERVICE_NAMES.JNDI_MODULES_DEFAULT;//ModulesCmd.JNDI_SERVICE;// "java:global/VNIIA_EJB/modules";
   public static String JNDI_SERVICE3=SERVICE_NAMES.JNDI_APPS;//AppsCmd.JNDI_SERVICE;//"java:global/VNIIA_EJB/apps";
   public static String JNDI_SERVICE4=SERVICE_NAMES.JNDI_PGM_MODULE_DEFAULT;//PgmModuleCmd.JNDI_SERVICE;
   public static String JNDI_SERVICE5=SERVICE_NAMES.JNDI_PERSON_DEFAULT;//PersonNumCmd.JNDI_SERVICE;
   public static String JNDI_SERVICE6=JNDI_SERVICE3;//AppsCmd.JNDI_SERVICE;

    
}
