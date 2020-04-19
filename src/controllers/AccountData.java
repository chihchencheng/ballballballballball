/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import role.role;

/**
 *
 * @author User
 */
public class AccountData {

    private int money;
    private int[] historyScore;
    private ArrayList<role> roleDatas;

    private static final AccountData account=new AccountData();

    private AccountData() {
        historyScore = new int[10];   //上限紀錄十筆資料
        roleDatas = new ArrayList<>();
        
    }

    public static AccountData getInstance() {
        return account;

    }

}
