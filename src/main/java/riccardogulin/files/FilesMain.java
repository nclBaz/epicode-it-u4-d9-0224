package riccardogulin.files;

import org.apache.commons.io.FileUtils;
import riccardogulin.entities.User;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FilesMain {
	public static void main(String[] args) {
		File file = new File("src/qualcosa.txt"); // Creo un riferimento alla posizione nelle cartelle del file

		try {
			User aldo = new User("Aldo", "Baglio", 20, "Roma");
			FileUtils.writeStringToFile(file, aldo.toString() + System.lineSeparator(), StandardCharsets.UTF_8, true);
			// FileUtils.writeStringToFile(file, "CIAOOOOOOOOOOOOOOOO", StandardCharsets.UTF_8);
			// Il comportamento di default di questo metodo è quello di sovrascrivere tutto il contenuto del file
			// Per evitare questo comportamento basta mettere un ultimo parametro (append) a true
			// System.lineSeparator() è la maniera corretta di gestire gli "a capo" in modo che il codice funzioni sia su Windows che su Mac o Linux
			System.out.println("File scritto correttamente!");

			String contenuto = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
			System.out.println(contenuto);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
