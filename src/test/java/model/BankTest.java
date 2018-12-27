package model;

import org.junit.Test;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BankTest {

    @Test
    public void whenAddUserThenUserInBank() {
        Bank bank = new Bank();
        String passport = "BobsPassport";
        User user = new User("Bob", passport);

        bank.addUser(user);

        User findUser = bank.findUserByPassport(passport);

        assertThat(findUser, is(user));
    }

    @Test
    public void whenAddTwoUsersTheTwoUsersInBank() {
        Bank bank = new Bank();
        bank.addUser(new User("Bob", "BobPassport"));
        bank.addUser(new User("Steve", "StevePassport"));

        Set<User> usersOfBank = bank.getUsers();

        assertThat(usersOfBank.size(), is(2));
    }

    @Test
    public void whenAddTwoUsersWithSameNameButDiffPassportTheTwoUsersInBank() {
        Bank bank = new Bank();
        User user1 = new User("Bob", "BobsPassport");
        User user2 = new User(user1.getName(), "BobsPassport2");

        bank.addUser(user1);
        bank.addUser(user2);

        Set<User> usersOfBank = bank.getUsers();

        assertThat(usersOfBank.size(), is(2));
    }

    @Test
    public void whenAddUserTwiceThenOnlyOneUserInBank() {
        Bank bank = new Bank();
        String passport = "BobsPassport";
        User user = new User("Bob", passport);

        bank.addUser(user);
        bank.addUser(user);

        Set<User> usersOfBank = bank.getUsers();

        assertThat(usersOfBank.size(), is(1));
    }

    @Test
    public void whenDeleteUserThenUserNotInBak() {
        Bank bank = new Bank();
        String passport = "bobsPassport";
        User user = new User("Bob", passport);

        bank.addUser(user);
        bank.deleteUser(user);

        User findUser = bank.findUserByPassport(passport);

        assertThat(findUser == null, is(true));
    }

    @Test
    public void whenAddAccountThenUserHaveAccount() {
        Bank bank = new Bank();
        String passport = "BobsPassport";
        User user = new User("Bob", passport);
        bank.addUser(user);
        Account account = new Account("BobsAccount", 10);

        bank.addAccountToUser(passport, account);

        Optional<List<Account>> usersAccount = bank.getUserAccounts(passport);
        assert usersAccount.isPresent();
        if (usersAccount.isPresent()) {
            assertThat(usersAccount.get().contains(account), is(true));
        }

    }

    @Test
    public void whenAddTwoAccountThenUserHaveTwoAccount() {
        Bank bank = new Bank();
        String passport = "BobsPassport";
        User user = new User("Bob", passport);
        bank.addUser(user);
        Account account1 = new Account("BobsAccount1", 10);
        Account account2 = new Account("BobsAccount2", 10);

        bank.addAccountToUser(passport, account1);
        bank.addAccountToUser(passport, account2);

        assertThat(bank.getUserAccounts(passport).get().size(), is(2));
    }

    @Test
    public void whenAddAccountTwiceThenUserHaveOnlyOneAccount() {
        Bank bank = new Bank();
        String passport = "BobsPassport";
        User user = new User("Bob", passport);
        bank.addUser(user);
        Account account1 = new Account("BobsAccount1", 10);

        bank.addAccountToUser(passport, account1);
        bank.addAccountToUser(passport, account1);

        assertThat(bank.getUserAccounts(passport).get().size(), is(1));
    }

    @Test
    public void whenDeleteAccountThenUserHaveNotAccount() {
        Bank bank = new Bank();
        String passport = "BobsPassport";
        User user = new User("Bob", passport);
        bank.addUser(user);
        Account account = new Account("BobsAccount", 10);

        bank.addAccountToUser(passport, account);
        bank.deleteAccountFromUser(passport, account);

        assertThat(bank.getUserAccounts(passport).get().contains(account), is(false));
    }


    @Test
    public void whenTransferMoneyThenDistAccountHaveThem() {
        Bank bank = new Bank();

        String srcPassport = "BobPassport";
        String srcRequisite = "BobAccount";
        User userBob = new User("Bob", srcPassport);
        bank.addUser(userBob);
        bank.addAccountToUser(srcPassport, new Account(srcRequisite, 10));

        String distPassport = "StevePassport";
        String distRequisite = "SteveAccount";
        User userSteve = new User("Steve", distPassport);
        bank.addUser(userSteve);
        bank.addAccountToUser(distPassport, new Account(distRequisite, 0));

        boolean result = bank.transferMoney(srcPassport, srcRequisite, distPassport, distRequisite, 10);
        assertThat(result, is(true));

        Account steveAccount = bank.getUserAccounts(distPassport).get().get(0);
        assertThat(steveAccount.getValue(), is(10.0d));



    }

    @Test
    public void whenTransferMoneyMoreThanAccountHaveThenFalse() {
        Bank bank = new Bank();

        String srcPassport = "BobPassport";
        String srcRequisite = "BobAccount";
        User userBob = new User("Bob", srcPassport);
        bank.addUser(userBob);
        bank.addAccountToUser(srcPassport, new Account(srcRequisite, 10));

        String distPassport = "StevePassport";
        String distRequisite = "SteveAccount";
        User userSteve = new User("Steve", distPassport);
        bank.addUser(userSteve);
        bank.addAccountToUser(distPassport, new Account(distRequisite, 0));

        boolean result = bank.transferMoney(srcPassport, srcRequisite, distPassport, distRequisite, 15);
        assertThat(result, is(false));

    }
}