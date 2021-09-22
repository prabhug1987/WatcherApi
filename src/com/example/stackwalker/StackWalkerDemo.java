package com.example.stackwalker;

import java.lang.StackWalker.StackFrame;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Stream;

public class StackWalkerDemo {
	
	public void findCaller() {
		Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
		System.out.println(caller.getCanonicalName());
	}
	public void method1() {

	}

	public void method2() {
		List<StackFrame> stackTrace = StackWalker.getInstance(StackWalker.Option.SHOW_REFLECT_FRAMES)
				.walk(this::walkExample);

	}

	public void method3() {

		List<StackFrame> stackTrace = StackWalker.getInstance().walk(this::walkExample);
	}
	
	public void method4() {
		Runnable r = () ->{
			List<StackFrame> stackTrace2 = StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES).walk(this::walkExample);
			//printStackTrace(stackTrace2);
		};
		
		r.run();
	}
	
	public static void main(String[] args) {
		
	}

	public List<StackFrame> walkExample(Stream<StackFrame> stackFrameStream) {
		return stackFrameStream.collect(Collectors.toList());
	}

	public List<StackFrame> walkExample2(Stream<StackFrame> stackFrameStream) {
		return stackFrameStream.filter(f -> f.getClassName().contains("com.example.stackwalker"))
				.collect(Collectors.toList());
	}

	public String walkExample3(Stream<StackFrame> stackFrameStream) {
		return stackFrameStream
				.filter(frame -> frame.getClassName().contains("com.example.stackwalker")
						&& frame.getClassName().startsWith("StackWalker"))
				.findFirst().map(f -> f.getClassName() + " * " + f.getMethodName() + ", Line " + f.getLineNumber())
				.orElse("None of the class matches with the condition");
	}
	
	

}
