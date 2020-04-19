/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package role;

/**
 *
 * @author User
 */
public abstract class role {

    protected int level;

    protected int[] levelUpSet;
    protected int exp;
    protected String symbol;
    protected int[] cd;

    public role(int level, int exp, String symbol, int[] levelUpSet) {
        //傳入部分
        this.level = level;
        this.exp = exp;
        this.symbol = symbol;
        this.levelUpSet = levelUpSet;
    }

    public boolean addExp(int exp) {
        this.exp += exp;
        if (exp >= accumuExp(level)) {
            this.level++;
            return true; //upgrade
        }
        return false; //not upgrade
    }

    private int accumuExp(int level) {
        int accumuExp = 0;
        for (int i = 0; i < level; i++) {
            accumuExp += levelUpSet[i];
        }
        return accumuExp;
    }

    //強制撰寫技能
    public abstract boolean skill(int cd);

}
