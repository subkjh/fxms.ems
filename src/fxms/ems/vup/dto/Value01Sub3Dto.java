package fxms.ems.vup.dto;

public class Value01Sub3Dto {
	public String point_pid;
	public Object value;

	public static void main(String[] args) {
		
		Double num = Double.valueOf("1.75217E9");
		Double num2 = Double.valueOf("-2.13647E9");
		
		
		
		System.out.println(num.intValue());
		System.out.println(num.longValue());
		System.out.println(num.floatValue());
		
		System.out.println(num2.intValue());
		System.out.println(num2.longValue());
		System.out.println(num2.floatValue());

	}
}
