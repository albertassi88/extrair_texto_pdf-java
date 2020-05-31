package program;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class LerPdf {

	public static void main(String[] args) throws ParseException {

		List<Double> list = new ArrayList<>();
		Locale.setDefault(Locale.US);

		try {
			PdfReader reader = new PdfReader("C:\\Users\\Ruben\\Desktop\\java\\texto2.pdf");
			String texto = PdfTextExtractor.getTextFromPage(reader, 1);
			String converter = texto.replaceAll(",", ".");
			String regex = "(\\d{1,}-BOVESPA V|\\d{1,}-BOVESPA C).*( \\d{1,}[\\.\\d{3}]*\\.\\d{2})";
			Pattern padrao = Pattern.compile(regex);
			Matcher matcher = padrao.matcher(converter);

			StringBuilder numero;
			double soma = 0;
			double valor = 0;
			while (matcher.find()) {
				numero = new StringBuilder(matcher.group(2).replaceAll("[\\.,]", ""));
				numero.insert(numero.length() - 2, ".");
				valor = Double.parseDouble(numero.toString());
				list.add(valor);
			}
			for (Double obj : list) {
				soma += obj;
			}
			
			BigDecimal bd = new BigDecimal(soma).setScale(2, RoundingMode.HALF_EVEN);
			
			System.out.println("Valores da Lista: " + list);
	
			System.out.println("Soma dos valores: " + bd);

		} catch (IOException e) {
			e.getStackTrace();
		}
	}
}
