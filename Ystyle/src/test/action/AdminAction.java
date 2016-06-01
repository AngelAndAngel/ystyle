package test.action;

import java.util.UUID;

import org.ystyle.Annotation.Autowired;
import org.ystyle.action.BaseAction;
import org.ystyle.po.FilePo;

import test.entity.Userinfo;
import test.helper.FormVo;
import test.service.UserService;

public class AdminAction extends BaseAction {

	private FormVo formVo;

	private Userinfo userinfo;

	public Userinfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}

	@Autowired(iocClass = UserService.class)
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public FormVo getFormVo() {
		return formVo;
	}

	public void setFormVo(FormVo formVo) {
		this.formVo = formVo;
	}

	public String manager() {

		return SUCCESS;
	}

	public String upload() throws Exception {
		FilePo[] fps = formVo.getSourceFile();
		for (FilePo fp : fps) {
			System.out.println("上传示例:"+fp.getFile().getAbsolutePath());
		}
		return SUCCESS;
	}

	public String toUserList() {
		request.setAttribute("users", userService.findAllUser());
		return SUCCESS;
	}

	public String toAddUser() {

		return SUCCESS;
	}

	public String addUser() {
		String uuid = UUID.randomUUID().toString();
		userinfo.setToid(uuid);
		userService.saveUser(userinfo);
		return SUCCESS;
	}

	public String toUpdateUser() {
		String toid = request.getParameter("toid");
		Userinfo user=userService.findUserByToid(toid);
		request.setAttribute("user",user);
		return SUCCESS;
	}

	public String updateUser() {
		userService.updateUser(userinfo);
		Userinfo ui=userService.findUserByToid(userinfo.getToid());
		return SUCCESS;
	}
	
	public String deleteUser(){
		String id=request.getParameter("id");
		userService.deleteUser(id);
		return SUCCESS;
	}
}
