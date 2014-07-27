package com.jcommerce.migration.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This Util used to delete all .svn folder
 * 
 * @author kylin
 * 
 */
public class FolderUtil {

	static List<String> files = new ArrayList<String>();

	public static void count(File folder, boolean dump) throws IOException {
	
		init(folder, files);
		
		System.out.println("Total file counts: " + files.size());
		
		if(dump) {
			Collections.sort(files);
			File dumpFile = new File("dump.out");
			FileWriter writer = new FileWriter(dumpFile);
			
			for(int i = 0 ; i < files.size() ; i ++) {
				writer.write(i + ": " + files.get(i)  + "\n");
			}
			
			writer.flush();
			writer.close();
			System.out.println("Dump File: " + dumpFile);
		}
	}

	private static void init(File folder, List<String> files) {
		if (folder.isDirectory()) {
			for (File f : folder.listFiles()){
				init(f, files);
			}
		}
		
		files.add(folder.getAbsolutePath());
	}

	public static void remove(File folder, String name) {

		if (folder.isDirectory()) {

			if (folder.getName().contains(".svn")) {
				System.out.println("rm -fr " + folder);
//				System.out.println("Remove " + folder + " " + folder.delete());
			} else {
				for (File f : folder.listFiles()) {
					remove(f, name);
				}
			}

		} 
	}

	public static void main(String[] args) throws Exception {
//		File folder = new File("/home/kylin/work/test");
		File folder = new File("/home/kylin/project/jcommerce");
		String name = ".svn";
		remove(folder, name);
		count(folder, true);
	}

}
