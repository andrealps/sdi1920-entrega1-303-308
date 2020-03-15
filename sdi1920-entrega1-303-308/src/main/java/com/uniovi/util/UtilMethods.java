package com.uniovi.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UtilMethods {
	
	/**
	 * Obtiene un archivo a través de su url relativa
	 * @param url
	 * @return fichero
	 */
	public static File getFile(String url) {
		ClassLoader classLoader = new UtilMethods().getClass().getClassLoader(); 
        File file = new File(classLoader.getResource(url).getFile());
        
        return file;
	}
	
	/**
	 * Para leer una imagen y obtener sus bytes
	 * @param url donde se encuentra la imagen
	 * @return bytes de la imagen
	 * @throws IOException si no encuentra la imagen
	 */
	public static byte[] getImageContent(String url) throws IOException {
		File file = getFile(url);
        byte[] content = Files.readAllBytes(file.toPath());
        
        return content;
	}
	
	/**
	 * Obtiene el path absoluto de un archivo a través de su url relativa
	 * @param urlFile
	 * @return path absoluto
	 */
	public static String getAbsolutePath(String url) {
		File file = getFile(url);
        return file.getAbsolutePath();
	}
}
