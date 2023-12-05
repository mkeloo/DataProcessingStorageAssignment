import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MemoryDB implements InMemoryDB {
    private final Map<String, Integer> database = new HashMap<>();
    private final Stack<Map<String, Integer>> transactionStack;
    private boolean isInTransaction;


    public MemoryDB() {
        this.transactionStack = new Stack<>();
        this.isInTransaction = false;
    }

    // get(key) will return the value associated with the key or null if the key does not exist.
    @Override
    public Integer get(String key) {
        return database.get(key);
    }

    // put(key, val) will create a new key with the provided value if a key does not exist.
    // Otherwise, it will update the value of an existing key.
    // If put(key, val) is called when a transaction is not in progress throw an exception
    @Override
    public void put(String key, int value) {
        if (!isInTransaction) {
            throw new IllegalStateException("No transaction in progress");
        }
        transactionStack.peek().put(key, value);
    }


    // beginTransaction() will start a new transaction.
    @Override
    public void beginTransaction() {
        transactionStack.push(new HashMap<>());
        isInTransaction = true;
    }


    // A transaction ends when either commit() or rollback() is called


    // commit() will commit the changes to the database.
    // commit() applies changes made within the transaction to the main state.
    // Allowing any future gets() to “see” the changes made within the transaction
    @Override
    public void commit() {
        if (!isInTransaction) {
            throw new IllegalStateException("No transaction to commit");
        }
        Map<String, Integer> currentTransaction = transactionStack.pop();
        database.putAll(currentTransaction);
        isInTransaction = !transactionStack.isEmpty();
    }

    // rollback() should abort all the changes made within the transaction
    // and everything should go back to the way it was before.
    @Override
    public void rollback() {
        if (!isInTransaction) {
            throw new IllegalStateException("No transaction to rollback");
        }
        transactionStack.pop();
        isInTransaction = !transactionStack.isEmpty();
    }
}
