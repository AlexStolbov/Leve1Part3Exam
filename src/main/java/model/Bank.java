package model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Bank where clients save their money.
 */
public class Bank {

    /**
     * Map that mapped accounts to clients.
     */
    private final Map<User, List<Account>> userAccounts;

    /**
     * Constructor.
     */
    public Bank() {
        userAccounts = new TreeMap<>();
    }

    /**
     * Add one client to bank.
     * @param user client.
     */
    public void addUser(User user) {
        userAccounts.put(user, new LinkedList<>());
    }

    /**
     * Delete client from bank.
     * @param user client.
     */
    public void deleteUser(User user) {
        userAccounts.remove(user);
    }

    /**
     * Add one account to client.
     * @param passport passport of client.
     * @param account account that we add.
     */
    public void addAccountToUser(String passport, Account account) {
        User findUser = findUserByPassport(passport);
        if (findUser != null) {
            if (!getUserAccounts(passport).contains(account)) {
                userAccounts.get(findUser).add(account);
            }
        }
    }

    /**
     * Delete one client's account.
     * @param passport passport of client
     * @param account account that we deleted
     */
    public void deleteAccountFromUser(String passport, Account account) {
        User findUser = findUserByPassport(passport);
        if (findUser != null) {
            userAccounts.get(findUser).remove(account);
        }
    }

    /**
     * Get all clients of bank.
     * @return - Set of clients.
     */
    public Set<User> getUsers() {
        return userAccounts.keySet();
    }

    /**
     * Get all accounts for client
     * @param passport client's passport
     * @return all client's accounts
     */
    public List<Account> getUserAccounts(String passport) {

        List<Account> result;

        User findUser = findUserByPassport(passport);
        if (findUser != null) {
            result = userAccounts.get(findUser);
        } else {
            result = new LinkedList<>();
        }

        return result;
    }

    /**
     * Transfer money between two accounts.
     * If no accounts are found, return false.
     * If the account does not have enough money, return false.
     * @param srcPassport Passport of the client transferring the money.
     * @param srcRequisite Requisite of the account transferring the money.
     * @param destPassport Passport of the client receiving the money.
     * @param destRequisite Requisite of the account transferring the money.
     * @param amount Amount of money to be transferred.
     * @return true if transfer successful.
     */
    public boolean transferMoney(String srcPassport, String srcRequisite, String destPassport, String destRequisite, double amount) {

        boolean result = false;

        User srcUser = findUserByPassport(srcPassport);
        User destUser = findUserByPassport(destPassport);
        if (srcUser != null && destUser != null) {

            Account srcAccount = findAccountByRequisite(srcUser, srcRequisite);
            Account destAccount = findAccountByRequisite(destUser, destRequisite);
            if (srcAccount != null && destAccount != null) {

                boolean resultOfWithdraw = withdrawValueFromAccount(srcAccount, amount);
                if (resultOfWithdraw) {
                    addValueToAccount(destAccount, amount);
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * Find client by passport.
     * @param passportFind clients's passport
     * @return client
     */
    public User findUserByPassport(String passportFind) {
//        for (User currUser : userAccounts.keySet()) {
//            if (currUser.getPassport().equals(passportFind)) {
//                return currUser;
//            }
//        }
//        return null;
        return userAccounts.keySet().stream().filter(us -> us.getPassport().equals(passportFind)).findAny().orElse(null);
    }

    //
    private Account findAccountByRequisite(User user, String requisiteFind) {
//        Account result = null;
//        for (Account currAccount : userAccounts.get(user)) {
//            if (currAccount.getRequisites().equals(requisiteFind)) {
//                result = currAccount;
//            }
//        }
//        return result;
        return userAccounts.get(user).stream().filter(acc -> acc.getRequisites().equals(requisiteFind)).findAny().orElse(null);
    }

    /**
     * Add value to account.
     * @param account account
     * @param value value
     */
    private void addValueToAccount(Account account, double value) {
        account.setValue(account.getValue() + value);
    }

    /**
     * Withdraws money from the account.
     * @param account account
     * @param valueToWithdraw value to withdraw
     * @return true if successful
     */
    private boolean withdrawValueFromAccount(Account account, double valueToWithdraw) {
        boolean result;
        if (valueToWithdraw > account.getValue()) {
            result = false;
        } else {
            account.setValue(account.getValue() - valueToWithdraw);
            result = true;
        }
        return result;
    }

}
