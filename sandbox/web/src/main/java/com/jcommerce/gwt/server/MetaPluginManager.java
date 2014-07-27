/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.jcommerce.core.model.Payment;
import com.jcommerce.core.module.DynamicClassLoader;
import com.jcommerce.core.module.IModule;
import com.jcommerce.core.payment.IPaymentMetaPlugin;

public class MetaPluginManager {
    private File rootPath = new File("C:\\modules");
    
    public void setRootPath(File _rootPath) {
        rootPath = _rootPath;
    }
    
    public List<IPaymentMetaPlugin> getPayments() {
        List<IPaymentMetaPlugin> payments = (List)loadModules(new File(rootPath, "payment"), IPaymentMetaPlugin.class);
        return payments;
    }
    
//    public List<IShippingModule> getShippings() {
//        List<IShippingModule> shippings = (List)loadModules(new File(rootPath, "shipping"), IShippingModule.class);
//        return shippings;
//    }
    
    public void initDb() {
        List<IPaymentMetaPlugin> ps = getPayments();
        if (ps != null) {
            for (IPaymentMetaPlugin p : ps) {
                Payment payment = getPayment(p);
            }
        }
    }
    
    private Payment getPayment(IPaymentMetaPlugin pm) {
        Payment p = new Payment();
//        p.setName(name)
        return p;
    }
    
    public void install(IModule module) {
        
    }
    
    public void uninstall(IModule module) {
        
    }

    private List<IPaymentMetaPlugin> loadModules(File path, Class type) {
        Vector repo = new Vector();
        
        if (path == null || !path.exists() || !path.isDirectory()) {
            return null;
        }
        
        List<String> allClasses = new ArrayList<String>();
        
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".jar") || file.getName().endsWith(".zip")) {
                    try {
                        List<String> names = loadModuleInZip(file);
                        
                        allClasses.addAll(names);

                        repo.add(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } 
            }
        }
        
//        if( !isSame(clsLoader.getClassRepository(), repo) || clsLoader.shouldReload() || shouldReload(clsLoader, allClasses)) {
            DynamicClassLoader clsLoader = new DynamicClassLoader(repo, getClass().getClassLoader());
//        }
        
        List<IPaymentMetaPlugin> modules = new ArrayList<IPaymentMetaPlugin>();
        
        for (String name : allClasses) {
            try {
                if (type.isAssignableFrom(clsLoader.loadClass(name))) {
                    Object o = clsLoader.loadClass(name).newInstance();
                    modules.add((IPaymentMetaPlugin)o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return modules;
    }
    
    private boolean isSame(Vector v1, Vector v2) {
        if (v1 == null) {
            v1 = new Vector();
        }
        if (v2 == null) {
            v2 = new Vector();
        }
        
        if (v1.size() != v2.size()) {
            return false;
        }
        
        for (int i = 0 ; i < v1.size(); i++) {
            if (!v2.contains(v1.get(i))) {
                return false;
            }
            
            if (v1.get(i) instanceof File) {
                long t1 = ((File)v1.get(i)).lastModified();
                long t2 = ((File)v2.get(v2.indexOf(v1.get(i)))).lastModified();
                if (t1 - t2 != 0) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
//    private boolean shouldReload(AdaptiveClassLoader clsLoader, List<String> allClasses) {
//        for (String name : allClasses) {
//            if (clsLoader.shouldReload(name)) {
//                return true;
//            }
//        }
//        
//        return false;
//    }
//    
    private List<String> loadModuleInZip(File file) throws IOException {
        List<String> names = new ArrayList<String>();
        
        ZipFile zipFile = new ZipFile(file);

        Enumeration entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();

            if (entry.isDirectory()) {
                continue;
            }

            String name = entry.getName();
            if (name.endsWith(".class")) {
                name = name.substring(0, name.length() - ".class".length()).replace('/', '.');
                names.add(name);
            }
        }

        zipFile.close();
        
        return names;
    }
    
//    public static void main(String[] args) {
//        MetaPluginManager loader = new MetaPluginManager();
//        loader.setRootPath(new File("c:/modules"));
//        
//        List<BeanObject> payments = new ArrayList<BeanObject>();
//		Map<String, Object> maps = new HashMap<String, Object>();
//		MetaPluginManager manager = new MetaPluginManager();
//		List<IPaymentMetaPlugin> lists = manager.getPayments();
//		for(IPaymentMetaPlugin module : lists){
//		
//			System.out.println("module.getCode():\t" + module.getCode());
//			maps.put(IPayment.CODE, module.getCode());
//			
//			System.out.println("module.getName():\t" + module.getName());
//			maps.put(IPayment.NAME, module.getName());
//			
//			System.out.println("module.getDescription():\t" + module.getDescription());
//			maps.put(IPayment.DESCRIPTION, module.getDescription());
//			
//			System.out.println("module.getDefaultConfig():\t" + module.getDefaultConfig());
//			maps.put(IPayment.CONFIG, module.getDefaultConfig());
//			
//			System.out.println("module.getPayFee():\t" + module.getPayFee());
//			maps.put(IPayment.FEE, module.getPayFee());
//			
//			System.out.println("module.isCod():\t" + module.isCod());
//			maps.put(IPayment.COD, module.isCod());
//			
//			System.out.println("module.isOnline():\t" + module.isOnline());
//			maps.put(IPayment.ONLINE, module.isOnline());
//			
//			payments.add(new BeanObject(ModelNames.PAYMENT,maps));
//			
//		}
//        for ( ;;) {
//            List<IPaymentMetaPlugin> ps = loader.getPayments();
//            Map<String, Object> maps = new HashMap<String, Object>();
//            for(int i=0; i<ps.size(); i++)
//            {
//            	System.out.println("\n\n\n");
//            	
//            	IPaymentMetaPlugin p = ps.get(i); 
//            	
//            	System.out.println("Code:\t " + p.getCode());
//            	System.out.println("Name:\t " + p.getName());
//            	System.out.println("DefaultConfig:\t " + p.getDefaultConfig());
//            	System.out.println("Description:\t " + p.getDescription());
//            	System.out.println("PayFee:\t " + p.getPayFee());
//            	System.out.println("PayFee:\t " + p.isCod());
//            	System.out.println("PayFee:\t " + p.isOnline());
//            }
//            
//            try {
//                Thread.sleep(10*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
