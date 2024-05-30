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

		// 5. Calcolo la media delle età
		double average = usersList.stream().collect(Collectors.averagingInt(user -> user.getAge()));
		System.out.println("Media delle età: " + average);

		// 6. Raggruppiamo per città e calcoliamo la media delle età
		Map<String, Double> mediaEtàPerCittà = usersList.stream().collect(Collectors.groupingBy(user -> user.getCity(), Collectors.averagingInt(user -> user.getAge())));
		mediaEtàPerCittà.forEach((città, etàMedia) -> System.out.println("Città: " + città + ", media: " + etàMedia));

		// 7. Raggruppiamo per città con informazioni su età media, età più bassa, eta più alta, ...
		Map<Object, IntSummaryStatistics> raggruppatiPerCittàConStatistiche = usersList.stream().collect(Collectors.groupingBy(user -> user.getCity(),
				Collectors.summarizingInt(user -> user.getAge())));
		raggruppatiPerCittàConStatistiche.forEach((città, stats) -> System.out.println("Città: " + città + ", stats: " + stats));

		// ***************************************** COMPARATORS *********************************************
		System.out.println("***************************************** COMPARATORS *********************************************");
		// 1. Ordinare la lista in base all'età (ordine crescente)
		List<User> usersSortedByAge = usersList.stream().sorted(Comparator.comparingInt(user -> user.getAge())).toList();
		usersSortedByAge.forEach(user -> System.out.println(user));
		System.out.println();
		// 2. Ordinare la lista in base all'età (ordine decrescente)
		List<User> usersSortedByAgeDesc = usersList.stream().sorted(Comparator.comparingInt(User::getAge).reversed()).toList();
		// se si vuole utilizzare il .reversed() nelle parentesi di comparingInt bisogna mettere User::getAge e non la lambda
		usersSortedByAgeDesc.forEach(user -> System.out.println(user));
		System.out.println();
		// 3. Ordinare la lista in base al cognome
		List<User> usersSortedBySurname = usersList.stream().sorted(Comparator.comparing(user -> user.getLastName())).toList();
		usersSortedBySurname.forEach(user -> System.out.println(user));

		// ***************************************** LIMIT *********************************************
		System.out.println("***************************************** LIMIT *********************************************");
		// 1. Ordinare la lista degli utenti in base al cognome, saltiamo (skip) i primi 10 e ci facciamo tornare solo 5 elementi (limit)
		List<User> fiveUsersSortedBySurname = usersList.stream().sorted(Comparator.comparing(user -> user.getLastName())).skip(10).limit(5).toList();
		fiveUsersSortedBySurname.forEach(user -> System.out.println(user));

		// ***************************************** MAP TO *********************************************
		System.out.println("***************************************** MAP TO *********************************************");
		// 1. Calcolo della somma totale delle età degli utenti (con map e reduce)
		// int totalAge = usersList.stream().map(user -> user.getAge()).reduce(0, (partialSum, currentAge) -> partialSum + currentAge);
		// int totalAge2 = usersList.stream().reduce(0, (partialSum, currentUser) -> partialSum + currentUser.getAge());

		// 2. Calcolo della somma totale delle età degli utenti (con mapToInt)
		int totalAge3 = usersList.stream().mapToInt(User::getAge).sum();
		System.out.println("La somma delle età è: " + totalAge3);

		// 3. Calcolo della media delle età degli utenti (con mapToInt)
		OptionalDouble average2 = usersList.stream().mapToInt(User::getAge).average();
		if (average2.isPresent()) {
			System.out.println("La media delle età è: " + average2.getAsDouble());
		} else {
			System.out.println("La media non può essere calcolata perché la lista è vuota");
		}

		// 4. Restituisce l'età massima
		OptionalInt etàMassima = usersList.stream().mapToInt(User::getAge).max();
		if (etàMassima.isPresent()) {
			System.out.println("L'età massima è: " + etàMassima.getAsInt());
		} else {
			System.out.println("Lista vuota quindi non c'è un massimo");
		}


	}
}
