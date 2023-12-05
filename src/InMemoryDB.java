public interface InMemoryDB {

    // Interface for the in-memory database
    Integer get(String key);
    void put(String key, int value);
    void beginTransaction();
    void commit();
    void rollback();
}
