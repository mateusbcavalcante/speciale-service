package br.com.a2dm.spdm.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

public class UploadHelper {

	private final int limit_max_size = 10240000;
	private final String limit_type_file = "gif|jpg|png|jpeg";
	private final String path_to = "C:\\Users\\joaop\\Documentos\\turyn\\spdm-fotos";

	public String processUpload(Part fileUpload, BigInteger idImage) {
		String fileSaveData = "noimages.jpg";
		System.out.println("1");
		try {
			System.out.println("2");
			if (fileUpload.getSize() > 0) {
				System.out.println("3");
				String submittedFileName = getFilename(fileUpload);
				if (checkFileType(submittedFileName)) {
					System.out.println("4");
					if (fileUpload.getSize() > this.limit_max_size) {
						System.out.println("5");
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File size too large!", ""));
					} else {
						System.out.println("6");
						String currentFileName = submittedFileName;
						String extension = currentFileName.substring(currentFileName.lastIndexOf("."), currentFileName.length());
						String newfilename = "NC_" + idImage + extension;
						System.out.println("newfilename: " + newfilename);
						fileSaveData = newfilename;
						String fileSavePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + this.path_to;
						System.out.println("fileSavePath: " + fileSavePath);
						try {
							System.out.println("10");
							byte[] fileContent = new byte[(int) fileUpload.getSize()];
							InputStream in = fileUpload.getInputStream();
							in.read(fileContent);

							File fileToCreate = new File(fileSavePath, newfilename);
							File folder = new File(fileSavePath);
							if (!folder.exists()) {
								folder.mkdirs();
							}
							FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
							fileOutStream.write(fileContent);
							fileOutStream.flush();
							fileOutStream.close();
							fileSaveData = newfilename;
							System.out.println("fileSaveData: " + fileSaveData);
						} catch (IOException e) {
							System.out.println("100");
							fileSaveData = "noimages.jpg";
						}
					}
				} else {
					fileSaveData = "noimages.jpg";
				}
			}
		} catch (Exception ex) {
			System.out.println("200");
			fileSaveData = "noimages.jpg";
		}
		System.out.println("300");
		return fileSaveData;
	}

	private String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
			}
		}
		return null;
	}

	private boolean checkFileType(String fileName) {
		if (fileName.length() > 0) {
			String[] parts = fileName.split("\\.");
			if (parts.length > 0) {
				String extention = parts[parts.length - 1];
				return this.limit_type_file.contains(extention);
			}
		}
		return false;
	}

}
