package cn.tedu.note.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class Md5Test {
	@Test
	public void Md5Test() {
		String str="123456";
		String md5=DigestUtils.md2Hex(str);
		System.out.println(md5);
		
		String salt="今天你吃了吗?";
		md5=DigestUtils.md5Hex(str+salt);
		System.out.println(md5);
	}

}
