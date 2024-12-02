package com.example.datn.services;



import com.example.datn.dto.Account.AccountDto;
import com.example.datn.dto.Account.ChangePasswordDto;
import com.example.datn.dto.Statistic.UserStatistic;
import com.example.datn.entities.Account;

import java.util.List;

public interface AccountService  {
    Account findByEmail(String email);

    List<Account> findAllAccount();
    Account save(Account account);

    List<UserStatistic> getUserStatistics(String startDate, String endDate);

    Account blockAccount(Long id);
    Integer countAllByRole_IdAndIsNonLockedTrue(Integer roleId);
    Account openAccount(Long id);

    Account changeRole(String email, Long roleId);

    AccountDto getAccountLogin();

    AccountDto updateProfile(AccountDto accountDto);

    void changePassword(ChangePasswordDto changePasswordDto);

    void resetPassword(Account account, String newPassword);
}
