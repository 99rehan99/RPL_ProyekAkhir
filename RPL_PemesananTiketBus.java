import java.util.*;


class PemesananTiketBus {
	final int GET_HOURS = (new Random().nextInt(8-7) + 7);
	final int GET_MINUTE = (new Random().nextInt(9-0) + 0);
	private String name, id, date;
	private int age, number, seatType;

	PemesananTiketBus() {
		// Should not call this constructor.
	}

	PemesananTiketBus(String name, String id, int age, String date, int number, int seatType) {
		this.name = name;
		this.id = id;
		this.age = age;
		this.date = date;
		this.number = number;
		this.seatType = seatType;
	}

	String getHours(int temp) {
		return String.valueOf(GET_HOURS + ":" + temp + "" + GET_MINUTE);
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

	int getNumber() {
		return number;
	}

	int getSeatType() {
		return seatType;
	}

	protected void printInfo() {
		int temp = (new Random().nextInt(5-1) + 1);
		System.out.println("Cetak Tiket Pesanan . . . . .");
		System.out.println(getSeatType() == 1 ? "Tiket pesanan untuk tempat duduk nomor (1-15)" : "Tiket pesanan untuk tempat duduk nomor (16-30)");
		System.out.println("Nomor bus anda        : " + temp);
		System.out.println("Nomor tempat duduk    : " + getNumber());
		System.out.println("Jadwal keberangkatan  : 0" + getHours(temp));
		System.out.println("Tanggal pemesanan     : " + getDate());
		System.out.println("Tanggal cetak pesanan : " + new java.text.SimpleDateFormat("dd/MM/yyyy | HH:mm:ss").format(new Date()));
		System.out.println("Kode tiket            : " + getNumber() + getId() + String.valueOf(getName().charAt(0)).toUpperCase() + temp);
	}
}

public class RPL_PemesananTiketBus {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		LinkedList<Integer> window = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
		LinkedList<Integer> aisle = new LinkedList<>(Arrays.asList(16, 17, 18, 19, 20, 21, 23, 24, 25, 27, 28 ,29, 30));
		Collections.shuffle(window);
		Collections.shuffle(aisle);

		// Init var.
		String name, date;
		int purchase, number=0, age = -1, seatType = -1, aisleSeat = 1, windowSeat = 1;
		PemesananTiketBus[] tickets;

		System.out.println("=====================================================");
		System.out.println("\tSelamat datang di Pemesanan Tiket Bus");
		System.out.println("=====================================================\n");

		purchase = checkFormatIntDeep(30, 0, "Maaf, masukkan format dan range yang sesuai.", scan, "Jumlah tiket yang dipesan (1-30): ");
		tickets = new PemesananTiketBus[purchase];

		System.out.println("\nSilahkan lengkapi identitas pemesanan.");

		for (int i = 1; i <= purchase; ++i) {
			System.out.println("Pemesanan ke-"+i);
			System.out.println("---------------");

			// Formatting
			String regex = "(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[0-2])\\/([0-9]{4})";
			name = checkFormat("[a-zA-Z ]+", "Maaf, masukkan inputan berupa huruf.", scan, "Nama Pemesanan                           : ");
			age = checkFormatIntDeep(100, 0, "Maaf, umur harus angka (1-100).", scan, "Umur Pemesanan                           : ");
			String id = age >= 17 ? "KTP" : "KK";
			System.out.println("Tanda Pengenal (KTP/KK)                  : " + id);
			date = isValid(regex, "Maaf, tanggal tidak valid atau format tidak sesuai (dd/mm/yyyy).", scan, "Tanggal Pemesanan (ex. 12/12/2020)       : ");
			seatType = checkFormatIntDeep(2, 0, "Maaf, masukkan format dan range yang sesuai.", scan, "Pilih letak kursi (1. Window / 2. Aisle) : ");
			
			if (seatType == 1 && windowSeat <= 15) {
				windowSeat++;
				number = window.poll();
			} else if (aisleSeat <= 15) {
				aisleSeat++;
				seatType = 2;
				number = aisle.poll();
			} else System.out.println("Maaf kursi sudah penuh, silahkan melakukan pemesanan nanti lagi.");
			
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n");

			// Initialization
			tickets[i-1] = new PemesananTiketBus(name, id, age, date, number, seatType);
		}
		
		System.out.println(">>>>>>>>>> Tiket sukses dipesan <<<<<<<<<<\n");

		// Print ticket(s)
		for (int i = 0; i < tickets.length; ++i)
			tickets[i].printInfo();

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
			isValid(regex, message, scan, text);
		}

		return value;
	}

	private static String checkFormat(String regex, String message, Scanner scan, String text) {
		System.out.print(text);
		String value = scan.nextLine();
		if (!value.matches(regex)) {
			System.out.println(message);
			checkFormat(regex, message, scan, text);
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
