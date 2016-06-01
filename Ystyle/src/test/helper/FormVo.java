package test.helper;

import org.ystyle.Annotation.UploadFile;
import org.ystyle.po.FilePo;

public class FormVo {

	@UploadFile(path="doc/old/")
	private FilePo[] sourceFile;

	public FilePo[] getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(FilePo[] sourceFile) {
		this.sourceFile = sourceFile;
	}
	
}
