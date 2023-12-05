public class Main {
    public static void main(String[] args) {
        InMemoryDB inMemoryDB = new MemoryDB();

        // example for the program
        System.out.println("Initial get on A (should be null): " + inMemoryDB.get("A")); // null

        try {
            inMemoryDB.put("A", 5);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        inMemoryDB.beginTransaction();
        inMemoryDB.put("A", 5);
        System.out.println("Get A within transaction (should be null): " + inMemoryDB.get("A"));

        inMemoryDB.put("A", 6);

        inMemoryDB.commit();
        System.out.println("Get A after commit (should be 6): " + inMemoryDB.get("A")); // 6

        try {
            inMemoryDB.commit();
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            inMemoryDB.rollback();
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Get B (should be null): " + inMemoryDB.get("B")); // null

        inMemoryDB.beginTransaction();
        inMemoryDB.put("B", 10);

        inMemoryDB.rollback();

        System.out.println("Get B after rollback (should be null): " + inMemoryDB.get("B")); // null
    }
}
