package com.vmoscalciuc.budget.service;

import com.vmoscalciuc.budget.model.Goal;
import com.vmoscalciuc.budget.model.Goal;
import com.vmoscalciuc.budget.model.Role;
import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.model.dto.GoalDto;
import com.vmoscalciuc.budget.model.dto.GoalDto;
import com.vmoscalciuc.budget.model.dto.ShowGoalDto;
import com.vmoscalciuc.budget.model.dto.UserDto;
import com.vmoscalciuc.budget.repository.impl.GoalRepositoryImpl;
import com.vmoscalciuc.budget.repository.impl.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GoalService {

    private final GoalRepositoryImpl goalRepositoryImpl;
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
    public Goal saveGoal(GoalDto goalDto) throws ParseException {
        Goal goal = new Goal();
        goal.setName(goalDto.getName());
        goal.setGoalDate(convertStringToDate(goalDto.getGoalDate()));
        goal.setAmount(goalDto.getAmount());
        goal.setInvestment(0.0);
        goal.setUser(userRepositoryImpl.findById(goalDto.getUserId()));
        goalRepositoryImpl.save(goal);
        return convertDtoToEntity(goalDto);
    }

    private GoalDto convertEntityToDto(Goal goal){
        GoalDto goalDto = new GoalDto();
        goalDto.setName(goal.getName());
        goalDto.setGoalDate(convertDateToString(goal.getGoalDate()));
        goalDto.setAmount(goal.getAmount());
        return goalDto;
    }

    private Goal convertDtoToEntity(GoalDto goalDto) throws ParseException {
        Goal goal = new Goal();
        goal.setName(goalDto.getName());
        goal.setGoalDate(convertStringToDate(goalDto.getGoalDate()));
        goal.setAmount(goalDto.getAmount());
        return goal;
    }


    public Optional<User> findByEmail(String email) {
        System.out.println("finding by email from userService");
        return userRepositoryImpl.findByEmail(email);
    }

    public Goal updateGoal(GoalDto newGoal, Long oldGoalId) throws ParseException {
        Goal goal = goalRepositoryImpl.findById(oldGoalId);
        goal.setName(newGoal.getName());
        goal.setGoalDate(convertStringToDate(newGoal.getGoalDate()));
//        if(newGoal.getAmount()>goal.getAmount()){
//            System.out.println("Updating using balance in goal service from if");
//            System.out.println("UserId= "+goal.getUser().getId());
//            System.out.println("Old goal"+goal.getAmount());
//            System.out.println("New Goal"+newGoal.getAmount());
//            userService.addToUserBalance(goal.getUser().getId(), (goal.getAmount()-newGoal.getAmount()));
//        }else{
//            System.out.println("Updating using balance in goal service from else");
//            userService.updateUserBalance(goal.getUser().getId(), newGoal.getAmount()-goal.getAmount());
//        }
        goal.setAmount(newGoal.getAmount());
        goalRepositoryImpl.update(goal);
        return convertUpdatedDtoToEntity(newGoal);
    }

    private Goal convertUpdatedDtoToEntity(GoalDto goalDto) throws ParseException {
        Goal goal = new Goal();
        goal.setId(goalDto.getId());
        goal.setName(goalDto.getName());
        goal.setGoalDate(convertStringToDate(goalDto.getGoalDate()));
        goal.setAmount(goalDto.getAmount());
        return goal;
    }

    public GoalDto findById(Long goalId){
        return convertEntityToDto(goalRepositoryImpl.findById(goalId));
    }

    public void deleteGoal(Long goalId){
        Goal goal = goalRepositoryImpl.findById(goalId);
        userService.addToUserBalance(goal.getUser().getId(), goal.getAmount());
        goalRepositoryImpl.delete(goalId);
    }

    public void addFundsToGoal(Long goalId,Double amount){
        Goal goal = goalRepositoryImpl.findById(goalId);
        userService.updateUserBalance(goal.getUser().getId(),amount);
        goalRepositoryImpl.updateGoalInvestment(goalId, amount);
    }

    public List<Goal> findAll() {
        return goalRepositoryImpl.findAll();
    }

    private ShowGoalDto convertShowEntityToDto(Goal goal){
        ShowGoalDto goalDto = new ShowGoalDto();
        goalDto.setName(goal.getName());
        goalDto.setDeadLine(convertDateToString(goal.getGoalDate()));
        goalDto.setPrice(goal.getAmount());
        return goalDto;
    }

    public ShowGoalDto showGoalDetails(Long goalId){
        Goal goal = goalRepositoryImpl.findById(goalId);
        Duration duration = Duration.between(new Date().toInstant(), goal.getGoalDate().toInstant());
        long differenceInDays = duration.toDays();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedDoneBy = decimalFormat.format(goal.getInvestment()/goal.getAmount()* 100);
        ShowGoalDto goalDto = new ShowGoalDto();
        goalDto.setId(goalId);
        goalDto.setName(goal.getName());
        goalDto.setPrice(goal.getAmount());
        goalDto.setDeadLine(convertDateToString(goal.getGoalDate()));
        goalDto.setTimeRemains(String.valueOf(differenceInDays));
        goalDto.setInvestment(goal.getInvestment());
        goalDto.setDoneBy(formattedDoneBy);
        return goalDto;
    }

//    public List<GoalDto> findAll() {
//        List<Goal> goals = goalRepositoryImpl.findAll();
//        return goals.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }


}
