package com.example.y.bookborrow_lendv2;

public class Login extends lender {
    private String name;
    private String password;

    lender lender1= new lender();
    public Login(){
        this.name= lender1.getName();
        this.password= lender1.getPassword();

    }

}
