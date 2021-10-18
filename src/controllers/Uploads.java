package controllers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import models.Image;
import models.Type;
import security.InputsValidation;

@WebServlet({ "/upload", "/dashboard/upload" })

@MultipartConfig
public class Uploads extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Uploads() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath()+"/index");
	}

	private String upload(String savePath, String folderName, String fileName, Part part) throws IOException {
		folderName=folderName.replaceAll(" ", "_");
		fileName=fileName.replaceAll(" ", "_");
		folderName=folderName.replaceAll("/", "_");
		fileName=fileName.replaceAll("/", "_");


				
		if (savePath.endsWith(File.separator))
			savePath = savePath + "UPLOADS";
		else
			savePath = savePath + File.separator + "UPLOADS";
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		if (folderName != null) {
			savePath = savePath + File.separator + folderName;
			fileSaveDir = new File(savePath);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdir();
			}

			part.write(savePath + File.separator + fileName);

			return "UPLOADS" + File.separator + folderName + File.separator + fileName;
		}
		part.write(savePath + File.separator + fileName);

		return "UPLOADS" + File.separator + fileName;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession ses = request.getSession();

		if (!InputsValidation.isLogIn(ses)) {
			response.sendRedirect(request.getContextPath()+"/dashboard/login");
			 return; 
		}
		HashMap<String, Integer> sesInfo=InputsValidation.getSessionInfo(ses);
		if(sesInfo.get("role")==0)//client  don't have access to this area 
		{
			response.sendRedirect(request.getContextPath()+"/index");
			return;
		}

		
		String op = request.getParameter("op");
		String savePath = request.getServletContext().getRealPath("");
		if (op == null) {
			response.sendRedirect(request.getContextPath()+"/index");
			return;
		}
		if (op.equals("img")) {
			try {
				String _typeId = request.getParameter("id");
				int typeId = Integer.parseInt(_typeId);
				Type type = Type.getTypeById(typeId);
				for (Part part : request.getParts()) {
					
					if (InputsValidation.getExtension(part.getSubmittedFileName()) == null)
						continue;
					String url = upload(savePath, type.getName(), part.getSubmittedFileName(), part);
					Image.addImage(url, typeId);	
				}
				ses.setAttribute("msg", "Image uploaded");
				ses.setAttribute("alartType", "Success");
				response.sendRedirect(request.getContextPath()+"/dashboard/type");
				return;
			}catch(Exception e) {}
			ses.setAttribute("msg", "there is a problem try again");
			ses.setAttribute("alartType", "Warning");
			response.sendRedirect(request.getContextPath()+"/dashboard/type");
			return;

		}
		
		
		if (sesInfo.get("role") == 1)// employee don't have access to this area 
			{
			response.sendRedirect(request.getContextPath()+"/dashboard");
			return;
			}
		
		if (op.equals("logo")) {
			if (request.getParts().size() != 1) {
				ses.setAttribute("msg", "you can't upload more than one image");
				ses.setAttribute("alartType", "Warning");
				response.sendRedirect(request.getContextPath()+"/dashboard/setting");
				return;
			}
			for (Part part : request.getParts()) {

				if (InputsValidation.getExtension(part.getSubmittedFileName()) == null)
					continue;
				String fileName = "logo" + InputsValidation.getExtension(part.getSubmittedFileName());
				String url = upload(savePath, null, fileName, part);
				Image.addImage(url, 0);
				ses.setAttribute("msg", "image uploaded");
				ses.setAttribute("alartType", "Success");
				response.sendRedirect(request.getContextPath()+"/dashboard/setting");
				return;
			}
			ses.setAttribute("msg", "there is a problem try again");
			ses.setAttribute("alartType", "Warning");
			response.sendRedirect(request.getContextPath()+"/dashboard/setting");
			return;

		}

	}

}
