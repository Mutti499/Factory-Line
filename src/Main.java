import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException {
		ArrayList<String> inputList = new ArrayList<String>();
		ArrayList<String> outputList = new ArrayList<String>();
		BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(args[0]));


			String line = reader.readLine();
			while (line != null) {
				// System.out.println(line);
				inputList.add(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FactoryImpl Factory = new FactoryImpl();
		for (int i = 0; i < inputList.size(); i++) {

			String line = inputList.get(i);
			String[] array = line.split(" ");

			switch (array[0]) {
				case "AF":

					try {

						int id = Integer.parseInt(array[1]);
						int value = Integer.parseInt(array[2]);

						Product product = new Product(id, value);
						Factory.addFirst(product);

					} catch (Exception e) {

					}

					break;

				case "AL":
					try {

						int id = Integer.parseInt(array[1]);
						int value = Integer.parseInt(array[2]);

						Product product = new Product(id, value);
						Factory.addLast(product);
					} catch (Exception e) {
					}

					break;

				case "RF":
					String string;
					try {
						Product product = Factory.removeFirst();
						string = "(" + product.getId() + ", " + product.getValue() + ")";

					} catch (Exception e) {
						string = "Factory is empty.";
					}
					outputList.add(string);
					break;

				case "A":
					try {

						int index = Integer.parseInt(array[1]);
						int id = Integer.parseInt(array[2]);
						int value = Integer.parseInt(array[3]);

						Product product = new Product(id, value);
						Factory.add(index, product);

					} catch (Exception e) {

						outputList.add("Index out of bounds.");
					}

					break;

				case "RL":
					String string2;
					try {
						Product product = Factory.removeLast();
						string2 = "(" + product.getId() + ", " + product.getValue() + ")";

					} catch (Exception e) {
						string2 = "Factory is empty.";
					}
					outputList.add(string2);

					break;

				case "RI":
					String string7;
					try {
						int index = Integer.parseInt(array[1]);

						Product product = Factory.removeIndex(index);
						string7 = "(" + product.getId() + ", " + product.getValue() + ")";

					} catch (Exception e) {
						string7 = "Index out of bounds.";
					}
					outputList.add(string7);
					break;

				case "RP":
					String string8;
					try {
						int value = Integer.parseInt(array[1]);

						Product product = Factory.removeProduct(value);
						string8 = "(" + product.getId() + ", " + product.getValue() + ")";

					} catch (Exception e) {
						string8 = "Product not found.";
					}
					outputList.add(string8);
					break;

				case "F":

					String string3;
					try {
						int index = Integer.parseInt(array[1]);

						Product product = Factory.find(index);
						string3 = "(" + product.getId() + ", " + product.getValue() + ")";

					} catch (Exception e) {
						string3 = "Product not found.";
					}
					outputList.add(string3);

					break;

				case "G":
					String string5;
					try {
						int index = Integer.parseInt(array[1]);

						Product product = Factory.get(index);
						string5 = "(" + product.getId() + ", " + product.getValue() + ")";

					} catch (Exception e) {
						string5 = "Index out of bounds.";
					}
					outputList.add(string5);

					break;

				case "U":
					String string4;
					try {
						int id = Integer.parseInt(array[1]);
						int value = Integer.parseInt(array[2]);
						Product product = Factory.update(id, value);

						string4 = "(" + id + ", " + product.getValue() + ")";

					} catch (Exception e) {
						string4 = "Product not found.";
					}
					outputList.add(string4);

					break;

				case "FD":

					String string6;
					try {
						int num = Factory.filterDuplicates();
						string6 = Integer.toString(num);

					} catch (Exception e) {
						string6 = "Something bad happened while reversing the line";
					}
					outputList.add(string6);

					break;

				case "R":
					Factory.reverse();
					String printed2 = Factory.print();
					outputList.add(printed2);
					break;

				case "P":
					String printed = Factory.print();
					outputList.add(printed);
					break;

				default:
					// code block
			}


		}

		for (int i = 0; i < outputList.size(); i++)

			writer.write(outputList.get(i) + "\n");
		{
			writer.close();
			System.out.println("***** Process Completed *****");

		}
	}
}
