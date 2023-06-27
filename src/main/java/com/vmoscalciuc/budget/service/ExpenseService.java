package com.vmoscalciuc.budget.service;

import com.vmoscalciuc.budget.model.Expense;
import com.vmoscalciuc.budget.model.Goal;
import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.model.dto.ExpenseDto;
import com.vmoscalciuc.budget.model.dto.GoalDto;
import com.vmoscalciuc.budget.repository.impl.ExpenseRepositoryImpl;
import com.vmoscalciuc.budget.repository.impl.GoalRepositoryImpl;
import com.vmoscalciuc.budget.repository.impl.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final ExpenseRepositoryImpl expenseRepositoryImpl;

    private final UserRepositoryImpl userRepositoryImpl;
    private final UserService userService;

    public Expense saveExpense(ExpenseDto expenseDto) throws ParseException {
        Expense expense = new Expense();
        expense.setName(expenseDto.getName());
        expense.setExpenseDate(convertStringToDate(expenseDto.getExpenseDate()));
        expense.setAmount(expenseDto.getAmount());
        expense.setUser(userRepositoryImpl.findById(expenseDto.getUserId()));
        expenseRepositoryImpl.save(expense);
        return convertDtoToEntity(expenseDto);
    }

    public Date convertStringToDate(String convertedDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(convertedDate);
        return date;
    }
    public String convertDateToString(Date date){
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = dateFormat.format(date);
    return dateString;
    }



    public Expense updateExpense(ExpenseDto newExpense,Long oldExpenseId) throws ParseException {
        Expense expense = expenseRepositoryImpl.findById(oldExpenseId);
        expense.setName(newExpense.getName());
        expense.setExpenseDate(convertStringToDate(newExpense.getExpenseDate()));
        if(newExpense.getAmount()>expense.getAmount()){
            System.out.println("Updating using balance in expense service from if");
            System.out.println("UserId= "+expense.getUser().getId());
            System.out.println("Old expense"+expense.getAmount());
            System.out.println("New Expense"+newExpense.getAmount());
            userService.addToUserBalance(expense.getUser().getId(), (expense.getAmount()-newExpense.getAmount()));
        }else{
            System.out.println("Updating using balance in expense service from else");
            userService.updateUserBalance(expense.getUser().getId(), newExpense.getAmount()-expense.getAmount());
        }
        expense.setAmount(newExpense.getAmount());
        expenseRepositoryImpl.update(expense);
        return convertUpdatedDtoToEntity(newExpense);
    }

    public ExpenseDto findById(Long expenseId){
        return convertEntityToDto(expenseRepositoryImpl.findById(expenseId));
    }

    private ExpenseDto convertEntityToDto(Expense expense){
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setName(expense.getName());
        expenseDto.setExpenseDate(convertDateToString(expense.getExpenseDate()));
        expenseDto.setAmount(expense.getAmount());
        return expenseDto;
    }

    private Expense convertDtoToEntity(ExpenseDto expenseDto) throws ParseException {
        Expense expense = new Expense();
        expense.setName(expenseDto.getName());
        expense.setExpenseDate(convertStringToDate(expenseDto.getExpenseDate()));
        expense.setAmount(expenseDto.getAmount());
        return expense;
    }

    private Expense convertUpdatedDtoToEntity(ExpenseDto expenseDto) throws ParseException {
        Expense expense = new Expense();
        expense.setId(expenseDto.getId());
        expense.setName(expenseDto.getName());
        expense.setExpenseDate(convertStringToDate(expenseDto.getExpenseDate()));
        expense.setAmount(expenseDto.getAmount());
        return expense;
    }

    public void deleteExpense(Long expenseId){
        Expense expense = expenseRepositoryImpl.findById(expenseId);
        userService.addToUserBalance(expense.getUser().getId(), expense.getAmount());
        expenseRepositoryImpl.delete(expenseId);
    }

    public List<Expense> findAll(Long userId) {
        return expenseRepositoryImpl.findAll(userId);
    }

}
