package cn.tedu.note.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PointcutAspect {

	//@Before("bean(*Service)")
	//@Before("bean(notebookService)")
	public void test(){
		System.out.println("切入点测试！");
	}

	//@Before("within(cn.tedu.note.*.impl.*ServiceImpl)")
	public void test1(){
		System.out.println("切入点测试");
	}
	
	//@Before("* cn.tedu.note.*.*Service.list*()")
}
