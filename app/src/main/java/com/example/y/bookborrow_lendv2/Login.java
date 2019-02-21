package com.example.y.bookborrow_lendv2;

public class Login {
    protected String username;
    protected String passwords;

    public Login (String user_name, String user_passwords ){
        this.username= user_name;
        this.passwords=user_passwords;
    }

    public void setUsername(String text){
        this.username=text;
    }

    public String getUsername(){
        return this.username;
    }
    public String getPasswords(){
        return this.passwords;
    }
    public void setPasswords (String passwords){
        this.passwords= passwords;
    }

}
