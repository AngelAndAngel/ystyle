package org.ystyle.converter;

import java.io.File;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.ystyle.converter.FileConverter;
import org.ystyle.converter.TypeConverter;
import org.ystyle.Annotation.UploadFile;
import org.ystyle.po.FilePo;
import org.ystyle.utils.ActionContext;
import org.ystyle.utils.ActionReplaceHolder;
import org.ystyle.utils.Utils;

public class FileConverter implements TypeConverter {

	private static final Logger logger = Logger.getLogger(FileConverter.class);

	@Override
	public Object convertValue(Object value, Field field) {
		if(!(value instanceof FileItem) && !(value instanceof FileItem[])){
			return null;
		}
		UploadFile uf = field.getAnnotation(UploadFile.class);
		HttpServletRequest request = ActionContext.getRequest();
		try {
			
			if (uf != null) {
				String path = uf.path();
				logger.debug("path: " + path);
				path=Utils.resolvePlaceHolder(path, ActionReplaceHolder.getInstance());
				String name = uf.name();
				String realpath = request.getRealPath(path);
				logger.debug("realpath: " + realpath);
				File dsk = new File(realpath);
				if (!dsk.exists()) {
					dsk.mkdirs();
				}
				
				FilePo[] fps=new FilePo[0];
				if (field.getType().isArray()) {
					
					// 假如是数组，那么保存在同一个注释的文件夹里面，并且文件名为源文件的名字
					FileItem[] fis = (FileItem[]) value;
					for (int i = 0; i < fis.length; i++) {
						FileItem item = fis[i];
						long filesize=(item==null?0:item.getSize());
						if(filesize==0){
							continue;
						}
						String filename = item.getName();
						if(filename.indexOf(File.separator)>=0){
							filename=filename.substring(filename.lastIndexOf(File.separator)+1);
						}
						logger.debug("filesize: "+filesize);
						File file = new File(realpath + File.separator
								+ filename);
						
						item.write(file);
                        
						FilePo[] fps_temp=fps;
						fps=new FilePo[fps.length+1];
						for(int j=0;j<fps_temp.length;j++){
							fps[j]=fps_temp[j];
						}
						FilePo fp=new FilePo();
						fp.setFile(file);
						fp.setFilename(filename);
						fp.setWebpath(path+filename);
						fp.setContentType(item.getContentType());
						fp.setSize(filesize/1024);
						fps[fps.length-1]=fp;
					}
					if(fps!=null && fps.length>0){
						return fps;
					}
					return null;
                   
				} else {
					FileItem[] items=(FileItem[])value;
					FileItem item=items[0];
					long filesize=(item==null?0:item.getSize());
					if(filesize==0){
						return null;
					}
					String filename = item.getName();
					if(!name.equals("")){
						String exts = filename.substring(filename
								.lastIndexOf("."));
						filename=name+exts ;
					}else{
						filename=filename.substring(filename.lastIndexOf(File.separator)+1);
					}
					File file = new File(realpath + File.separator
							+ filename);
					item.write(file);
					FilePo fp=new FilePo();
					fp.setFile(file);
					fp.setFilename(filename);
					fp.setWebpath(path+filename);
					fp.setContentType(item.getContentType());
					fp.setSize(filesize/1024);
					return fp;
				}
			} else {
				// 没有注解的暂时不作任何处理
				return null;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("上传模块出现错误:"+ex.getMessage());
		}

		return null;
	}

	
}
