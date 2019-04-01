package com.vueJSComponents;

import java.io.IOException;

import io.github.jeemv.springboot.vuejs.components.VueComponent;

public class SearchBar {
	
	public static void main(String[] args) throws IOException {
		VueComponent vue = new VueComponent("m-search-bar");
		
		vue.setProps("value");
		
		vue.setDefaultTemplateFile();
		vue.createFile(false);
		
	}

}
