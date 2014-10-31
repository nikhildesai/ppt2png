package com.gotodeveloper.converter;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public interface FileConverter {

	/**
	 * Convert and save the given file to PNGs
	 * 
	 * @param file
	 * @param scale
	 *            0-1 quality of the PNG
	 * @param pageNum
	 *            -1 for converting the entire doc, otherwise the pageNum to be
	 *            converted
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public abstract void convert(String file, float scale, int pageNum)
			throws IOException, InvalidFormatException;

}