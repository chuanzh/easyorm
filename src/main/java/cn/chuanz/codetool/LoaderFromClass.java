package cn.chuanz.codetool;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class LoaderFromClass {

	public static void main(String[] args) throws Exception{
        new LoaderFromClass().test();
    }
	
	private void test() {
		String path = this.getClass().getResource("").getPath();
		path = path.substring(path.indexOf("/classes/")+9);
		System.out.println(path);
	}
	
}
