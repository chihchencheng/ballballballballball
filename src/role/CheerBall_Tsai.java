/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package role;

import util.Global;

/**
 *
 * @author User
 */
public class CheerBall_Tsai extends role {
    public static final int[] CHEERBALL_LEVEL_SET={0,100,300,300,800,600,700,700,900,1000,2000};
    public static final int[] CHEERBALL_CD={16,16,16,16,14,14,14,14,14,12};
    
    public CheerBall_Tsai(int level, int exp) {
        super(level, exp, "CheerBall_Tsai", CHEERBALL_LEVEL_SET);
    }

    @Override
    public boolean skill(int cd) {
        if(cd==this.cd[level-1]){
            return true;
        }
        
        return false;
    }

}
