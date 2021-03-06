package few.annotations;


import few.services.AnnotationFinder;
import org.apache.tomcat.util.bcel.classfile.AnnotationEntry;
import org.apache.tomcat.util.bcel.classfile.ClassParser;
import org.apache.tomcat.util.bcel.classfile.JavaClass;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: gerbylev
 * Date: 30.11.11
 */
public class AnnotationFinderImpl implements AnnotationFinder {

    Annotations annotations;
    List<String> xmlFiles;
    ServletContext context;
    ClassLoader classLoader;

    private static final class Annotations extends HashMap<Class, List<Class>> {
    }

    public static final String ANNOTATION_FINDER_CONEXT_KEY = AnnotationFinderImpl.class.getName();

    public AnnotationFinderImpl(ServletContext context, ClassLoader classLoader) {
        this.context = context;
        this.classLoader = classLoader;

        context.setAttribute(ANNOTATION_FINDER_CONEXT_KEY, this);
    }

    public Map<Class, List<Class>> findAnnotations() {
        if( annotations != null )
            return annotations;

        find();

        return annotations;
    }

    public List<String> findXmlFiles()  {
        if( xmlFiles != null )
            return xmlFiles;

        find();

        return xmlFiles;
    }

    private void find() {
        long start = System.currentTimeMillis();
        try {
            annotations = new Annotations();
            xmlFiles = new LinkedList<String>();

            // 1. scan WEB-INF/classes folder
            String u = context.getRealPath("/WEB-INF/classes");
            File f = new File(u);
            if( f.exists() )
                scanClassesFolder(f);

            // 2. scan WEB-INF/lib folder
            u = context.getRealPath("/WEB-INF/lib");
            f = new File(u);
            if( f.exists() )
                scanLibFolder(f);
        } finally {
            log.info("find procedure took " + (System.currentTimeMillis() - start) + " msec");
        }
    }

    private void scanLibFolder(File folder) {
        File [] files = folder.listFiles();

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if( file.getName().endsWith(".jar") )
                scanJar(file);
        }

    }

    private void scanJar(File file) {
        JarFile jf ;

        try {
            jf = new JarFile(file);
        } catch (IOException e) {
            log.log(Level.SEVERE, "can not read jar-file " + file.getAbsolutePath(), e);
            return;
        }
        Enumeration<JarEntry> jarEntries = jf.entries();
        while(jarEntries.hasMoreElements()){
            JarEntry entry = jarEntries.nextElement();
            if( entry.getName().endsWith(".class") && !entry.getName().contains("$") ) {
                try {
                    InputStream stream = jf.getInputStream(entry);
                    scanClass(stream);
                } catch (IOException e) {
                    log.log(Level.SEVERE, "", e);
                }
            }
        }

    }

    private void scanClassesFolder(File folder) {
        scanClassesFolder(folder, folder);
    }

    private void scanClassesFolder(File baseFolder, File folder) {
        File [] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if( file.isDirectory() )
                scanClassesFolder(baseFolder, file);
            else
                scanClassFile(baseFolder, file);
        }

    }

    private void scanClassFile(File baseFolder, File file) {
        String className = file.getAbsolutePath().substring(baseFolder.getAbsolutePath().length());

        if( className.endsWith(".xml") ) {
            xmlFiles.add(file.getAbsolutePath());
            return;
        }

        if( !className.endsWith(".class") || className.contains("$"))
            return;

        className = className.substring(0, className.length() - ".class".length());
        className = className.replace('/', '.').replace('\\', '.');
        if( className.startsWith(".") )
            className = className.substring(1);

        try {
            InputStream stream = new FileInputStream(file);
            scanClass(stream);
            stream.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, "", e);
        }
    }

    private void scanClass(InputStream stream) throws IOException {
        ClassParser parser = new ClassParser(stream, null);
        JavaClass clazz = parser.parse();

//        if( !className.equals(clazz.getClassName()) )
//            throw new RuntimeException("that strange...");

        AnnotationEntry[] annotationsEntries = clazz.getAnnotationEntries();

        for (int i = 0; i < annotationsEntries.length; i++) {
            AnnotationEntry annotationsEntry = annotationsEntries[i];
            Class an = loadAnnotation(annotationsEntry.getAnnotationType());
            if( an != null ) {
                Class cl;
                try {
                    cl = classLoader.loadClass(clazz.getClassName());
                } catch (ClassNotFoundException e) {
                    log.log(Level.SEVERE, "", e);
                    return;
                }
                List<Class> clazzez = annotations.get(an);
                if( clazzez == null ) {
                    clazzez = new LinkedList<Class>();
                    annotations.put(an, clazzez);
                }
                clazzez.add(cl);
            }
        }
    }

    Map<String, Class> annotationTypeCache = new HashMap<String, Class>();
    private Class loadAnnotation(String annotationType) {
        String annName = annotationType.substring(1, annotationType.length() - 1).replace('/', '.');

        Class ann = annotationTypeCache.get(annotationType);
        if( ann != null )
            return ann;

        try {
            Class clazz = classLoader.loadClass(annName);
            if( clazz.isAnnotation() ) {
                annotationTypeCache.put(annName, clazz);
                return clazz;
            } else {
                log.severe("type " + annotationType + " is not an annotation");
            }
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, "", e);
        }
        return null;
    }

    Logger log = Logger.getLogger(AnnotationFinderImpl.class.getName());
}
