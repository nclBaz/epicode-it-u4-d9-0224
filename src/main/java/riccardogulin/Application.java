package riccardogulin;

import com.github.javafaker.Faker;
import riccardogulin.entities.User;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

	public static void main(String[] args) {


		Supplier<User> userSupplier = () -> {
			Faker faker = new Faker(Locale.ITALY);
			Random rndm = new Random();
			return new User(faker.lordOfTheRings().character(), faker.name().lastName(), rndm.nextInt(0, 100), faker.lordOfTheRings().location());
		};

		List<User> usersList = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			usersList.add(userSupplier.get());
		}

		usersList.forEach(System.out::println);

		// ***************************************** COLLECTORS *********************************************
		// 1. Raggruppiamo gli users per location
		Map<String, List<User>> usersByCity = usersList.stream().filter(user -> user.getAge() < 18).collect(Collectors.groupingBy(user -> user.getCity()));
		usersByCity.forEach((città, listaUtenti) -> System.out.println("Città: " + città + ", " + listaUtenti));

		// 2. Raggruppiamo gli users per età
		Map<Integer, List<User>> usersByAge = usersList.stream().collect(Collectors.groupingBy(user -> user.getAge()));
		usersByAge.forEach((età, listaUtenti) -> System.out.println("Età: " + età + ", " + listaUtenti));

		// 3. Concateniamo tutti i nomi degli user per ottenere una stringa tipo "nome1 cognome1, nome2 cognome2, ...."
		String nomiConcatenati = usersList.stream().map(user -> user.getFirstName() + " " + user.getLastName()).collect(Collectors.joining(", "));
		System.out.println(nomiConcatenati);

		// 4. Concateniamo tutte le età degli user per ottenere una stringa tipo "20, 21, 22, ...."
		String etàConcatenate = usersList.stream().map(user -> String.valueOf(user.getAge())).collect(Collectors.joining(", "));
		System.out.println(etàConcatenate);
	}
}
