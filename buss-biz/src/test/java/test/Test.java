package test;

public class Test {
	public static void main(String[] args) {
		String pdfFile = "C:/sellTemp.pdf";
		String imgPath = pdfFile.split("\\.")[0];
		System.out.println(imgPath);
	}
}
