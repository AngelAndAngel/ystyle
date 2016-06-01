package org.ystyle.servlet.util;

import java.util.Map;

public class Namespace {

	private String name;
	private Map<String,ActionVo> listActions;
	
	public Namespace() {
	}
	
	public Namespace(String name, Map<String, ActionVo> listActions) {
		this.name = name;
		this.listActions = listActions;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, ActionVo> getListActions() {
		return listActions;
	}
	public void setListActions(Map<String, ActionVo> listActions) {
		this.listActions = listActions;
	}
	
	
}
