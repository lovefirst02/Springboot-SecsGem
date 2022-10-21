package com.example.demo.service;

import com.example.demo.base.Result;
import com.example.demo.base.ResultResponse;
import com.example.demo.model.dao.TodoDao;
import com.example.demo.model.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class TodoService {
    @Autowired
    TodoDao tododao;

    public Result getTodos(){
        Result result;
        Iterable<Todo> todoList = tododao.findAll();
        result = ResultResponse.getSucessResult(todoList);
        return result;
//        return tododao.findAll();
    }

    public Result createTodo(Todo todo){
        Result result;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        String date = df.format(new Date());
        todo.setStatus(0);
        todo.setCreateTime(date);
        todo.setUpdateTime(date);
        tododao.save(todo);
        Iterable<Todo> todoList = tododao.findAll();
        result = ResultResponse.getSucessResult(todoList);
        return result;
    }

    public Result updateTodo(Integer id){
        Result result;
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
            String date = df.format(new Date());
            Todo resTodo = tododao.findById(id).get();
            Integer Status = resTodo.getStatus() == 0 ? 1 : 0;
            resTodo.setStatus(Status);
            resTodo.setUpdateTime(date);
            tododao.save(resTodo);
            Todo todoRes = tododao.findById(id).get();
            result = ResultResponse.getSucessResult(todoRes);
            return result;
        }catch (Exception exception){
            result = ResultResponse.getFailResult(exception.getMessage());
            return result;
        }
    }


    public Result deleteTodo(Integer id){
        Result result;
        try{
            Todo resTodo = tododao.findById(id).get();
            tododao.deleteById(id);
            result = ResultResponse.getSucessResult();
            return result;
        }catch (Exception exception){
            result = ResultResponse.getFailResult(exception.getMessage());
            return result;
        }
    }
}
