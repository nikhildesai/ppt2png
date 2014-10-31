package com.gotodeveloper.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gotodeveloper.converter.PptxToPngFileConverter;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/upload")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileUploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Get");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Post");

		response.setContentType("text/html;charset=UTF-8");

		ServletFileUpload.isMultipartContent(request);

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig()
				.getServletContext();
		File repository = (File) servletContext
				.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		// Process the uploaded items
		Iterator<FileItem> iter = items.iterator();
		File uploadedFile = null;
		while (iter.hasNext()) {
			FileItem item = iter.next();

			if (!item.isFormField()) {
				uploadedFile = new File(System.getProperty("java.io.tmpdir")
						+ File.separator + System.currentTimeMillis()
						+ item.getName());
				try {
					item.write(uploadedFile);
					// TODO: uploadeded file holds the uploaded ppt file. Invoke
					// the conversion operation here
					PptxToPngFileConverter.getInstance().convert(uploadedFile.getAbsolutePath(), 1, -1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		uploadedFile.delete();
	}
}
