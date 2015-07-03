package jieyun.jjlink.com.jieyunclass;

import android.content.Context;
import android.graphics.Color;
import android.widget.TableRow;

/**
 * Created by zlu on 15-7-3.
 */
public class JieyunRow extends TableRow {
    public JieyunRow(Context context) {
        super(context);
        setPadding(1,1,1,1);
        setBackgroundColor(Color.GRAY);
    }
}
