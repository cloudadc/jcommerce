/**
* Author: Bob Chen
*/

package com.jcommerce.core.module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.jcommerce.core.module.payment.IPaymentModule;
import com.jcommerce.core.module.shipping.IShippingModule;

public class ModuleLoader {
    private File rootPath = new File("modules");
    
    public void setRootPath(File _rootPath) {
        rootPath = _rootPath;
    }
    
    public List<IPaymentModule> getPayments() {
        List<IPaymentModule> payments = (List)loadModules(new File(rootPath, "payment"), IPaymentModule.class);
        return payments;
    }
    
    public List<IShippingModule> getShippings() {
        List<IShippingModule> shippings = (List)loadModules(new File(rootPath, "shipping"), IShippingModule.class);
        return shippings;
    }
    
    private List<IModule> loadModules(File path, Class type) {
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
        
        List<IModule> modules = new ArrayList<IModule>();
        
        for (String name : allClasses) {
            try {
                if (type.isAssignableFrom(clsLoader.loadClass(name))) {
                    Object o = clsLoader.loadClass(name).newInstance();
                    modules.add((IModule)o);
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
    
    public static void main(String[] args) {
        ModuleLoader loader = new ModuleLoader();
        loader.setRootPath(new File("c:/modules"));
        for ( ;;) {
            List<IPaymentModule> ps = loader.getPayments();
            System.out.println("ps:"+ps);
            
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
