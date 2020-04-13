package com.ahoubouby.demo.dmain;
/*
 * ahoubouby created on 09/04/2020
 * E-MAIL: ahoubouby@gmail.com
 */

public class TodoBuilder {
    private static TodoBuilder instance = new TodoBuilder();
    private String id = null;
    private String description = "";
    private boolean completed = false;

    private TodoBuilder(){}

    public static TodoBuilder create() {
        return instance;
    }

    public TodoBuilder withDescription(String description){
        this.description = description;
        return instance;
    }

    public TodoBuilder withId(String id){
        this.id = id;
        return instance;
    }
    
    public TodoBuilder withCompleted (boolean isCompleted ){
        this.completed = isCompleted;
        return instance;
    }

    public Todo build(){
        Todo result = new Todo(this.description);
        result.setCompleted(completed);
        if(id != null)
            result.setId(id);
        return result;
    }
}
