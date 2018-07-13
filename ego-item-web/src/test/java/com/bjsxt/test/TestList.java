package com.bjsxt.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestList {
	
	@Test
	public void testList() {
		
		List<String> strs = new ArrayList<String>();
		
		strs.add("a");
		strs.add("b");
		strs.add("c");
		strs.add("d");
		strs.add("e");
		strs.add("f");
		
		for (String str : strs) {
			if ("c".equals(str)) {
				strs.remove(str);
				break;
			}
		}
		
	}
	
}
