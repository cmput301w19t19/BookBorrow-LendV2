package com.example.y.bookborrow_lendv2;

public class Login extends Lender {
    private String name;
    private String password;

    Lender lender1= new Lender();
    public Login(){
        this.name= lender1.getName();
        this.password= lender1.getPassword();

    }

}
