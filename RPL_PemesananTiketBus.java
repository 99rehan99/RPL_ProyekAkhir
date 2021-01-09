import java.util.*;


class PemesananTiketBus {
	private String name, id, date, number;
	private int age, seatType;

	PemesananTiketBus() {
		// Should not call this constructor.
	}

	PemesananTiketBus(String name, String id, int age, String date, String number, int seatType) {
		this.name = name;
		this.id = id;
		this.age = age;
		this.date = date;
		this.number = number;
		this.seatType = seatType;
	}

	String getName() {
		return name;
	}

	String getId() {
		return id;
	}

	int getAge() {
		return age;
	}

	String getDate() {
		return date;
	}

	String getNumber() {
		return number;
	}

	int getSeatType() {
		return seatType;
	}

	protected void printInfo(int hour, int minute, int minute2) {
		System.out.println("Cetak Tiket Pesanan . . . . .");
		System.out.println("Nomor tempat duduk    : " + getName());
		System.out.println("Nomor tempat duduk    : " + getNumber());
		System.out.println("Jadwal keberangkatan  : 0" + String.valueOf(hour + ":" + minute2 + "" + minute));
		System.out.println("Tanggal pemesanan     : " + getDate());
		System.out.println("Tanggal cetak pesanan : " + new java.text.SimpleDateFormat("dd/MM/yyyy | HH:mm:ss").format(new Date()));
		System.out.println("Kode tiket            : " + getNumber().charAt(0) + getId() + String.valueOf(getName().charAt(0)).toUpperCase() + getNumber().charAt(1) + "\n");
	}
}

public class RPL_PemesananTiketBus {
	final static Random random = new Random();
	final static int hours = random.nextInt(8-7) + 7;
	final static int minutes = random.nextInt(9-0) + 0;
	final static int minutes2 = random.nextInt(5-1) + 1;

	public static void main(String[] args) {
		final int hour = hours;
		final int minute = minutes;
		final int minute2 = minutes2;
		final LinkedList<String> NOMOR_BUS = new LinkedList<>(Arrays.asList());
		Scanner scan = new Scanner(System.in);

		// Init var.
		String name, date, number;
		int purchase, age = -1, seatType = -1, aisleSeat = 1, windowSeat = 1 ;
		PemesananTiketBus[] tickets;

		System.out.println("=====================================================");
		System.out.println("\tSelamat datang di Pemesanan Tiket Bus");
		System.out.println("=====================================================\n");

		purchase = checkFormatIntDeep(30, 0, "Maaf, masukkan format dan range yang sesuai.", scan, "Jumlah tiket yang dipesan (1-33): ");
		tickets = new PemesananTiketBus[purchase];

		System.out.println("\nSilahkan lengkapi identitas pemesanan.");

		for (int i = 1; i <= purchase; ++i) {
			System.out.println("Pemesanan ke-"+i);
			System.out.println("---------------");

			// Formatting
			String regex = "(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[0-2])\\/([0-9]{4})";
			name = checkFormat("[a-zA-Z ]+", "Maaf, masukkan inputan berupa huruf.", scan, "Nama Pemesan                       : ");
			age = checkFormatIntDeep(100, 0, "Maaf, umur harus angka (1-100).", scan, "Umur Pemesan                       : ");
			String id = age >= 17 ? "KTP" : "KK";
			System.out.println("Tanda Pengenal (KTP/KK)            : " + id);
			date = isValid(regex, "Maaf, tanggal tidak valid atau format tidak sesuai (dd/mm/yyyy).", scan, "Tanggal Pemesanan (ex. 12/12/2020) : ");
			do {

				number = checkFormat("[1-8]{1}[a-dA-D]{1}", "Maaf, masukkan inputan sesuai pilihan dibawah ini.", scan, "    --------------    \n    Pintu    Supir\n    1A 1B    1C 1D\n    2A 2B    2C 2D\n    3A 3B    3C 3D\n    4A 4B    4C 4D\n    5A 5B    5C 5D\n    6A 6B    6C 6D\n    7A 7B    7C 7D\n    Pintu\n    8A 8B 8C 8D 8E\n    --------------    \nPilih Nomor Tempat Duduk           : ");
				number = number.toUpperCase();
				if (NOMOR_BUS.indexOf(number) != -1) 
					System.out.println("Maaf, nomor tersebut sudah dipilih sebelumnya, mohon untuk memasukan nomor yang lain.");
			} while(NOMOR_BUS.indexOf(number) != -1);
			NOMOR_BUS.add(number);

			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n");

			// Initialization
			tickets[i-1] = new PemesananTiketBus(name, id, age, date, number, seatType);
		}
		
		System.out.println(">>>>>>>>>> Tiket sukses dipesan <<<<<<<<<<\n");

		// Print ticket(s)
		for (int i = 0; i < tickets.length; ++i)
			tickets[i].printInfo(hour, minute, minute2);

		System.out.println("\nTiket ini tidak bisa di refund!");
		System.out.println("Patuhi peraturan dan hati-hati di jalan.\n");
	}

	private static String isValid(String regex, String message, Scanner scan, String text) {
		java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
		System.out.print(text);
		String value = scan.nextLine();
		try {
			formatter.setLenient(false);
			if (formatter.parse(value) != null) return value;
		} catch(java.text.ParseException e) {
			System.out.println(message);
			value = isValid(regex, message, scan, text);
		}

		return value;
	}

	private static String checkFormat(String regex, String message, Scanner scan, String text) {
		System.out.print(text);
		String value = scan.nextLine();
		if (!value.matches(regex)) {
			System.out.println(message);
			value = checkFormat(regex, message, scan, text);
		}

		return value;
	}

	private static int checkFormatIntDeep(int max, int min, String message, Scanner scan, String text) {
		int value = 0;
		try {
			System.out.print(text);
			value = scan.nextInt();
			scan.nextLine();
		} catch(InputMismatchException | NegativeArraySizeException e) {
			scan.nextLine();
			System.out.println(message);
			value = checkFormatIntDeep(max, min, message, scan, text);
		}
		if (value > max || value <= min) {
			System.out.println(message);
			value = checkFormatIntDeep(max, min, message, scan, text);
		}
		return value;
	}
}
