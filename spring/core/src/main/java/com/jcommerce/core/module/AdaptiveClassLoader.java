/**
 * Author: Bob Chen
 */

package com.jcommerce.core.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * A class loader that loads classes from directories and/or zip-format
 * file such as JAR file. It tracks the modification time of the classes
 * it loads to permit reloading through re-instantiation.
 * <P>
 * When the classloader reports its creator that one of the classes it
 * has loaded has changed on disk, it should discard the classloader
 * and create a new instance using <CODE>reinstantiate</CODE>.
 * The classes are then reloaded into the new classloader as required.
 *
 * <P>The classloader can also load resources, which are a means
 * for packaging application data such as images within a jar file
 * or directory.
 *
 * <P>The classloader always first tries to load classes and resources
 * from the system, and uses it's own path if that fails. This is also
 * done if an empty repository is passed at construction.
 */
public class AdaptiveClassLoader extends ClassLoader {

    /**
     * Generation counter, incremented for each classloader as they are
     * created.
     */
    static private int generationCounter = 0;

    /**
     * Generation number of the classloader, used to distinguish between
     * different instances.
     */
    private int generation;

    /**
     * Cache of the loaded classes. This contains ClassCacheEntry keyed
     * by class names.
     */
    private Hashtable cache;

    /**
     * The classpath which this classloader searches for class definitions.
     * Each element of the vector should be either a directory, a .zip
     * file, or a .jar file.
     * <p>
     * It may be empty when only system classes are controlled.
     */
    private Vector repository;
    
    ClassLoader parentClassLoader;

    /**
     * Private class used to maintain information about the classes that
     * we loaded.
     */
    private static class ClassCacheEntry {

        /**
         * The actual loaded class
         */
        Class loadedClass;

        /**
         * The file from which this class was loaded; or null if
         * it was loaded from the system.
         */
        File origin;

        /**
         * The time at which the class was loaded from the origin
         * file, in ms since the epoch.
         */
        long lastModified;

        /**
         * Check whether this class was loaded from the system.
         */
        public boolean isSystemClass() {
            return origin == null;
        }
    }

    //------------------------------------------------------- Constructors

    /**
     * Creates a new class loader that will load classes from specified
     * class repositories.
     *
     * @param classRepository An set of File classes indicating
     *        directories and/or zip/jar files. It may be empty when
     *        only system classes are loaded.
     * @throw java.lang.IllegalArgumentException if the objects contained
     *        in the vector are not a file instance or the file is not
     *        a valid directory or a zip/jar file.
     */
    public AdaptiveClassLoader(Vector classRepository)
        throws IllegalArgumentException
    {
        this(classRepository, null);
    }

    public AdaptiveClassLoader(Vector classRepository, ClassLoader parentClassLoader)
    throws IllegalArgumentException
    {
        super(parentClassLoader);
        
        this.parentClassLoader = parentClassLoader;
        
        // Create the cache of loaded classes
        cache = new Hashtable();

        // Verify that all the repository are valid.
        Enumeration e = classRepository.elements();
        while(e.hasMoreElements()) {
            Object o = e.nextElement();
            File file;

            // Check to see if element is a File instance.
            try {
                file = (File) o;
            } catch (ClassCastException objectIsNotFile) {
                throw new IllegalArgumentException("Object " + o
                    + "is not a valid \"File\" instance");
            }

            // Check to see if we have proper access.
            if (!file.exists()) {
                throw new IllegalArgumentException("Repository "
                    + file.getAbsolutePath() + " doesn't exist!");
            } else if (!file.canRead()) {
                throw new IllegalArgumentException(
                    "Don't have read access for file "
                     + file.getAbsolutePath());
            }

            // Check that it is a directory or zip/jar file
            if (!(file.isDirectory() || isZipOrJarArchive(file))) {
                throw new IllegalArgumentException(file.getAbsolutePath()
                    + " is not a directory or zip/jar file"
                    + " or if it's a zip/jar file then it is corrupted.");
            }
        }

        // Store the class repository for use
        this.repository = classRepository;

        // Increment and store generation counter
        this.generation = generationCounter++;
    }

