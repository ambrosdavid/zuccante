public class TestCinema {
    
    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        
        TicketOffice1 ticketOffice1 = new TicketOffice1(cinema);
        Thread thread1 = new Thread(ticketOffice1, "TicketOffice1");
        
        TicketOffice2 ticketOffice2 = new TicketOffice2(cinema);
        Thread thread2 = new Thread(ticketOffice1, "TicketOffice2");
        
        thread1.start();
        thread2.start();
    
        // waiting for tasks
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.printf("Room 1 Vacancies: %d\n",cinema.getVacanciesCinema1());
        System.out.printf("Room 2 Vacancies: %d\n",cinema.getVacanciesCinema2());
    }
}


class TicketOffice1 implements Runnable {
    
    private Cinema cinema;

    public TicketOffice1 (Cinema cinema) {
        this.cinema = cinema;
    }
    
    @Override
    public void run() {
        cinema.sellTickets1(3);
        cinema.sellTickets1(2);
        cinema.sellTickets2(2);
        cinema.returnTickets1(3);
        cinema.sellTickets1(5);
        cinema.sellTickets2(2);
        cinema.sellTickets2(2);
        cinema.sellTickets2(2);
    }
}

class TicketOffice2 implements Runnable {
    
    private Cinema cinema;

    public TicketOffice2 (Cinema cinema) {
        this.cinema = cinema;
    }
    
    @Override
    public void run() {
        cinema.sellTickets2(2);
        cinema.sellTickets2(4);
        cinema.sellTickets1(2);
        cinema.sellTickets1(1);
        cinema.returnTickets2(2);
        cinema.sellTickets1(3);
        cinema.sellTickets2(2);
        cinema.sellTickets1(2);
    }
}


class Cinema {
    
    private long vacanciesCinema1; // i posti vuoti
    private long vacanciesCinema2;
    private final Object controlCinema1, controlCinema2;
    
    public Cinema(){
        controlCinema1 = new Object();
        controlCinema2 = new Object();
        vacanciesCinema1 = 20;
        vacanciesCinema2 = 20;
    }
    
    /* SYNCHRONIZED
     * 
     * The object taken in the parentheses by the synchronized construct 
     * is called a monitor object. The code is said to be synchronized on the monitor object. 
     * A synchronized instance method uses the object it belongs to as monitor object.
     * (by J.Jenkov)
     *
     */
    
    public boolean sellTickets1 (int number) {
        synchronized (controlCinema1) {
            if (number < vacanciesCinema1) {
                vacanciesCinema1 -= number;
                return true;
            } else {
                return false;
            }
        }
    }
    
    public boolean sellTickets2 (int number) {
        synchronized (controlCinema2) {
            if (number < vacanciesCinema2) {
                vacanciesCinema2 -= number;
                return true;
            } else {
                return false;
            }
        }
    }
    
    public boolean returnTickets1 (int number) {
        synchronized (controlCinema1) {
            vacanciesCinema1+=number;
        return true;
        }
    }
    
    public boolean returnTickets2 (int number) {
        synchronized (controlCinema2) {
            vacanciesCinema2 += number;
        return true;
        }
    }
    
    public long getVacanciesCinema1() {
        return vacanciesCinema1;
    }
    
    public long getVacanciesCinema2() {
        return vacanciesCinema2;
    }
} 
    
    