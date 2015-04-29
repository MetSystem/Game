package edu.cgxy.tetris;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.cgxy.tetris.menu.GenericMenu;

/**
 * Created by zhouyang3 on 2015/4/22.
 */
public class TetrisActiviy extends Activity {
    TetrisView gV;
    GenericMenu gM;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gV = new TetrisView(this);
        setContentView(gV);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        gV.setGameFocus(hasFocus);
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        gM = new GenericMenu(menu);
        gM.populate();
        // TODO Auto-generated method stub
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if(item.getTitle().equals("Restart"))
            gV.restartGame();
        if(item.getTitle().equals("Quit"))
            finish();//just close
        return super.onMenuItemSelected(featureId, item);
    }
}