    public Vector getClassRepository() {
        return repository;
    }
    
    //------------------------------------------------------- Methods

    /**
     * Test if a file is a ZIP or JAR archive.
     *
     * @param file the file to be tested.
     * @return true if the file is a ZIP/JAR archive, false otherwise.
     */
    private boolean isZipOrJarArchive(File file) {
        boolean isArchive = true;
        ZipFile zipFile = null;

        try {
            zipFile = new ZipFile(file);
        } catch (ZipException zipCurrupted) {
            isArchive = false;
        } catch (IOException anyIOError) {
            isArchive = false;
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException ignored) {}
            }
        }

        return isArchive;
    }

    /**
     * Check to see if a given class should be reloaded because of a
     * modification to the original class.
     *
     * @param className The name of the class to check for modification.
     */
    public synchronized boolean shouldReload(String classname) {

        ClassCacheEntry entry = (ClassCacheEntry) cache.get(classname);

        if (entry == null) {
            // class wasn't even loaded
            return false;
        } else if (entry.isSystemClass()) {
            // System classes cannot be reloaded
            return false;
        } else {
            boolean reload =
                (entry.origin.lastModified() != entry.lastModified);
            return reload;
        }
    }

    /**
     * Check whether the classloader should be reinstantiated.
     * <P>
     * The classloader must be replaced if there is any class whose
        * origin file has changed since it was last loaded.
     */
    public synchronized boolean shouldReload() {

        // Check whether any class has changed
        Enumeration e = cache.elements();
        while (e.hasMoreElements()) {
            ClassCacheEntry entry = (ClassCacheEntry) e.nextElement();

        if (entry.isSystemClass()) continue;

            // XXX: Because we want the classloader to be an accurate
            // reflection of the contents of the repository, we also
            // reload if a class origin file is now missing.  This
            // probably makes things a bit more fragile, but is OK in
            // a servlet development situation. <mbp@pharos.com.au>

            long msOrigin = entry.origin.lastModified();

            if (msOrigin == 0) {
                // class no longer exists
                return true;
            }

            if (msOrigin != entry.lastModified) {
                // class is modified
                return true;
            }
        }

        // No changes, no need to reload
        return false;
    }

    /**
     * Re-instantiate this class loader.
     * <p>
     * This method creates a new instance
     * of the class loader that will load classes form the same path
     * as this one.
     */
    public AdaptiveClassLoader reinstantiate(Vector repository) {
        return new AdaptiveClassLoader(repository, parentClassLoader);
    }

    //------------------------------------ Implementation of Classloader
    /**
     * Resolves the specified name to a Class. The method loadClass()
     * is called by the virtual machine.  As an abstract method,
     * loadClass() must be defined in a subclass of ClassLoader.
     *
     * @param      name the name of the desired Class.
     * @param      resolve true if the Class needs to be resolved;
     *             false if the virtual machine just wants to determine
     *             whether the class exists or not
     * @return     the resulting Class.
     * @exception  ClassNotFoundException  if the class loader cannot
     *             find a the requested class.
     */
    protected synchronized Class loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {
        // The class object that will be returned.
        Class c = null;

        // Use the cached value, if this class is already loaded into
        // this classloader.
        ClassCacheEntry entry = (ClassCacheEntry) cache.get(name);

        if (entry != null) {
            // Class found in our cache
            c = entry.loadedClass;
            if (resolve) resolveClass(c);
            return c;
        }

        if (!securityAllowsClass(name)) {
            return loadSystemClass(name, resolve);
        }

        // Attempt to load the class from the system
        try {
            c = loadSystemClass(name, resolve);
            if (c != null) {
                if (resolve) resolveClass(c);
                return c;
            }
        } catch (Exception e) {
            c = null;
        }

        // Try to load it from each repository
        Enumeration repEnum = repository.elements();

        // Cache entry.
        ClassCacheEntry classCache = new ClassCacheEntry();
        while (repEnum.hasMoreElements()) {
            byte[] classData;

            File file = (File) repEnum.nextElement();
            try {
                if (file.isDirectory()) {
                    classData =
                        loadClassFromDirectory(file, name, classCache);
                } else {
                    classData =
                        loadClassFromZipfile(file, name, classCache);
                }
            } catch(IOException ioe) {
                // Error while reading in data, consider it as not found
                classData = null;
            }
    
            if (classData != null) {
                // Define the class
                c = defineClass(name, classData, 0, classData.length);
                // Cache the result;
                classCache.loadedClass = c;
                // Origin is set by the specific loader
                classCache.lastModified = classCache.origin.lastModified();
                cache.put(name, classCache);
    
                // Resolve it if necessary
                if (resolve) resolveClass(c);
                
                return c;
            }
        }

        // If not found in any repository
        throw new ClassNotFoundException(name);
    }

    /**
     * Load a class using the system classloader.
     *
     * @exception  ClassNotFoundException  if the class loader cannot
     *             find a the requested class.
     * @exception  NoClassDefFoundError  if the class loader cannot
     *             find a definition for the class.
     */
    private Class loadSystemClass(String name, boolean resolve)
        throws NoClassDefFoundError, ClassNotFoundException
    {
        Class c = findSystemClass(name);
        // Throws if not found.

        // Add cache entry
        ClassCacheEntry cacheEntry = new ClassCacheEntry();
        cacheEntry.origin = null;
        cacheEntry.loadedClass = c;
        cacheEntry.lastModified = Long.MAX_VALUE;
        cache.put(name, cacheEntry);

        if (resolve) resolveClass(c);

        return c;
    }

    /**
     * Checks whether a classloader is allowed to define a given class,
     * within the security manager restrictions.
     */
    // XXX: Should we perhaps also not allow classes to be dynamically
    // loaded from org.apache.jserv.*?  Would it introduce security
    // problems if people could override classes here?
    // <mbp@humbug.org.au 1998-07-29>
    private boolean securityAllowsClass(String className) {
        try {
            SecurityManager security = System.getSecurityManager();

            if (security == null) {
                // if there's no security manager then all classes
                // are allowed to be loaded
                return true;
            }

            int lastDot = className.lastIndexOf('.');
            // Check if we are allowed to load the class' package
            security.checkPackageDefinition((lastDot > -1)
                ? className.substring(0, lastDot) : "");
            // Throws if not allowed
            return true;
        } catch (SecurityException e) {
            return false;
        }
    }

    /**
     * Tries to load the class from a directory.
     *
     * @param dir The directory that contains classes.
     * @param name The classname
     * @param cache The cache entry to set the file if successful.
     */
    private byte[] loadClassFromDirectory(File dir, String name,
        ClassCacheEntry cache)
            throws IOException
    {
        // Translate class name to file name
        String classFileName =
            name.replace('.', File.separatorChar) + ".class";

        // Check for garbage input at beginning of file name
        // i.e. ../ or similar
        if (!Character.isJavaIdentifierStart(classFileName.charAt(0))) {
            // Find real beginning of class name
            int start = 1;
            while (!Character.isJavaIdentifierStart(
                classFileName.charAt(start++)));
            classFileName = classFileName.substring(start);
        }

        File classFile = new File(dir, classFileName);

        if (classFile.exists()) {
            cache.origin = classFile;
            InputStream in = new FileInputStream(classFile);
            try {
                return loadBytesFromStream(in, (int) classFile.length());
            } finally {
                in.close();
            }
        } else {
            // Not found
            return null;
        }
    }

    /**
     * Tries to load the class from a zip file.
     *
     * @param file The zipfile that contains classes.
     * @param name The classname
     * @param cache The cache entry to set the file if successful.
     */
    private byte[] loadClassFromZipfile(File file, String name,
            ClassCacheEntry cache)
        throws IOException
    {
        // Translate class name to file name
        String classFileName = name.replace('.', '/') + ".class";

        ZipFile zipfile = new ZipFile(file);

        try {
            ZipEntry entry = zipfile.getEntry(classFileName);
            if (entry != null) {
                cache.origin = file;
                return loadBytesFromStream(zipfile.getInputStream(entry),
                    (int) entry.getSize());
            } else {
                // Not found
                return null;
            }
        } finally {
            zipfile.close();
        }
    }

    /**
     * Loads all the bytes of an InputStream.
     */
    private byte[] loadBytesFromStream(InputStream in, int length)
        throws IOException
    {
        byte[] buf = new byte[length];
        int nRead, count = 0;

        while ((length > 0) && ((nRead = in.read(buf,count,length)) != -1)) {
            count += nRead;
            length -= nRead;
        }

        return buf;
    }

    /**
     * Get an InputStream on a given resource.  Will return null if no
     * resource with this name is found.
     * <p>
     * The JServClassLoader translate the resource's name to a file
     * or a zip entry. It looks for the resource in all its repository
     * entry.
     *
     * @see     java.lang.Class#getResourceAsStream(String)
     * @param   name    the name of the resource, to be used as is.
     * @return  an InputStream on the resource, or null if not found.
     */
    public InputStream getResourceAsStream(String name) {
        // Try to load it from the system class
        InputStream s = getSystemResourceAsStream(name);

        if (s == null) {
            // Try to find it from every repository
            Enumeration repEnum = repository.elements();
            while (repEnum.hasMoreElements()) {
                File file = (File) repEnum.nextElement();
                if (file.isDirectory()) {
                    s = loadResourceFromDirectory(file, name);
                } else {
                    s = loadResourceFromZipfile(file, name);
                }

                if (s != null) {
                    break;
                }
            }
        }

        return s;
    }

    /**
     * Loads resource from a directory.
     */
    private InputStream loadResourceFromDirectory(File dir, String name) {
        // Name of resources are always separated by /
        String fileName = name.replace('/', File.separatorChar);
        File resFile = new File(dir, fileName);

        if (resFile.exists()) {
            try {
                return new FileInputStream(resFile);
            } catch (FileNotFoundException shouldnothappen) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Loads resource from a zip file
     */
    private InputStream loadResourceFromZipfile(File file, String name) {
        try {
            ZipFile zipfile = new ZipFile(file);
            ZipEntry entry = zipfile.getEntry(name);

            if (entry != null) {
                return zipfile.getInputStream(entry);
            } else {
                return null;
            }
        } catch(IOException e) {
            return null;
        }
    }

    /**
     * Find a resource with a given name.  The return is a URL to the
     * resource. Doing a getContent() on the URL may return an Image,
     * an AudioClip,or an InputStream.
     * <p>
     * This classloader looks for the resource only in the directory
     * repository for this resource.
     *
     * @param   name    the name of the resource, to be used as is.
     * @return  an URL on the resource, or null if not found.
     */
    public URL getResource(String name) {

        URL u = getSystemResource(name);
        if (u != null) {
            return u;
        }

        // Load for it only in directories since no URL can point into
        // a zip file.
        Enumeration repEnum = repository.elements();
        while (repEnum.hasMoreElements()) {
            File file = (File) repEnum.nextElement();
            if (file.isDirectory()) {
                String fileName = name.replace('/', File.separatorChar);
                File resFile = new File(file, fileName);
                if (resFile.exists()) {
                    // Build a file:// URL form the file name
                    try {
                        return new URL("file://"
                            + resFile.getAbsolutePath());
                    } catch(java.net.MalformedURLException badurl) {
                        badurl.printStackTrace();
                        return null;
                    }
                }
            }
        }

        // Not found
        return null;
    }
}
