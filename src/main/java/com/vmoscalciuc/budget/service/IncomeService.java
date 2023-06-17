package com.vmoscalciuc.budget.service;

import com.vmoscalciuc.budget.model.Income;
import com.vmoscalciuc.budget.model.Income;
import com.vmoscalciuc.budget.model.dto.IncomeDto;
import com.vmoscalciuc.budget.repository.impl.IncomeRepositoryImpl;
import com.vmoscalciuc.budget.repository.impl.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class IncomeService {
    private final IncomeRepositoryImpl incomeRepositoryImpl;

    private final UserRepositoryImpl userRepositoryImpl;
    private final UserService userService;

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

    public Income saveIncome(IncomeDto incomeDto) throws ParseException {
        Income income = new Income();
        income.setName(incomeDto.getName());
        income.setIncomeDate(convertStringToDate(incomeDto.getIncomeDate()));
        income.setAmount(incomeDto.getAmount());
        income.setUser(userRepositoryImpl.findById(incomeDto.getUserId()));
        incomeRepositoryImpl.save(income);
        return convertDtoToEntity(incomeDto);
    }

    public Income updateIncome(IncomeDto newIncome,Long oldIncomeId) throws ParseException {
        Income income = incomeRepositoryImpl.findById(oldIncomeId);
        income.setName(newIncome.getName());
        income.setIncomeDate(convertStringToDate(newIncome.getIncomeDate()));
        if(newIncome.getAmount()>income.getAmount()){
            System.out.println("Updating using balance in income service from if");
            System.out.println("UserId= "+income.getUser().getId());
            System.out.println("Old income"+income.getAmount());
            System.out.println("New Income"+newIncome.getAmount());
            userService.addToUserBalance(income.getUser().getId(),newIncome.getAmount()-income.getAmount() );
        }else{
            System.out.println("Updating using balance in income service from else");
            userService.updateUserBalance(income.getUser().getId(), (income.getAmount()-newIncome.getAmount()));
        }
        income.setAmount(newIncome.getAmount());
        incomeRepositoryImpl.update(income);
        return convertUpdatedDtoToEntity(newIncome);
    }

    public IncomeDto findById(Long incomeId){
        return convertEntityToDto(incomeRepositoryImpl.findById(incomeId));
    }

    private IncomeDto convertEntityToDto(Income income){
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setName(income.getName());
        incomeDto.setIncomeDate(convertDateToString(income.getIncomeDate()));
        incomeDto.setAmount(income.getAmount());
        return incomeDto;
    }

    private Income convertDtoToEntity(IncomeDto incomeDto) throws ParseException {
        Income income = new Income();
        income.setName(incomeDto.getName());
        income.setIncomeDate(convertStringToDate(incomeDto.getIncomeDate()));
        income.setAmount(incomeDto.getAmount());
        return income;
    }

    private Income convertUpdatedDtoToEntity(IncomeDto incomeDto) throws ParseException {
        Income income = new Income();
        income.setId(incomeDto.getId());
        income.setName(incomeDto.getName());
        income.setIncomeDate(convertStringToDate(incomeDto.getIncomeDate()));
        income.setAmount(incomeDto.getAmount());
        return income;
    }

    public void deleteIncome(Long incomeId){
        Income income = incomeRepositoryImpl.findById(incomeId);
        userService.addToUserBalance(income.getUser().getId(), income.getAmount());
        incomeRepositoryImpl.delete(incomeId);
    }

    public List<Income> findAll() {
        return incomeRepositoryImpl.findAll();
    }
}
